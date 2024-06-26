package se.alipsa.groovy.datautil.sqltypes

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
  def typeForFloat() {
    return "REAL"
  }

  @Override
  def typeForInstant() {
    return "TIMESTAMP"
  }

  @Override
  def typeForInteger() {
    return "INTEGER"
  }

  @Override
  def typeForLocalDate() {
    return "DATE"
  }

  @Override
  def typeForLocalTime() {
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
}
