package se.alipsa.groovy.datautil.sqltypes

class DefaultTypeMapperMapper extends SqlTypeMapper {

  @Override
  protected String typeForBigDecimal(Integer precision, Integer scale) {
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
  protected String typeForBigInteger() {
    return SqlType.BIGINT.toString()
  }

  @Override
  protected String typeForBoolean() {
    return SqlType.BIT.toString()
  }

  @Override
  protected String typeForByte() {
    return SqlType.TINYINT.toString()
  }

  @Override
  protected String typeForCharacter() {
    return SqlType.CHAR.toString()
  }

  @Override
  protected String typeForDouble() {
    return SqlType.DOUBLE.toString()
  }

  @Override
  protected String typeForFloat() {
    return SqlType.REAL.toString()
  }

  @Override
  protected String typeForInstant() {
    return SqlType.TIMESTAMP.toString()
  }

  @Override
  protected String typeForInteger() {
    return SqlType.INTEGER.toString()
  }

  @Override
  protected String typeForDate() {
    return SqlType.DATE.toString()
  }

  @Override
  protected String typeForLocalDate() {
    return SqlType.DATE.toString()
  }

  @Override
  protected String typeForLocalTime() {
    return SqlType.TIME.toString()
  }

  @Override
  protected String typeForLocalDateTime() {
    return SqlType.TIMESTAMP.toString()
  }

  @Override
  protected String typeForLong() {
    return SqlType.BIGINT.toString()
  }

  @Override
  protected String typeForShort() {
    return SqlType.SMALLINT.toString()
  }

  @Override
  protected String typeForString(Integer size) {
    if (size == null) {
      return SqlType.VARCHAR.toString()
    }
    if (size > 8000) {
      return SqlType.LONGVARCHAR // Maybe this should be CLOB?
    }
    return "$SqlType.VARCHAR(${Math.max(size, 1)})"
  }

  @Override
  protected String typeForTime() {
    return SqlType.TIME.toString()
  }

  @Override
  protected String typeForTimestamp() {
    return SqlType.TIMESTAMP.toString()
  }

  @Override
  protected String typeForByteArray() {
    return SqlType.VARBINARY.toString()
  }

  @Override
  protected String typeForZonedDateTime() {
    return SqlType.TIMESTAMP_WITH_TIMEZONE.toString()
  }

  @Override
  protected int jdbcTypeForBigDecimal() {
    SqlType.NUMERIC.jdbcType
  }

  @Override
  protected int jdbcTypeForBigInteger() {
    SqlType.BIGINT.jdbcType
  }

  @Override
  protected int jdbcTypeForBoolean() {
    SqlType.BIT.jdbcType
  }

  @Override
  protected int jdbcTypeForByte() {
    SqlType.TINYINT.jdbcType
  }

  @Override
  protected int jdbcTypeForCharacter() {
    SqlType.CHAR.jdbcType
  }

  @Override
  protected int jdbcTypeForDouble() {
    SqlType.DOUBLE.jdbcType
  }

  @Override
  protected int jdbcTypeForFloat() {
    SqlType.REAL.jdbcType
  }

  @Override
  protected int jdbcTypeForInstant() {
    SqlType.TIMESTAMP.jdbcType
  }

  @Override
  protected int jdbcTypeForInteger() {
    SqlType.INTEGER.jdbcType
  }

  @Override
  protected int jdbcTypeForDate() {
    SqlType.DATE.jdbcType
  }

  @Override
  protected int jdbcTypeForLocalDate() {
    SqlType.DATE.jdbcType
  }

  @Override
  protected int jdbcTypeForLocalTime() {
    SqlType.TIME.jdbcType
  }

  @Override
  protected int jdbcTypeForLocalDateTime() {
    SqlType.TIMESTAMP.jdbcType
  }

  @Override
  protected int jdbcTypeForLong() {
    SqlType.BIGINT.jdbcType
  }

  @Override
  protected int jdbcTypeForShort() {
    SqlType.SMALLINT.jdbcType
  }

  @Override
  protected int jdbcTypeForString() {
    SqlType.VARCHAR.jdbcType
  }

  @Override
  protected int jdbcTypeForTime() {
    SqlType.TIME.jdbcType
  }

  @Override
  protected int jdbcTypeForTimestamp() {
    SqlType.TIMESTAMP.jdbcType
  }

  @Override
  protected int jdbcTypeForByteArray() {
    SqlType.VARBINARY.jdbcType
  }

  @Override
  protected int jdbcTypeForZonedDateTime() {
    SqlType.TIMESTAMP_WITH_TIMEZONE.jdbcType
  }
}
