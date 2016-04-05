package edu.asupoly.ser422.info;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ErrorHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Throwable throwable = (Throwable)request.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
		String statusMessage = (String)request.getAttribute("javax.servlet.error.message");
		
		String servletName = (String)request.getAttribute("javax.servlet.error.servlet_name");
		if (servletName == null)
			         servletName = "UNIDENTIFIED SERVLET";
		
		String requestUri = (String)request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null)
			         requestUri = "UNIDENTIFIED REQUEST URI";

			response.setContentType("text/html");
			PrintWriter out= response.getWriter();
			out.println("<HTML><HEAD><TITLE>ADD CODER DETAILS</TITLE></HEAD><BODY>");
	
			      if (statusCode != null)
			         out.println("The status code : " + statusCode);
			         
			       if (statusMessage != null)
				     out.println("The status message : " + statusMessage);   

			         out.println("<h2>Error information</h2>");
			         out.println("Servlet Name : " + servletName + 
			                             "</br></br>");
			         out.println("The request URI: " + requestUri + 
                             "<br><br>");
			         
			        if(throwable != null) {
			         out.println("Exception Type : " + 
			                             throwable.getClass( ).getName( ) + 
			                             "</br></br>");
			         out.println("The exception message: " + 
			                                 throwable.getMessage( ));
			         
			        } 
				     out.println("</body>");
				     out.println("</html>");
		
	}
}
