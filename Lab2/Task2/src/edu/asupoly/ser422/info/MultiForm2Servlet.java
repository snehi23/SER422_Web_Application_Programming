package edu.asupoly.ser422.info;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MultiForm2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fname = request.getParameter("fname");
		
		HttpSession session = request.getSession();
		
		if(fname != null)
		session.setAttribute("first_name",fname);
		
		String lname = (String)session.getAttribute("last_name");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>ADD CODER DETAILS</TITLE></HEAD><BODY>");
		
		out.println("<form action=\"multiform3\" method=\"POST\">");
		if(lname != null)
			out.println("Last Name: <input type=\"text\" name=\"lname\" value="+lname+"><br>");
		else
			out.println("Last Name: <input type=\"text\" name=\"lname\"><br>");
		out.println("<input type=\"submit\" value=\"Next\">");
		out.println("</form>");
		
		out.println("<form action=\"multiform1\" method=\"POST\">");
		out.println("<input type=\"submit\" value=\"Prev\">");
		out.println("</form>");
		
		out.println("</BODY></HTML>"); 
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
		
	}

}
