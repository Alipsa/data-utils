# data-utils
Groovy data utils. The main issue I needed to address was the reliance on System classloader in @Grab to handle JDBC drivers.
This is very inconvenient when using an IDE such as [Gride](/perNyfelt/gride) as it requires you to copy jars to the Gride lib folder and
restart the IDE in order for the System classpath to be updated.

However, with some Reflection magic it is possible to do without this. This is what se.alipsa.groovy.datautil.SqlUtil does.

Here is an example:

```groovy
@Grab('se.alipsa.groovy:data-utils:1.0.0')
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