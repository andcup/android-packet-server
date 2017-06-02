package com.andcup.hades.hts.core.tools;

import com.andcup.hades.hts.server.utils.LogUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/24 17:46.
 * Description:
 */
public class XmlMatchEditor {

    public interface Match{
        void match(Document doc, String key, String value);

        Match APK = new Match() {
            @Override
            public void match(Document doc, String key, String value) {
                NodeList employees = doc.getElementsByTagName("meta-data");
                Element emp = null;
                //loop for each employee
                for (int i = 0; i < employees.getLength(); i++) {
                    emp = (Element) employees.item(i);
                    String gender = emp.getAttribute("android:name");
                    if (gender.equals(key)) {
                        emp.setAttribute("android:value", value);
                    }
                }
            }
        };

        Match IPA = new Match() {
            @Override
            public void match(Document doc, String key, String value) {
                NodeList nodeList = doc.getElementsByTagName("string");
                Element emp = null;
                //loop for each employee
                for (int i = 0; i < nodeList.getLength(); i++) {
                    emp = (Element) nodeList.item(i);
                    String nodeValue = emp.getTextContent();
                    if (null != nodeValue && nodeValue.equals(key)) {
                        emp.setTextContent(value);
                    }
                }
            }
        };
    }

    Match match;

    public XmlMatchEditor(Match match){
        this.match = match;
    }

    public boolean edit(String src, String dst, Map<String, String> maps) {
        // delete dst file.
        new File(dst).delete();
        // edit src file.
        File xmlFile = new File(src);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            for (String key : maps.keySet()) {
                String value = maps.get(key);
                match.match(document, key, value);
            }
            document.getDocumentElement().normalize();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(dst));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            return true;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return false;
    }
}