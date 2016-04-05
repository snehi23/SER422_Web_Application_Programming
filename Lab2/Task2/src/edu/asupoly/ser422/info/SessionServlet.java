package edu.asupoly.ser422.info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
					
		session.setAttribute("login_first_name",fname);
		session.setAttribute("login_last_name",lname);
		
		response.sendRedirect(getServletContext().getContextPath()+"/multiform1");
	
	}
}
