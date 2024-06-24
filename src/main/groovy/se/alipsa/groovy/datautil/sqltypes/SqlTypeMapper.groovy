package se.alipsa.groovy.datautil.sqltypes

import se.alipsa.groovy.datautil.ConnectionInfo
import se.alipsa.groovy.datautil.DataBaseProvider

import java.sql.Time
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

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
  abstract String typeForDouble()
  abstract typeForFloat()
  abstract typeForInstant()
  abstract typeForInteger()
  abstract typeForLocalDate()
  abstract typeForLocalTime()
  abstract String typeForLocalDateTime()
  abstract String typeForLong()
  abstract String typeForShort()
  abstract String typeForString(Integer size)
  abstract String typeForTime()
  abstract String typeForTimestamp()

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
      default -> mapper = new DefaultTypeMapperMapper()
    }
    mapper
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
    println("No match for $columnType, returning blob")
    return "BLOB"
  }
}
