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
    DataBaseProvider provider = findEnumMatch(url)
    if (provider == H2 && url.contains('MODE=')) {
      if (url.contains('MODE=DB2')) {
        provider = DB2
      } else if (url.contains('MODE=Derby')) {
        provider = DERBY
      } else if (url.contains('MODE=HSQLDB')) {
        provider = HSQLDB
      } else if (url.contains('MODE=MariaDB')) {
        provider = MARIADB
      } else if (url.contains('MODE=MySQL')) {
        provider = MYSQL
      } else if (url.contains('MODE=Oracle')) {
        provider = ORACLE
      } else if (url.contains('MODE=PostgreSQL')) {
        provider = POSTGRESQL
      } else if (url.contains('MODE=MSSQLServer')) {
        provider = MSSQL
      }
    }
    provider
  }

  private static DataBaseProvider findEnumMatch(String url) {
    for (DataBaseProvider db : values()) {
      if (url.startsWith(db.urlStart)) {
        return db
      }
    }
    return UNKNOWN
  }

}