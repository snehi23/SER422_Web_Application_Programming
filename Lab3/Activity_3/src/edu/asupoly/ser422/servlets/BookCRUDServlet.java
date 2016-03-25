package edu.asupoly.ser422.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import edu.asupoly.ser422.dao.AuthorAndBookDAO;
import edu.asupoly.ser422.dao.AuthorAndBookDAOFactory;
import edu.asupoly.ser422.dao.AuthorDAO;
import edu.asupoly.ser422.dao.AuthorDAOFactory;
import edu.asupoly.ser422.dao.BookDAO;
import edu.asupoly.ser422.dao.BookDAOFactory;
import edu.asupoly.ser422.model.Author_Book;
import edu.asupoly.ser422.model.Book;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
public class BookCRUDServlet extends javax.servlet.http.HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher rd = null;

		AuthorDAO authorService = AuthorDAOFactory.getInstance();
		BookDAO bookService = BookDAOFactory.getInstance();
		AuthorAndBookDAO authorBookService = AuthorAndBookDAOFactory.getInstance();

		String action = req.getParameter("action");
		String book_id = req.getParameter("book_id");
		String publisher = req.getParameter("publisher");
		String title = req.getParameter("title");
		String publish_year = req.getParameter("publish_year");
		String deleteAuthor = req.getParameter("deleteAuthor");
		String[] author_ids = req.getParameterValues("authors");
		String assoAuthor = req.getParameter("assoAuthor");

		if (book_id != null && book_id.length() != 0) {
			if ("add".equals(action)) {
				if (bookService.isBookExist(book_id))
					req.setAttribute("response_message", "Can not add : Book Already Exist");
				else {
					bookService.create(new Book(book_id, publisher, title, Integer.parseInt(publish_year)));
					if(author_ids != null) {
						for (String author_id : author_ids) {
							if (!authorBookService
									.isAssociationExist(new Author_Book(Integer.parseInt(author_id), book_id)))
								authorBookService.create(new Author_Book(Integer.parseInt(author_id), book_id));
						}
					}	
				}
			}

			if ("update".equals(action)) {
				if (bookService.isBookExist(book_id)) {
					bookService.update(new Book(book_id, publisher, title, Integer.parseInt(publish_year)));
					if ("create".equals(assoAuthor)) {
						for (String author_id : author_ids) {
							if (!authorBookService
									.isAssociationExist(new Author_Book(Integer.parseInt(author_id), book_id)))
								authorBookService.create(new Author_Book(Integer.parseInt(author_id), book_id));
						}
					} else if ("remove".equals(assoAuthor)) {
						for (String author_id : author_ids) {
							if (authorBookService
									.isAssociationExist(new Author_Book(Integer.parseInt(author_id), book_id)))
								authorBookService.delete(new Author_Book(Integer.parseInt(author_id), book_id));
						}
					}
				} else
					req.setAttribute("response_message", "Can not update : Book does not Exist");
			}

			if ("delete".equals(action)) {
				if (bookService.isBookExist(book_id)) {
					if ("yes".equals(deleteAuthor)) {
						List<Author_Book> author_book_list = authorBookService.findByIsbn(book_id);
						authorBookService.deletebyIsbn(book_id);

						for (Author_Book author_book : author_book_list) {
							authorBookService.deletebyId(author_book.getId());
							authorService.delete(author_book.getId());		
						}	

					} else
						authorBookService.deletebyIsbn(book_id);

					bookService.delete(book_id);
				} else
					req.setAttribute("response_message", "Can not delete : Book does not Exist");
			}

		} else
			req.setAttribute("response_message", "Please insert valid Book ISBN");

		rd = req.getRequestDispatcher("/book.jsp");
		rd.forward(req, res);
	}

}
