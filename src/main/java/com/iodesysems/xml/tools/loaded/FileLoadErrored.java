package com.iodesysems.xml.tools.loaded;

import com.iodesysems.xml.tools.XmlLoader;

public class FileLoadErrored extends LoadErrored {

    private final String fileSource;
    private final Class loadClass;
    private final XmlLoader xmlLoader;

    public FileLoadErrored(String fileSource, Class loadClass, Exception loadException, XmlLoader xmlLoader) {
        super(loadException);
        this.fileSource = fileSource;
        this.loadClass = loadClass;
        this.xmlLoader = xmlLoader;
    }

    public String getFileSource() {
        return fileSource;
    }

    public Class getLoadClass() {
        return loadClass;
    }

    public XmlLoader getXmlLoader() {
        return xmlLoader;
    }
}
