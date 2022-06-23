package se.alipsa.groovy.datautil

import groovy.sql.Sql
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import org.codehaus.groovy.reflection.ReflectionUtils

import java.lang.reflect.InvocationTargetException
import java.sql.Connection
import java.sql.Driver
import java.sql.SQLException

/**
 * using grab to connect to a database involves grabbing using the System Classloader which in many cases is problematic.
 * This works around the problem so that grab can be used "normally". e.g:
 * <code><pre>
 * @Grab('se.alipsa.groovy:data-utils:1.0-SNAPSHOT')
 * @Grab('org.postgresql:postgresql:42.4.0')
 *
 * import se.alipsa.groovy.datautil.SqlUtil
 *
 * def sql = SqlUtil.newInstance("jdbc:postgresql://localhost:5432/foo", "bar", "baz", 'org.postgresql.Driver')
 * def idList = new ArrayList()
 * try {
 *   sql.eachRow("SELECT id FROM projects") {
 *     idList.add(it.getLong(1))
 *   }
 * } finally {
 *   sql.close()
 * }
 * println("got ${idList.size()} ids")
 * </pre>
 * </code>
 *
 * In the above example we make an educated guess on what the calling class is. If that does not work for some reason
 * you can pass the instance of the class/script that called Grab e.g
 * <code><pre>
 *   def sql = SqlUtil.newInstance("jdbc:postgresql://localhost:5432/foo", "bar", "baz", 'org.postgresql.Driver', this)
 * </pre></code>
 */
class SqlUtil {

