package test.alipsa.groovy.datautil

import org.junit.jupiter.api.Test
import se.alipsa.groovy.datautil.DataBaseProvider
import se.alipsa.groovy.datautil.sqltypes.PostgresTypeMapper
import se.alipsa.groovy.datautil.sqltypes.SqlServerTypeMapper
import se.alipsa.groovy.datautil.sqltypes.SqlType
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

  @Test
  void testSqlServerMapping() {
    SqlTypeMapper mapper = SqlTypeMapper.create(DataBaseProvider.MSSQL)
    assertInstanceOf(SqlServerTypeMapper, mapper)

    // Test SQL Server specific type mappings
    assertEquals("datetime2(3)", mapper.typeForTimestamp())
    assertEquals(SqlType.OTHER.jdbcType, mapper.jdbcTypeForTimestamp())

    assertEquals("FLOAT", mapper.typeForDouble())
    assertEquals(SqlType.FLOAT.jdbcType, mapper.jdbcTypeForDouble())

    assertEquals("INT", mapper.typeForInteger())

    assertEquals("datetime2", mapper.typeForLocalDateTime())
    assertEquals(SqlType.OTHER.jdbcType, mapper.jdbcTypeForLocalDateTime())

    // Test SQL Server string size handling
    assertEquals("VARCHAR", mapper.typeForString(null))
    assertEquals("VARCHAR(100)", mapper.typeForString(100))
    assertEquals("VARCHAR(1)", mapper.typeForString(0))  // Min size is 1
    assertEquals("VARCHAR(max)", mapper.typeForString(9000))  // > 8000 gets max

    // Test SQL Server decimal with precision and scale
    assertEquals("DECIMAL", mapper.typeForBigDecimal(null, null))
    assertEquals("DECIMAL(10)", mapper.typeForBigDecimal(10, null))
    assertEquals("DECIMAL(10, 2)", mapper.typeForBigDecimal(10, 2))
    assertEquals(SqlType.DECIMAL.jdbcType, mapper.jdbcTypeForBigDecimal())
  }

  @Test
  void testPostgresMapping() {
    SqlTypeMapper mapper = SqlTypeMapper.create(DataBaseProvider.POSTGRESQL)
    assertInstanceOf(PostgresTypeMapper, mapper)

    // Test PostgreSQL specific type mappings
    assertEquals("SMALLINT", mapper.typeForByte())
    assertEquals(SqlType.SMALLINT.jdbcType, mapper.jdbcTypeForByte())

    // Test PostgreSQL string size handling
    assertEquals("VARCHAR", mapper.typeForString(null))
    assertEquals("VARCHAR(100)", mapper.typeForString(100))
    assertEquals("VARCHAR(1)", mapper.typeForString(0))  // Min size is 1
    assertEquals("TEXT", mapper.typeForString(9000))  // > 8000 gets TEXT
  }

  @Test
  void testUnknownTypeReturnsBlob() {
    SqlTypeMapper mapper = SqlTypeMapper.create(DataBaseProvider.UNKNOWN)
    // Test that unknown types fall back to BLOB
    assertEquals("BLOB", mapper.sqlType(URL.class))
  }
}
