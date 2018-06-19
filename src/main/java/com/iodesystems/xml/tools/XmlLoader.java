package com.iodesystems.xml.tools;

import com.iodesystems.xml.tools.loaded.FileLoadErrored;
import com.iodesystems.xml.tools.loaded.FileLoaded;
import com.iodesystems.xml.tools.loaded.Loaded;
import com.iodesystems.xml.tools.loaded.NodeLoadErrored;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Unmarshaller.Listener;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlLoader {

  private final Map<String, Class> xmlIdentifierToClass = new HashMap<>();
  private final JAXBContext jaxbContext;
  private final XMLInputFactory xmlInputFactory;
  private final Map<String, Schema> schemasByLocation = new HashMap<>();
  private final Map<Class<?>, String> classesBySchemaLocation;

  public XmlLoader(Map<Class<?>, String> classesBySchemaLocation)
      throws JAXBException, MalformedURLException, SAXException {
    this.classesBySchemaLocation = classesBySchemaLocation;
    jaxbContext =
        JAXBContext.newInstance(classesBySchemaLocation.keySet().toArray(new Class<?>[0]));
    xmlInputFactory = XMLInputFactory.newFactory();

    // Preload all schemas
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    for (String schemaLocation : classesBySchemaLocation.values()) {
      if (schemaLocation == null) {
        continue;
      }
      initSchema(schemaFactory, schemaLocation);
    }

    // Register xmlIdentifiers
    for (Class<?> cl : classesBySchemaLocation.keySet()) {
      XmlRootElement xmlElementName = cl.getAnnotation(XmlRootElement.class);
      if (xmlElementName == null) {
        continue;
      }
      String name = xmlElementName.name();
      String namespace = xmlElementName.namespace();
      if ("##default".equals(namespace)) {
        XmlSchema xmlElementNamespace = cl.getPackage().getAnnotation(XmlSchema.class);
        if (xmlElementNamespace == null) {
          continue;
        }
        namespace = xmlElementNamespace.namespace();
      }
      xmlIdentifierToClass.put(namespace + ":" + name, cl);
    }
  }

  public <T> Loaded<T> load(Class<T> cls, String source) throws FileLoadErrored, NodeLoadErrored {
    if (source.startsWith("filesystem:")) {
      try {
        return load(cls, source, new FileInputStream(source.substring("filesystem:".length())));
      } catch (FileNotFoundException e) {
        throw new FileLoadErrored(source, cls, e, this);
      }
    } else if (source.startsWith("classpath:")) {
      return load(
          cls,
          source,
          getClass().getClassLoader().getResourceAsStream(source.substring("classpath:".length())));
    } else {
      try {
        throw new IllegalArgumentException("Unknown scheme: " + source);
      } catch (IllegalArgumentException e) {
        throw new FileLoadErrored(source, cls, e, this);
      }
    }
  }

  public <T> Loaded<T> load(Class<T> cls, String fileSource, InputStream inputStream)
      throws FileLoadErrored, NodeLoadErrored {
    try {
      XMLStreamReader xsr = xmlInputFactory.createXMLStreamReader(inputStream);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      String validateSchema = classesBySchemaLocation.get(cls);
      if (validateSchema != null) {
        unmarshaller.setSchema(schemasByLocation.get(validateSchema));
      }
      Locator locator = new Locator(xsr);
      final List<Object> loaded = new ArrayList<>();
      final Listener listener = locator.getListener();

      unmarshaller.setListener(
          new Listener() {
            @Override
            public void beforeUnmarshal(Object target, Object parent) {
              loaded.add(target);
              listener.beforeUnmarshal(target, parent);
            }
          });
      T value = unmarshaller.unmarshal(xsr, cls).getValue();
      postProcess(loaded, locator);
      return new FileLoaded<>(fileSource, value, locator, this);
    } catch (XMLStreamException | JAXBException | XmlValidationException e) {
      throw new FileLoadErrored(fileSource, cls, e, this);
    }
  }

  private void initSchema(SchemaFactory schemaFactory, String validateSchemaLocation)
      throws SAXException, MalformedURLException {
    Schema schema = schemasByLocation.get(validateSchemaLocation);
    if (schema != null) {
      return;
    }
    if (validateSchemaLocation.startsWith("classpath:")) {
      schema =
          schemaFactory.newSchema(
              new StreamSource(
                  getClass()
                      .getClassLoader()
                      .getResourceAsStream(
                          validateSchemaLocation.substring("classpath:".length()))));
    } else if (validateSchemaLocation.startsWith("http")) {
      schema = schemaFactory.newSchema(new URL(validateSchemaLocation));
    } else {
      schema = schemaFactory.newSchema(new File(validateSchemaLocation));
    }
    schemasByLocation.put(validateSchemaLocation, schema);
  }

  public <T> T load(Class<T> cls, Node node, Locator nodeLocator) throws NodeLoadErrored {
    try {
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      final List<Object> loaded = new ArrayList<>();
      unmarshaller.setListener(
          new Listener() {
            @Override
            public void beforeUnmarshal(Object target, Object parent) {
              loaded.add(target);
            }
          });
      JAXBElement<T> unmarshal = unmarshaller.unmarshal(node, cls);
      T value = unmarshal.getValue();
      postProcess(loaded, nodeLocator);
      return value;
    } catch (JAXBException | XmlValidationException e) {
      throw new NodeLoadErrored(node, nodeLocator, cls, e, this);
    }
  }

  private void postProcess(List<Object> values, Locator locator)
      throws NodeLoadErrored, XmlValidationException {
    for (Object value : values) {
      if (value instanceof RequiresNodeResolution) {
        ((RequiresNodeResolution) value).resolveNodes(new NodeResolver(this, locator));
      }
      if (value instanceof XmlValidated) {
        ((XmlValidated) value).validate();
      }
    }
  }

  public static class NodeResolver {

    private final XmlLoader xmlLoader;
    private final Locator locator;

    public NodeResolver(XmlLoader xmlLoader, Locator locator) {
      this.xmlLoader = xmlLoader;
      this.locator = locator;
    }

    @SuppressWarnings("unchecked")
    public ResolvedNode<?> resolve(Object parent, Object node) throws NodeLoadErrored {
      if (node == null) {
        return null;
      }
      if (node instanceof Node) {
        Node element = (Node) node;
        String nodeName = element.getLocalName();
        String namespaceURI = element.getNamespaceURI();
        Class resolveClass = xmlLoader.xmlIdentifierToClass.get(namespaceURI + ":" + nodeName);
        if (resolveClass != null) {
          Object load = xmlLoader.load(resolveClass, element, null);
          locator.alias(parent, load);
          ResolvedNode<Object> resolvedNode = new ResolvedNode<>(element, load);
          return resolvedNode;
        } else {
          ResolvedNode<Object> resolvedNode = new ResolvedNode<>(element, null);
          NodeList childNodes = element.getChildNodes();
          for (int i = 0; i < childNodes.getLength(); i++) {
            resolvedNode.addChild(resolve(parent, childNodes.item(i)));
          }
          return resolvedNode;
        }
      } else {
        return new ResolvedNode<>(null, node);
      }
    }

    public List<ResolvedNode<?>> resolveAll(Object parent, List<Object> nodes)
        throws NodeLoadErrored {
      if (nodes == null) {
        return null;
      }
      List<ResolvedNode<?>> processed = new ArrayList<>(nodes.size());
      for (Object node : nodes) {
        processed.add(resolve(parent, node));
      }
      return processed;
    }
  }
}
