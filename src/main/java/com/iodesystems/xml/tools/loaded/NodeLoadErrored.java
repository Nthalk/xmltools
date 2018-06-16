package com.iodesystems.xml.tools.loaded;

import com.iodesystems.xml.tools.Locator;
import com.iodesystems.xml.tools.XmlLoader;
import org.w3c.dom.Node;

public class NodeLoadErrored extends LoadErrored {

  public NodeLoadErrored(
      Node nodeSource,
      Locator nodeLocator,
      Class loadClass,
      Exception loadException,
      XmlLoader xmlLoader) {
    super(loadException);
  }
}
