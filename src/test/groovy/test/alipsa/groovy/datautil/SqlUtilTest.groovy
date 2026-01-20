package test.alipsa.groovy.datautil

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import se.alipsa.groovy.datautil.ConnectionInfo
import se.alipsa.groovy.datautil.SqlUtil

import java.time.LocalDate

import static org.junit.jupiter.api.Assertions.*

class SqlUtilTest {

  def static final dbDriver = "org.h2.Driver"
  def static final dbUrl = "jdbc:h2:file:" + System.getProperty("java.io.tmpdir") + "/testdb"
  def static final dbUser = "sa"
  def static final dbPasswd = "123"

  @BeforeAll
  static void init() {

    try (def sql = SqlUtil.newInstance(dbUrl, dbUser, dbPasswd, dbDriver, this.class)) {
      sql.execute '''
                create table IF NOT EXISTS PROJECT  (
                    id integer not null primary key,
                    name varchar(50),
                    url varchar(100)
                )
            '''
      sql.execute('delete from PROJECT')
      sql.execute 'insert into PROJECT (id, name, url) values (?, ?, ?)', [10, 'Groovy', 'http://groovy.codehaus.org']
      sql.execute 'insert into PROJECT (id, name, url) values (?, ?, ?)', [20, 'Alipsa', 'http://www.alipsa.se']
    }
  }

  @Test
  void testWithInstance() {
    def idList = new ArrayList()

    SqlUtil.withInstance(dbUrl, dbUser, dbPasswd, dbDriver, this) { sql ->
      sql.query('SELECT id FROM PROJECT') { rs ->
        while (rs.next()) {
          idList.add(rs.getLong(1))
        }
      }
    }
    assertEquals(2, idList.size(), "Number of rows")
  }

  @Test
  void testDriverFromUrl() {

    def idList = new ArrayList()

    String driverName = SqlUtil.getDriverClassName(dbUrl)
    assertEquals(dbDriver, driverName)

    SqlUtil.withInstance(dbUrl, dbUser, dbPasswd) { sql ->
      sql.query('SELECT id FROM PROJECT') { rs ->
        while (rs.next()) {
          idList.add(rs.getLong(1))
        }
      }
    }
    assertEquals(2, idList.size(), "Number of rows")
  }

  @Test
  void testGetDriverClassNameForKnownDatabases() {
    assertEquals("com.microsoft.sqlserver.jdbc.SQLServerDriver", SqlUtil.getDriverClassName("jdbc:sqlserver://localhost"))
    assertEquals("com.mysql.jdbc.Driver", SqlUtil.getDriverClassName("jdbc:mysql://localhost"))
    assertEquals("org.mariadb.jdbc.Driver", SqlUtil.getDriverClassName("jdbc:mariadb://localhost"))
    assertEquals("org.postgresql.Driver", SqlUtil.getDriverClassName("jdbc:postgresql://localhost"))
    assertEquals("org.apache.derby.jdbc.EmbeddedDriver", SqlUtil.getDriverClassName("jdbc:derby:testdb"))
    assertEquals("org.hsqldb.jdbc.JDBCDriver", SqlUtil.getDriverClassName("jdbc:hsqldb:mem:test"))
    assertEquals("org.sqlite.JDBC", SqlUtil.getDriverClassName("jdbc:sqlite:test.db"))
    assertEquals("com.ibm.db2.jcc.DB2Driver", SqlUtil.getDriverClassName("jdbc:db2://localhost"))
    assertEquals("oracle.jdbc.OracleDriver", SqlUtil.getDriverClassName("jdbc:oracle:thin:@localhost"))
    assertEquals("org.h2.Driver", SqlUtil.getDriverClassName("jdbc:h2:mem:test"))
  }

  @Test
  void testGetDriverClassNameUnknownUrl() {
    assertThrows(RuntimeException) {
      SqlUtil.getDriverClassName("jdbc:unknown://localhost")
    }
  }

  @Test
  void testGetDriverClassNameWithNullUrl() {
    // String.valueOf(null) returns "null", which won't match any known prefix
    assertThrows(RuntimeException) {
      SqlUtil.getDriverClassName(null)
    }
  }

  @Test
  void testDriverWithNonExistentClass() {
    assertThrows(ClassNotFoundException) {
      SqlUtil.driver("com.nonexistent.Driver", this.class)
    }
  }

  @Test
  void testNewInstanceWithConnectionInfo() {
    def ci = new ConnectionInfo("test", null, dbDriver, dbUrl, dbUser, dbPasswd)
    def sql = SqlUtil.newInstance(ci)
    assertNotNull(sql)
    sql.close()
  }

  @Test
  void testNewInstanceWithConnectionInfoNoUser() {
    // H2 allows connections without user/password in certain modes
    def ci = new ConnectionInfo()
    ci.name = "test"
    ci.driver = dbDriver
    ci.url = "jdbc:h2:mem:testNoUser"

    def sql = SqlUtil.newInstance(ci)
    assertNotNull(sql)
    sql.close()
  }

}
