package com.iodesystems.xml.tools;

import com.iodesystems.xml.tools.loaded.FileLoadErrored;
import com.iodesystems.xml.tools.loaded.NodeLoadErrored;
import java.io.InputStream;
import java.util.HashMap;

public class SimpleXmlLoader<T> {

  private final XmlLoader loader;
  private final Class<T> cls;

  public SimpleXmlLoader(Class<T> cls, String schemaLocation) {
    this.loader =
        new XmlLoader(
            new HashMap<Class<?>, String>() {
              {
                put(cls, schemaLocation);
              }
            });
    this.cls = cls;
  }

  public Result<T> result(String file, InputStream contents) {
    try {
      return new Result<>(loader.load(cls, file, contents));
    } catch (FileLoadErrored fileLoadErrored) {
      return new Result<>(fileLoadErrored);
    } catch (NodeLoadErrored nodeLoadErrored) {
      return new Result<>(nodeLoadErrored);
    }
  }

  public Result<T> result(String file) {
    try {
      return new Result<>(loader.load(cls, file));
    } catch (FileLoadErrored fileLoadErrored) {
      return new Result<>(fileLoadErrored);
    } catch (NodeLoadErrored nodeLoadErrored) {
      return new Result<>(nodeLoadErrored);
    }
  }
}
