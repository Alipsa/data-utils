package se.alipsa.groovy.datautil

import java.time.YearMonth

class DataGenerator {

  static List<YearMonth> yearMonthsBetween(YearMonth start, YearMonth end, boolean includeStart = true, boolean includeEnd = true) {
    def result = []
    def current = start
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
