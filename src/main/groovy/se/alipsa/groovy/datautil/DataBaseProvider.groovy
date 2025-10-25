package se.alipsa.groovy.datautil

enum DataBaseProvider {

  UNKNOWN('unknown', null, null),
  H2('jdbc:h2:', 'com.h2database', 'h2'),
  MSSQL('jdbc:sqlserver:', 'com.microsoft.sqlserver', 'mssql-jdbc'),
  POSTGRESQL('jdbc:postgresql:', 'org.postgresql', 'postgresql'),
  MYSQL('jdbc:mysql:', 'com.mysql', 'mysql-connector-j'),
  MARIADB('jdbc:mariadb:', 'org.mariadb.jdbc', 'mariadb-java-client'),
  DERBY('jdbc:derby:', 'org.apache.derby', 'derby'),
  HSQLDB('jdbc:hsqldb:', 'org.hsqldb', 'hsqldb'),
  SQLLITE('jdbc:sqlite:', 'org.sqlite', 'sqlite-jdbc'),
  ORACLE('jdbc:oracle', 'com.oracle.database.jdbc', 'ojdbc11'),
  DB2('jdbc:db2:', 'com.ibm.db2', 'jcc'),
  INFORMIX('jdbc:ids:', 'com.ibm.informix', 'jdbc'),
  SNOWFLAKE('jdbc:snowflake:', 'net.snowflake', 'snowflake-jdbc'),
  HIVE('jdbc:hive2:', 'org.apache.hive', 'hive-jdbc'),
  TERADATA('jdbc:teradata:', 'com.teradata.jdbc', 'terajdbc'),
  SYBASE('jdbc:jtds:sybase:', 'net.sourceforge.jtds', 'jtds'),
  SAP_HANA('jdbc:sap:', 'com.sap.cloud.db.jdbc', 'ngdbc')

  String urlStart
  String dependencyGroupId
  String dependencyArtifactId

  DataBaseProvider(String urlStart, String dependencyGroupId, String dependencyArtifactId) {
    this.urlStart = urlStart
    this.dependencyGroupId = dependencyGroupId
    this.dependencyArtifactId = dependencyArtifactId
  }

  String getUrlStart() {
    return urlStart
  }

  String getDependencyGroupId() {
    return dependencyGroupId
  }

  String getDependencyArtifactId() {
    return dependencyArtifactId
  }

  String getDependencyString() {
    return "$dependencyGroupId:$dependencyArtifactId"
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