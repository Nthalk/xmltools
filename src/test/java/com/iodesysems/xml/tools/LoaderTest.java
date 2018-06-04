package com.iodesysems.xml.tools;

import com.iodesysems.xml.tools.loaded.FileLoaded;
import com.iodesysems.xml.tools.loaded.LoadErrored;
import com.iodesysems.xml.tools.loaded.Loaded;
import com.iodesysems.xml.tools.xsd.external.External;
import com.iodesysems.xml.tools.xsd.sample.Sample;
import com.iodesysems.xml.tools.xsd.sample.SampleEmbedded;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.stream.Location;

public class LoaderTest {

    @Test
    public void test() throws JAXBException {
        XmlLoader xmlLoader = new XmlLoader(Sample.class, SampleEmbedded.class, External.class);
        try {
            Loaded<Sample> load = xmlLoader.load(
                    "classpath:sample.xsd",
                    Sample.class,
                    "classpath:sample.xml");
            Sample sample = load.getValue();
            SampleEmbedded sampleEmbedded = sample.getSampleEmbedded();
            String sampleValue = sampleEmbedded.getSampleValue();
            Location location = load.getLocation(sampleEmbedded);
            load = ((FileLoaded<Sample>) load).reload();
            System.out.println();

        } catch (LoadErrored loadErrored) {
            loadErrored.printStackTrace();
        }
    }
}
