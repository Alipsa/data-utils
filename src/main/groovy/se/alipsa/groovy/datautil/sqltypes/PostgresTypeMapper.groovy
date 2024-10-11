package se.alipsa.groovy.datautil.sqltypes

class PostgresTypeMapper extends DefaultTypeMapperMapper {

  @Override
  protected String typeForByte() {
    return SqlType.SMALLINT.toString()
  }

  @Override
  protected int jdbcTypeForByte() {
    SqlType.SMALLINT.jdbcType
  }

  @Override
  protected String typeForString(Integer size) {
    if (size == null) {
      return SqlType.VARCHAR.toString()
    }
    if (size > 8000) {
      return 'TEXT' // Maybe this should be CLOB?
    }
    return "$SqlType.VARCHAR($size)"
  }
}
