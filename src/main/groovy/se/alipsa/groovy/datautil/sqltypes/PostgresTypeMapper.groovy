package se.alipsa.groovy.datautil.sqltypes

import groovy.transform.CompileStatic

@CompileStatic
class PostgresTypeMapper extends DefaultTypeMapper {

  @Override
  String typeForByte() {
    return SqlType.SMALLINT.toString()
  }

  @Override
  int jdbcTypeForByte() {
    return SqlType.SMALLINT.jdbcType
  }

  @Override
  String typeForString(Integer size) {
    if (size == null) {
      return SqlType.VARCHAR.toString()
    }
    return size > 8000 ? 'TEXT' : "$SqlType.VARCHAR(${Math.max(size, 1)})" // Maybe this should be CLOB?
  }
}
