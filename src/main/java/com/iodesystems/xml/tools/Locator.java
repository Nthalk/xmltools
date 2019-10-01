package com.iodesystems.xml.tools;

import com.iodesystems.xml.tools.loaded.XmlLocation;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;

public class Locator extends Unmarshaller.Listener {

  private final XMLStreamReader xsr;
  private final Map<Object, Location> locations;
  private final Unmarshaller.Listener listener =
      new Unmarshaller.Listener() {
        @Override
        public void beforeUnmarshal(Object target, Object parent) {
          locations.put(target, xsr.getLocation());
        }
      };
  private String xmlSource;

  public Locator(String xmlSource, XMLStreamReader xsr) {
    this.xmlSource = xmlSource;
    this.xsr = xsr;
    this.locations = new HashMap<>();
  }

  public XmlLocation getLocation(Object o) {
    return new XmlLocation(xmlSource, locations.get(o));
  }

  public void alias(Object node, Object resolved) {
    locations.put(resolved, locations.get(node));
  }

  public Unmarshaller.Listener getListener() {
    return listener;
  }
}
