package se.alipsa.groovy.datautil.sqltypes

class DefaultTypeMapper extends SqlTypeMapper {

  @Override
  String typeForBigDecimal(Integer precision, Integer scale) {
    String value = SqlType.NUMERIC.toString()
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
    SqlType.NUMERIC.jdbcType
  }

  @Override
  int jdbcTypeForBigInteger() {
    SqlType.BIGINT.jdbcType
  }

  @Override
  int jdbcTypeForBoolean() {
    SqlType.BIT.jdbcType
  }

  @Override
  int jdbcTypeForByte() {
    SqlType.TINYINT.jdbcType
  }

  @Override
  int jdbcTypeForCharacter() {
    SqlType.CHAR.jdbcType
  }

  @Override
  int jdbcTypeForDouble() {
    SqlType.DOUBLE.jdbcType
  }

  @Override
  int jdbcTypeForFloat() {
    SqlType.REAL.jdbcType
  }

  @Override
  int jdbcTypeForInstant() {
    SqlType.TIMESTAMP.jdbcType
  }

  @Override
  int jdbcTypeForInteger() {
    SqlType.INTEGER.jdbcType
  }

  @Override
  int jdbcTypeForDate() {
    SqlType.DATE.jdbcType
  }

  @Override
  int jdbcTypeForLocalDate() {
    SqlType.DATE.jdbcType
  }

  @Override
  int jdbcTypeForLocalTime() {
    SqlType.TIME.jdbcType
  }

  @Override
  int jdbcTypeForLocalDateTime() {
    SqlType.TIMESTAMP.jdbcType
  }

  @Override
  int jdbcTypeForLong() {
    SqlType.BIGINT.jdbcType
  }

  @Override
  int jdbcTypeForShort() {
    SqlType.SMALLINT.jdbcType
  }

  @Override
  int jdbcTypeForString() {
    SqlType.VARCHAR.jdbcType
  }

  @Override
  int jdbcTypeForTime() {
    SqlType.TIME.jdbcType
  }

  @Override
  int jdbcTypeForTimestamp() {
    SqlType.TIMESTAMP.jdbcType
  }

  @Override
  int jdbcTypeForByteArray() {
    SqlType.VARBINARY.jdbcType
  }

  @Override
  int jdbcTypeForZonedDateTime() {
    SqlType.TIMESTAMP_WITH_TIMEZONE.jdbcType
  }

  @Override
  Object convertToDbValue(Object o) {
    return o
  }
}
