package edu.asupoly.ser422.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.List;
import java.io.IOException;

import edu.asupoly.ser422.model.Author;
import edu.asupoly.ser422.services.BooktownService;
import edu.asupoly.ser422.services.BooktownServiceFactory;


@SuppressWarnings("serial")
public class BooktownServlet extends javax.servlet.http.HttpServlet
{

	public void doGet (HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		RequestDispatcher rd = null;
		BooktownService bookstore = null;
		
		try {
			bookstore = BooktownServiceFactory.getInstance();
			if (bookstore == null)
				req.setAttribute("response_message", "Servlet execution error: The bookstore is closed.");
		}
		catch (Throwable t) {
			req.setAttribute("response_message", "Error while trying to retrieve authors!!!");
		}

		String action = req.getParameter("action");
		int authorId = -1;
		if (action != null && action.equalsIgnoreCase("delete")) {
			authorId = Integer.parseInt(req.getParameter("authorid"));
			if (bookstore.deleteAuthor(authorId)) {
				req.setAttribute("response_message", "Author deleted: ");
				req.setAttribute("author_id", authorId);
			} else {
				req.setAttribute("response_message", "Could not delete author with id: ");
				req.setAttribute("author_id", authorId);
			}
		} else if (action != null && action.equalsIgnoreCase("create")) {
			String lname = req.getParameter("lastname");
			String fname = req.getParameter("firstname");
			if (lname != null && fname != null && lname.length() > 0 && fname.length() > 0) {
				authorId = bookstore.createAuthor(lname, fname);
				if (authorId > 0)
					req.setAttribute("response_message", "Created new author");
				else
					req.setAttribute("response_message", "ERROR: Unable to create an author!");
			} else
				req.setAttribute("response_message", "Cannot create an author with an empty first or last name");
		}
		
		List<Author> authorsList = bookstore.getAuthors();

		if (authorsList == null || authorsList.size() == 0) {
			req.setAttribute("response_message", "No authors found!!!");
			req.removeAttribute("author_id");
		}	
		else 
			req.setAttribute("authorsList", authorsList);
		
		rd = req.getRequestDispatcher("/default.jsp");
		rd.forward(req, res);
	}
}
