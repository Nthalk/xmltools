package com.iodesysems.xml.tools.xsd.external;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "external")
public class External {
    @XmlAttribute(name = "external-attribute")
    String externalAttribute;

    public String getExternalAttribute() {
        return externalAttribute;
    }
}
