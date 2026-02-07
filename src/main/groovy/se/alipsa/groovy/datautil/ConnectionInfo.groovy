package se.alipsa.groovy.datautil

import groovy.transform.CompileStatic

@CompileStatic
class ConnectionInfo implements Comparable<ConnectionInfo> {

  //private static final Logger log = LogManager.getLogger(ConnectionInfo.class)

  private String name

  private String dependency
  private String driver
  private String url
  private String user
  private String password

  ConnectionInfo() {
  }

  ConnectionInfo(String name, String dependency, String driver, String url, String user, String password) {
    this.name = name
    this.dependency = dependency
    this.driver = driver
    this.url = url
    this.user = user
    this.password = password
  }

  ConnectionInfo(ConnectionInfo ci) {
    this.name = ci.getName()
    this.dependency = ci.getDependency()
    this.driver = ci.getDriver()
    this.url = ci.getUrl()
    this.user = ci.getUser()
    this.password = ci.getPassword()
  }

  String getName() {
    return name
  }

  void setName(String name) {
    this.name = name
  }

  String getDependency() {
    return dependency
  }

  /**
   * A dependency typically inludes one dependency in the gradle short format e.g.
   * 'se.alipsa.matrix:matrix-stats:1.1.0'. However sometimes multiple dependencies are needed.
   * If that is the case, you can separate the dependencies with a semicolon (;) e.g:
   *"org.apache.derby:derby:10.17.1.0;org.apache.derby:derbytools:10.17.1.0;org.apache.derby:derbyshared:10.17.1.0"
   *
   * @param dependency the dependency or dependencies needed to dynamically fetch the driver
   * from maven central and instantiate the driver
   */
  void setDependency(String dependency) {
    this.dependency = dependency
  }

  String getDriver() {
    return driver
  }

  void setDriver(String driver) {
    this.driver = driver
  }

  String getUrl() {
    return url
  }

  String getUrlSafe() {
    return url == null ? '' : url
  }

  void setUrl(String url) {
    this.url = url
    if (driver == null) {
      setDriver(SqlUtil.getDriverClassName(url))
    }
  }

  @Override
  String toString() {
    return name
  }

  String getUser() {
    return user
  }

  void setUser(String user) {
    this.user = user
  }

  String getPassword() {
    return password
  }

  void setPassword(String password) {
    this.password = password
  }

  ConnectionInfo withPassword(String password) {
    setPassword(password)
    return this
  }

  @Override
  int compareTo(ConnectionInfo obj) {
    Objects.requireNonNull(obj, 'Cannot compare with null')
    int byName = this.name <=> obj.name
    if (this.name != null || obj.name != null) {
      return byName
    }

    int byDependency = this.dependency <=> obj.dependency
    if (byDependency != 0) {
      return byDependency
    }
    int byDriver = this.driver <=> obj.driver
    if (byDriver != 0) {
      return byDriver
    }
    int byUrl = this.url <=> obj.url
    if (byUrl != 0) {
      return byUrl
    }
    int byUser = this.user <=> obj.user
    if (byUser != 0) {
      return byUser
    }
    return this.password <=> obj.password
  }

  @Override
  int hashCode() {
    if (name != null) {
      return name.hashCode()
    }
    return Objects.hash(dependency, driver, url, user, password)
  }

  @Override
  boolean equals(Object obj) {
    if (this.is(obj)) {
      return true
    }
    if (!(obj instanceof ConnectionInfo)) {
      return false
    }
    ConnectionInfo other = (ConnectionInfo) obj
    if (name != null || other.name != null) {
      return name == other.name
    }
    return dependency == other.dependency
        && driver == other.driver
        && url == other.url
        && user == other.user
        && password == other.password
  }

  Properties getProperties() {
    var props = new Properties()
    if (user != null) {
      props.setProperty('user', user)
    }
    if (password != null) {
      props.setProperty('password', password)
    }
    return props
  }

  String getDependencyVersion() {
    if (dependency == null) {
      return null
    }
    def parts = dependency.split(':')
    if (parts.length > 2) {
      return parts[2]
    }
    return null
  }

  String asJson(boolean maskPassword = true) {
    String pwd = password
    if (maskPassword) {
      pwd = password == null ? '' : ('*' * password.length())
    }
    return '{"name":' + jsonValue(name) +
        ',"driver":' + jsonValue(driver) +
        ',"url":' + jsonValue(url) +
        ',"user":' + jsonValue(user) +
        ',"password":' + jsonValue(pwd) +
        '}'
  }

  private static String jsonValue(String value) {
    if (value == null) {
      return 'null'
    }
    String escaped = value
        .replace('\\', '\\\\')
        .replace('"', '\\"')
        .replace('\n', '\\n')
        .replace('\r', '\\r')
        .replace('\t', '\\t')
    return "\"${escaped}\""
  }
}
