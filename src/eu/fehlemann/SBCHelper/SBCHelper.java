package eu.fehlemann.SBCHelper;

import eu.fehlemann.SBCHelper.processes.Processor;
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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SBCHelper {

    public static Document process(File file, Processor[] processors) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        return process(file, processors, "CubeBlocks", "Definition");
    }

    public static Map<String, Integer> preflightTieredDefinitions(File file) throws ParserConfigurationException, IOException, SAXException {
        Map<String, Integer> result = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        Document doc = getDocument(file);
        NodeList blocks = doc.getElementsByTagName("CubeBlocks");
        Node block = blocks.item(0);
        NodeList definitions = doc.getElementsByTagName("Definition");
        for (int i = 0; i < definitions.getLength(); i++) {
            Element element = (Element) definitions.item(i);
            SBCID id = getDefinitionId(element);
            if (id != null) {
                System.out.println("Current Definition: " + id.toString());
                String text = scanner.nextLine();
                if (text.trim().equals("")) {
                    continue;
                }
                result.put(id.toString(), Integer.valueOf(text));
            }
        }
        return result;
    }

    public static Document process(File file, Processor[] processors, String root, String elements) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Document doc = getDocument(file);
        NodeList blocks = doc.getElementsByTagName(root);
        Node block = blocks.item(0);
        NodeList definitions = doc.getElementsByTagName(elements);
        ArrayList<Node> remove = new ArrayList<>();
        for (int i = 0; i < definitions.getLength(); i++) {
            Element element = (Element) definitions.item(i);
            SBCID id = getDefinitionId(element);
            if (id != null) {
                System.out.println("Current Definition: " + id.toString());
                for (Processor rep : processors) {
                    if (!rep.process(id, element)) {
                        remove.add(element);
                        break;
                    }
                }
            }
        }
        for (Node n : remove) {
            block.removeChild(n);
        }
        return doc;
    }

    public static Document getDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.normalizeDocument();
        return doc;
    }

    public static void writeDocument(Document doc, File file) throws TransformerException, FileNotFoundException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        doc.getDocumentElement().normalize();
        DOMSource source = new DOMSource(doc);
        StreamResult console = new StreamResult(new FileOutputStream(file));
        transformer.transform(source, console);
    }

    public static SBCID getDefinitionId(Element ele) {
        NodeList idList = ele.getElementsByTagName("Id");
        if (idList != null && idList.getLength() > 0) {
            NodeList typeId = ((Element) idList.item(0)).getElementsByTagName("TypeId");
            NodeList subtypeId = ((Element) idList.item(0)).getElementsByTagName("SubtypeId");
            return new SBCID(typeId.item(0).getTextContent(), subtypeId.item(0).getTextContent());
        } else if (ele.getParentNode() != null) {
            return getDefinitionId((Element) ele.getParentNode());
        } else {
            return null;
        }
    }

}
