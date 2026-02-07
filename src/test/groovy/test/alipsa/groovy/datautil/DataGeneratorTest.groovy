package test.alipsa.groovy.datautil

import org.junit.jupiter.api.Test
import se.alipsa.groovy.datautil.DataGenerator

import java.time.YearMonth

import static org.junit.jupiter.api.Assertions.*

class DataGeneratorTest {

  @Test
  void testYearMonthsBetweenIncludingBoth() {
    def start = YearMonth.of(2024, 1)
    def end = YearMonth.of(2024, 4)

    def result = DataGenerator.yearMonthsBetween(start, end)

    assertEquals(4, result.size())
    assertEquals(YearMonth.of(2024, 1), result[0])
    assertEquals(YearMonth.of(2024, 2), result[1])
    assertEquals(YearMonth.of(2024, 3), result[2])
    assertEquals(YearMonth.of(2024, 4), result[3])
  }

  @Test
  void testYearMonthsBetweenExcludingStart() {
    def start = YearMonth.of(2024, 1)
    def end = YearMonth.of(2024, 4)

    def result = DataGenerator.yearMonthsBetween(start, end, false, true)

    assertEquals(3, result.size())
    assertEquals(YearMonth.of(2024, 2), result[0])
    assertEquals(YearMonth.of(2024, 3), result[1])
    assertEquals(YearMonth.of(2024, 4), result[2])
  }

  @Test
  void testYearMonthsBetweenExcludingEnd() {
    def start = YearMonth.of(2024, 1)
    def end = YearMonth.of(2024, 4)

    def result = DataGenerator.yearMonthsBetween(start, end, true, false)

    assertEquals(3, result.size())
    assertEquals(YearMonth.of(2024, 1), result[0])
    assertEquals(YearMonth.of(2024, 2), result[1])
    assertEquals(YearMonth.of(2024, 3), result[2])
  }

  @Test
  void testYearMonthsBetweenExcludingBoth() {
    def start = YearMonth.of(2024, 1)
    def end = YearMonth.of(2024, 4)

    def result = DataGenerator.yearMonthsBetween(start, end, false, false)

    assertEquals(2, result.size())
    assertEquals(YearMonth.of(2024, 2), result[0])
    assertEquals(YearMonth.of(2024, 3), result[1])
  }

  @Test
  void testYearMonthsBetweenSameMonth() {
    def start = YearMonth.of(2024, 6)
    def end = YearMonth.of(2024, 6)

    def result = DataGenerator.yearMonthsBetween(start, end)

    assertEquals(1, result.size())
    assertEquals(YearMonth.of(2024, 6), result[0])
  }

  @Test
  void testYearMonthsBetweenCrossYearBoundary() {
    def start = YearMonth.of(2023, 11)
    def end = YearMonth.of(2024, 2)

    def result = DataGenerator.yearMonthsBetween(start, end)

    assertEquals(4, result.size())
    assertEquals(YearMonth.of(2023, 11), result[0])
    assertEquals(YearMonth.of(2023, 12), result[1])
    assertEquals(YearMonth.of(2024, 1), result[2])
    assertEquals(YearMonth.of(2024, 2), result[3])
  }

  @Test
  void testYearMonthsBetweenConsecutiveMonths() {
    def start = YearMonth.of(2024, 5)
    def end = YearMonth.of(2024, 6)

    def result = DataGenerator.yearMonthsBetween(start, end)

    assertEquals(2, result.size())
    assertEquals(YearMonth.of(2024, 5), result[0])
    assertEquals(YearMonth.of(2024, 6), result[1])
  }

  @Test
  void testYearMonthsBetweenStartAfterEndThrows() {
    def start = YearMonth.of(2024, 6)
    def end = YearMonth.of(2024, 5)

    assertThrows(IllegalArgumentException) {
      DataGenerator.yearMonthsBetween(start, end)
    }
  }
}
