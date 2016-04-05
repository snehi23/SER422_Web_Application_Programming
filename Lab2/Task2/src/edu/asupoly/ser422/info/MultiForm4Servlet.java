package edu.asupoly.ser422.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MultiForm4Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String[] dayValues = {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] languages = request.getParameterValues("progLanguages");
		
		HttpSession session = request.getSession();
		
		if(languages != null)
		session.setAttribute("languages",languages);
		
		String[] days = (String[])session.getAttribute("days");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>ADD CODER DETAILS</TITLE></HEAD><BODY>");
		out.println(" Available Days of Week:<br>");
		out.println("<form action=\"multiform5\" method=\"POST\">");
		
			for(String day : dayValues) {
				
				if(days != null && Arrays.asList(days).contains(day))
					out.println("<input type = \"checkbox\" name = \"daysOfWeek\" value= \""+day+"\"checked/>"+day+"<br>");
				else
					out.println("<input type = \"checkbox\" name = \"daysOfWeek\" value= \""+day+"\" />"+day+"<br>");
			}
			
		out.println("<input type=\"submit\" value=\"Next\">");
		out.println("</form>");
		
		out.println("<form action=\"multiform3\" method=\"POST\">");
		out.println("<input type=\"submit\" value=\"Prev\">");
		out.println("</form>");
		
		out.println("</BODY></HTML>"); 
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
		
	}

}
