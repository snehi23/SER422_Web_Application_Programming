package edu.asupoly.ser422.info;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MultiForm5Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String[] hairColorValues = {"brunette","blonde","black","other"};
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] days = request.getParameterValues("daysOfWeek");
		
		HttpSession session = request.getSession();
		
		if(days != null)
		session.setAttribute("days",days);
		String hairColor = (String)session.getAttribute("hair_color");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>ADD CODER DETAILS</TITLE></HEAD><BODY>");
		
		out.println("<form action=\"multiform6\" method=\"POST\">");
		out.println(" Hair Color: <br>");
		out.println("<select name=\"hairColor\">");
		out.println("<option value=\"\">Please select color</option>");
		for(String hair : hairColorValues) {
		
			if(hair.equals(hairColor))
				out.println("<option selected=\"selected\" value= \""+hair+"\" />"+hair+"</option>");
			else
				out.println("<option value= \""+hair+"\" />"+hair+"</option>");
			
		}

    	out.println("</select><br>");
		
		out.println("<input type=\"submit\" value=\"Next\">");
		out.println("</form>");
		
		out.println("<form action=\"multiform4\" method=\"POST\">");
		out.println("<input type=\"submit\" value=\"Prev\">");
		out.println("</form>");
		
		out.println("</BODY></HTML>"); 
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
		
	}

}
