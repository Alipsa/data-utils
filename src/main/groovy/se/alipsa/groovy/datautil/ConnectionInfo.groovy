package se.alipsa.groovy.datautil

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
    if (obj == null) {
      throw new NullPointerException("Cannot compare with null")
    }
    return this.toString() <=> obj.toString()
  }

  @Override
  int hashCode() {
    return name?.hashCode() ?: 0
  }

  @Override
  boolean equals(Object obj) {
    if (obj instanceof ConnectionInfo) {
      return toString() == obj.toString();
    } else {
      return false;
    }
  }

  Properties getProperties() {
    var props = new Properties()
    if (user != null) {
      props.setProperty("user", user)
    }
    if (password != null) {
      props.setProperty("password", password)
    }
    props
  }

  String getDependencyVersion() {
    if (dependency == null) return null
    def parts = dependency.split(':')
    if (parts.length > 2) {
      return parts[2]
    } else {
      return null
    }
  }

  String asJson(boolean maskPassword = true) {

    String pwd = password
    if(maskPassword) {
      pwd = password == null ? "" : password.replaceAll(".", "*")
    }
    return "{" +
        "\"name\":${jsonValue(name)}," +
        "\"driver\":${jsonValue(driver)}," +
        "\"url\":${jsonValue(url)}," +
        "\"user\":${jsonValue(user)}," +
        "\"password\":${jsonValue(pwd)}" +
        "}"
  }

  private static String jsonValue(String value) {
    if (value == null) {
      return "null"
    }
    String escaped = value
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
    return "\"${escaped}\""
  }


}
