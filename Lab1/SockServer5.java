import java.net.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class SockServer5 {

	public final static String XML_PATH = "totals.xml";
	public final static String ELEMENT = "element";
	public final static String TOTAL = "total";
	public final static String CLIENT_ID = "clientID";
	public final static String ROOT = "root";

	public static void main(String args[]) throws Exception {
		ServerSocket serv = null;
		InputStream in = null;
		OutputStream out = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Socket sock = null;
		Integer clientId = 0, x = 0;
		
		try {
			serv = new ServerSocket(8888);
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (serv.isBound() && !serv.isClosed()) {
			System.out.println("Server5 Ready...");
			try {
				sock = serv.accept();
				
				in = sock.getInputStream();
				out = sock.getOutputStream();
				dis = new DataInputStream(in);
				dos = new DataOutputStream(out);

				char c = (char) dis.read();
				System.out.print("Server5 received " + c);
				if (args.length > 0)
					Thread.sleep(Long.parseLong(args[0]));
				switch (c) {
				case 'r':
					clientId = dis.readInt();
					if(isClientExist(clientId)) {
						modifyXML(clientId, 0);
						dos.writeInt(0);
					}	
					break;
				case 't':
					clientId = dis.readInt();
					x = dis.readInt();
					System.out.print(" for client " + clientId + " " + x);
					Integer total = readXML(clientId);
					if (total == null) {
						total = 0;
					}
					modifyXML(clientId, total + x);
					dos.writeInt(readXML(clientId));
					break;
				default:
					Integer x2 = dis.readInt();
					Integer y = dis.readInt();
					System.out.print(" " + x2 + " " + y);
					dos.writeInt(x2 + y);
				}
				System.out.println("");
				dos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (dis != null)
					dis.close();
				if (out != null)
					out.close();
				if (in != null)
					in.close();
				if (sock != null)
					sock.close();
			}
		}
	}

	// create XML if not present
	public static void createXML(File file) {

		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root element
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("root");
		doc.appendChild(rootElement);

		saveXML(doc);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	  
	}
	
	public static Integer readXML(Integer id) {

		try {
			File xmlFile = new File(XML_PATH);
			if(!xmlFile.exists())
				createXML(xmlFile);
			
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(ELEMENT);

			for (int i = 0; i < nList.getLength(); i++) {

				Node n = nList.item(i);

				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) n;
					if (id.equals(Integer.parseInt((element.getElementsByTagName(CLIENT_ID).item(0).getTextContent()))))
						return Integer.parseInt(element.getElementsByTagName(TOTAL).item(0).getTextContent());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Boolean isClientExist(Integer id) {

		try {
			File xmlFile = new File(XML_PATH);
			if(!xmlFile.exists())
				createXML(xmlFile);
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(ELEMENT);

			for (int i = 0; i < nList.getLength(); i++) {

				Node n = nList.item(i);

				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) n;
					if (id.equals(
							Integer.parseInt((element.getElementsByTagName(CLIENT_ID).item(0).getTextContent())))) {
						return Boolean.TRUE;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

	public static Integer modifyXML(Integer id, Integer total) {
		
		
		try {
			File xmlFile = new File(XML_PATH);
			if(!xmlFile.exists())
				createXML(xmlFile);
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(ELEMENT);

			for (int i = 0; i < nList.getLength(); i++) {

				Node n = nList.item(i);

				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) n;
					if (id.equals(
							Integer.parseInt((element.getElementsByTagName(CLIENT_ID).item(0).getTextContent())))) {

						// modify already present data
						element.getElementsByTagName(TOTAL).item(0).setTextContent(total.toString());
						saveXML(doc);
						
						return total;
					}
				}
			}

			// add new data
			Element addNewClient = doc.createElement(CLIENT_ID);
			addNewClient.appendChild(doc.createTextNode(id.toString()));
			Element addNewTotal = doc.createElement(TOTAL);
			addNewTotal.appendChild(doc.createTextNode(total.toString()));

			Node m = doc.getElementsByTagName(ROOT).item(0);
			Node addNewElement = m.appendChild(doc.createElement(ELEMENT));
			addNewElement.appendChild(addNewClient);
			addNewElement.appendChild(addNewTotal);

			saveXML(doc);

			return total;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	// Transformer API
	public static synchronized void saveXML(Document doc) {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(XML_PATH));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
