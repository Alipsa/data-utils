# data-utils
Groovy data utils. The main issue I needed to address was the reliance on System classloader in @Grab to handle JDBC drivers.
This is very inconvenient when using an IDE such as [Gade](/Alipsa/gade) and [Ride](/Alipsa/ride) as it requires you to copy jars to the lib folder and
restart the IDE in order for the System classpath to be updated.

However, with some Reflection magic it is possible to do without this. This is what se.alipsa.groovy.datautil.SqlUtil does.

Here is an example:

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.2')
@Grab('org.postgresql:postgresql:42.7.8')

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

Gradle:
```groovy
implementation "se.alipsa.groovy:data-utils:2.0.2"
```

Maven:
```xml
<dependency>
    <groupId>se.alipsa.groovy</groupId>
    <artifactId>data-utils</artifactId>
    <version>2.0.2</version>
</dependency>
```

