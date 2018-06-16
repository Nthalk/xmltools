package com.iodesystems.xml.tools;

import com.iodesystems.xml.tools.loaded.NodeLoadErrored;

public interface RequiresNodeResolution {
  void resolveNodes(XmlLoader.NodeResolver nodeResolver) throws NodeLoadErrored;
}