    /**
     * Replacement for the groovy.sql.Sql.withInstance() static method
     *
     * @param url the db url
     * @param user the db user name
     * @param password the db password
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling class (i.e. the class which executed Grab, usually this.getClass())
     * @param c the closure expression
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static void withInstance(String url, String user, String password, String driverClassName, Class caller, @ClosureParams(value= SimpleType.class, options="groovy.sql.Sql") Closure c) throws SQLException, ClassNotFoundException {
        def sql = null
        try {
            sql = newInstance(url, user, password, driverClassName, caller) as Sql
            c.call(sql)
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ClassNotFoundException(e.getMessage())
        } finally {
            if (sql != null) {
                sql.close()
            }
        }
    }

    /**
     * Replacement for the groovy.sql.Sql.withInstance() static method
     *
     * @param url the db url
     * @param user the db user name
     * @param password the db password
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling instance (i.e. the object which executed Grab, usually this)
     * @param c the closure expression
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static void withInstance(String url, String user, String password, String driverClassName, Object caller, @ClosureParams(value= SimpleType.class, options="groovy.sql.Sql") Closure c) throws SQLException, ClassNotFoundException {
        withInstance(url, user, password, driverClassName, caller.getClass(), c)
    }

    /**
     * Replacement for the groovy.sql.Sql.withInstance() static method
     *
     * @param url the db url
     * @param user the db user name
     * @param password the db password
     * @param driverClassName the fully qualified name of the Driver class
     * @param c the closure expression
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static void withInstance(String url, String user, String password, String driverClassName, @ClosureParams(value= SimpleType.class, options="groovy.sql.Sql") Closure c) throws SQLException, ClassNotFoundException {
        withInstance(url, user, password, driverClassName, getCallingClass(), c);
    }

    /**
     * Replacement for the groovy.sql.Sql.withInstance() static method
     *
     * @param url the db url, should normally contain username and password information
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling class (i.e. the class which executed Grab, usually this.getClass())
     * @param c the closure expression
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static void withInstance(String url, String driverClassName, Class caller, @ClosureParams(value= SimpleType.class, options="groovy.sql.Sql") Closure c) throws SQLException, ClassNotFoundException {
        def sql = null
        try {
            sql = newInstance(url, driverClassName, caller) as Sql
            c.call(sql)
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ClassNotFoundException(e.getMessage());
        } finally {
            if (sql != null) {
                sql.close()
            }
        }
    }

    /**
     * Replacement for the groovy.sql.Sql.withInstance() static method
     *
     * @param url the db url, should normally contain username and password information
     * @param driverClassName the fully qualified name of the Driver class
     * @param c the closure expression
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static void withInstance(String url, String driverClassName, @ClosureParams(value= SimpleType.class, options="groovy.sql.Sql") Closure c) throws SQLException, ClassNotFoundException {
        withInstance(url, driverClassName, getCallingClass(), c)
    }

    /**
     * Replacement for the groovy.sql.Sql.withInstance() static method
     *
     * @param url the db url, should normally contain username and password information
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling instance (i.e. the object which executed Grab, usually this)
     * @param c the closure expression
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static void withInstance(String url, String driverClassName, Object caller, @ClosureParams(value= SimpleType.class, options="groovy.sql.Sql") Closure c) throws SQLException, ClassNotFoundException {
        withInstance(url, driverClassName, caller.getClass(), c)
    }

    /**
     * Replacement for the groovy.sql.Sql.newInstance() static method that creates an instance of {@link groovy.sql.Sql}
     *
     * @param url the db url
     * @param user the db user name
     * @param password the db password
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling class (i.e. the class which executed Grab, usually this.getClass())
     * @return an instance of {@link groovy.sql.Sql}
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static Sql newInstance(String url, String user, String password, String driverClassName, Class caller) throws SQLException, ClassNotFoundException {
        try {
            Driver driver = driver(driverClassName, caller);
            return newInstance(url, user, password, driver)
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ClassNotFoundException(e.getMessage());
        }
    }

    /**
     * Replacement for the groovy.sql.Sql.newInstance() static method that creates an instance of {@link groovy.sql.Sql}
     *
     * @param url the db url
     * @param user the db user name
     * @param password the db password
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling instance (i.e. the object which executed Grab, usually this)
     * @return an instance of {@link groovy.sql.Sql}
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static Sql newInstance(String url, String user, String password, String driverClassName, Object caller) throws SQLException, ClassNotFoundException {
        return newInstance(url, user, password, driverClassName, caller.getClass())
    }

    /**
     * Replacement for the groovy.sql.Sql.newInstance() static method that creates an instance of {@link groovy.sql.Sql}
     *
     * @param url the db url
     * @param user the db user name
     * @param password the db password
     * @param driverClassName the fully qualified name of the Driver class
     * @return an instance of {@link groovy.sql.Sql}
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static Sql newInstance(String url, String user, String password, String driverClassName) throws SQLException, ClassNotFoundException {
        return newInstance(url, user, password, driverClassName, getCallingClass())
    }

    /**
     * Replacement for the groovy.sql.Sql.newInstance() static method that creates an instance of {@link groovy.sql.Sql}
     *
     * @param url the db url
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling class (i.e. the class which executed Grab, usually this.getClass())
     * @return an instance of {@link groovy.sql.Sql}
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static Sql newInstance(String url, String driverClassName, Class caller) throws SQLException, ClassNotFoundException {
        return newInstance(url, null, null, driverClassName, caller);
    }

    /**
     * Replacement for the groovy.sql.Sql.newInstance() static method that creates an instance of {@link groovy.sql.Sql}
     *
     * @param url the db url
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling instance (i.e. the object which executed Grab, usually this)
     * @return an instance of {@link groovy.sql.Sql}
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static Sql newInstance(String url, String driverClassName, Object caller) throws SQLException, ClassNotFoundException {
        return newInstance(url, null, null, driverClassName, caller.getClass());
    }

    /**
     * Replacement for the groovy.sql.Sql.newInstance() static method that creates an instance of {@link groovy.sql.Sql}
     *
     * @param url the db url
     * @param driverClassName the fully qualified name of the Driver class
     * @return an instance of {@link groovy.sql.Sql}
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static Sql newInstance(String url, String driverClassName) throws SQLException, ClassNotFoundException {
        return newInstance(url, driverClassName, getCallingClass());
    }

    /**
     * Replacement for the groovy.sql.Sql.newInstance() static method that creates an instance of {@link groovy.sql.Sql}
     *
     * @param url the db url
     * @param user the db user name, may be null
     * @param password the db password, may be null
     * @param driver an instance of the db Driver to use
     * @return and instance of {@link groovy.sql.Sql}
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static Sql newInstance(String url, String user, String password, Driver driver) throws SQLException, ClassNotFoundException {
        try {
            var props = new Properties()
            if (user != null) {
                props.setProperty("user", user)
            }
            if (password != null) {
                props.setProperty("password", password)
            }
            Connection con = driver.connect(url, props)
            return new Sql(con)
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ClassNotFoundException(e.getMessage());
        }
    }

    /**
     * Loads a database driver using the classloaders in the following order
     * <ol>
     *     <li>1. The classloader for the caller</li>
     *     <li>2. The context classloader</li>
     *     <li>3. The classloader for this (SqlUtil) class
     *     <li>4. Class.forName</li>
     * </ol>
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling class (i.e. the class which executed Grab, usually this.getClass())
     * @return An instance of the Driver class specified
     * @throws ClassNotFoundException if the class cannot be found from any of the classloaders
     * @throws NoSuchMethodException if a non empty constuctor does not exist
     * @throws InvocationTargetException if a new instance cannot be created
     * @throws InstantiationException if a new instance cannot be created
     * @throws IllegalAccessException if a new instance cannot be created
     */
    static Driver driver(String driverClassName, Class caller) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        try {
            return (Driver)caller.getClassLoader().loadClass(driverClassName).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException ignored) {
            try {
                return (Driver) Thread.currentThread().getContextClassLoader().loadClass(driverClassName).getDeclaredConstructor().newInstance()
            } catch(ClassNotFoundException e2) {
                try {
                    return (Driver)SqlUtil.class.getClassLoader().loadClass(driverClassName).getDeclaredConstructor().newInstance()
                } catch (ClassNotFoundException e3) {
                    return (Driver)Class.forName(driverClassName).getDeclaredConstructor().newInstance();
                }
            }
        }
    }

    /**
     * Loads a database driver using the classloaders in the following order
     * <ol>
     *     <li>1. The classloader for the caller</li>
     *     <li>2. The context classloader</li>
     *     <li>3. The classloader for this (SqlUtil) class
     *     <li>4. Class.forName</li>
     * </ol>
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling instance (i.e. the object which executed Grab, usually this)
     * @return An instance of the Driver class specified
     * @throws ClassNotFoundException if the class cannot be found from any of the classloaders
     * @throws NoSuchMethodException if a non empty constuctor does not exist
     * @throws InvocationTargetException if a new instance cannot be created
     * @throws InstantiationException if a new instance cannot be created
     * @throws IllegalAccessException if a new instance cannot be created
     */
    static Driver driver(String driverClassName, Object caller) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return driver(driverClassName, caller.getClass())
    }

    private static Class getCallingClass() {
        /*
        println("Call stack")
        for (StackTraceElement se : Thread.currentThread().getStackTrace()) {
            println(se.getClassName())
        }
        println("Call stack StackTraceElement[6] = " + Thread.currentThread().getStackTrace()[6].getClassName())
        println("Call stack ReflectionUtils(2) = " + ReflectionUtils.getCallingClass(2))
         */
        return ReflectionUtils.getCallingClass(2)
    }
}
