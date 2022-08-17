package se.alipsa.groovy.datautil

import tech.tablesaw.api.ColumnType
import tech.tablesaw.api.Row
import tech.tablesaw.api.Table
import tech.tablesaw.columns.Column

import java.math.RoundingMode
import java.util.concurrent.atomic.AtomicInteger

class TableUtil {

  static Table frequency(Column<?> column) {
    Map<Object, AtomicInteger> freq = new HashMap<>()
    column.forEach(v -> {
      freq.computeIfAbsent(v, k -> new AtomicInteger(0)).incrementAndGet()
    });
    int size = column.size()
    def table = Table.create(column.name())
    def valueCol = ColumnType.STRING.create("Value")
    def freqCol = ColumnType.INTEGER.create("Frequency")
    def percentCol = ColumnType.DOUBLE.create("Percent")
    table.addColumns(valueCol, freqCol, percentCol)
    for (Map.Entry<Object, AtomicInteger> entry : freq.entrySet()) {
      Row row = table.appendRow()
      row.setString("Value", String.valueOf(entry.getKey()))
      int numOccurrence = entry.getValue().intValue()
      row.setInt("Frequency", numOccurrence)
      row.setDouble("Percent", round(numOccurrence * 100.0 / size, 2))
    }
    return table.sortDescendingOn("Frequency")
  }

  static Table frequency(Table table, String columnName) {
    return frequency(table.column(columnName))
  }

  static double round(double value, int numDecimals) {
    if (numDecimals < 0) throw new IllegalArgumentException("numDecimals cannot be a negative number: was " + numDecimals)

    BigDecimal bd = BigDecimal.valueOf(value)
    bd = bd.setScale(numDecimals, RoundingMode.HALF_UP)
    return bd.doubleValue()
  }
}
