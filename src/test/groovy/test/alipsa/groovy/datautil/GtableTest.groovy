package test.alipsa.groovy.datautil

import org.junit.jupiter.api.Test
import se.alipsa.groovy.datautil.gtable.Gtable
import tech.tablesaw.api.ColumnType
import tech.tablesaw.column.numbers.BigDecimalColumnType
import tech.tablesaw.io.csv.CsvReadOptions

import static org.junit.jupiter.api.Assertions.*
import static se.alipsa.groovy.matrix.ListConverter.toLocalDates
import static tech.tablesaw.api.ColumnType.*
class GtableTest {

  @Test
  void testProgrammaticCreation() {
    def empData = [
        emp_id: 1..5,
        emp_name: ["Rick","Dan","Michelle","Ryan","Gary"],
        salary: [623.3,515.2,611.0,729.0,843.25],
        start_date: toLocalDates("2012-01-01", "2013-09-23", "2014-11-15", "2014-05-11", "2015-03-27")
        ]
    Gtable table = Gtable.create(empData, [INTEGER, STRING, DOUBLE, LOCAL_DATE])
    assertEquals(5, table.rowCount(), "number of rows")
    assertEquals(4, table.columnCount(), "number of columns")
    assertEquals("Gary", table[4, 1])
    assertEquals("Gary", table[4, "emp_name"])
  }

  @Test
  void testCreateFromCsv() {
    def csv = getClass().getResource("/glaciers.csv")
    CsvReadOptions.Builder builder = CsvReadOptions.builder(csv)
        .separator(',' as Character)
        .columnTypes([INTEGER, BigDecimalColumnType.instance(), INTEGER] as ColumnType[])

    Gtable glaciers = Gtable.read().usingOptions(builder.build())

    assertEquals(1946, glaciers[1,0])
    assertEquals(-3.19, glaciers[2,1])
    assertEquals(1, glaciers[3,2])
  }
}
