package se.alipsa.groovy.datautil

enum DataBaseProvider {

  UNKNOWN('unknown'),
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
    String ucUrl = url.toUpperCase()
    if (provider == H2 && ucUrl.contains('MODE=')) {
      if (ucUrl.contains('MODE=DB2')) {
        provider = DB2
      } else if (ucUrl.contains('MODE=DERBY')) {
        provider = DERBY
      } else if (ucUrl.contains('MODE=HSQLDB')) {
        provider = HSQLDB
      } else if (ucUrl.contains('MODE=MARIADB')) {
        provider = MARIADB
      } else if (ucUrl.contains('MODE=MYSQL')) {
        provider = MYSQL
      } else if (ucUrl.contains('MODE=ORACLE')) {
        provider = ORACLE
      } else if (ucUrl.contains('MODE=POSTGRESQL')) {
        provider = POSTGRESQL
      } else if (ucUrl.contains('MODE=MSSQLSERVER')) {
        provider = MSSQL
      }
    }
    provider
  }

  static DataBaseProvider findEnumMatch(String url) {
    for (DataBaseProvider db : values()) {
      //println "${url.startsWith(db.urlStart)} ${db.urlStart} in url"
      if (url.startsWith(db.urlStart)) {
        return db
      }
    }
    return UNKNOWN
  }

}