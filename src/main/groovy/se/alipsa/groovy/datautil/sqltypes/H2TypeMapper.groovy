package se.alipsa.groovy.datautil.sqltypes

class H2TypeMapper extends DefaultTypeMapperMapper {

  @Override
  String typeForBigDecimal(Integer precision, Integer scale) {
    return "NUMBER"
  }

  @Override
  String typeForString(Integer size) {
    // Todo: consider adding support for json
    if (size == null) {
      return 'VARCHAR'
    }
    if (size > 1_000_000_000   ) {
      return "CLOB($size)"
    }
    return "VARCHAR($size)"
  }
}
