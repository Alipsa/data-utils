package se.alipsa.groovy.datautil.sqltypes

import java.sql.Types

class DefaultTypeMapperMapper extends SqlTypeMapper {



  @Override
  String typeForBigDecimal(Integer precision, Integer scale) {
    String value = 'DECIMAL'
    if (precision != null) {
      value += "($precision"
      if (scale != null) {
        value += ", $scale"
      }
      value += ')'
    }
    value
  }

  @Override
  String typeForBigInteger() {
    return 'BIGINT'
  }

  @Override
  String typeForBoolean() {
    return 'BIT'
  }

  @Override
  String typeForByte() {
    return 'TINYINT'
  }

  @Override
  String typeForCharacter() {
    return 'CHAR'
  }

  @Override
  String typeForDouble() {
    return 'DOUBLE'
  }

  @Override
  String typeForFloat() {
    return "REAL"
  }

  @Override
  String typeForInstant() {
    return "TIMESTAMP"
  }

  @Override
  String typeForInteger() {
    return "INTEGER"
  }

  @Override
  String typeForDate() {
    return "DATE"
  }

  @Override
  String typeForLocalDate() {
    return "DATE"
  }

  @Override
  String typeForLocalTime() {
    return "TIME"
  }

  @Override
  String typeForLocalDateTime() {
    return "TIMESTAMP"
  }

  @Override
  String typeForLong() {
    return "BIGINT"
  }

  @Override
  String typeForShort() {
    return "SMALLINT"
  }

  @Override
  String typeForString(Integer size) {
    if (size == null) {
      return 'VARCHAR'
    }
    if (size > 8000) {
      return 'TEXT' // Maybe this should be CLOB?
    }
    return "VARCHAR($size)"
  }

  @Override
  String typeForTime() {
    return "TIME"
  }

  @Override
  String typeForTimestamp() {
    return "TIMESTAMP"
  }

  @Override
  String typeForByteArray() {
    return 'VARBINARY'
  }

  @Override
  String typeForZonedDateTime() {
    return 'TIMESTAMP WITH TIME ZONE'
  }

  @Override
  int jdbcTypeForBigDecimal() {
    Types.NUMERIC
  }

  @Override
  int jdbcTypeForBigInteger() {
    Types.BIGINT
  }

  @Override
  int jdbcTypeForBoolean() {
    Types.BIT
  }

  @Override
  int jdbcTypeForByte() {
    Types.TINYINT
  }

  @Override
  int jdbcTypeForCharacter() {
    Types.CHAR
  }

  @Override
  int jdbcTypeForDouble() {
    Types.DOUBLE
  }

  @Override
  int jdbcTypeForFloat() {
    Types.REAL
  }

  @Override
  int jdbcTypeForInstant() {
    Types.TIMESTAMP
  }

  @Override
  int jdbcTypeForInteger() {
    Types.INTEGER
  }

  @Override
  int jdbcTypeForDate() {
    return Types.DATE
  }

  @Override
  int jdbcTypeForLocalDate() {
    Types.DATE
  }

  @Override
  int jdbcTypeForLocalTime() {
    Types.TIME
  }

  @Override
  int jdbcTypeForLocalDateTime() {
    Types.TIMESTAMP
  }

  @Override
  int jdbcTypeForLong() {
    Types.BIGINT
  }

  @Override
  int jdbcTypeForShort() {
    Types.SMALLINT
  }

  @Override
  int jdbcTypeForString() {
    Types.VARCHAR
  }

  @Override
  int jdbcTypeForTime() {
    Types.TIME
  }

  @Override
  int jdbcTypeForTimestamp() {
    Types.TIMESTAMP
  }

  @Override
  int jdbcTypeForByteArray() {
    Types.VARBINARY
  }

  @Override
  int jdbcTypeForZonedDateTime() {
    Types.TIMESTAMP_WITH_TIMEZONE
  }
}
