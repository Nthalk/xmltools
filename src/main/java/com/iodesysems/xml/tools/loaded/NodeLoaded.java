package com.iodesysems.xml.tools.loaded;

import com.iodesysems.xml.tools.Locator;
import org.w3c.dom.Node;

import javax.xml.stream.Location;

public class NodeLoaded<T> implements Loaded<T> {
    private final Node node;
    private final Locator nodeLocator;
    private final T value;

    public NodeLoaded(Node node, Locator nodeLocator, T value) {
        this.node = node;
        this.nodeLocator = nodeLocator;
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public Location getLocation(Object o) {
        return nodeLocator.getLocation(node);
    }

}
