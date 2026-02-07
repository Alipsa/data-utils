package se.alipsa.groovy.datautil.sqltypes

import groovy.transform.CompileStatic

@CompileStatic
class DefaultTypeMapper extends SqlTypeMapper {

  @Override
  String typeForBigDecimal(Integer precision, Integer scale) {
    String value = SqlType.NUMERIC
    if (precision != null) {
      value += "($precision"
      if (scale != null) {
        value += ", $scale"
      }
      value += ')'
    }
    return value
  }

  @Override
  String typeForBigInteger() {
    return SqlType.BIGINT.toString()
  }

  @Override
  String typeForBoolean() {
    return SqlType.BIT.toString()
  }

  @Override
  String typeForByte() {
    return SqlType.TINYINT.toString()
  }

  @Override
  String typeForCharacter() {
    return SqlType.CHAR.toString()
  }

  @Override
  String typeForDouble() {
    return SqlType.DOUBLE.toString()
  }

  @Override
  String typeForFloat() {
    return SqlType.REAL.toString()
  }

  @Override
  String typeForInstant() {
    return SqlType.TIMESTAMP.toString()
  }

  @Override
  String typeForInteger() {
    return SqlType.INTEGER.toString()
  }

  @Override
  String typeForDate() {
    return SqlType.DATE.toString()
  }

  @Override
  String typeForLocalDate() {
    return SqlType.DATE.toString()
  }

  @Override
  String typeForLocalTime() {
    return SqlType.TIME.toString()
  }

  @Override
  String typeForLocalDateTime() {
    return SqlType.TIMESTAMP.toString()
  }

  @Override
  String typeForLong() {
    return SqlType.BIGINT.toString()
  }

  @Override
  String typeForShort() {
    return SqlType.SMALLINT.toString()
  }

  @Override
  String typeForString(Integer size) {
    if (size == null) {
      return SqlType.VARCHAR.toString()
    }
    if (size > 8000) {
      return SqlType.LONGVARCHAR // Maybe this should be CLOB?
    }
    return "$SqlType.VARCHAR(${Math.max(size, 1)})"
  }

  @Override
  String typeForTime() {
    return SqlType.TIME.toString()
  }

  @Override
  String typeForTimestamp() {
    return SqlType.TIMESTAMP.toString()
  }

  @Override
  String typeForByteArray() {
    return SqlType.VARBINARY.toString()
  }

  @Override
  String typeForZonedDateTime() {
    return SqlType.TIMESTAMP_WITH_TIMEZONE.toString()
  }

  @Override
  int jdbcTypeForBigDecimal() {
    return SqlType.NUMERIC.jdbcType
  }

  @Override
  int jdbcTypeForBigInteger() {
    return SqlType.BIGINT.jdbcType
  }

  @Override
  int jdbcTypeForBoolean() {
    return SqlType.BIT.jdbcType
  }

  @Override
  int jdbcTypeForByte() {
    return SqlType.TINYINT.jdbcType
  }

  @Override
  int jdbcTypeForCharacter() {
    return SqlType.CHAR.jdbcType
  }

  @Override
  int jdbcTypeForDouble() {
    return SqlType.DOUBLE.jdbcType
  }

  @Override
  int jdbcTypeForFloat() {
    return SqlType.REAL.jdbcType
  }

  @Override
  int jdbcTypeForInstant() {
    return SqlType.TIMESTAMP.jdbcType
  }

  @Override
  int jdbcTypeForInteger() {
    return SqlType.INTEGER.jdbcType
  }

  @Override
  int jdbcTypeForDate() {
    return SqlType.DATE.jdbcType
  }

  @Override
  int jdbcTypeForLocalDate() {
    return SqlType.DATE.jdbcType
  }

  @Override
  int jdbcTypeForLocalTime() {
    return SqlType.TIME.jdbcType
  }

  @Override
  int jdbcTypeForLocalDateTime() {
    return SqlType.TIMESTAMP.jdbcType
  }

  @Override
  int jdbcTypeForLong() {
    return SqlType.BIGINT.jdbcType
  }

  @Override
  int jdbcTypeForShort() {
    return SqlType.SMALLINT.jdbcType
  }

  @Override
  int jdbcTypeForString() {
    return SqlType.VARCHAR.jdbcType
  }

  @Override
  int jdbcTypeForTime() {
    return SqlType.TIME.jdbcType
  }

  @Override
  int jdbcTypeForTimestamp() {
    return SqlType.TIMESTAMP.jdbcType
  }

  @Override
  int jdbcTypeForByteArray() {
    return SqlType.VARBINARY.jdbcType
  }

  @Override
  int jdbcTypeForZonedDateTime() {
    return SqlType.TIMESTAMP_WITH_TIMEZONE.jdbcType
  }

  @Override
  Object convertToDbValue(Object o) {
    return o
  }
}
