package se.alipsa.groovy.datautil.sqltypes

class PostgresTypeMapper extends DefaultTypeMapperMapper {

  @Override
  String typeForByte() {
    return "SMALLINT"
  }
}
