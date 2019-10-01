package com.iodesystems.xml.tools.loaded;

import javax.xml.stream.Location;

public class XmlLocation {

  private final Location location;
  private String source;

  public XmlLocation(String source, Location location) {
    this.source = source;
    this.location = location;
  }

  public String getSource() {
    return source;
  }

  public int getLine() {
    return location.getLineNumber();
  }

  public int getColumn() {
    return location.getColumnNumber();
  }

  public int getOffset() {
    return location.getCharacterOffset();
  }

  @Override
  public String toString() {
    if (location == null) {
      return source + "@unknown";
    } else {
      return source + "@(" + getLine() + ":" + getColumn() + "+" + getOffset() + ")";
    }
  }
}
