package edu.asupoly.ser422.info;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MultiForm1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String fname = (String)session.getAttribute("first_name");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>ADD CODER DETAILS</TITLE></HEAD><BODY>");
		out.println("Please provide preference details:<br>"); 
		out.println("<form action=\"multiform2\" method=\"POST\">");
		if(fname != null)
			out.println("First Name: <input type=\"text\" name=\"fname\" value="+fname+"><br>");
		else
			out.println("First Name: <input type=\"text\" name=\"fname\"><br>");
		out.println("<input type=\"submit\" value=\"Next\">");
		out.println("</form>");
		
		out.println("</BODY></HTML>"); 
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
		
	}

}
