package com.iodesystems.xml.tools;

import com.iodesystems.xml.tools.loaded.NodeLoadErrored;

public class SneakyException extends RuntimeException {

  public SneakyException(NodeLoadErrored e) {
    super(e);
  }

  public SneakyException(XmlValidationException e) {
    super(e);
  }
}
