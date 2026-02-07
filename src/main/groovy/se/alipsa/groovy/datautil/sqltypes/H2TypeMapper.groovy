package se.alipsa.groovy.datautil.sqltypes

import groovy.transform.CompileStatic

@CompileStatic
class H2TypeMapper extends DefaultTypeMapper {

  @Override
  String typeForBigDecimal(Integer precision, Integer scale) {
    return 'NUMBER'
  }

  @Override
  String typeForString(Integer size) {
    // Todo: consider adding support for json
    if (size == null) {
      return SqlType.VARCHAR.toString()
    }
    return size > 1_000_000_000 ? "$SqlType.CLOB($size)" : "$SqlType.VARCHAR(${Math.max(size, 1)})"
  }
}
