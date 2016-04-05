package edu.asupoly.ser422.info;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class UserInfoServlet extends HttpServlet {
	private static String _filename = null;
	private static String path = null;
	private static String xmlPath = null; 
	private static String ROOT = "root"; 
	private static String ELEMENT = "element"; 
	private static String FNAME = "fname"; 
	private static String LNAME = "lname"; 
	private static String PROGLANGUAGES = "proglanguages"; 
	private static String LANGUAGE = "language"; 
	private static String DOW = "daysofweek"; 
	private static String DAY = "day"; 
	private static String HAIRCOLOR = "haircolor"; 
	
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		
		_filename = config.getInitParameter("userinfobook");
		path = System.getProperty("catalina.home");
		
		xmlPath = path + _filename;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException	{
		
		File file = new File(xmlPath);
		
		if(!file.exists())
			createXML();
		
		HttpSession session = req.getSession();
		
		String fname = (String)session.getAttribute("first_name");
		String lname = (String)session.getAttribute("last_name");
		String[] languages = (String[])session.getAttribute("languages");
		String[] days = (String[])session.getAttribute("days");
		String hair = (String)session.getAttribute("hair_color");
		
		if(fname != null && lname != null && languages != null && days != null && hair != null) {
			UserInfo userInfo = new UserInfo(fname,lname,languages,days,hair);
			insertInfo(xmlPath,userInfo);
		}	
		
		res.sendRedirect(getServletContext().getContextPath()+"/remove");
		
	}
	
	
	public void insertInfo(String path,UserInfo userInfo) {
		
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		
		try {
			dBuilder = dbfactory.newDocumentBuilder();
			doc = dBuilder.parse(path);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	

		doc.getDocumentElement().normalize();

		// add new data
		Element addFirstName = doc.createElement(FNAME);
		addFirstName.appendChild(doc.createTextNode(userInfo.fname));
	
		Element addLastName = doc.createElement(LNAME);
		addLastName.appendChild(doc.createTextNode(userInfo.lname));
		
		Element addLanguages = doc.createElement(PROGLANGUAGES);
		
		if(userInfo.languages != null) {
			for(String languages : userInfo.languages){
				Element addLanguage = doc.createElement(LANGUAGE);
				addLanguage.appendChild(doc.createTextNode(languages));	
				addLanguages.appendChild(addLanguage);
			}
		}
		
		Element addDaysOfWeek = doc.createElement(DOW);
		
		if(userInfo.daysOfWeek != null) {
			for(String day : userInfo.daysOfWeek){
				Element addDay = doc.createElement(DAY);
				addDay.appendChild(doc.createTextNode(day));	
				addDaysOfWeek.appendChild(addDay);
			}
		}
		
		Element addHairColor = doc.createElement(HAIRCOLOR);
		addHairColor.appendChild(doc.createTextNode(userInfo.hairColor));
	
		Node m = doc.getElementsByTagName(ROOT).item(0);
		Node addNewElement = m.appendChild(doc.createElement(ELEMENT));
		addNewElement.appendChild(addFirstName);
		addNewElement.appendChild(addLastName);
		addNewElement.appendChild(addLanguages);
		addNewElement.appendChild(addDaysOfWeek);
		addNewElement.appendChild(addHairColor);
		
		saveXML(doc);
		
	}
	
	// create XML if not present
	public static void createXML() {

		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root element
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(ROOT);
		doc.appendChild(rootElement);

		saveXML(doc);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	  
	}
	
	// Transformer API
	public static synchronized boolean saveXML(Document doc) {
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlPath));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			return Boolean.FALSE;
		} catch (TransformerException e) {
			e.printStackTrace();
			return Boolean.FALSE;		
		}
		return Boolean.TRUE;
	}
}
