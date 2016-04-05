package edu.asupoly.ser422.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MultiForm3Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String[] langValues = {"C","JAVA","PYTHON","JAVA SCRIPT","OPA"};
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String lname = request.getParameter("lname");
		
		HttpSession session = request.getSession();
		
		if(lname != null)
		session.setAttribute("last_name",lname);
		
		String[] languages = (String[])session.getAttribute("languages");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>ADD CODER DETAILS</TITLE></HEAD><BODY>");
		out.println("Known Programming Languages: <br>");
		out.println("<form action=\"multiform4\" method=\"POST\">");
		
			for(String lang : langValues) {
				
				if(languages != null && Arrays.asList(languages).contains(lang))
					out.println("<input type = \"checkbox\" name = \"progLanguages\" value= \""+lang+"\"checked/>"+lang+"<br>");
				else
					out.println("<input type = \"checkbox\" name = \"progLanguages\" value= \""+lang+"\" />"+lang+"<br>");
			}
			
		out.println("<input type=\"submit\" value=\"Next\">");
		out.println("</form>");
		
		out.println("<form action=\"multiform2\" method=\"POST\">");
		out.println("<input type=\"submit\" value=\"Prev\">");
		out.println("</form>");
		
		out.println("</BODY></HTML>"); 
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
		
	}

}
