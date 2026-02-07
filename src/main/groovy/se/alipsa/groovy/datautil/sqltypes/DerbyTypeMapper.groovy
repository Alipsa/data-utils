package se.alipsa.groovy.datautil.sqltypes

import groovy.transform.CompileStatic

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

@CompileStatic
class DerbyTypeMapper extends DefaultTypeMapper {

  @Override
  String typeForByteArray() {
    // TODO perhaps allow scale to be parameterized
    Integer scale = 32672
    return "VARCHAR($scale) FOR BIT DATA"
  }

  @Override
  Object convertToDbValue(Object o) {
    if (o instanceof LocalDate) {
      return java.sql.Date.valueOf(o)
    }
    if (o instanceof LocalDateTime) {
      return Timestamp.valueOf(o)
    }
    return o
  }
}
