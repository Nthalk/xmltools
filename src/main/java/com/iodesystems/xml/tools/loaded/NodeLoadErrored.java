package com.iodesystems.xml.tools.loaded;

import com.iodesystems.xml.tools.Locator;
import com.iodesystems.xml.tools.XmlLoader;
import org.w3c.dom.Node;

public class NodeLoadErrored extends LoadErrored {

  private final Node nodeSource;
  private final Locator nodeLocator;
  private final Class loadClass;
  private final XmlLoader xmlLoader;

  public NodeLoadErrored(
      Node nodeSource,
      Locator nodeLocator,
      Class loadClass,
      Exception loadException,
      XmlLoader xmlLoader) {
    super(loadException);
    this.nodeSource = nodeSource;
    this.nodeLocator = nodeLocator;
    this.loadClass = loadClass;
    this.xmlLoader = xmlLoader;
  }

  public Node getNodeSource() {
    return nodeSource;
  }

  public Locator getNodeLocator() {
    return nodeLocator;
  }

  public Class getLoadClass() {
    return loadClass;
  }

  public XmlLoader getXmlLoader() {
    return xmlLoader;
  }
}
