package se.alipsa.groovy.datautil.sqltypes

import groovy.transform.CompileStatic
import se.alipsa.groovy.datautil.ConnectionInfo
import se.alipsa.groovy.datautil.DataBaseProvider

import java.sql.Date as SqlDate
import java.sql.Time
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime

@CompileStatic
abstract class SqlTypeMapper {

  static final String VARCHAR_SIZE = 'varcharSize'
  static final String DECIMAL_PRECISION = 'decimalPrecision'
  static final String DECIMAL_SCALE = 'decimalScale'

  abstract String typeForBigDecimal(Integer precision, Integer scale)

  abstract String typeForBigInteger()

  abstract String typeForBoolean()

  abstract String typeForByte()

  abstract String typeForCharacter()

  abstract String typeForDate()

  abstract String typeForDouble()

  abstract String typeForFloat()

  abstract String typeForInstant()

  abstract String typeForInteger()

  abstract String typeForLocalDate()

  abstract String typeForLocalTime()

  abstract String typeForLocalDateTime()

  abstract String typeForLong()

  abstract String typeForShort()

  abstract String typeForString(Integer size)

  abstract String typeForTime()

  abstract String typeForTimestamp()

  abstract String typeForByteArray()

  abstract String typeForZonedDateTime()

  abstract int jdbcTypeForBigDecimal()

  abstract int jdbcTypeForBigInteger()

  abstract int jdbcTypeForBoolean()

  abstract int jdbcTypeForByte()

  abstract int jdbcTypeForCharacter()

  abstract int jdbcTypeForDouble()

  abstract int jdbcTypeForFloat()

  abstract int jdbcTypeForInstant()

  abstract int jdbcTypeForInteger()

  abstract int jdbcTypeForDate()

  abstract int jdbcTypeForLocalDate()

  abstract int jdbcTypeForLocalTime()

  abstract int jdbcTypeForLocalDateTime()

  abstract int jdbcTypeForLong()

  abstract int jdbcTypeForShort()

  abstract int jdbcTypeForString()

  abstract int jdbcTypeForTime()

  abstract int jdbcTypeForTimestamp()

  abstract int jdbcTypeForByteArray()

  abstract int jdbcTypeForZonedDateTime()

  /**
   * Handle conversion from non supported types in setObject
   * e.g. LocalDate in Derby which ust be converted to java.sql.Date
   */
  abstract Object convertToDbValue(Object o)

  protected SqlTypeMapper() {
    // for subclasses only, users should use only the static create methods
  }

  static SqlTypeMapper create(ConnectionInfo ci) {
    return create(ci.urlSafe)
  }

  static SqlTypeMapper create(String jdbcUrl) {
    return create(DataBaseProvider.fromUrl(jdbcUrl))
  }

  static SqlTypeMapper create(DataBaseProvider provider) {
    SqlTypeMapper mapper
    switch (provider) {
      case DataBaseProvider.H2:
        mapper = new H2TypeMapper()
        break
      case DataBaseProvider.POSTGRESQL:
        mapper = new PostgresTypeMapper()
        break
      case DataBaseProvider.MSSQL:
        mapper = new SqlServerTypeMapper()
        break
      case DataBaseProvider.DERBY:
        mapper = new DerbyTypeMapper()
        break
      default:
        mapper = new DefaultTypeMapper()
    }
    return mapper
  }

  String sqlType(Class<?> columnType) {
    return sqlType(columnType, [:])
  }

  String sqlType(Class columnType, Map<String, Integer> sizeMap) {
    if (BigDecimal == columnType) {
      return typeForBigDecimal(sizeMap[DECIMAL_PRECISION], sizeMap[DECIMAL_SCALE])
    }
    if (BigInteger == columnType) {
      return typeForBigInteger()
    }
    if (Boolean == columnType) {
      return typeForBoolean()
    }
    if (Byte == columnType) {
      return typeForByte()
    }
    if (Character == columnType) {
      return typeForCharacter()
    }
    if (Double == columnType) {
      return typeForDouble()
    }
    if (Float == columnType) {
      return typeForFloat()
    }
    if (Instant == columnType) {
      return typeForInstant()
    }
    if (Integer == columnType) {
      return typeForInteger()
    }
    if (LocalDate == columnType) {
      return typeForLocalDate()
    }
    if (LocalTime == columnType) {
      return typeForLocalTime()
    }
    if (LocalDateTime == columnType) {
      return typeForLocalDateTime()
    }
    if (Long == columnType) {
      return typeForLong()
    }
    if (Short == columnType) {
      return typeForShort()
    }
    if (String == columnType) {
      return typeForString(sizeMap[VARCHAR_SIZE])
    }
    if (Time == columnType) {
      return typeForTime()
    }
    if (Timestamp == columnType) {
      return typeForTimestamp()
    }
    if (SqlDate == columnType) {
      return typeForDate()
    }
    if (byte[] == columnType) {
      return typeForByteArray()
    }
    if (ZonedDateTime == columnType) {
      return typeForZonedDateTime()
    }
    // No SQL type mapping found for columnType, defaulting to BLOB
    return 'BLOB'
  }

  int jdbcType(Class type) {
    // Note: Order is important since Time and TimeStamp extends Date
    switch (type) {
      case String:
        return jdbcTypeForString()
      case byte[]:
      case Byte[]:
        return jdbcTypeForByteArray()
      case boolean:
      case Boolean:
        return jdbcTypeForBoolean()
      case short:
      case Short:
        return jdbcTypeForShort()
      case int:
      case Integer:
        return jdbcTypeForInteger()
      case long:
      case Long:
        return jdbcTypeForLong()
      case BigInteger:
        return jdbcTypeForBigInteger()
      case float:
      case Float:
        return jdbcTypeForFloat()
      case double:
      case Double:
        return jdbcTypeForDouble()
      case BigDecimal:
        return jdbcTypeForBigDecimal()
      case Time:
        return jdbcTypeForTime()
      case LocalTime:
        return jdbcTypeForLocalTime()
      case Instant:
        return jdbcTypeForInstant()
      case Timestamp:
        return jdbcTypeForTimestamp()
      case LocalDateTime:
        return jdbcTypeForLocalDateTime()
      case Date:
      case SqlDate:
        return jdbcTypeForDate()
      case LocalDate:
        return jdbcTypeForLocalDate()
      case ZonedDateTime:
        return jdbcTypeForZonedDateTime()
      case char:
      case Character:
        return jdbcTypeForCharacter()
      case byte:
      case Byte:
        return jdbcTypeForByte()
      default:
        return SqlType.OTHER.jdbcType
    }
  }
}
