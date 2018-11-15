package com.iodesystems.xml.tools;

import com.iodesystems.xml.tools.loaded.FileLoadErrored;
import com.iodesystems.xml.tools.loaded.Loaded;
import com.iodesystems.xml.tools.loaded.NodeLoadErrored;

public class Result<T> {

  private final Loaded<T> loaded;
  private final FileLoadErrored fileLoadError;
  private final NodeLoadErrored nodeLoadError;

  public Result(Loaded<T> loaded) {
    this.loaded = loaded;
    this.fileLoadError = null;
    nodeLoadError = null;
  }

  public Result(FileLoadErrored fileLoadError) {
    this.fileLoadError = fileLoadError;
    loaded = null;
    nodeLoadError = null;
  }

  public Result(NodeLoadErrored nodeLoadError) {
    this.nodeLoadError = nodeLoadError;
    fileLoadError = null;
    loaded = null;
  }

  public NodeLoadErrored getNodeLoadError() {
    return nodeLoadError;
  }

  public FileLoadErrored getFileLoadError() {
    return fileLoadError;
  }

  public Loaded<T> get() {
    return loaded;
  }
}
