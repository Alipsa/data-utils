package se.alipsa.groovy.datautil.sqltypes

class SqlServerTypeMapper extends DefaultTypeMapperMapper {

  @Override
  String typeForTimestamp() {
    return 'datetime2(3)'
  }

  @Override
  String typeForDouble() {
    return 'FLOAT'
  }

  @Override
  def typeForInteger() {
    return "INT"
  }

  @Override
  String typeForLocalDateTime() {
    return "datetime2"
  }
}
