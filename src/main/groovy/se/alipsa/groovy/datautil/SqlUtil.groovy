package se.alipsa.groovy.datautil

import groovy.sql.Sql
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

import java.lang.reflect.InvocationTargetException
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
 * def sql = SqlUtil.newInstance("jdbc:postgresql://localhost:5432/foo", "bar", "baz", 'org.postgresql.Driver', this)
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
 */
class SqlUtil {

    /**
     * Wrapper for the groovy.sql.Sql.withInstance() static method
     * @param url the db url
     * @param user the db user name
     * @param password the db password
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling class (i.e. the class which executed Grab, usually this)
     * @param c the closure expression
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static void withInstance(String url, String user, String password, String driverClassName, Object caller, @ClosureParams(value= SimpleType.class, options="groovy.sql.Sql") Closure c) throws SQLException, ClassNotFoundException {
        try {
            Driver driver = driver(driverClassName, caller);
            def sql = newInstance(url, user, password, driver) as Sql
            c.call(sql);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ClassNotFoundException(e.getMessage());
        }
    }

    /**
     * Wrapper for the groovy.sql.Sql.newInstance() static method that creates an instance of {@link groovy.sql.Sql}
     * @param url the db url
     * @param user the db user name
     * @param password the db password
     * @param driverClassName the fully qualified name of the Driver class
     * @param caller the calling class (i.e. the class which executed Grab, usually this)
     * @return and instance of {@link groovy.sql.Sql}
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static Sql newInstance(String url, String user, String password, String driverClassName, Object caller) throws SQLException, ClassNotFoundException {
        try {
            Driver driver = driver(driverClassName, caller);
            return newInstance(url, user, password, driver)
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ClassNotFoundException(e.getMessage());
        }
    }

    /**
     *
     * @param url the db url
     * @param user the db user name
     * @param password the db password
     * @param driver an instance of the db Driver to use
     * @return and instance of {@link groovy.sql.Sql}
     * @throws SQLException if a db issue eg. a connection issue occurred
     * @throws ClassNotFoundException if loading the driver was unsuccessful
     */
    static Sql newInstance(String url, String user, String password, Driver driver) throws SQLException, ClassNotFoundException {
        try {
            var props = new Properties()
            props.setProperty("user", user)
            props.setProperty("password", password)
            return new Sql(driver.connect(url, props));
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
     * @param caller the calling class (i.e. the class which executed Grab, usually this)
     * @return An instance of the Driver class specified
     * @throws ClassNotFoundException if the class cannot be found from any of the classloaders
     * @throws NoSuchMethodException if a non empty constuctor does not exist
     * @throws InvocationTargetException if a new instance cannot be crated
     * @throws InstantiationException if a new instance cannot be crated
     * @throws IllegalAccessException if a new instance cannot be crated
     */
    static Driver driver(String driverClassName, Object caller) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        try {
            return (Driver)caller.getClass().getClassLoader().loadClass(driverClassName).getDeclaredConstructor().newInstance();
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
}
