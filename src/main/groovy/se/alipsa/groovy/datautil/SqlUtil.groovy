package se.alipsa.groovy.datautil

import groovy.sql.Sql
import groovy.transform.CompileStatic
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
 * @Grab ('se.alipsa.groovy:data-utils:2.0.0')
 * @Grab ('org.postgresql:postgresql:42.4.0')
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
@CompileStatic
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
  static void withInstance(String url, String user, String password, String driverClassName, Class caller, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
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

  static void withInstance(String url, String user, String password, Class caller, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    withInstance(url, user, password, getDriverClassName(url), caller, c)
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
  static void withInstance(String url, String user, String password, String driverClassName, Object caller, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    withInstance(url, user, password, driverClassName, caller.getClass(), c)
  }

  static void withInstance(String url, String user, String password, Object caller, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    withInstance(url, user, password, getDriverClassName(url), caller.getClass(), c)
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
  static void withInstance(String url, String user, String password, String driverClassName, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    withInstance(url, user, password, driverClassName, getCallingClass(), c)
  }

  static void withInstance(String url, String user, String password, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    withInstance(url, user, password, getDriverClassName(url), getCallingClass(), c)
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
  static void withInstance(String url, String driverClassName, Class caller, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    def sql = null
    try {
      sql = newInstance(url, driverClassName, caller) as Sql
      c.call(sql)
    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new ClassNotFoundException(e.getMessage())
    } finally {
      if (sql != null) {
        sql.close()
      }
    }
  }

  static void withInstance(String url, Class caller, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    withInstance(url, getDriverClassName(url), caller, c)
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
  static void withInstance(String url, String driverClassName, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    withInstance(url, driverClassName, getCallingClass(), c)
  }

  static void withInstance(String url, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    withInstance(url, getDriverClassName(url), getCallingClass(), c)
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
  static void withInstance(String url, String driverClassName, Object caller, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    withInstance(url, driverClassName, caller.getClass(), c)
  }

  static void withInstance(String url, Object caller, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    withInstance(url, getDriverClassName(url), caller, c)
  }

  static void withInstance(ConnectionInfo ci, @ClosureParams(value = SimpleType, options = 'groovy.sql.Sql') Closure c) throws SQLException, ClassNotFoundException {
    if (ci.user == null) {
      withInstance(ci.url, ci.driver, getCallingClass(), c)
    } else {
      withInstance(ci.url, ci.user, ci.password, ci.driver, getCallingClass(), c)
    }
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
      Driver driver = driver(driverClassName, caller)
      return newInstance(url, user, password, driver)
    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new ClassNotFoundException(e.getMessage())
    }
  }

  static Sql newInstance(String url, String user, String password, Class caller) throws SQLException, ClassNotFoundException {
    return newInstance(url, user, password, getDriverClassName(url), caller)
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

  static Sql newInstance(String url, String user, String password, Object caller) throws SQLException, ClassNotFoundException {
    return newInstance(url, user, password, getDriverClassName(url), caller)
  }

  static Sql newInstance(String url, String user, String password, String driverClassName, ClassLoader classLoader) throws SQLException, ClassNotFoundException {
    Driver driver = classLoader.loadClass(driverClassName).getDeclaredConstructor().newInstance() as Driver
    return newInstance(url, user, password, driver)
  }

  static Sql newInstance(String url, String user, String password, ClassLoader classLoader) throws SQLException, ClassNotFoundException {
    return newInstance(url, user, password, getDriverClassName(url), classLoader)
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

  static Sql newInstance(String url, String user, String password) throws SQLException, ClassNotFoundException {
    return newInstance(url, user, password, getDriverClassName(url))
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
    return newInstance(url, null, null, driverClassName, caller)
  }

  static Sql newInstance(String url, Class caller) throws SQLException, ClassNotFoundException {
    return newInstance(url, getDriverClassName(url), caller)
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
    return newInstance(url, null, null, driverClassName, caller.getClass())
  }

  static Sql newInstance(String url, Object caller) throws SQLException, ClassNotFoundException {
    return newInstance(url, getDriverClassName(url), caller)
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
    return newInstance(url, driverClassName, getCallingClass())
  }

  static Sql newInstance(String url) throws SQLException, ClassNotFoundException {
    return newInstance(url, getDriverClassName(url))
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
        props.setProperty('user', user)
      }
      if (password != null) {
        props.setProperty('password', password)
      }
      Connection con = driver.connect(url, props)
      return new Sql(con)
    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new ClassNotFoundException(e.getMessage())
    }
  }

  static Sql newInstance(ConnectionInfo connectionInfo, Class caller = SqlUtil) {
    return new Sql(connect(connectionInfo, caller))
  }

  static Connection connect(String driverClass, String jdbcUrl, Properties props, Class caller = SqlUtil) {
    Driver driver = driver(driverClass, caller)
    return driver.connect(jdbcUrl, props)
  }

  static Connection connect(String jdbcUrl, Properties props, Class caller = SqlUtil) {
    Driver driver = driver(getDriverClassName(jdbcUrl), caller)
    return driver.connect(jdbcUrl, props)
  }

  static Connection connect(ConnectionInfo connectionInfo, Class caller = SqlUtil) {
    Driver driver = driver(connectionInfo.driver, caller)
    return driver.connect(connectionInfo.url, connectionInfo.properties)
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
   * @throws NoSuchMethodException if a non empty constructor does not exist
   * @throws InvocationTargetException if a new instance cannot be created
   * @throws InstantiationException if a new instance cannot be created
   * @throws IllegalAccessException if a new instance cannot be created
   */
  static Driver driver(String driverClassName, Class caller) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    // Try caller's classloader
    ClassLoader callerClassLoader = caller?.getClassLoader()
    if (callerClassLoader != null) {
      try {
        return (Driver) callerClassLoader.loadClass(driverClassName).getDeclaredConstructor().newInstance()
      } catch (ClassNotFoundException ignored) {
        // Fall through to next classloader
      }
    }

    // Try context classloader
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader()
    if (contextClassLoader != null) {
      try {
        return (Driver) contextClassLoader.loadClass(driverClassName).getDeclaredConstructor().newInstance()
      } catch (ClassNotFoundException ignored) {
        // Fall through to next classloader
      }
    }

    // Try SqlUtil's classloader
    ClassLoader sqlUtilClassLoader = SqlUtil.getClassLoader()
    if (sqlUtilClassLoader != null) {
      try {
        return (Driver) sqlUtilClassLoader.loadClass(driverClassName).getDeclaredConstructor().newInstance()
      } catch (ClassNotFoundException ignored) {
        // Fall through to Class.forName
      }
    }

    // Final fallback: Class.forName
    return (Driver) Class.forName(driverClassName).getDeclaredConstructor().newInstance()
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
   * @throws NoSuchMethodException if a non empty constructor does not exist
   * @throws InvocationTargetException if a new instance cannot be created
   * @throws InstantiationException if a new instance cannot be created
   * @throws IllegalAccessException if a new instance cannot be created
   */
  static Driver driver(String driverClassName, Object caller) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    return driver(driverClassName, caller.getClass())
  }

  private static Class getCallingClass() {
    return ReflectionUtils.getCallingClass(2)
  }

  /**
   * Try to figure out the driver classname based on the url.
   *
   * @param url the jdbc url
   * @return the driver class fqn or null if not known
   */
  static String getDriverClassName(String url) {
    String lcUrl = String.valueOf(url).toLowerCase()
    if (lcUrl.startsWith(DataBaseProvider.MSSQL.urlStart)) {
      return 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
    }
    if (lcUrl.startsWith(DataBaseProvider.MYSQL.urlStart)) {
      return 'com.mysql.jdbc.Driver'
    }
    if (lcUrl.startsWith(DataBaseProvider.MARIADB.urlStart)) {
      return 'org.mariadb.jdbc.Driver'
    }
    if (lcUrl.startsWith(DataBaseProvider.POSTGRESQL.urlStart)) {
      return 'org.postgresql.Driver'
    }
    if (lcUrl.startsWith(DataBaseProvider.DERBY.urlStart)) {
      return 'org.apache.derby.jdbc.EmbeddedDriver'
    }
    if (lcUrl.startsWith(DataBaseProvider.HSQLDB.urlStart)) {
      return 'org.hsqldb.jdbc.JDBCDriver'
    }
    if (lcUrl.startsWith(DataBaseProvider.SQLLITE.urlStart)) {
      return 'org.sqlite.JDBC'
    }
    if (lcUrl.startsWith(DataBaseProvider.DB2.urlStart)) {
      return 'com.ibm.db2.jcc.DB2Driver'
    }
    if (lcUrl.startsWith(DataBaseProvider.ORACLE.urlStart)) {
      return 'oracle.jdbc.OracleDriver'
    }
    if (lcUrl.startsWith(DataBaseProvider.H2.urlStart)) {
      return 'org.h2.Driver'
    }
    throw new RuntimeException("Cannot determine driver class name from url: $url, please provide driver class name explicitly")
  }
}
