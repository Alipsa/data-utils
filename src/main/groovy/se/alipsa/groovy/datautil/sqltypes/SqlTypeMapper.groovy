package se.alipsa.groovy.datautil.sqltypes

import se.alipsa.groovy.datautil.ConnectionInfo
import se.alipsa.groovy.datautil.DataBaseProvider

import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.time.*

import static se.alipsa.groovy.datautil.DataBaseProvider.*

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
    create(ci.urlSafe)
  }

  static SqlTypeMapper create(String jdbcUrl) {
    create(fromUrl(jdbcUrl))
  }

  static SqlTypeMapper create(DataBaseProvider provider) {
    SqlTypeMapper mapper
    switch (provider) {
      case H2 -> mapper = new H2TypeMapper()
      case POSTGRESQL -> mapper = new PostgresTypeMapper()
      case MSSQL -> mapper = new SqlServerTypeMapper()
      case DERBY -> mapper = new DerbyTypeMapper()
      default -> mapper = new DefaultTypeMapper()
    }
    mapper
  }

  String sqlType(Class<?> columnType) {
    sqlType(columnType, [:])
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
    if (Date == columnType) {
      return typeForDate()
    }
    if (byte[] == columnType) {
      return typeForByteArray()
    }
    if (ZonedDateTime == columnType) {
      return typeForZonedDateTime()
    }
    // No SQL type mapping found for columnType, defaulting to BLOB
    return "BLOB"
  }

  int jdbcType(Class type) {
    // Note: Order is important since Time and TimeStamp extends Date
    return switch (type) {
      case String -> jdbcTypeForString()
      case byte[], Byte[] -> jdbcTypeForByteArray()
      case boolean, Boolean -> jdbcTypeForBoolean()
      case short, Short -> jdbcTypeForShort()
      case int, Integer -> jdbcTypeForInteger()
      case long, Long -> jdbcTypeForLong()
      case BigInteger -> jdbcTypeForBigInteger()
      case float, Float -> jdbcTypeForFloat()
      case double, Double -> jdbcTypeForDouble()
      case BigDecimal -> jdbcTypeForBigDecimal()
      case Time -> jdbcTypeForTime()
      case LocalTime -> jdbcTypeForLocalTime()
      case Instant -> jdbcTypeForInstant()
      case Timestamp -> jdbcTypeForTimestamp()
      case LocalDateTime -> jdbcTypeForLocalDateTime()
      case java.util.Date, Date -> jdbcTypeForDate()
      case LocalDate -> jdbcTypeForLocalDate()
      case ZonedDateTime -> jdbcTypeForZonedDateTime()
      case char, Character -> jdbcTypeForCharacter()
      case byte, Byte -> jdbcTypeForByte()
      default -> SqlType.OTHER.jdbcType
    }
  }
}
