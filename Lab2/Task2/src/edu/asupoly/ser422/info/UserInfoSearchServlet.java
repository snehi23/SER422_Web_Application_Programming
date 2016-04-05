package edu.asupoly.ser422.info;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class UserInfoSearchServlet extends HttpServlet {
	private static String _filename = null;
	private static String path = null;
	private static String xmlPath = null; 
	private static String ELEMENT = "element";
	private static String FNAME = "fname";
	private static String LNAME = "lname";
	private static String PROGLANGUAGES = "proglanguages";
	private static String DOW = "daysofweek";
	private static String HAIRCOLOR = "haircolor";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		_filename = config.getInitParameter("userinfobook");
		path = System.getProperty("catalina.home");
		
		xmlPath = path + _filename;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		File file = new File(xmlPath);
		
		if(!file.exists())
			UserInfoServlet.createXML();
		
		UserInfo userInfo = null;
		ArrayList<UserInfo> records = null;
		int count = 0;
		
		String fname = req.getParameter("fname");
		String lname = req.getParameter("lname");
		String[] progLanguages = req.getParameterValues("progLanguages");
		String[] daysOfWeek = req.getParameterValues("daysOfWeek");
		String hairColor = req.getParameter("hairColor");

		if (fname == "" && lname == "" && progLanguages == null && daysOfWeek == null && hairColor == "")
			records = fetchAll(xmlPath);
		else {
			userInfo = new UserInfo(fname, lname, progLanguages, daysOfWeek, hairColor);
			records = searchUser(xmlPath, userInfo);
		}

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		String userAgent = req.getHeader("User-Agent");
		if (userAgent.contains("Chrome"))
			out.println("<HTML><HEAD><TITLE>VIEW CODER DETAILS</TITLE></HEAD><BODY bgcolor=pink>");
		else
			out.println("<HTML><HEAD><TITLE>VIEW CODER DETAILS</TITLE></HEAD><BODY>");

		for (UserInfo ui : records) {
			count++;
			out.println(count + ". " + ui.fname + " " + ui.lname + " " + Arrays.toString(ui.languages) + " "
					+ Arrays.toString(ui.daysOfWeek) + " " + ui.hairColor + "<br>");
		}
		out.print("<a href='" + getServletContext().getContextPath() + "/index.html'> Return Home</a>");
		out.println("</BODY></HTML>");

	}

	public ArrayList<UserInfo> fetchAll(String path) {

		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		ArrayList<UserInfo> records = new ArrayList<>();

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

		NodeList nList = doc.getElementsByTagName(ELEMENT);

		for (int i = 0; i < nList.getLength(); i++) {

			Node n = nList.item(i);

			if (n.getNodeType() == Node.ELEMENT_NODE && n != null) {
				Element element = (Element) n;

				NodeList langList = element.getElementsByTagName(PROGLANGUAGES).item(0).getChildNodes();
				List<String> llist = new ArrayList<String>();
				for (int j = 0; j < langList.getLength(); j++) {

					Node lang = langList.item(j);
					Element el = (Element) lang;
					llist.add(langList.item(j).getTextContent());

				}

				NodeList dowList = element.getElementsByTagName(DOW).item(0).getChildNodes();
				List<String> dlist = new ArrayList<String>();
				for (int j = 0; j < dowList.getLength(); j++) {

					Node day = dowList.item(j);
					Element el = (Element) day;
					dlist.add(dowList.item(j).getTextContent());

				}

				records.add(new UserInfo(element.getElementsByTagName(FNAME).item(0).getTextContent(),
						element.getElementsByTagName(LNAME).item(0).getTextContent(),
						llist.toArray(new String[llist.size()]), dlist.toArray(new String[dlist.size()]),
						element.getElementsByTagName(HAIRCOLOR).item(0).getTextContent()));
			}
		}

		return records;

	}

	public ArrayList<UserInfo> searchUser(String path, UserInfo userInfo) {

		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		ArrayList<UserInfo> records = new ArrayList<>();

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

		NodeList nList = doc.getElementsByTagName(ELEMENT);

		for (int i = 0; i < nList.getLength(); i++) {

			Node n = nList.item(i);
			Boolean isExist = false;

			if (n.getNodeType() == Node.ELEMENT_NODE && n != null) {
				Element element = (Element) n;

				if (!element.getElementsByTagName(FNAME).item(0).getTextContent().contains(userInfo.fname))
					continue;

				if (!element.getElementsByTagName(LNAME).item(0).getTextContent().contains(userInfo.lname))
					continue;

				NodeList langList = element.getElementsByTagName(PROGLANGUAGES).item(0).getChildNodes();
				List<String> llist = new ArrayList<String>();
				for (int j = 0; j < langList.getLength(); j++) {

					Node lang = langList.item(j);
					Element el = (Element) lang;
					llist.add(langList.item(j).getTextContent());

				}

				if (userInfo.languages != null) {
					for (int j = 0; j < userInfo.languages.length; j++) {
						if (llist.contains(userInfo.languages[j])) {
							isExist = true;
							break;
						}
					}

					if (!isExist)
						continue;
				}

				NodeList dowList = element.getElementsByTagName(DOW).item(0).getChildNodes();
				List<String> dlist = new ArrayList<String>();
				for (int j = 0; j < dowList.getLength(); j++) {

					Node day = dowList.item(j);
					Element el = (Element) day;
					dlist.add(dowList.item(j).getTextContent());

				}

				isExist = false;

				if (userInfo.daysOfWeek != null) {

					for (int j = 0; j < userInfo.daysOfWeek.length; j++) {
						if (dlist.contains(userInfo.daysOfWeek[j])) {
							isExist = true;
							break;
						}
					}

					if (!isExist)
						continue;
				}

				if (!element.getElementsByTagName(HAIRCOLOR).item(0).getTextContent().contains(userInfo.hairColor))
					continue;

				records.add(new UserInfo(element.getElementsByTagName(FNAME).item(0).getTextContent(),
						element.getElementsByTagName(LNAME).item(0).getTextContent(),
						llist.toArray(new String[llist.size()]), dlist.toArray(new String[dlist.size()]),
						element.getElementsByTagName(HAIRCOLOR).item(0).getTextContent()));

			}
		}

		return records;

	}

}
