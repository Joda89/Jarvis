package fr.lore.dom;

import java.io.StringWriter;
import java.util.Locale;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

public class Jarvis {



    public static void main(String[] args)
            throws MaryConfigurationException, TransformerFactoryConfigurationError, TransformerException {

        // init mary
        LocalMaryInterface mary = null;
        try {
            mary = new LocalMaryInterface();
        } catch (MaryConfigurationException e) {
            System.err.println("Could not initialize MaryTTS interface: " + e.getMessage());
            throw e;
        }

        mary.setLocale(Locale.FRENCH);
        mary.setOutputType("PHONEMES");

        // synthesize
        Document phonemes = null;
        try {
            phonemes = mary.generateXML("Salut");
        } catch (SynthesisException e) {
            System.err.println("Synthesis failed: " + e.getMessage());
            System.exit(1);
        }

        DOMSource domSource = new DOMSource(phonemes);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StringWriter sw = new StringWriter();
        StreamResult sr = new StreamResult(sw);
        transformer.transform(domSource, sr);

        System.out.println(sw.toString());

    }

}