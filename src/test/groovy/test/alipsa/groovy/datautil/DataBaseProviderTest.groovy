package test.alipsa.groovy.datautil

import org.junit.jupiter.api.Test
import se.alipsa.groovy.datautil.DataBaseProvider

import static org.junit.jupiter.api.Assertions.*
import static se.alipsa.groovy.datautil.DataBaseProvider.*

class DataBaseProviderTest {

  @Test
  void testFromUrl() {
    String url = "jdbc:h2:file:/tmp/tmpDb;MODE=MSSQLServer;DATABASE_TO_UPPER=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE"
    assertEquals(H2, findEnumMatch(url))
    assertEquals(MSSQL, fromUrl(url))
  }
}
