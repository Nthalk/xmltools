package com.iodesystems.xml.tools;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

public class XmlLoaderBuilder {

  private final Map<Class<?>, String> classesBySchemaLocation = new HashMap<>();

  public XmlLoaderBuilder addClass(Class<?> cls) {
    classesBySchemaLocation.put(cls, null);
    return this;
  }

  public XmlLoaderBuilder addClass(Class<?> cls, String validationLocation) {
    classesBySchemaLocation.put(cls, validationLocation);
    return this;
  }

  public XmlLoader build() throws JAXBException, MalformedURLException, SAXException {
    return new XmlLoader(classesBySchemaLocation);
  }
}
