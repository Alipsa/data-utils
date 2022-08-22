package tech.tablesaw.api;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class BigDecimalColumnTest {

  @Test
  public void testTop() {
    var obs = BigDecimalColumn.create("values", new Number[]{1200,34567,3456,12,3456,985,1211});
    assertArrayEquals(bdArr(34567, 3456, 3456), obs.top(3).asBigDecimalArray());
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
