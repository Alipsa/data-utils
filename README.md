# data-utils
Groovy data utils. The main issue I needed to address was the reliance on System classloader in @Grab to handle JDBC drivers.
This is very inconvenient when using an IDE such as [Gride](/perNyfelt/gride) as it requires you to copy jars to the Gride lib folder and
restart the IDE in order for the System classpath to be updated.

However, with some Reflection magic it is possible to do without this. This is what se.alipsa.groovy.datautil.SqlUtil does.

Here is an example:

```groovy
@Grab('se.alipsa.groovy:data-utils:1.0.2')
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

The other thing are some complementary operations to deal with Tablesaw data, e.g. the ability to create frequency tables.
See [test.alipsa.groovy.datautil.TableUtilTest](https://github.com/perNyfelt/data-utils/blob/master/src/test/groovy/test/alipsa/groovy/datautil/TableUtilTest.groovy)
for usage examples!

## Using the dependency
data-utils is available from maven central

Groovy:
```groovy
implementation "se.alipsa.groovy:data-utils:1.0.2"
```

Maven:
```xml
<dependency>
    <groupId>se.alipsa.groovy</groupId>
    <artifactId>data-utils</artifactId>
    <version>1.0.2</version>
</dependency>
```

## Version history

### 1.0.3, in development

### 1.0.2, 2022-08-17
- Add TableUtil with support for frequency tables

### 1.0.1, 2022-07-25
- Upgrade to Groovy 4.0.4
- Build script fixes

### 1.0.0, 2022-07-15
- initial release