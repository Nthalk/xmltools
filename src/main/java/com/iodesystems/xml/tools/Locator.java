package com.iodesystems.xml.tools;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;

public class Locator extends Unmarshaller.Listener {
  private XMLStreamReader xsr;
  private Map<Object, Location> locations;
  private final Unmarshaller.Listener listener =
      new Unmarshaller.Listener() {
        @Override
        public void beforeUnmarshal(Object target, Object parent) {
          locations.put(target, xsr.getLocation());
        }
      };

  public Locator(XMLStreamReader xsr) {
    this.xsr = xsr;
    this.locations = new HashMap<>();
  }

  public Location getLocation(Object o) {
    return locations.get(o);
  }

  public void alias(Object node, Object resolved) {
    locations.put(resolved, locations.get(node));
  }

  public Unmarshaller.Listener getListener() {
    return listener;
  }
}
