package se.alipsa.groovy.datautil.sqltypes

class SqlServerTypeMapper extends DefaultTypeMapper {

  @Override
  String typeForTimestamp() {
    return 'datetime2(3)'
  }

  @Override
  int jdbcTypeForTimestamp() {
    return SqlType.OTHER.jdbcType
  }

  @Override
  String typeForDouble() {
    return SqlType.FLOAT.toString()
  }

  @Override
  int jdbcTypeForDouble() {
    return SqlType.FLOAT.jdbcType
  }

  @Override
  String typeForInteger() {
    return "INT"
  }

  @Override
  String typeForLocalDateTime() {
    return "datetime2"
  }

  @Override
  int jdbcTypeForLocalDateTime() {
    return SqlType.OTHER.jdbcType
  }

  @Override
  String typeForString(Integer size) {
    if (size == null) {
      return SqlType.VARCHAR.toString()
    }
    if (size > 8000) {
      return "$SqlType.VARCHAR(max)" // Maybe this should be CLOB?
    }
    return "$SqlType.VARCHAR(${Math.max(size, 1)})"
  }

  @Override
  String typeForBigDecimal(Integer precision, Integer scale) {
    String value = SqlType.DECIMAL.toString()
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
  int jdbcTypeForBigDecimal() {
    SqlType.DECIMAL.jdbcType
  }
}
