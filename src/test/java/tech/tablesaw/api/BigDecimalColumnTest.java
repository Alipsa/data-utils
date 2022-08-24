package tech.tablesaw.api;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import org.junit.jupiter.api.Test;
import tech.tablesaw.column.numbers.BigDecimalColumnType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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
    var previous = obs.lag(1);
    assertArrayEquals(bdArr(null, 1200, null, 3456, 12.1, 3456.4, 985, 1211.9, null), previous.asBigDecimalArray(), "lag 1");
  }

  @Test
  public void testGetDouble() {
    assertEquals(3456.4, obs.getDouble(4), "getDouble");
  }

  @Test
  public void testRemoveMissing() {
    assertArrayEquals(bdArr(1200, 3456, 12.1, 3456.4, 985, 1211.9, 12.1), obs.removeMissing().asBigDecimalArray(), "remove missing");
  }

  @Test
  public void testAppendBigDecimal() {
    var appended = obs.copy().append(new BigDecimal("123.333"));
    assertEquals(new BigDecimal("123.333"), appended.get(9), "Append BigDecimal");
  }

  @Test
  public void testAppendFloat() {
    var appended = obs.copy().append(123.333f);
    assertEquals(123.333f, appended.get(9).floatValue(), "Append float");
  }

  @Test
  public void testAppendDouble() {
    var appended = obs.copy().append(123.333d);
    assertEquals(123.333d, appended.get(9).doubleValue(), "Append double");
  }

  @Test
  public void testAppendInt() {
    var appended = obs.copy().append(1231);
    assertEquals(1231, appended.get(9).intValue(), "Append int");
  }

  @Test
  public void testAppendLong() {
    var appended = obs.copy().append(1231123411234511234L);
    assertEquals(1231123411234511234L, appended.get(9).longValue(), "Append long");
  }

  @Test
  public void testAppendNumber() {
    var appended = obs.copy().append(BigInteger.valueOf(1231123411234511234L));
    assertEquals(BigDecimal.valueOf(1231123411234511234L), appended.get(9), "Append number");
  }

  @Test
  public void testAppendString() {
    var appended = obs.copy().append("123.333444666777888999");
    assertEquals(new BigDecimal("123.333444666777888999"), appended.get(9), "Append String");
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
      arr[i] = num == null ? null : num instanceof Long || num instanceof Integer
          ? BigDecimal.valueOf(num.longValue())
          : BigDecimal.valueOf(num.doubleValue());
    }
    return arr;
  }

}
