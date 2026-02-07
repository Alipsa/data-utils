# data-utils

Groovy SQL utilities library that solves the JDBC driver classloader issue when using `@Grab` in IDEs like [Gade](https://github.com/Alipsa/gade) and [Ride](https://github.com/Alipsa/ride).

The standard approach with `@Grab` relies on the System classloader, requiring you to copy JDBC driver JARs to the lib folder and restart the IDE. This library uses reflection to load drivers from the appropriate classloader, allowing seamless database connections without IDE restarts.

## Features

- **SqlUtil**: Drop-in replacement for `groovy.sql.Sql` factory methods with proper classloader handling
- **ConnectionInfo**: Data class for managing database connection configuration
- **SqlTypeMapper**: Database-specific SQL type mapping for Java types
- **DataBaseProvider**: Enum with metadata for 16 supported database systems

## Installation

data-utils is available from Maven Central.

**Gradle:**
```groovy
implementation "se.alipsa.groovy:data-utils:2.0.5"
```

**Maven:**
```xml
<dependency>
    <groupId>se.alipsa.groovy</groupId>
    <artifactId>data-utils</artifactId>
    <version>2.0.5</version>
</dependency>
```

## Quick Start

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.5')
@Grab('org.postgresql:postgresql:42.7.8')

import se.alipsa.groovy.datautil.SqlUtil

SqlUtil.withInstance("jdbc:postgresql://localhost:5432/mydb", "user", "password") { sql ->
    sql.eachRow('SELECT * FROM users') { row ->
        println "${row.id}: ${row.name}"
    }
}
```

## Database Examples

### H2 (In-Memory)

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.5')
@Grab('com.h2database:h2:2.2.224')

import se.alipsa.groovy.datautil.SqlUtil

// Driver class is auto-detected from URL
SqlUtil.withInstance("jdbc:h2:mem:testdb", "sa", "") { sql ->
    sql.execute '''
        CREATE TABLE users (
            id INT PRIMARY KEY,
            name VARCHAR(100)
        )
    '''
    sql.execute "INSERT INTO users VALUES (1, 'Alice')"

    sql.eachRow('SELECT * FROM users') { row ->
        println "${row.id}: ${row.name}"
    }
}
```

### H2 (File-based)

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.5')
@Grab('com.h2database:h2:2.2.224')

import se.alipsa.groovy.datautil.SqlUtil

def dbPath = new File(System.getProperty('java.io.tmpdir'), 'mydb').absolutePath
SqlUtil.withInstance("jdbc:h2:file:${dbPath}", "sa", "password") { sql ->
    // Use the connection
}
```

### MySQL / MariaDB

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.5')
@Grab('com.mysql:mysql-connector-j:8.2.0')

import se.alipsa.groovy.datautil.SqlUtil

SqlUtil.withInstance("jdbc:mysql://localhost:3306/mydb", "root", "password") { sql ->
    sql.eachRow('SELECT * FROM products') { row ->
        println row.name
    }
}
```

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.5')
@Grab('org.mariadb.jdbc:mariadb-java-client:3.3.0')

import se.alipsa.groovy.datautil.SqlUtil

SqlUtil.withInstance("jdbc:mariadb://localhost:3306/mydb", "root", "password") { sql ->
    // Use the connection
}
```

### PostgreSQL

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.5')
@Grab('org.postgresql:postgresql:42.7.8')

import se.alipsa.groovy.datautil.SqlUtil

SqlUtil.withInstance("jdbc:postgresql://localhost:5432/mydb", "postgres", "password") { sql ->
    sql.eachRow('SELECT * FROM orders WHERE status = ?', ['pending']) { row ->
        println "Order ${row.id}: ${row.total}"
    }
}
```

### SQL Server

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.5')
@Grab('com.microsoft.sqlserver:mssql-jdbc:12.4.2.jre11')

import se.alipsa.groovy.datautil.SqlUtil

SqlUtil.withInstance(
    "jdbc:sqlserver://localhost:1433;databaseName=mydb;encrypt=false",
    "sa", "password"
) { sql ->
    sql.eachRow('SELECT TOP 10 * FROM customers') { row ->
        println row.name
    }
}
```

### Oracle

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.5')
@Grab('com.oracle.database.jdbc:ojdbc11:23.3.0.23.09')

import se.alipsa.groovy.datautil.SqlUtil

SqlUtil.withInstance(
    "jdbc:oracle:thin:@localhost:1521:ORCL",
    "system", "password"
) { sql ->
    sql.eachRow('SELECT * FROM employees WHERE ROWNUM <= 10') { row ->
        println row.employee_name
    }
}
```

### SQLite

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.5')
@Grab('org.xerial:sqlite-jdbc:3.44.1.0')

import se.alipsa.groovy.datautil.SqlUtil

SqlUtil.withInstance("jdbc:sqlite:mydb.sqlite", null, null) { sql ->
    sql.execute 'CREATE TABLE IF NOT EXISTS items (id INTEGER PRIMARY KEY, name TEXT)'
    sql.execute "INSERT INTO items (name) VALUES ('Test')"
}
```

### Apache Derby

```groovy
@Grab('se.alipsa.groovy:data-utils:2.0.5')
@Grab('org.apache.derby:derby:10.16.1.1')
@Grab('org.apache.derby:derbytools:10.16.1.1')

import se.alipsa.groovy.datautil.SqlUtil

SqlUtil.withInstance("jdbc:derby:memory:mydb;create=true", null, null) { sql ->
    sql.execute '''
        CREATE TABLE test (
            id INT PRIMARY KEY,
            data VARCHAR(100)
        )
    '''
}
```

## API Reference

### SqlUtil

The main utility class providing factory methods for database connections.

#### Factory Methods

**withInstance** - Execute code with auto-closing connection:
```groovy
// With explicit driver
SqlUtil.withInstance(url, user, password, driverClassName) { sql -> ... }

// With auto-detected driver
SqlUtil.withInstance(url, user, password) { sql -> ... }

// URL-only (credentials in URL)
SqlUtil.withInstance(url) { sql -> ... }

// With ConnectionInfo
SqlUtil.withInstance(connectionInfo) { sql -> ... }
```

**newInstance** - Create a Sql instance (caller manages closing):
```groovy
def sql = SqlUtil.newInstance(url, user, password)
try {
    // Use sql
} finally {
    sql.close()
}
```

**connect** - Get raw JDBC Connection:
```groovy
def connection = SqlUtil.connect(driverClass, jdbcUrl, properties)
```

**driver** - Load a JDBC driver instance:
```groovy
def driver = SqlUtil.driver(driverClassName, callerClass)
```

**getDriverClassName** - Auto-detect driver class from URL:
```groovy
def driverClass = SqlUtil.getDriverClassName("jdbc:postgresql://localhost/db")
// Returns: "org.postgresql.Driver"
```

### ConnectionInfo

A data class for managing connection configuration.

```groovy
import se.alipsa.groovy.datautil.ConnectionInfo

// Full constructor
def ci = new ConnectionInfo(
    "myConnection",                    // name
    "org.postgresql:postgresql:42.7.8", // dependency (for @Grab)
    "org.postgresql.Driver",           // driver class
    "jdbc:postgresql://localhost/db",  // url
    "user",                            // user
    "password"                         // password
)

// Using setters (driver auto-detected from URL)
def ci = new ConnectionInfo()
ci.name = "myConnection"
ci.url = "jdbc:postgresql://localhost/db"  // Sets driver automatically
ci.user = "user"
ci.password = "password"

// Fluent password setting
ci.withPassword("newPassword")

// Get as Properties (for JDBC)
Properties props = ci.properties

// Serialize to JSON (password masked)
String json = ci.asJson()

// Get dependency version
String version = ci.dependencyVersion  // e.g., "42.7.8"
```

### SqlTypeMapper

Maps Java types to database-specific SQL types. Useful for DDL generation.

```groovy
import se.alipsa.groovy.datautil.sqltypes.SqlTypeMapper
import se.alipsa.groovy.datautil.DataBaseProvider

// Create mapper for specific database
def mapper = SqlTypeMapper.create(DataBaseProvider.POSTGRESQL)

// Or from JDBC URL
def mapper = SqlTypeMapper.create("jdbc:postgresql://localhost/db")

// Or from ConnectionInfo
def mapper = SqlTypeMapper.create(connectionInfo)

// Get SQL type for Java class
mapper.sqlType(String)        // "VARCHAR"
mapper.sqlType(Integer)       // "INTEGER"
mapper.sqlType(BigDecimal)    // "NUMERIC(38, 10)"
mapper.sqlType(LocalDateTime) // "TIMESTAMP"

// With size hints
mapper.typeForString(255)     // "VARCHAR(255)"
mapper.typeForBigDecimal(10, 2) // "NUMERIC(10, 2)"

// Get JDBC type constant
mapper.jdbcType(String)       // java.sql.Types.VARCHAR
mapper.jdbcType(LocalDate)    // java.sql.Types.DATE
```

#### Database-Specific Mappings

| Java Type        | Default   | PostgreSQL | SQL Server   | Derby                |
|------------------|-----------|------------|--------------|----------------------|
| `Byte`           | TINYINT   | SMALLINT   | TINYINT      | SMALLINT             |
| `Double`         | DOUBLE    | DOUBLE     | FLOAT        | DOUBLE               |
| `Timestamp`      | TIMESTAMP | TIMESTAMP  | datetime2(3) | TIMESTAMP            |
| `LocalDateTime`  | TIMESTAMP | TIMESTAMP  | datetime2    | TIMESTAMP            |
| `String (>8000)` | CLOB      | TEXT       | VARCHAR(max) | CLOB                 |
| `byte[]`         | VARBINARY | VARBINARY  | VARBINARY    | VARCHAR FOR BIT DATA |

### DataBaseProvider

Enum containing metadata for supported databases.

```groovy
import se.alipsa.groovy.datautil.DataBaseProvider

// Get provider from URL
def provider = DataBaseProvider.fromUrl("jdbc:postgresql://localhost/db")
// Returns: DataBaseProvider.POSTGRESQL

// Access metadata
provider.urlStart      // "jdbc:postgresql:"
provider.dependency    // "org.postgresql:postgresql:42.7.1"

// Supported providers
DataBaseProvider.values().each { println it.name() }
// H2, POSTGRESQL, MYSQL, MARIADB, MSSQL, ORACLE, DERBY, HSQLDB, SQLLITE, DB2, etc.
```

## Thread Safety and Connection Pooling

### Thread Safety

- **SqlUtil factory methods** are thread-safe and can be called from multiple threads
- **Sql instances** returned by `newInstance()` are NOT thread-safe - each thread should have its own instance
- **ConnectionInfo** is a simple data class with no synchronization - treat as effectively immutable after configuration

### Connection Pooling Recommendations

This library creates direct JDBC connections without pooling. For production applications with concurrent access, use a connection pool:

**HikariCP (Recommended):**
```groovy
@Grab('com.zaxxer:HikariCP:5.1.0')
@Grab('org.postgresql:postgresql:42.7.8')

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import groovy.sql.Sql

def config = new HikariConfig()
config.jdbcUrl = "jdbc:postgresql://localhost/mydb"
config.username = "user"
config.password = "password"
config.maximumPoolSize = 10

def dataSource = new HikariDataSource(config)

// Use pooled connections
def sql = new Sql(dataSource)
try {
    sql.eachRow('SELECT * FROM users') { row ->
        println row.name
    }
} finally {
    sql.close()  // Returns connection to pool
}

// Shutdown pool when done
dataSource.close()
```

**When to use connection pooling:**
- Web applications with concurrent requests
- Multi-threaded batch processing
- Long-running applications
- When connection establishment time is a bottleneck

**When direct connections (this library) are sufficient:**
- Scripts and one-off tasks
- Single-threaded applications
- Development and testing
- Low-concurrency scenarios

### Best Practices

1. **Always close connections** - Use `withInstance` for automatic closing, or try-finally with `newInstance`
2. **One Sql instance per thread** - Don't share Sql objects between threads
3. **Use parameterized queries** - Prevent SQL injection: `sql.eachRow('SELECT * FROM users WHERE id = ?', [userId])`
4. **Handle transactions explicitly** when needed:
   ```groovy
   sql.withTransaction {
       sql.execute "UPDATE accounts SET balance = balance - 100 WHERE id = 1"
       sql.execute "UPDATE accounts SET balance = balance + 100 WHERE id = 2"
   }
   ```

## Supported Databases

| Database   | URL Prefix         | Auto-detected Driver                           |
|------------|--------------------|------------------------------------------------|
| H2         | `jdbc:h2:`         | `org.h2.Driver`                                |
| PostgreSQL | `jdbc:postgresql:` | `org.postgresql.Driver`                        |
| MySQL      | `jdbc:mysql:`      | `com.mysql.jdbc.Driver`                        |
| MariaDB    | `jdbc:mariadb:`    | `org.mariadb.jdbc.Driver`                      |
| SQL Server | `jdbc:sqlserver:`  | `com.microsoft.sqlserver.jdbc.SQLServerDriver` |
| Oracle     | `jdbc:oracle:`     | `oracle.jdbc.OracleDriver`                     |
| Derby      | `jdbc:derby:`      | `org.apache.derby.jdbc.EmbeddedDriver`         |
| HSQLDB     | `jdbc:hsqldb:`     | `org.hsqldb.jdbc.JDBCDriver`                   |
| SQLite     | `jdbc:sqlite:`     | `org.sqlite.JDBC`                              |
| DB2        | `jdbc:db2:`        | `com.ibm.db2.jcc.DB2Driver`                    |

## More Examples

See the [test classes](https://github.com/Alipsa/data-utils/tree/master/src/test/groovy/test/alipsa/groovy/datautil) for more examples:
- [SqlUtilTest](https://github.com/Alipsa/data-utils/blob/master/src/test/groovy/test/alipsa/groovy/datautil/SqlUtilTest.groovy)
- [SqlTypeMapperTest](https://github.com/Alipsa/data-utils/blob/master/src/test/groovy/test/alipsa/groovy/datautil/SqlTypeMapperTest.groovy)
- [ConnectionInfoTest](https://github.com/Alipsa/data-utils/blob/master/src/test/groovy/test/alipsa/groovy/datautil/ConnectionInfoTest.groovy)

## Version History

See [releases.md](releases.md) for the full version history.

## License

MIT License - see [LICENSE](LICENSE) for details.
