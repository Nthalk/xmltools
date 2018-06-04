package com.iodesysems.xml.tools;

import com.iodesysems.xml.tools.loaded.NodeLoadErrored;

public interface RequiresNodeResolution {
    void resolveNodes(XmlLoader.NodeResolver nodeResolver) throws NodeLoadErrored;
}
