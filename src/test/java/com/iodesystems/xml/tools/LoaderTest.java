package com.iodesystems.xml.tools;

import com.iodesystems.xml.tools.loaded.FileLoaded;
import com.iodesystems.xml.tools.loaded.LoadErrored;
import com.iodesystems.xml.tools.loaded.Loaded;
import com.iodesystems.xml.tools.loaded.XmlLocation;
import com.iodesystems.xml.tools.xsd.external.External;
import com.iodesystems.xml.tools.xsd.sample.Sample;
import com.iodesystems.xml.tools.xsd.sample.SampleEmbedded;
import org.junit.Test;

public class LoaderTest {

  @Test
  public void test() {
    XmlLoader xmlloader =
        new XmlLoaderBuilder()
            .addClass(Sample.class)
            .addClass("classpath:sample.xsd", SampleEmbedded.class)
            .addClass("classpath:external.xsd", External.class)
            .build();
    try {
      Loaded<Sample> load = xmlloader.load(Sample.class, "classpath:sample.xml");
      Sample sample = load.getValue();
      SampleEmbedded sampleEmbedded = sample.getSampleEmbedded();
      String sampleValue = sampleEmbedded.getSampleValue();
      XmlLocation location = load.getLocation(sample.getExternal().get(1));
      String s = location.toString();
      load = ((FileLoaded<Sample>) load).reload();
      System.out.println();

    } catch (LoadErrored loadErrored) {
      loadErrored.printStackTrace();
    }
  }
}
