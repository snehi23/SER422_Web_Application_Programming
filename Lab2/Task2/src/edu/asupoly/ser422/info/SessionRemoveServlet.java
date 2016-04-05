package edu.asupoly.ser422.info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SessionRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(session != null){
			session.removeAttribute("first_name");
			session.removeAttribute("last_name");
			session.removeAttribute("languages");
			session.removeAttribute("days");
			session.removeAttribute("hair_color");
		}
		
		response.sendRedirect(getServletContext().getContextPath()+"/home");
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
		
	}
}
