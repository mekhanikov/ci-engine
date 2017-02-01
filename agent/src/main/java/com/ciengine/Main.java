package com.ciengine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by emekhanikov on 01.02.2017.
 */
public class Main {
    public static void main(String[] ss) {
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try {
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement = doc.createElementNS("http://crunchify.com/CrunchifyCreateXMLDOM", "Companies");
            doc.appendChild(mainRootElement);

            // append child elements to root element
            mainRootElement.appendChild(getCompany(doc, "1", "Paypal", "Payment", "1000"));
            mainRootElement.appendChild(getCompany(doc, "2", "eBay", "Shopping", "2000"));
            mainRootElement.appendChild(getCompany(doc, "3", "Google", "Search", "3000"));

            doc = readXML("/Users/evgenymekhanikov/prj/ci-engine/tmp/sources/_commerce_entitlements/pom.xml");
            List<String> dependencies = new ArrayList<>();
            NodeList deps = doc.getElementsByTagName("dependency");
            for (int i = 0; i < deps.getLength(); i++) {
                Node dep = deps.item(i);
                if (dep.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) dep;
                    String artifactId = eElement
                            .getElementsByTagName("artifactId")
                            .item(0)
                            .getTextContent();

                    String groupId = eElement
                            .getElementsByTagName("groupId")
                            .item(0)
                            .getTextContent();
                    dependencies.add(groupId + ":" + artifactId);

                }
            }

            //doc.getElementsByTagName("cis-module.version").item(0).getTextContent()
            // doc.getElementsByTagName("dependency").item(5).getTextContent()
            // output DOM XML to console
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);

            System.out.println("\nXML DOM Created Successfully..");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getCompany(Document doc, String id, String name, String age, String role) {
        Element company = doc.createElement("Company");
        company.setAttribute("id", id);
        company.appendChild(getCompanyElements(doc, company, "Name", name));
        company.appendChild(getCompanyElements(doc, company, "Type", age));
        company.appendChild(getCompanyElements(doc, company, "Employees", role));
        return company;
    }

    // utility method to create text node
    private static Node getCompanyElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public static Document readXML(String xml) {
        String role1 = null;
        String role2 = null;
        String role3 = null;
        String role4 = null;
        ArrayList<String> rolev;
        rolev = new ArrayList<>();
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            dom = db.parse(xml);
            return dom;


        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
return null;
    }
}
