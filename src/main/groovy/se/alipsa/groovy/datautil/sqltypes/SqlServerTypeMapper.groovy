package se.alipsa.groovy.datautil.sqltypes

class SqlServerTypeMapper extends DefaultTypeMapperMapper {

  @Override
  protected String typeForTimestamp() {
    return 'datetime2(3)'
  }

  @Override
  protected String typeForDouble() {
    return 'FLOAT'
  }

  @Override
  protected String typeForInteger() {
    return "INT"
  }

  @Override
  protected String typeForLocalDateTime() {
    return "datetime2"
  }
}
