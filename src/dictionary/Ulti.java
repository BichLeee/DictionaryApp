/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author HP
 */
public class Ulti {

    public static HashMap<String, String> readCSVFile(String filepath) {
        HashMap<String, String> result = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 2) {
                    continue;
                }
                String key = fields[0];
                String value = fields[1];
                result.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void writeCSVFile(HashMap<String, String> src, String despath) {
        try (FileWriter writer = new FileWriter(despath)) {
            for (String key : src.keySet()) {
                writer.write(key + "," + src.get(key) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> readXmlDomParser(String filepath) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(filepath);
            InputStream inputStream = new FileInputStream(file);
            Document doc = db.parse(inputStream, "UTF-8");
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("record");
            HashMap<String, String> result_list = new HashMap<String, String>();

            for (int idx = 0; idx < list.getLength(); idx++) {
                Node node_temp = list.item(idx);
                if (node_temp.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node_temp;
                    String word = el.getElementsByTagName("word").item(0).getTextContent();
                    String meaning = el.getElementsByTagName("meaning").item(0).getTextContent();
                    result_list.put(word, meaning);
                } else {
                }
            }
            //System.out.print(result_list.get("school"));
            return result_list;
        } catch (IOException|ParserConfigurationException|SAXException ex) {
            return null;
        }
    }

    public static void writeXmlDomParser(String filepath, HashMap<String, String> data) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element rootElement = doc.createElement("dictionary");
            doc.appendChild(rootElement);

            for (Map.Entry<String, String> entry : data.entrySet()) {
                Element recordElement = doc.createElement("record");
                rootElement.appendChild(recordElement);

                Element wordElement = doc.createElement("word");
                wordElement.appendChild(doc.createTextNode(entry.getKey()));
                recordElement.appendChild(wordElement);

                Element meaningElement = doc.createElement("meaning");
                meaningElement.appendChild(doc.createTextNode(entry.getValue()));
                recordElement.appendChild(meaningElement);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            File file = new File(filepath);
            transformer.transform(new DOMSource(doc, "UTF-8"), new StreamResult(file));
        } catch (TransformerException | ParserConfigurationException ex) {

        }
    }

    public static void removeBOM(String input_file, String output_file) throws FileNotFoundException, IOException {
        File inputFile = new File(Constants.DataPath + input_file);
        File outputFile = new File(Constants.DataPath + output_file);

        InputStream inputStream = new FileInputStream(inputFile);
        byte[] bytes = inputStream.readAllBytes();
        inputStream.close();

        // Check if the file starts with BOM
        int offset = 0;
        if (bytes.length > 2 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
            offset = 3;
        }

        OutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(bytes, offset, bytes.length - offset);
        outputStream.close();
    }

    class Helper {

        public static String unicodeToASCII(String s) {
            String s1 = Normalizer.normalize(s, Normalizer.Form.NFKD);
            String regex
                    = "[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+";
            String s2 = null;
            try {
                s2 = new String(s1.replaceAll(regex, "").getBytes("ascii"),
                        "ascii");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
            return s2;
        }
    }
}
