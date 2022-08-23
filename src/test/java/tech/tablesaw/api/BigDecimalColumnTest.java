package tech.tablesaw.api;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import org.junit.jupiter.api.Test;
import tech.tablesaw.column.numbers.BigDecimalColumnType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class BigDecimalColumnTest {

  Number[] values = new Number[]{1200, null, 3456, 12.1, 3456.4, 985, 1211.9, null, 12.1};
  BigDecimalColumn obs = BigDecimalColumn.create("values", values);
  @Test
  public void testGetString() {
    assertEquals("12.1", obs.getString(3));

    // TODO is this what we should expect?
    assertEquals("", obs.getString(1));
  }

  @Test
  public void testSize() {
    assertEquals(9, obs.size());
  }

  @Test
  public void testCopyAndSetMissing() {
    var copy = obs.copy();
    copy.setMissing(0);
    assertEquals(BigDecimalColumnType.missingValueIndicator(), copy.get(0));
  }

  @Test
  void testCopyAndSetWithPredicate(){
    var copy = obs.copy();
    var replacementValues = new DoubleArrayList(new double[]{1200, 2, 3456, 12.1, 3456.4, 985, 1211.9, 8, 12.1});
    var replacementColumn = new DoubleColumn("replacement", replacementValues);
    copy.set(Objects::isNull, replacementColumn);
    assertEquals(BigDecimal.valueOf(2.0), copy.get(1));
    assertEquals(BigDecimal.valueOf(8.0), copy.get(7));
  }

  @Test
  public void testCopyAndSetWithString() {
    var copy = obs.copy();
    copy.set(3, "-23.8", BigDecimalColumnType.DEFAULT_PARSER);
    assertEquals(new BigDecimal("-23.8"), copy.getBigDecimal(3));
  }

  @Test
  public void setFromColumn() {
    var copy = obs.copy();
    copy.set(5, BigDecimalColumn.create("another", List.of(BigDecimal.valueOf(111), new BigDecimal("3.14"))), 1);
    assertEquals(new BigDecimal("3.14"), copy.get(5));
  }

  @Test
  public void testWhere() {
    var selection = obs.where(obs.isGreaterThan(1000.0));
    assertEquals(4, selection.size());
  }

  @Test
  public void testIsNotIn() {
    var excluded = obs.isNotIn(BigDecimal.valueOf(1200), new BigDecimal("12.1"));
    assertEquals(6, excluded.size(), "all not 12.1");
    excluded = obs.isNotIn((BigDecimal) null);
    assertEquals(7, excluded.size(), "all not null");
  }

  @Test
  public void testIsIn() {
    var included = obs.isIn(BigDecimal.valueOf(1200), new BigDecimal("12.1"));
    assertEquals(3, included.size(), "contains 1200 and 12.1");
    included = obs.isIn((BigDecimal) null);
    assertEquals(2, included.size(), "number of nulls");
  }

  @Test
  public void testSubset() {
    var subset = obs.subset(new int[]{0,1,6});
    assertEquals(3, subset.size(), "subset of row 0,1,6");
  }

  @Test
  public void testUnique(){
    assertEquals(7, obs.unique().size(), "unique values");
  }

  @Test
  public void testTop() {
    assertArrayEquals(bdArr(3456.4, 3456, 1211.9), obs.top(3).asBigDecimalArray());
  }

  @Test
  public void testBottom() {
    assertArrayEquals(bdArr(12.1, 12.1, 985), obs.bottom(3).asObjectArray());
  }

  @Test
  public void testLag() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetDouble() {
    fail("Not yet implemented");
  }

  @Test
  public void testRemoveMissing() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendBigDecimal() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendFloat() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendDouble() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendInt() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendLong() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendNumber() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendString() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendColumn() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendValueFromColumn() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendMissing() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendObject() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendCell() {
    fail("Not yet implemented");
  }

  @Test
  public void testAppendCellWithParser() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetUnformattedString() {
    fail("Not yet implemented");
  }

  @Test
  public void testValueHash() {
    fail("Not yet implemented");
  }

  @Test
  public void testEquals() {
    fail("Not yet implemented");
  }

  @Test
  public void testIterator() {
    fail("Not yet implemented");
  }

  @Test
  public void testCompare() {
    fail("Not yet implemented");
  }

  @Test
  public void testFilter() {
    fail("Not yet implemented");
  }

  @Test
  public void testAsBytes() {
    fail("Not yet implemented");
  }

  @Test
  public void testAsSet() {
    fail("Not yet implemented");
  }

  @Test
  public void testCountUnique() {
    fail("Not yet implemented");
  }

  @Test
  public void testIsMissingValue() {
    fail("Not yet implemented");
  }

  @Test
  public void testIsMissing() {
    fail("Not yet implemented");
  }

  @Test
  public void testSortAscending() {
    fail("Not yet implemented");
  }

  @Test
  public void testSortDescending() {
    fail("Not yet implemented");
  }

  @Test
  public void testFillWith() {
    fail("Not yet implemented");
  }

  @Test
  public void testAsLongColumn() {
    fail("Not yet implemented");
  }

  @Test
  public void testAsIntColumn() {
    fail("Not yet implemented");
  }

  @Test
  public void testAsShortColumn() {
    fail("Not yet implemented");
  }

  @Test
  public void testAsFloatColumn() {
    fail("Not yet implemented");
  }

  @Test
  public void testAddAll() {
    fail("Not yet implemented");
  }

  @Test
  public void testSetPrintFormatter() {
    fail("Not yet implemented");
  }

  private BigDecimal[] bdArr(Number... numbers) {
    BigDecimal[] arr = new BigDecimal[numbers.length];
    for (int i = 0; i < numbers.length; i++) {
      Number num = numbers[i];
      arr[i] = num instanceof Long || num instanceof Integer
          ? BigDecimal.valueOf(num.longValue())
          : BigDecimal.valueOf(num.doubleValue());
    }
    return arr;
  }

}
