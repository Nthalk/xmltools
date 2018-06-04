package com.iodesysems.xml.tools.xsd.sample;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "sample-embededded")
@XmlType(propOrder = {})
public class SampleEmbedded {
    @XmlAttribute(name = "sample-value")
    String sampleValue;

    public String getSampleValue() {
        return sampleValue;
    }
}
