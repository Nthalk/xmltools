package com.iodesystems.xml.tools;

public class XmlValidationException extends Exception {
  public XmlValidationException() {}

  public XmlValidationException(String message) {
    super(message);
  }

  public XmlValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public XmlValidationException(Throwable cause) {
    super(cause);
  }

  public XmlValidationException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
