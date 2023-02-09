package Persistence;

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
import java.util.List;

public class SaveXML extends SaveDocument {
    public ArrayList<String> ReadDocument(String path) {
        ArrayList<String> result = new ArrayList<>();
        try {
            File f = new File(path);
            if (f.exists()) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(f);

                if (!document.getDocumentElement().getTagName().equals("document")) return null;
                NodeList nodeList = document.getElementsByTagName("document");
                int i = 0;
                Node act = nodeList.item(i);
                while (act.getNodeType() != Node.ELEMENT_NODE & i < nodeList.getLength())
                    act = nodeList.item(++i);

                Element docElement = (Element) act;
                if (!docElement.getNodeName().contains("document")) return null;
                if (docElement.getElementsByTagName("author").getLength() == 0) return null;
                result.add(docElement.getElementsByTagName("author").item(0).getTextContent());
                if (docElement.getElementsByTagName("title").getLength() == 0) return null;
                result.add(docElement.getElementsByTagName("title").item(0).getTextContent());
                if (docElement.getElementsByTagName("body").getLength() == 0) return null;
                String tmp = docElement.getElementsByTagName("body").item(0).getTextContent();
                result.addAll(List.of(tmp.split("\n")));
            } else return null;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            //throw new RuntimeException(e);
        }
        return result;
    }

    public void WriteDocument(String path, ArrayList<String> data) {
        try {
            File f = new File(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();

            Element documentElement = doc.createElement("document");
            doc.appendChild(documentElement);

            Element authorElement = doc.createElement("author");
            authorElement.setTextContent(data.remove(0));
            Element titleElement = doc.createElement("title");
            titleElement.setTextContent(data.remove(0));
            Element bodyElement = doc.createElement("body");
            StringBuilder tmp = new StringBuilder();
            for (String s : data) tmp.append(s).append('\n');
            bodyElement.setTextContent(tmp.toString());

            documentElement.appendChild(authorElement);
            documentElement.appendChild(titleElement);
            documentElement.appendChild(bodyElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(f));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException | FileNotFoundException e) {
            //throw new RuntimeException(e);
        }
    }
}
