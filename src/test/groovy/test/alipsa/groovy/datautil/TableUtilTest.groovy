package test.alipsa.groovy.datautil

import org.junit.jupiter.api.Assertions
import se.alipsa.groovy.datautil.TableUtil;

import static tech.tablesaw.api.ColumnType.*

import org.junit.jupiter.api.Test
import tech.tablesaw.api.*
import tech.tablesaw.io.csv.CsvReadOptions

public class TableUtilTest {

  @Test
  void testFrequency() {
    def csv = getClass().getResource("/glaciers.csv")
    CsvReadOptions.Builder builder = CsvReadOptions.builder(csv)
        .separator(',' as Character)
        .columnTypes([INTEGER, DOUBLE, INTEGER] as ColumnType[])

    def glaciers = Table.read().usingOptions(builder.build())
    def freq = TableUtil.frequency(glaciers, "Number of observations")
    Assertions.assertEquals(20, freq.size())
    Assertions.assertEquals(31, freq.get(0, 1))
  }
}
