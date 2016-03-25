import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

class SockServer6 {

	public static void main(String args[]) throws Exception {

		ServerSocket serv = null;
		Integer delay = 0;
		ExecutorService executorService = Executors.newFixedThreadPool(150);
		try {
			serv = new ServerSocket(8888);
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (serv.isBound() && !serv.isClosed()) {
			System.out.println("Server6 Ready...");
			try {
				if (args.length > 0)
					delay = Integer.parseInt(args[0]);
				
				Socket sock = serv.accept();
				executorService.execute(new ConnectionHandler(sock,delay));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

class ConnectionHandler implements Runnable {

	private Socket sock;
	private Integer delay;
	private static List<Integer> clientStatus = new LinkedList<Integer>();

	public ConnectionHandler(Socket sock, Integer delay) {
		this.sock = sock;
		this.delay = delay;
	}

	public void run() {
		
		InputStream in = null;
		OutputStream out = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Integer clientId = 0, x = 0;
		XMLParser xmlParser = new XMLParser();
		try {
			Thread.sleep(delay);
			in = sock.getInputStream();
			out = sock.getOutputStream();
			dis = new DataInputStream(in);
			dos = new DataOutputStream(out);

			char c = (char) dis.read();
			System.out.print("Server received " + c);

			switch (c) {
			case 'r':
				clientId = dis.readInt();
				if (xmlParser.isClientExist(clientId)) {
					xmlParser.modifyXML(clientId, 0, true);
					dos.writeInt(0);
				}
				break;
			case 't':
				clientId = dis.readInt();
				x = dis.readInt();
				System.out.print(" for client " + clientId + " " + x);
				Integer total = xmlParser.readXML(clientId);
				if (total == null) {
					total = 0;
				}
				if(clientStatus.contains(clientId)) {
					xmlParser.modifyXML(clientId, total + x,true);
					clientStatus.remove(clientId);
				} else {
					xmlParser.modifyXML(clientId, total + x,false);
					clientStatus.add(clientId);
				}
					dos.writeInt(xmlParser.readXML(clientId));
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
			try {
				if (dos != null)
					dos.close();
				if (dis != null)
					dis.close();
				if (out != null)
					out.close();
				if (in != null)
					in.close();
				if (sock != null)
					sock.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

class XMLParser {

	public final static String XML_PATH = "totals.xml";
	public final static String ELEMENT = "element";
	public final static String TOTAL = "total";
	public final static String CLIENT_ID = "clientID";
	public final static String ROOT = "root";
	
	// create XML if not present
		public void createXML(File file) {

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

	public Integer readXML(Integer id) throws Exception {

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

		return null;
	}

	public Boolean isClientExist(Integer id) throws Exception {

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
				if (id.equals(Integer.parseInt((element.getElementsByTagName(CLIENT_ID).item(0).getTextContent())))) {
					return Boolean.TRUE;
				}
			}
		}

		return Boolean.FALSE;
	}

	public Integer modifyXML(Integer id, Integer total, Boolean status) throws Exception {

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
				if (id.equals(Integer.parseInt((element.getElementsByTagName(CLIENT_ID).item(0).getTextContent())))) {

						element.getElementsByTagName(TOTAL).item(0).setTextContent(total.toString());
						if(status)
							saveXML(doc);
						else
							unsynchronisedSaveXML(doc);
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

			if(status)
				saveXML(doc);
			else
				unsynchronisedSaveXML(doc);

			return total;

	}

	// Transformer API
	public synchronized void saveXML(Document doc) {

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
	
	// Transformer API
		public void unsynchronisedSaveXML(Document doc) {

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
