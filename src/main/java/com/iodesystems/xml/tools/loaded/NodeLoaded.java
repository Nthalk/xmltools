package com.iodesystems.xml.tools.loaded;

import com.iodesystems.xml.tools.Locator;
import java.util.List;
import org.w3c.dom.Node;

public class NodeLoaded<T> implements Loaded<T> {

  private final Node node;
  private final Locator nodeLocator;
  private final T value;
  private List<String> sourcePath;

  public NodeLoaded(Node node, Locator nodeLocator, T value) {
    this.sourcePath = sourcePath;
    this.node = node;
    this.nodeLocator = nodeLocator;
    this.value = value;
  }

  @Override
  public T getValue() {
    return value;
  }

  @Override
  public XmlLocation getLocation(Object o) {
    return nodeLocator.getLocation(node);
  }
}
