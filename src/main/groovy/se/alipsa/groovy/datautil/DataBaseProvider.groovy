package se.alipsa.groovy.datautil

enum DataBaseProvider {

  UNKNOWN(''),
  H2('jdbc:h2:'),
  MSSQL('jdbc:sqlserver:'),
  POSTGRESQL('jdbc:postgresql:'),
  MYSQL('jdbc:mysql:'),
  MARIADB('jdbc:mariadb:'),
  DERBY('jdbc:derby:'),
  HSQLDB('jdbc:hsqldb:'),
  SQLLITE('jdbc:sqlite:'),
  ORACLE('jdbc:oracle'),
  DB2('jdbc:db2:'),
  CLOUDSCAPE('jdbc:db2j:net:'),
  INFORMIX('jdbc:ids:'),
  SNOWFLAKE('jdbc:snowflake:'),
  HIVE('jdbc:hive2:'),
  TERADATA('jdbc:teradata:'),
  SYBASE('jdbc:sybase:'),
  SAP_HANA('jdbc:sap:')

  String urlStart

  DataBaseProvider(String urlStart) {
    this.urlStart = urlStart
  }

  String getUrlStart() {
    return urlStart
  }

  static DataBaseProvider fromUrl(String url) {
    for (DataBaseProvider db : values()) {
      if (url.startsWith(db.urlStart)) {
        return db
      }
    }
    return UNKNOWN
  }

}