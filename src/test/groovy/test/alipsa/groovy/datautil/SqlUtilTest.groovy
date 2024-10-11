package test.alipsa.groovy.datautil
import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import se.alipsa.groovy.datautil.SqlUtil

class SqlUtilTest {

    def static final dbDriver = "org.h2.Driver"
    def static final dbUrl = "jdbc:h2:file:" + System.getProperty("java.io.tmpdir") + "/testdb"
    def static final dbUser = "sa"
    def static final dbPasswd = "123"

    @BeforeAll
    static void init() {

        try (def sql = SqlUtil.newInstance(dbUrl, dbUser, dbPasswd, dbDriver, this.class)) {
            sql.execute '''
                create table IF NOT EXISTS PROJECT  (
                    id integer not null primary key,
                    name varchar(50),
                    url varchar(100)
                )
            '''
            sql.execute('delete from PROJECT')
            sql.execute 'insert into PROJECT (id, name, url) values (?, ?, ?)', [10, 'Groovy', 'http://groovy.codehaus.org']
            sql.execute 'insert into PROJECT (id, name, url) values (?, ?, ?)', [20, 'Alipsa', 'http://www.alipsa.se']
        }
    }

    @Test
    void testWithInstance() {
        def idList = new ArrayList()

        SqlUtil.withInstance(dbUrl, dbUser, dbPasswd, dbDriver, this) { sql ->
            sql.query('SELECT id FROM PROJECT') { rs ->
                while (rs.next()) {
                    idList.add(rs.getLong(1))
                }
            }
        }
        assertEquals(2, idList.size(), "Number of rows")
    }
}
