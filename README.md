# data-utils
Groovy data utils. The main issue I needed to address was the reliance on System classloader in @Grab to handle JDBC drivers.
This is very inconvenient when using an IDE such as [Gade](/Alipsa/gade) and [Ride](/Alipsa/ride) as it requires you to copy jars to the lib folder and
restart the IDE in order for the System classpath to be updated.

However, with some Reflection magic it is possible to do without this. This is what se.alipsa.groovy.datautil.SqlUtil does.

Here is an example:

```groovy
@Grab('se.alipsa.groovy:data-utils:1.0.5')
@Grab('org.postgresql:postgresql:42.4.0')

import se.alipsa.groovy.datautil.SqlUtil

def idList = new ArrayList()

SqlUtil.withInstance("jdbc:postgresql://localhost:5432/mydb", "dbUser", "dbPasswd", "org.postgresql.Driver") { sql ->
    sql.query('SELECT id FROM project') { rs ->
        while (rs.next()) {
            idList.add(rs.getLong(1))
        }
    }
}
```
See [test.alipsa.groovy.datautil.SqlUtilTest](https://github.com/perNyfelt/data-utils/blob/master/src/test/groovy/test/alipsa/groovy/datautil/SqlUtilTest.groovy) 
for more examples!

## Using the dependency
data-utils is available from maven central

Groovy:
```groovy
implementation "se.alipsa.groovy:data-utils:1.0.5"
```

Maven:
```xml
<dependency>
    <groupId>se.alipsa.groovy</groupId>
    <artifactId>data-utils</artifactId>
    <version>1.0.5</version>
</dependency>
```

## Version history

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