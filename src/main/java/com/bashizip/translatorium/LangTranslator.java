/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bashizip.translatorium;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
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

    static void init() {

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
        for (int i = 0; i < nodes.getLength(); i++) {
            Node textAttrib = nodes.item(i).getAttributes().getNamedItem("Text");
            String val = textAttrib.getNodeValue();
            String newValue = Translatorium.translate(langFrom, langTo, val);
            System.out.println(val + " translated to " + newValue);
            textAttrib.setNodeValue(newValue);
        }

        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        xformer.transform(new DOMSource(input), new StreamResult(translatedFile));

        return translatedFile;
    }

}
