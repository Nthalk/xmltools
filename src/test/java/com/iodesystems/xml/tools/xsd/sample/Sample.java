package com.iodesystems.xml.tools.xsd.sample;

import com.iodesystems.fn.Fn;
import com.iodesystems.xml.tools.RequiresNodeResolution;
import com.iodesystems.xml.tools.ResolvedNode;
import com.iodesystems.xml.tools.XmlLoader;
import com.iodesystems.xml.tools.loaded.NodeLoadErrored;
import com.iodesystems.xml.tools.xsd.external.External;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "sample")
@XmlType(propOrder = {})
public class Sample implements RequiresNodeResolution {

  private final List<External> externals = new ArrayList<>();

  @XmlAnyElement(lax = true)
  private List<Object> children;

  private SampleEmbedded sampleEmbedded;

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
    List<ResolvedNode<?>> resolve = nodeResolver.resolveAll(this, children);
    Fn.of(resolve)
        .depth(ResolvedNode::getChildren)
        .each(
            n -> {
              Object resolvedAs = n.getResolvedAs();
              if (resolvedAs instanceof SampleEmbedded) {
                sampleEmbedded = ((SampleEmbedded) resolvedAs);
              } else if (resolvedAs instanceof External) {
                externals.add((External) resolvedAs);
              }
            })
        .consume();
  }
}
