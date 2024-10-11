package se.alipsa.groovy.datautil.sqltypes

import se.alipsa.groovy.datautil.ConnectionInfo
import se.alipsa.groovy.datautil.DataBaseProvider

import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime

import static se.alipsa.groovy.datautil.DataBaseProvider.*

abstract class SqlTypeMapper {

  static final String VARCHAR_SIZE = 'varcharSize'
  static final String DECIMAL_PRECISION = 'decimalPrecision'
  static final String DECIMAL_SCALE = 'decimalScale'

  protected abstract String typeForBigDecimal(Integer precision, Integer scale)
  protected abstract String typeForBigInteger()
  protected abstract String typeForBoolean()
  protected abstract String typeForByte()
  protected abstract String typeForCharacter()
  protected abstract String typeForDate()
  protected abstract String typeForDouble()
  protected abstract String typeForFloat()
  protected abstract String typeForInstant()
  protected abstract String typeForInteger()
  protected abstract String typeForLocalDate()
  protected abstract String typeForLocalTime()
  protected abstract String typeForLocalDateTime()
  protected abstract String typeForLong()
  protected abstract String typeForShort()
  protected abstract String typeForString(Integer size)
  protected abstract String typeForTime()
  protected abstract String typeForTimestamp()
  protected abstract String typeForByteArray()
  protected abstract String typeForZonedDateTime()

  protected abstract int jdbcTypeForBigDecimal()
  protected abstract int jdbcTypeForBigInteger()
  protected abstract int jdbcTypeForBoolean()
  protected abstract int jdbcTypeForByte()
  protected abstract int jdbcTypeForCharacter()
  protected abstract int jdbcTypeForDouble()
  protected abstract int jdbcTypeForFloat()
  protected abstract int jdbcTypeForInstant()
  protected abstract int jdbcTypeForInteger()
  protected abstract int jdbcTypeForDate()
  protected abstract int jdbcTypeForLocalDate()
  protected abstract int jdbcTypeForLocalTime()
  protected abstract int jdbcTypeForLocalDateTime()
  protected abstract int jdbcTypeForLong()
  protected abstract int jdbcTypeForShort()
  protected abstract int jdbcTypeForString()
  protected abstract int jdbcTypeForTime()
  protected abstract int jdbcTypeForTimestamp()
  protected abstract int jdbcTypeForByteArray()
  protected abstract int jdbcTypeForZonedDateTime()

  protected SqlTypeMapper() {
    // for subclasses only, users should use only the static create methods
  }

  static SqlTypeMapper create(ConnectionInfo ci) {
    create(ci.urlSafe)
  }

  static SqlTypeMapper create(String jdbcUrl) {
    create (fromUrl(jdbcUrl))
  }

  static SqlTypeMapper create(DataBaseProvider provider) {
    SqlTypeMapper mapper
    switch (provider) {
      case H2 -> mapper = new H2TypeMapper()
      case POSTGRESQL -> mapper = new PostgresTypeMapper()
      case MSSQL -> mapper = new SqlServerTypeMapper()
      default -> mapper = new DefaultTypeMapperMapper()
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
    if(LocalTime == columnType) {
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
    println("No match for $columnType, returning blob")
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
