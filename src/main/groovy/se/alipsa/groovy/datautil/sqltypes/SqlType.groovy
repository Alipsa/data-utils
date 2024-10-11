package se.alipsa.groovy.datautil.sqltypes

import java.sql.Types

enum SqlType {

  ARRAY(Types.ARRAY),
  BIGINT(Types.BIGINT),
  BINARY(Types.BINARY),
  BIT(Types.BIT),
  BLOB(Types.BLOB),
  BOOLEAN(Types.BOOLEAN),
  CHAR(Types.CHAR),
  CLOB(Types.CLOB),
  DATALINK(Types.DATALINK),
  DATE(Types.DATE),
  DECIMAL(Types.DECIMAL),
  DISTINCT(Types.DISTINCT),
  DOUBLE(Types.DOUBLE),
  FLOAT(Types.FLOAT),
  INTEGER(Types.INTEGER),
  JAVA_OBJECT(Types.JAVA_OBJECT),
  LONGVARBINARY(Types.LONGVARBINARY),
  LONGVARCHAR(Types.LONGNVARCHAR),
  NCAR(Types.NCHAR),
  NUMERIC(Types.NUMERIC),
  NVARCHAR(Types.NVARCHAR),
  OTHER(Types.OTHER),
  REAL(Types.REAL),
  REF(Types.REF),
  ROWID(Types.ROWID),
  SMALLINT(Types.SMALLINT),
  SQLXML(Types.SQLXML),
  STRUCT(Types.STRUCT),
  TIME(Types.TIME),
  TIME_WITH_TIMEZONE(Types.TIME_WITH_TIMEZONE, 'TIME WITH TIMEZONE'),
  TIMESTAMP(Types.TIMESTAMP),
  TIMESTAMP_WITH_TIMEZONE(Types.TIMESTAMP_WITH_TIMEZONE, 'TIMESTAMP WITH TIMEZONE'),
  TINYINT(Types.TINYINT),
  VARBINARY(Types.VARBINARY),
  VARCHAR(Types.VARCHAR)

  int jdbcType
  String sqlType
  private SqlType(int jdbcType) {
    this.jdbcType = jdbcType
    this.sqlType = name()
  }

  private SqlType(int jdbcType, String sqlType) {
    this.jdbcType = jdbcType
    this.sqlType = sqlType
  }

  int getJdbcType() {
    jdbcType
  }

  @Override
  String toString() {
    sqlType
  }
}