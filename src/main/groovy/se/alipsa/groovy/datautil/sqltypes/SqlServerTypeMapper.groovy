package se.alipsa.groovy.datautil.sqltypes

class SqlServerTypeMapper extends DefaultTypeMapperMapper {

  @Override
  protected String typeForTimestamp() {
    return 'datetime2(3)'
  }

  @Override
  protected String typeForDouble() {
    return SqlType.FLOAT.toString()
  }

  @Override
  protected String typeForInteger() {
    return "INT"
  }

  @Override
  protected String typeForLocalDateTime() {
    return "datetime2"
  }

  @Override
  protected String typeForString(Integer size) {
    if (size == null) {
      return SqlType.VARCHAR.toString()
    }
    if (size > 8000) {
      return "$SqlType.VARCHAR(max)" // Maybe this should be CLOB?
    }
    return "$SqlType.VARCHAR($size)"
  }

  @Override
  protected String typeForBigDecimal(Integer precision, Integer scale) {
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
}
