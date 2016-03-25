package edu.asupoly.ser422.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import edu.asupoly.ser422.dao.AuthorAndBookDAO;
import edu.asupoly.ser422.dao.AuthorAndBookDAOFactory;
import edu.asupoly.ser422.dao.AuthorDAO;
import edu.asupoly.ser422.dao.AuthorDAOFactory;
import edu.asupoly.ser422.dao.BookDAO;
import edu.asupoly.ser422.dao.BookDAOFactory;
import edu.asupoly.ser422.model.Author;
import edu.asupoly.ser422.model.Author_Book;
import edu.asupoly.ser422.model.Book;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
public class EnlistAuthorsServlet extends javax.servlet.http.HttpServlet
{

	public void doGet (HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		RequestDispatcher rd = null;
		
		String book_id = req.getParameter("book_id");
		
		AuthorDAO authorService = AuthorDAOFactory.getInstance(); 
		BookDAO bookService = BookDAOFactory.getInstance(); 
		AuthorAndBookDAO authorBookService = AuthorAndBookDAOFactory.getInstance();
		List<Author> all_author = authorService.retrieve();
		List<Author_Book> authors_of_book = authorBookService.findByIsbn(book_id);
		Book book = bookService.findByPrimaryKey(book_id);
		
		req.setAttribute("author_list", all_author);	
		req.setAttribute("authors_of_book", authors_of_book);
		req.setAttribute("book_info", book);
		
		rd = req.getRequestDispatcher("/book.jsp");
		rd.forward(req, res);
	}
}
