package edu.asupoly.ser422.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.asupoly.ser422.dao.BookDAO;
import edu.asupoly.ser422.model.Book;

public class BookDAOSimpleImpl implements BookDAO{

	private ArrayList<Book> books = null;
	
	public BookDAOSimpleImpl() {
		books = new ArrayList<Book>();
	}

	@Override
	public void create(Book book) {
		books.add(new Book(book.getIsbn(),book.getPublisher(),book.getTitle(),book.getPublishYear()));	
	}

	@Override
	public List<Book> retrieve() {
		return books;
	}

	@Override
	public void update(Book book) {
		if(!books.isEmpty()) {
			for(Book b : books) {
				if(book.getIsbn().equals(b.getIsbn())) {
					b.setPublisher(book.getPublisher());
					b.setTitle(book.getTitle());
					b.setPublishYear(book.getPublishYear());
				}	
			}
		}
		
	}

	@Override
	public void delete(String isbn) {
		List<Book> deepCopy = new ArrayList<Book>(books);
		
		if(!deepCopy.isEmpty()) {
			for(Book b : deepCopy) {
				if(isbn.equals(b.getIsbn()))
					books.remove(b);
			}	
		}
		
	}

	@Override
	public Book findByPrimaryKey(String isbn) {
		if(!books.isEmpty()) {
			for(Book b : books) {
				if(isbn.equals(b.getIsbn()))
					return b;
			}	
		}	
		return null;
	}

	@Override
	public List<Book> findBooksByTitle(String title) {
		
		List<Book> matched_books = new ArrayList<Book>();
		if(!books.isEmpty()) {
			for(Book b : books) {
				if(b.getTitle().matches(title+"(.*)"))
					matched_books.add(b);
			}	
		}	
		return matched_books;
		
	}

	@Override
	public Boolean isBookExist(String isbn) {
		if(!books.isEmpty()) {
			for(Book b : books) {
				if(isbn.equals(b.getIsbn() ))
					return Boolean.TRUE;
			}	
		}	
		return Boolean.FALSE;
	}
	
	

}
