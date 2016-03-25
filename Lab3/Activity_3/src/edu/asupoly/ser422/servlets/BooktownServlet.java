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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class BooktownServlet extends javax.servlet.http.HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher rd = null;
		Integer author_by_id = 0;
		String book_isbn = null;
		String book_by_title = null;
		Map<Book, List<Author>> book_with_authors = new HashMap<>();

		AuthorDAO authorService = AuthorDAOFactory.getInstance();
		BookDAO bookService = BookDAOFactory.getInstance();
		AuthorAndBookDAO authorBookService = AuthorAndBookDAOFactory.getInstance();

		if (req.getParameter("author_by_id") != null && req.getParameter("author_by_id").length() != 0) {
			try {
				author_by_id = Integer.parseInt(req.getParameter("author_by_id"));
			} catch (NumberFormatException nfe) {
				req.setAttribute("response_message", "Invalid Author ID");
			}
			if(authorService.isAuthorExist(author_by_id)){
				Author author = authorService.findByPrimaryKey(author_by_id);
				req.setAttribute("authorEntry", author);
			} else
				req.setAttribute("response_message", "Author Does not exist");
		}

		if (req.getParameter("book_by_id") != null && req.getParameter("book_by_id").length() != 0) {
			book_isbn = (String) req.getParameter("book_by_id");
			if(bookService.isBookExist(book_isbn)){
				Book book = bookService.findByPrimaryKey(book_isbn);
				req.setAttribute("bookEntry", book);
			} else
				req.setAttribute("response_message", "Book Does not exist");	
		}

		if (req.getParameter("book_by_title") != null) {
			book_by_title = req.getParameter("book_by_title");
			List<Book> books_by_title = bookService.findBooksByTitle(book_by_title);
			List<Author> authors = new ArrayList<>();

			if (books_by_title != null) {
				for (Book book : books_by_title) {

					List<Author_Book> author_book_list = authorBookService.findByIsbn(book.getIsbn());

					if (author_book_list != null) {
						for (Author_Book author_book : author_book_list)
							authors.add(authorService.findByPrimaryKey(author_book.getId()));
					}

					book_with_authors.put(book, authors);
					authors = new ArrayList<>();
				}
			}
			req.setAttribute("book_with_authors", book_with_authors);

		}

		rd = req.getRequestDispatcher("/default.jsp");
		rd.forward(req, res);
	}
}
