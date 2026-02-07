package se.alipsa.groovy.datautil

import groovy.transform.CompileStatic

import java.time.YearMonth

@CompileStatic
class DataGenerator {

  static List<YearMonth> yearMonthsBetween(YearMonth start, YearMonth end, boolean includeStart = true, boolean includeEnd = true) {
    if (start.isAfter(end)) {
      throw new IllegalArgumentException('start must not be after end')
    }
    List<YearMonth> result = []
    YearMonth current = start
    if (!includeStart) {
      current = current.plusMonths(1)
    }
    while (current.isBefore(end)) {
      result << current
      current = current.plusMonths(1)
    }
    if (includeEnd) {
      result << end
    }
    return result
  }

}
