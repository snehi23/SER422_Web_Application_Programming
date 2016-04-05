package edu.asupoly.ser422.info;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String _filename = null;
	private static String path = null;
	private static String xmlPath = null; 
	
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		
		_filename = config.getInitParameter("userinfobook");
		path = System.getProperty("catalina.home");
		
		xmlPath = path + _filename;
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int count =0;
		HttpSession session = request.getSession();
		
		File file = new File(xmlPath);
		
		if(!file.exists())
			UserInfoServlet.createXML();
		
		String fname = (String)session.getAttribute("login_first_name");
		String lname = (String)session.getAttribute("login_last_name");
		
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		out.println("<HTML><HEAD><TITLE>ADD CODER DETAILS</TITLE></HEAD><BODY>");
		
		if(fname == null && lname == null) {
			out.println("<a href='" + getServletContext().getContextPath() + "/login.html'> Login here...</a>");
		} else {
			
			out.println("Welcome Back "+fname);	
			out.println("Not you ??");
			out.println("&nbsp; &nbsp; <a href='" + getServletContext().getContextPath() + "/login.html'> Login here...</a>");	
			
			out.println("<br>Top 3 Search:<br>");
			
			ArrayList<UserInfo> users= new UserInfoSearchServlet().fetchAll(xmlPath);
			
			for(UserInfo user : users) {
				
				count++;
				out.println(count+". "+"fname: "+user.fname+" lname: "+user.lname+" languages: "+Arrays.toString(user.languages)+" days: "+Arrays.toString(user.daysOfWeek)+" hair color: "+user.hairColor+"<br>");
				if(count == 3) break;
				
			}		
			out.println("<A href=\""+response.encodeURL("multiform1")+"\">Multi form here...</A></BODY>");
			
		}
		out.println("</BODY></HTML>"); 
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
		
	}

}
