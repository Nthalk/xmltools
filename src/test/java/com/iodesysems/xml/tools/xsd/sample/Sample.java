package com.iodesysems.xml.tools.xsd.sample;

import com.iodesysems.xml.tools.loaded.NodeLoadErrored;
import com.iodesystems.fn.Fn;
import com.iodesysems.xml.tools.RequiresNodeResolution;
import com.iodesysems.xml.tools.ResolvedNode;
import com.iodesysems.xml.tools.XmlLoader;
import com.iodesysems.xml.tools.xsd.external.External;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "sample")
@XmlType(propOrder = {})
public class Sample implements RequiresNodeResolution {
    @XmlAnyElement(lax = true)
    private List<Object> children;
    private SampleEmbedded sampleEmbedded;
    private List<External> externals = new ArrayList<>();

    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }

    public List<External> getExternal() {
        return externals;
    }

    public SampleEmbedded getSampleEmbedded() {
        return sampleEmbedded;
    }

    @Override
    public void resolveNodes(XmlLoader.NodeResolver nodeResolver) throws NodeLoadErrored {
        List<ResolvedNode<?>> resolve = nodeResolver.resolve(this, children);
        Fn.of(resolve)
                .depth(ResolvedNode::getChildren)
                .each(n -> {
                    Object resolvedAs = n.getResolvedAs();
                    if (resolvedAs instanceof SampleEmbedded) {
                        sampleEmbedded = ((SampleEmbedded) resolvedAs);
                    } else if (resolvedAs instanceof External) {
                        externals.add((External) resolvedAs);
                    }
                }).consume();

    }
}
