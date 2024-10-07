package se.alipsa.groovy.datautil.sqltypes

class PostgresTypeMapper extends DefaultTypeMapperMapper {

  @Override
  protected String typeForByte() {
    return "SMALLINT"
  }
}
