package tech.tablesaw.io.xml;

import tech.tablesaw.io.Destination;
import tech.tablesaw.io.WriteOptions;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class XmlWriteOptions extends WriteOptions {

  public static Builder builder(Destination dest) {
    return new Builder(dest);
  }

  public static Builder builder(OutputStream dest) {
    return new Builder(dest);
  }

  public static Builder builder(Writer dest) {
    return new Builder(dest);
  }

  public static Builder builder(File dest) throws IOException {
    return new Builder(dest);
  }

  public static Builder builder(String fileName) throws IOException {
    return builder(new File(fileName));
  }

  protected XmlWriteOptions(Builder builder) {
    super(builder);
  }

  public static class Builder extends WriteOptions.Builder {

    protected Builder(Destination dest) {
      super(dest);
    }

    protected Builder(OutputStream dest) {
      super(dest);
    }

    protected Builder(Writer dest) {
      super(dest);
    }

    protected Builder(File dest) {
      super(dest);
    }

    public XmlWriteOptions build() {
      return new XmlWriteOptions(this);
    }
  }
}
