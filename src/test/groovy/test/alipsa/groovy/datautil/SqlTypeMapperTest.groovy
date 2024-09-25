package test.alipsa.groovy.datautil

import org.junit.jupiter.api.Test
import se.alipsa.groovy.datautil.sqltypes.SqlServerTypeMapper
import se.alipsa.groovy.datautil.sqltypes.SqlTypeMapper

import static org.junit.jupiter.api.Assertions.*

class SqlTypeMapperTest {

  @Test
  void testMapperFromUrl() {
    def tmpDb = new File(System.getProperty('java.io.tmpdir'), 'ddltestdb').getAbsolutePath()
    String url = "jdbc:h2:file:${tmpDb};MODE=MSSQLServer;DATABASE_TO_UPPER=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE"
    def mapper = SqlTypeMapper.create(url)
    assertEquals(SqlServerTypeMapper, mapper.class)
  }
}
