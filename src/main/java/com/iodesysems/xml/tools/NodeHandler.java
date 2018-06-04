package com.iodesysems.xml.tools;

import org.w3c.dom.Node;

public interface NodeHandler {
    void onNodeEnter(Node node);

    void onNodeExit(Node node);
}
