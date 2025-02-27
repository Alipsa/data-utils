package test.alipsa.groovy.datautil

import org.junit.jupiter.api.Test
import se.alipsa.groovy.datautil.DataBaseProvider
import se.alipsa.groovy.datautil.sqltypes.SqlServerTypeMapper
import se.alipsa.groovy.datautil.sqltypes.SqlTypeMapper

import java.sql.Time
import java.sql.Timestamp
import java.sql.Types
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import static org.junit.jupiter.api.Assertions.*

class SqlTypeMapperTest {

  @Test
  void testMapperFromUrl() {
    def tmpDb = new File(System.getProperty('java.io.tmpdir'), 'ddltestdb').getAbsolutePath()
    String url = "jdbc:h2:file:${tmpDb};MODE=MSSQLServer;DATABASE_TO_UPPER=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE"
    def mapper = SqlTypeMapper.create(url)
    assertEquals(SqlServerTypeMapper, mapper.class)
  }

  @Test
  void testJdbcTypeMapping() {
    SqlTypeMapper mapper = SqlTypeMapper.create(DataBaseProvider.UNKNOWN)
    assertEquals(Types.BIGINT, mapper.jdbcType(BigInteger))
    assertEquals(Types.BIGINT, mapper.jdbcType(Long))
    assertEquals(Types.CHAR, mapper.jdbcType(Character))
    assertEquals(Types.VARCHAR, mapper.jdbcType(String))
    assertEquals(Types.NUMERIC, mapper.jdbcType(BigDecimal))
    assertEquals(Types.INTEGER, mapper.jdbcType(Integer))
    assertEquals(Types.REAL, mapper.jdbcType(Float))
    assertEquals(Types.DOUBLE, mapper.jdbcType(Double))
    assertEquals(Types.VARBINARY, mapper.jdbcType(byte[]))
    assertEquals(Types.DATE, mapper.jdbcType(LocalDate))
    assertEquals(Types.DATE, mapper.jdbcType(Date))
    assertEquals(Types.TIME, mapper.jdbcType(LocalTime))
    assertEquals(Types.TIME, mapper.jdbcType(Time))
    assertEquals(Types.TIMESTAMP, mapper.jdbcType(LocalDateTime))
    assertEquals(Types.TIMESTAMP, mapper.jdbcType(Timestamp))
  }

  @Test
  void testDerbyMapping() {
    SqlTypeMapper mapper = SqlTypeMapper.create(DataBaseProvider.DERBY)
    assertEquals("VARCHAR(32672) FOR BIT DATA", mapper.typeForByteArray())
  }
}
