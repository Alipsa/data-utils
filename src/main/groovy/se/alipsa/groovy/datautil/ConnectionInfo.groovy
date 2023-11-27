package se.alipsa.groovy.datautil;

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ConnectionInfo implements Comparable<ConnectionInfo> {

  private static final Logger log = LogManager.getLogger(ConnectionInfo.class);

  private String name

  private String dependency
  private String driver
  private String url
  private String user
  private String password

  public ConnectionInfo() {
  }

  public ConnectionInfo(String name, String dependency, String driver, String url, String user, String password) {
    this.name = name
    this.dependency = dependency
    this.driver = driver
    this.url = url
    this.user = user
    this.password = password
  }

  public ConnectionInfo(ConnectionInfo ci) {
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
      return this.toString() <=> obj.toString();
  }

  @Override
  int hashCode() {
    return name.hashCode()
  }

  @Override
  boolean equals(Object obj) {
    if (obj instanceof ConnectionInfo) {
      return toString() == obj.toString();
    } else {
      return false;
    }
  }

  String asJson() {
    String pwd = password == null ? "" : password.replaceAll(".", "*");
    return "{" +
        "\"name\"=\"" + name +
        "\", \"driver\"=\"" + driver +
        "\", \"url\"=\"" + url +
        "\", \"user\"=" + user +
        "\", \"password\"=\"" + pwd +
        "\"}";
  }


}
