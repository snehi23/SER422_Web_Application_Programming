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

import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
public class AuthorCRUDServlet extends javax.servlet.http.HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher rd = null;

		AuthorDAO authorService = AuthorDAOFactory.getInstance();
		BookDAO bookService = BookDAOFactory.getInstance();
		AuthorAndBookDAO authorBookService = AuthorAndBookDAOFactory.getInstance();

		String action = req.getParameter("action");
		String author_id = req.getParameter("author_id");
		String fname = req.getParameter("firstname");
		String lname = req.getParameter("lastname");
		String deleteBook = req.getParameter("deleteBook");

		if (author_id != null && author_id.length() != 0) {
			if ("add".equals(action)) {
				if (authorService.isAuthorExist(Integer.parseInt(author_id)))
					req.setAttribute("response_message",
							"Can not add : Author Already Exist: Please choose different ID");
				else
					authorService.create(new Author(Integer.parseInt(author_id), fname, lname));
			}

			if ("update".equals(action)) {
				if (authorService.isAuthorExist(Integer.parseInt(author_id)))
					authorService.update(new Author(Integer.parseInt(author_id), fname, lname));
				else
					req.setAttribute("response_message",
							"Can not update : Author does not Exist: Please add before update");
			}

			if ("delete".equals(action)) {
				if (authorService.isAuthorExist(Integer.parseInt(author_id))) {
					if ("yes".equals(deleteBook)) {
						List<Author_Book> author_book_list = authorBookService.findById(Integer.parseInt(author_id));

						authorBookService.deletebyId(Integer.parseInt(author_id));

						for (Author_Book author_book : author_book_list)
							bookService.delete(author_book.getIsbn());

					} else
						authorBookService.deletebyId(Integer.parseInt(author_id));

					authorService.delete(Integer.parseInt(author_id));
				} else
					req.setAttribute("response_message",
							"Can not delete : Author does not Exist: Please add before delete");
			}

		} else
			req.setAttribute("response_message", "Please insert valid Author ID");

		rd = req.getRequestDispatcher("/author.jsp");
		rd.forward(req, res);
	}

}
