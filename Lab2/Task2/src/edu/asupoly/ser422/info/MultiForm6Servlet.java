package edu.asupoly.ser422.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MultiForm6Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String hairColor = request.getParameter("hairColor");
		
		if(hairColor != null)
		session.setAttribute("hair_color",hairColor);
		
		
		String fname = (String)session.getAttribute("first_name");
		String lname = (String)session.getAttribute("last_name");
		String[] languages = (String[])session.getAttribute("languages");
		String[] days = (String[])session.getAttribute("days");
		String hair = (String)session.getAttribute("hair_color");
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>ADD CODER DETAILS</TITLE></HEAD><BODY>");
		out.println("Your Choices so far...<br>");
		
		out.println("First Name: "+fname+"<br>");
		out.println("Last Name :"+lname+"<br>");
		out.println("Languages :"+Arrays.toString(languages)+"<br>");
		out.println("Days of Week :"+Arrays.toString(days)+"<br>");
		out.println("Hair Color :"+hair+"<br>");
		
		
		out.println("<form action=\"post_coder\" method=\"POST\">");
		out.println("<input type=\"submit\" value=\"Submit\">");
		out.println("</form>");
		
		out.println("<form action=\"remove\" method=\"POST\">");
		out.println("<input type=\"submit\" value=\"Cancel\">");
		out.println("</form>");
		
		out.println("<form action=\"multiform5\" method=\"POST\">");
		out.println("<input type=\"submit\" value=\"Prev\">");
		out.println("</form>");
		
		out.println("</BODY></HTML>"); 
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
		
	}

}
