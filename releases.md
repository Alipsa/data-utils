## Version history

### 1.0.7, in progress
- add getDependencyVersion method to ConnectionInfo
- ensure size in varchar(size) is minimum 1 (was 0)
- Improve support for Derby
- Rename DefaultTypeMapperMapper to DefaultTypeMapper
- Change scope of DefaultTypeMapper from protected to public in order to facilitate testing
- Improve support for Derby, adding a mapper method for converting values when the db does not support it
e.g. LocalDate inserts in Derby which must be converted to java.sql.Date for setObject to work.

### 1.0.6, 2024-10-11
- Change github repo references from perNyfelt to Alipsa
- Add support for ConnectionInfo as param to Sql factory methods
- upgrade test dependencies and gradle version
- fix bug in determining the DataBaseProvider from a URL
- Add support for handling java.sql.Types and a jdbcType in SqlTypeMapper
  that returns the Types int corresponding to the Java class

### 1.0.5, 2024-07-04
- move Tablesaw stuff to matrix-tablesaw
- add support for getting a db type for a java class
- remove dependency on log4j to slim down the library further and be logging agnostic

### 1.0.4, 2023-08-06
- upgrade to jdk17
- add conversions to/from Tablesaw and Matrix
- add wrappers to Gtable for all Table methods returning a Table
- move most of the Normalization code to the Matrix-stats package and adjust accordingly
- upgrade dependencies for groovy, tablesaw, SODS, dom4j

### 1.0.3, 2022-12-24
- upgrade transient dependencies with cve issues
- upgrade to groovy 4.0.6
- change groovy dependencies to compileOnly so that consumers of this library
  can use whatever compatible version of Groovy that they want without conflicts.

### 1.0.2, 2022-08-17
- Add TableUtil with support for frequency tables

### 1.0.1, 2022-07-25
- Upgrade to Groovy 4.0.4
- Build script fixes

### 1.0.0, 2022-07-15
- initial release