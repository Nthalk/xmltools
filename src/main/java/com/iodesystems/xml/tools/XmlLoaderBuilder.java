package com.iodesystems.xml.tools;

import java.util.HashMap;
import java.util.Map;

public class XmlLoaderBuilder {

  private final Map<Class<?>, String> classesBySchemaLocation = new HashMap<>();

  public XmlLoaderBuilder addClasses(String validationLocation, Class<?> cls, Class<?>... clses) {
    addClass(validationLocation, cls);
    for (Class<?> aClass : clses) {
      addClass(validationLocation, aClass);
    }
    return this;
  }

  public XmlLoaderBuilder addClass(Class<?> cls) {
    return addClass(null, cls);
  }

  public XmlLoaderBuilder addClass(String validationLocation, Class<?> cls) {
    classesBySchemaLocation.put(cls, validationLocation);
    return this;
  }

  public XmlLoader build() {
    return new XmlLoader(classesBySchemaLocation);
  }
}
