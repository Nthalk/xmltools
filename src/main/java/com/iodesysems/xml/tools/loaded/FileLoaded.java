package com.iodesysems.xml.tools.loaded;

import com.iodesysems.xml.tools.Locator;
import com.iodesysems.xml.tools.XmlLoader;

import javax.xml.stream.Location;

public class FileLoaded<T> implements Loaded<T> {
    private final String fileSource;
    private final T value;
    private final Locator locator;
    private final XmlLoader xmlLoader;

    public FileLoaded(String fileSource, T value, Locator locator, XmlLoader xmlLoader) {
        this.fileSource = fileSource;
        this.value = value;
        this.locator = locator;
        this.xmlLoader = xmlLoader;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public Location getLocation(Object o) {
        return locator.getLocation(o);
    }

    public Loaded<T> reload() throws FileLoadErrored, NodeLoadErrored {
        return xmlLoader.load((Class<T>) value.getClass(), fileSource);
    }
}
