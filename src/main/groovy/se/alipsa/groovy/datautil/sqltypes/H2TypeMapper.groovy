package se.alipsa.groovy.datautil.sqltypes

class H2TypeMapper extends DefaultTypeMapperMapper {

  @Override
  protected String typeForBigDecimal(Integer precision, Integer scale) {
    return "NUMBER"
  }

  @Override
  protected String typeForString(Integer size) {
    // Todo: consider adding support for json
    if (size == null) {
      return SqlType.VARCHAR.toString()
    }
    if (size > 1_000_000_000   ) {
      return "$SqlType.CLOB($size)"
    }
    return "$SqlType.VARCHAR(${Math.max(size, 1)})"
  }
}
