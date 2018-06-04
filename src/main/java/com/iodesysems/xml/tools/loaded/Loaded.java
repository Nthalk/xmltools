package com.iodesysems.xml.tools.loaded;

import javax.xml.stream.Location;

public interface Loaded<T> {
    T getValue();

    Location getLocation(Object o);
}
