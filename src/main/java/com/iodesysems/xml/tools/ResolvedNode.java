package com.iodesysems.xml.tools;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class ResolvedNode<T> {
    private final Node node;
    private final T resolvedAs;
    private final List<ResolvedNode<?>> children = new ArrayList<>();

    public ResolvedNode(Node node, T resolvedAs) {
        this.node = node;
        this.resolvedAs = resolvedAs;
    }

    public void addChild(ResolvedNode<?> resolvedNode) {
        children.add(resolvedNode);
    }

    public List<ResolvedNode<?>> getChildren() {
        return children;
    }

    public Node getNode() {
        return node;
    }

    public T getResolvedAs() {
        return resolvedAs;
    }
}
