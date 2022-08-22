package test.alipsa.groovy.datautil

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import se.alipsa.groovy.datautil.Normalize
import tech.tablesaw.api.DoubleColumn
import tech.tablesaw.api.FloatColumn
import tech.tablesaw.api.BigDecimalColumn

class NormalizeTest {

  @Test
  void testLogNorm() {
    def obs = DoubleColumn.create("values", [1200,34567,3456,12,3456,985,1211])
    def norm = Normalize.logNorm(obs, 6)
    def exp = [7.090077, 10.450655, 8.147867, 2.484907, 8.147867, 6.892642, 7.099202] as double[]
    Assertions.assertArrayEquals(exp, norm.asDoubleArray())

    obs = FloatColumn.create("values", [1200,34567,3456,12,3456,985,1211] as float[])
    norm = Normalize.logNorm(obs, 6)
    exp = [7.090077, 10.450655, 8.147867, 2.484907, 8.147867, 6.892642, 7.099202] as float[]
    Assertions.assertArrayEquals(exp, norm.asFloatArray())

    obs = BigDecimalColumn.create("values", [1200,34567,3456,12,3456,985,1211] as BigDecimal[])
    norm = Normalize.logNorm(obs, 6)
    exp = [7.090077, 10.450655, 8.147867, 2.484907, 8.147867, 6.892642, 7.099202] as BigDecimal[]
    Assertions.assertArrayEquals(exp, norm.asBigDecimalArray())
  }

  @Test
  void testMinMaxNormDouble() {

    Assertions.assertEquals(0.03437997d, Normalize.minMaxNorm(1200.0d, 12d, 34567d, 8))

    def obs = DoubleColumn.create("values", [1200,34567,3456,12,3456,985,1211])
    def norm = Normalize.minMaxNorm(obs, 8)
    def exp = [0.03437997, 1.00000000, 0.09966720, 0.00000000, 0.09966720, 0.02815801, 0.03469831] as double[]
    Assertions.assertArrayEquals(exp, norm.asDoubleArray())

  }

  @Test
  void testMinMaxNormFloat() {

    Assertions.assertEquals(0.03437997f, Normalize.minMaxNorm(1200f, 12f, 34567f, 8))

    def obs = FloatColumn.create("values", [1200,34567,3456,12,3456,985,1211] as float[])
    def norm = Normalize.minMaxNorm(obs, 8)
    def exp = [0.03437997, 1.00000000, 0.09966720, 0.00000000, 0.09966720, 0.02815801, 0.03469831] as float[]
    Assertions.assertArrayEquals(exp, norm.asFloatArray())

  }

  @Test
  void testMinMaxNormBigDecimal() {

    Assertions.assertEquals(0.03437997g, Normalize.minMaxNorm(1200.0g, 12.0g, 34567.0g, 8))

    def obs = BigDecimalColumn.create("values", [1200,34567,3456,12,3456,985,1211] as BigDecimal[])
    def norm = Normalize.minMaxNorm(obs, 8)
    def exp = [0.03437997, 1.00000000, 0.09966720, 0.00000000, 0.09966720, 0.02815801, 0.03469831] as BigDecimal[]
    Assertions.assertArrayEquals(exp, norm.asBigDecimalArray())
  }

  @Test
  void testStdScaleNormDouble() {
    Assertions.assertEquals(-0.4175944d, Normalize.stdScaleNorm(1200d, 6412.428571428572d, 12482.037558790136d, 7 ))

    def obs = DoubleColumn.create("values", [1200,34567,3456,12,3456,985,1211])
    def norm = Normalize.stdScaleNorm(obs, 7)
    def exp = [-0.4175944, 2.2556070, -0.2368546, -0.5127711, -0.2368546, -0.4348191, -0.4167131] as double[]
    Assertions.assertArrayEquals(exp, norm.asDoubleArray())
  }

  @Test
  void testStdScaleNormFloat() {
    Assertions.assertEquals(2.25560701f, Normalize.stdScaleNorm(34567f, 6412.428571428571f, 12482.037558790136f, 8))

    def obs = FloatColumn.create("values", [1200,34567,3456,12,3456,985,1211] as Float[])
    def norm = Normalize.stdScaleNorm(obs, 6)
    def exp = [-0.417594, 2.255607, -0.236855, -0.512771, -0.236855, -0.434819, -0.416713] as Float[]
    Assertions.assertArrayEquals(exp, norm.asFloatArray())
  }

  @Test
  void testStdScaleNormBigDecimal() {
    Assertions.assertEquals(-0.41759437g, Normalize.stdScaleNorm(1200.0g, 6412.428571428572g, 12482.037558790136g, 8 ))

    def obs = BigDecimalColumn.create("values", [1200,34567,3456,12,3456,985,1211])
    def norm = Normalize.stdScaleNorm(obs, 7)
    def exp = [-0.4175944, 2.2556070, -0.2368546, -0.5127711, -0.2368546, -0.4348191, -0.4167131] as BigDecimal[]
    Assertions.assertArrayEquals(exp, norm.asBigDecimalArray())
  }

  @Test
  void testMeanNormDouble() {
    Assertions.assertEquals(-0.150526076d, Normalize.meanNorm(1211d, 6412.428571428572d, 12d, 34567d, 9))

    def obs = DoubleColumn.create("values", [1200,34567,3456,12,3456,985,1211])
    def norm = Normalize.meanNorm(obs, 7)
    def exp = [-0.1508444, 0.8147756,  -0.0855572, -0.1852244, -0.0855572, -0.1570664, -0.1505261] as double[]
    Assertions.assertArrayEquals(exp, norm.asDoubleArray())
  }

  @Test
  void testMeanNormFloat() {
    Assertions.assertEquals(-0.150526076f, Normalize.meanNorm(1211f, 6412.428571428572f, 12f, 34567f, 9 ))

    def obs = FloatColumn.create("values", [1200, 34567, 3456, 12, 3456, 985, 1211] as Float[])
    def norm = Normalize.meanNorm(obs, 7)
    def exp = [ -0.1508444, 0.8147756, -0.0855572, -0.1852244, -0.0855572, -0.1570664, -0.1505261 ] as Float[]
    Assertions.assertArrayEquals(exp, norm.asFloatArray())
  }

  @Test
  void testMeanNormBigDecimal() {
    Assertions.assertEquals(-0.150526076g, Normalize.meanNorm(1211.0g, 6412.428571428572g, 12.0g, 34567.0g,  9 ))

    def obs = BigDecimalColumn.create("values", [1200,34567,3456,12,3456,985,1211])
    def norm = Normalize.meanNorm(obs, 7)
    def exp = [-0.1508444, 0.8147756,  -0.0855572, -0.1852244, -0.0855572, -0.1570664, -0.1505261] as BigDecimal[]
    Assertions.assertArrayEquals(exp, norm.asBigDecimalArray())
  }

}
