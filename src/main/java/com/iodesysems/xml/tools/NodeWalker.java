package com.iodesysems.xml.tools;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeWalker {

    public static void walk(Iterable<Node> nodes, NodeHandler nodeHandler) {
        for (Node node : nodes) {
            walk(node, nodeHandler);
        }
    }

    /**
     * Depth first walk of nodes
     *
     * @param node
     */
    public static void walk(Node node, NodeHandler nodeHandler) {
        nodeHandler.onNodeEnter(node);
        NodeList childNodes = node.getChildNodes();
        if (childNodes != null) {
            int length = childNodes.getLength();
            for (int i = 0; i < length; i++) {
                walk(childNodes.item(i), nodeHandler);
            }
        }
        nodeHandler.onNodeExit(node);
    }
}
