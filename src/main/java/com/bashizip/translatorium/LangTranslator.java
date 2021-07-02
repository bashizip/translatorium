/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bashizip.translatorium;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Rachel
 */
public class LangTranslator {

    public static void main(String[] args) throws Exception {
        File f = translate("en", "fr", "en.lang");

        System.out.println("FINISHED !");
        System.out.println("Output file : " + f.getAbsolutePath());
    }

    public static File translate(String langFrom, String langTo, String sourceFile) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        File translatedFile = new File(langTo + ".lang");

        String attribute = "//*[@Text]";
        //read the source file
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        Document input = factory
                .newDocumentBuilder()
                .parse(sourceFile);

        // locate all the Text attributes
        XPath xpath = XPathFactory
                .newInstance()
                .newXPath();
        NodeList nodes = (NodeList) xpath.evaluate(attribute, input, XPathConstants.NODESET);

        //replace values
        System.out.println("Total nodes to translate from " + langFrom + " to " + langTo + ":" + nodes.getLength());
        int count=0;
        for (int i = 0; i < nodes.getLength(); i++) {
            Node textAttrib = nodes.item(i).getAttributes().getNamedItem("Text");
            String val = textAttrib.getNodeValue();
            String newValue = Translatorium.translate(langFrom, langTo, val);
            System.out.println(val + " translated to " + newValue);
            textAttrib.setNodeValue(newValue);
            count++;
            System.out.println("--> " + 100*count/nodes.getLength()+ "%");
        }

        writeToOutputStream(input, new FileOutputStream(translatedFile));
        return translatedFile;
    }

    private static void writeToOutputStream(Document fDoc, OutputStream out) throws UnsupportedEncodingException, IOException, TransformerException, TransformerConfigurationException {
        fDoc.setXmlStandalone(true);
        DOMSource docSource = new DOMSource(fDoc);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes("UTF-8"));
        transformer.transform(docSource, new StreamResult(out));
    }

}
