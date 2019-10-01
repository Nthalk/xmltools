package com.iodesystems.xml.tools.loaded;

public interface Loaded<T> {

  T getValue();

  XmlLocation getLocation(Object o);
}
