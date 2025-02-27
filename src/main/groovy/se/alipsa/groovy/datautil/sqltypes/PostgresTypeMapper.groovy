package se.alipsa.groovy.datautil.sqltypes

class PostgresTypeMapper extends DefaultTypeMapper {

  @Override
  String typeForByte() {
    return SqlType.SMALLINT.toString()
  }

  @Override
  int jdbcTypeForByte() {
    SqlType.SMALLINT.jdbcType
  }

  @Override
  String typeForString(Integer size) {
    if (size == null) {
      return SqlType.VARCHAR.toString()
    }
    if (size > 8000) {
      return 'TEXT' // Maybe this should be CLOB?
    }
    return "$SqlType.VARCHAR(${Math.max(size, 1)})"
  }
}
