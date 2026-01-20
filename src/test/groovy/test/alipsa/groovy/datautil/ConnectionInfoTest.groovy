package test.alipsa.groovy.datautil

import org.junit.jupiter.api.Test
import se.alipsa.groovy.datautil.ConnectionInfo

import static org.junit.jupiter.api.Assertions.*

class ConnectionInfoTest {

  @Test
  void testDefaultConstructor() {
    def ci = new ConnectionInfo()
    assertNull(ci.name)
    assertNull(ci.driver)
    assertNull(ci.url)
    assertNull(ci.user)
    assertNull(ci.password)
    assertNull(ci.dependency)
  }

  @Test
  void testFullConstructor() {
    def ci = new ConnectionInfo("testDb", "org.h2:h2:2.0.0", "org.h2.Driver",
        "jdbc:h2:mem:test", "sa", "secret")

    assertEquals("testDb", ci.name)
    assertEquals("org.h2:h2:2.0.0", ci.dependency)
    assertEquals("org.h2.Driver", ci.driver)
    assertEquals("jdbc:h2:mem:test", ci.url)
    assertEquals("sa", ci.user)
    assertEquals("secret", ci.password)
  }

  @Test
  void testCopyConstructor() {
    def original = new ConnectionInfo("testDb", "org.h2:h2:2.0.0", "org.h2.Driver",
        "jdbc:h2:mem:test", "sa", "secret")
    def copy = new ConnectionInfo(original)

    assertEquals(original.name, copy.name)
    assertEquals(original.dependency, copy.dependency)
    assertEquals(original.driver, copy.driver)
    assertEquals(original.url, copy.url)
    assertEquals(original.user, copy.user)
    assertEquals(original.password, copy.password)
  }

  @Test
  void testToString() {
    def ci = new ConnectionInfo()
    ci.name = "MyConnection"
    assertEquals("MyConnection", ci.toString())
  }

  @Test
  void testToStringWithNullName() {
    def ci = new ConnectionInfo()
    assertNull(ci.toString())
  }

  @Test
  void testHashCodeWithNullName() {
    def ci = new ConnectionInfo()
    assertEquals(0, ci.hashCode())
  }

  @Test
  void testHashCodeWithName() {
    def ci = new ConnectionInfo()
    ci.name = "test"
    assertEquals("test".hashCode(), ci.hashCode())
  }

  @Test
  void testEqualsWithSameName() {
    def ci1 = new ConnectionInfo()
    ci1.name = "test"
    def ci2 = new ConnectionInfo()
    ci2.name = "test"

    assertEquals(ci1, ci2)
  }

  @Test
  void testEqualsWithDifferentName() {
    def ci1 = new ConnectionInfo()
    ci1.name = "test1"
    def ci2 = new ConnectionInfo()
    ci2.name = "test2"

    assertNotEquals(ci1, ci2)
  }

  @Test
  void testEqualsWithNonConnectionInfo() {
    def ci = new ConnectionInfo()
    ci.name = "test"

    assertNotEquals(ci, "test")
    assertNotEquals(ci, null)
  }

  @Test
  void testCompareToWithNull() {
    def ci = new ConnectionInfo()
    ci.name = "test"

    assertThrows(NullPointerException) {
      ci.compareTo(null)
    }
  }

  @Test
  void testCompareToOrdering() {
    def ci1 = new ConnectionInfo()
    ci1.name = "alpha"
    def ci2 = new ConnectionInfo()
    ci2.name = "beta"

    assertTrue(ci1.compareTo(ci2) < 0)
    assertTrue(ci2.compareTo(ci1) > 0)
    assertEquals(0, ci1.compareTo(ci1))
  }

  @Test
  void testUrlSafeWithNull() {
    def ci = new ConnectionInfo()
    assertEquals("", ci.urlSafe)
  }

  @Test
  void testUrlSafeWithValue() {
    def ci = new ConnectionInfo()
    ci.url = "jdbc:h2:mem:test"
    assertEquals("jdbc:h2:mem:test", ci.urlSafe)
  }

  @Test
  void testSetUrlInfersDriver() {
    def ci = new ConnectionInfo()
    ci.url = "jdbc:postgresql://localhost/test"

    assertEquals("org.postgresql.Driver", ci.driver)
  }

  @Test
  void testSetUrlDoesNotOverrideExistingDriver() {
    def ci = new ConnectionInfo()
    ci.driver = "custom.Driver"
    ci.url = "jdbc:postgresql://localhost/test"

    assertEquals("custom.Driver", ci.driver)
  }

  @Test
  void testWithPassword() {
    def ci = new ConnectionInfo()
    def result = ci.withPassword("secret")

    assertSame(ci, result)  // Returns same instance for chaining
    assertEquals("secret", ci.password)
  }

  @Test
  void testGetProperties() {
    def ci = new ConnectionInfo()
    ci.user = "testUser"
    ci.password = "testPass"

    def props = ci.properties
    assertEquals("testUser", props.getProperty("user"))
    assertEquals("testPass", props.getProperty("password"))
  }

  @Test
  void testGetPropertiesWithNullUserAndPassword() {
    def ci = new ConnectionInfo()

    def props = ci.properties
    assertNull(props.getProperty("user"))
    assertNull(props.getProperty("password"))
  }

  @Test
  void testGetDependencyVersion() {
    def ci = new ConnectionInfo()
    ci.dependency = "org.h2:h2:2.1.214"

    assertEquals("2.1.214", ci.dependencyVersion)
  }

  @Test
  void testGetDependencyVersionWithNull() {
    def ci = new ConnectionInfo()
    assertNull(ci.dependencyVersion)
  }

  @Test
  void testGetDependencyVersionWithInvalidFormat() {
    def ci = new ConnectionInfo()
    ci.dependency = "invalid"
    assertNull(ci.dependencyVersion)
  }

  @Test
  void testAsJsonBasic() {
    def ci = new ConnectionInfo("testDb", "org.h2:h2:2.0.0", "org.h2.Driver",
        "jdbc:h2:mem:test", "sa", "secret")

    def json = ci.asJson()

    assertTrue(json.contains('"name":"testDb"'))
    assertTrue(json.contains('"driver":"org.h2.Driver"'))
    assertTrue(json.contains('"url":"jdbc:h2:mem:test"'))
    assertTrue(json.contains('"user":"sa"'))
    assertTrue(json.contains('"password":"******"'))  // Password is masked
  }

  @Test
  void testAsJsonWithNullValues() {
    def ci = new ConnectionInfo()

    def json = ci.asJson()

    assertTrue(json.contains('"name":null'))
    assertTrue(json.contains('"driver":null'))
    assertTrue(json.contains('"url":null'))
    assertTrue(json.contains('"user":null'))
    assertTrue(json.contains('"password":""'))
  }

  @Test
  void testAsJsonWithSpecialCharacters() {
    def ci = new ConnectionInfo()
    ci.name = "test\"name"
    ci.url = "jdbc:h2:file:C:\\path\\to\\db"
    ci.user = "user\twith\ttabs"

    def json = ci.asJson()

    assertTrue(json.contains('\\"'))  // Escaped quote
    assertTrue(json.contains('\\\\'))  // Escaped backslash
    assertTrue(json.contains('\\t'))   // Escaped tab
  }

  @Test
  void testAsJsonWithNewlines() {
    def ci = new ConnectionInfo()
    ci.name = "line1\nline2\rline3"

    def json = ci.asJson()

    assertTrue(json.contains('\\n'))  // Escaped newline
    assertTrue(json.contains('\\r'))  // Escaped carriage return
  }
}
