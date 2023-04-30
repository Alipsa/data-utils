package se.alipsa.groovy.datautil.gtable

import se.alipsa.groovy.datautil.TableUtil
import tech.tablesaw.api.CategoricalColumn
import tech.tablesaw.api.ColumnType
import tech.tablesaw.api.NumericColumn
import tech.tablesaw.api.Row
import tech.tablesaw.api.Table
import tech.tablesaw.columns.Column
import tech.tablesaw.joining.DataFrameJoiner
import tech.tablesaw.table.Relation

import java.util.stream.Collectors
import java.util.stream.Stream

class Gtable extends Table {

  private Gtable() {
    super('', [])
  }

  Gtable(Table table) {
    this(table.name(), table.columns())
  }
  /** Returns a new table initialized with the given name */
  private Gtable(String name) {
    super(name, [])
  }

  protected Gtable(String name, Column<?>... columns) {
    super(name, columns)
  }

  protected Gtable(String name, Collection<Column<?>> columns) {
    super(name, columns)
  }

  static Gtable create(Table table) {
    return new Gtable(table)
  }

  static Gtable create(List<Column<?>> columns) {
    Gtable table = new Gtable()
    for (final Column<?> column : columns) {
      table.addColumns(column)
    }
    return table
  }

  static Gtable create(LinkedHashMap<String, List<?>> data, List<ColumnType> columnTypes) {
    List<Column<?>> columns = new ArrayList<>()
    int i = 0
    data.each {
      columns << TableUtil.createColumn(columnTypes.get(i++), it.key, it.value)
    }
    create(columns) as Gtable
  }

  static Gtable create() {
    return new Gtable()
  }

  /** Returns a new, empty table (without rows or columns) with the given name */
  static Gtable create(String tableName) {
    return new Gtable(tableName);
  }

  /**
   * Returns a new gtable with the given columns
   *
   * @param columns one or more columns, all of the same @code{column.size()}
   */
  static Gtable create(Column<?>... columns) {
    return new Gtable(null, columns);
  }

  /**
   * Returns a new gtable with the given columns
   *
   * @param columns one or more columns, all of the same @code{column.size()}
   */
  static Gtable create(Collection<Column<?>> columns) {
    return new Gtable(null, columns);
  }

  /**
   * Returns a new gtable with the given columns
   *
   * @param columns one or more columns, all of the same @code{column.size()}
   */
  static Gtable create(Stream<Column<?>> columns) {
    return new Gtable(null, columns.collect(Collectors.toList()));
  }

  /**
   * Returns a new gtable with the given columns and given name
   *
   * @param name the name for this gtable
   * @param columns one or more columns, all of the same @code{column.size()}
   */
  static Gtable create(String name, Column<?>... columns) {
    return new Gtable(name, columns)
  }

  /**
   * Returns a new gtable with the given columns and given name
   *
   * @param name the name for this table
   * @param columns one or more columns, all of the same @code{column.size()}
   */
  static Gtable create(String name, Collection<Column<?>> columns) {
    return new Gtable(name, columns);
  }

  /**
   * Returns a new tgable with the given columns and given name
   *
   * @param name the name for this table
   * @param columns one or more columns, all of the same @code{column.size()}
   */
  static Gtable create(String name, Stream<Column<?>> columns) {
    return new Gtable(name, columns.collect(Collectors.toList()));
  }

  Object getAt(int row, int column) {
    return get(row, column)
  }

  Object getAt(int row, String columnName) {
    return column(columnName).get(row)
  }

  Column<?> getAt(int columnIndex) {
    return column(columnIndex)
  }

  Column<?> getAt(String name) {
    return column(name)
  }

  static GdataFrameReader read() {
    return new GdataFrameReader(defaultReaderRegistry)
  }

  Gtable dropDuplicateRows() {
    return create(super.dropDuplicateRows())
  }

  Gtable dropRowsWithMissingValues() {
    return create(super.dropRowsWithMissingValues())
  }

  Gtable selectColumns(Column<?>... columns) {
    return create(super.selectColumns(columns))
  }

  Gtable selectColumns(String... columnNames) {
    return create(super.selectColumns(columnNames))
  }

  Gtable rejectColumns(int... columnIndexes) {
    return create(super.rejectColumns(columnIndexes))
  }

  Gtable rejectColumns(String... columnNames) {
    return create(super.rejectColumns(columnNames))
  }

  Gtable rejectColumns(Column<?>... columns) {
    return create(super.rejectColumns(columns))
  }

  Gtable selectColumns(int... columnIndexes) {
    return create(super.selectColumns(columnIndexes))
  }

  Gtable removeColumns(Column<?>... columns) {
    return create(super.removeColumns(columns))
  }

  Gtable removeColumnsWithMissingValues() {
    return create(super.removeColumnsWithMissingValues())
  }

  Gtable retainColumns(Column<?>... columns) {
    return create(super.retainColumns(columns))
  }

  Gtable retainColumns(int... columnIndexes) {
    return create(super.retainColumns(columnIndexes))
  }

  Gtable retainColumns(String... columnNames) {
    return create(super.retainColumns(columnNames))
  }

  Gtable append(Relation tableToAppend) {
    return super.append(tableToAppend) as Gtable
  }

  Gtable append(Row row) {
    return super.append(row) as Gtable
  }

  Gtable concat(Table tableToConcatenate) {
    return super.concat(tableToConcatenate) as Gtable
  }

  Gtable xTabCounts(String column1Name, String column2Name) {
    return create(super.xTabCounts(column1Name, column2Name))
  }

  Gtable xTabRowPercents(String column1Name, String column2Name) {
    return create(super.xTabRowPercents(column1Name, column2Name))
  }

  Gtable xTabColumnPercents(String column1Name, String column2Name) {
    return create(super.xTabColumnPercents(column1Name, column2Name))
  }

  Gtable xTabTablePercents(String column1Name, String column2Name) {
    return create(super.xTabTablePercents(column1Name, column2Name))
  }

  Gtable xTabPercents(String column1Name) {
    return create(super.xTabPercents(column1Name))
  }

  Gtable xTabCounts(String column1Name) {
    return create(super.xTabCounts(column1Name))
  }

  Gtable countBy(CategoricalColumn<?>... groupingColumns) {
    return create(super.countBy(groupingColumns))
  }

  Gtable countBy(String... categoricalColumnNames) {
    return create(super.countBy(categoricalColumnNames))
  }

  Gtable missingValueCounts() {
    return create(super.missingValueCounts())
  }

  Gtable transpose() {
    return create(super.transpose())
  }

  Gtable transpose(boolean includeColumnHeadingsAsFirstColumn, boolean useFirstColumnForHeadings) {
    return create(super.transpose(includeColumnHeadingsAsFirstColumn, useFirstColumnForHeadings))
  }

  Gtable melt(List<String> idVariables, List<NumericColumn<?>> measuredVariables, Boolean dropMissing) {
    return create(super.melt(idVariables, measuredVariables, dropMissing))
  }

  Gtable cast() {
    return create(super.cast())
  }

  GdataFrameJoiner joinOn(String... columnNames) {
    return new GdataFrameJoiner(this, columnNames)
  }
}
