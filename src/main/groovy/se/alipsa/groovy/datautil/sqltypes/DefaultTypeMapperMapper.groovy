package se.alipsa.groovy.datautil.sqltypes

import java.sql.Types

class DefaultTypeMapperMapper extends SqlTypeMapper {



  @Override
  protected String typeForBigDecimal(Integer precision, Integer scale) {
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
  protected String typeForBigInteger() {
    return 'BIGINT'
  }

  @Override
  protected String typeForBoolean() {
    return 'BIT'
  }

  @Override
  protected String typeForByte() {
    return 'TINYINT'
  }

  @Override
  protected String typeForCharacter() {
    return 'CHAR'
  }

  @Override
  protected String typeForDouble() {
    return 'DOUBLE'
  }

  @Override
  protected String typeForFloat() {
    return "REAL"
  }

  @Override
  protected String typeForInstant() {
    return "TIMESTAMP"
  }

  @Override
  protected String typeForInteger() {
    return "INTEGER"
  }

  @Override
  protected String typeForDate() {
    return "DATE"
  }

  @Override
  protected String typeForLocalDate() {
    return "DATE"
  }

  @Override
  protected String typeForLocalTime() {
    return "TIME"
  }

  @Override
  protected String typeForLocalDateTime() {
    return "TIMESTAMP"
  }

  @Override
  protected String typeForLong() {
    return "BIGINT"
  }

  @Override
  protected String typeForShort() {
    return "SMALLINT"
  }

  @Override
  protected String typeForString(Integer size) {
    if (size == null) {
      return 'VARCHAR'
    }
    if (size > 8000) {
      return 'TEXT' // Maybe this should be CLOB?
    }
    return "VARCHAR($size)"
  }

  @Override
  protected String typeForTime() {
    return "TIME"
  }

  @Override
  protected String typeForTimestamp() {
    return "TIMESTAMP"
  }

  @Override
  protected String typeForByteArray() {
    return 'VARBINARY'
  }

  @Override
  protected String typeForZonedDateTime() {
    return 'TIMESTAMP WITH TIME ZONE'
  }

  @Override
  protected int jdbcTypeForBigDecimal() {
    Types.NUMERIC
  }

  @Override
  protected int jdbcTypeForBigInteger() {
    Types.BIGINT
  }

  @Override
  protected int jdbcTypeForBoolean() {
    Types.BIT
  }

  @Override
  protected int jdbcTypeForByte() {
    Types.TINYINT
  }

  @Override
  protected int jdbcTypeForCharacter() {
    Types.CHAR
  }

  @Override
  protected int jdbcTypeForDouble() {
    Types.DOUBLE
  }

  @Override
  protected int jdbcTypeForFloat() {
    Types.REAL
  }

  @Override
  protected int jdbcTypeForInstant() {
    Types.TIMESTAMP
  }

  @Override
  protected int jdbcTypeForInteger() {
    Types.INTEGER
  }

  @Override
  protected int jdbcTypeForDate() {
    return Types.DATE
  }

  @Override
  protected int jdbcTypeForLocalDate() {
    Types.DATE
  }

  @Override
  protected int jdbcTypeForLocalTime() {
    Types.TIME
  }

  @Override
  protected int jdbcTypeForLocalDateTime() {
    Types.TIMESTAMP
  }

  @Override
  protected int jdbcTypeForLong() {
    Types.BIGINT
  }

  @Override
  protected int jdbcTypeForShort() {
    Types.SMALLINT
  }

  @Override
  protected int jdbcTypeForString() {
    Types.VARCHAR
  }

  @Override
  protected int jdbcTypeForTime() {
    Types.TIME
  }

  @Override
  protected int jdbcTypeForTimestamp() {
    Types.TIMESTAMP
  }

  @Override
  protected int jdbcTypeForByteArray() {
    Types.VARBINARY
  }

  @Override
  protected int jdbcTypeForZonedDateTime() {
    Types.TIMESTAMP_WITH_TIMEZONE
  }
}
