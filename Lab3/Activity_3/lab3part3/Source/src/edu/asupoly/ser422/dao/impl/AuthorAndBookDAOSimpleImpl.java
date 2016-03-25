package edu.asupoly.ser422.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.asupoly.ser422.dao.AuthorAndBookDAO;
import edu.asupoly.ser422.model.Author;
import edu.asupoly.ser422.model.Author_Book;
import edu.asupoly.ser422.model.Book;

public class AuthorAndBookDAOSimpleImpl implements AuthorAndBookDAO{

	private ArrayList<Author_Book> author_book_list = null;
	
	public AuthorAndBookDAOSimpleImpl() {
		author_book_list = new ArrayList<Author_Book>();
	}

	@Override
	public void create(Author_Book author_book) {
		author_book_list.add(new Author_Book(author_book.getId(), author_book.getIsbn()));
	}

	@Override
	public List<Author_Book> retrieve() {
		return author_book_list;
	}

	@Override
	public void deletebyId(Integer id) {	
		List<Author_Book> deepCopy = new ArrayList<Author_Book>(author_book_list);
		
		if(!deepCopy.isEmpty()) {
			for(Author_Book ab : deepCopy) {
				if(ab.getId() == id)
					author_book_list.remove(ab);
			}	
		}		
	}

	@Override
	public void deletebyIsbn(String isbn) {	
		List<Author_Book> deepCopy = new ArrayList<Author_Book>(author_book_list);
		
		if(!deepCopy.isEmpty()) {
			for(Author_Book ab : deepCopy) {
				if(isbn.equals(ab.getIsbn()))
					author_book_list.remove(ab);
			}	
		}
		
	}

	@Override
	public void delete(Author_Book author_book) {
		
		List<Author_Book> deepCopy = new ArrayList<Author_Book>(author_book_list);
		
		if(!deepCopy.isEmpty()) {
			for(Author_Book ab : deepCopy) {
				if(ab.getId() == author_book.getId() && author_book.getIsbn().equals(ab.getIsbn()))
					author_book_list.remove(ab);
			}	
		}
		
	}

	@Override
	public List<Author_Book> findById(Integer id) {
		List<Author_Book> matched_list = new ArrayList<Author_Book>();
		
		if(!author_book_list.isEmpty()) {
			for(Author_Book ab : author_book_list) {
				if(ab.getId() == id)
					matched_list.add(ab);
			}	
		}	
		return matched_list;
	}

	@Override
	public List<Author_Book> findByIsbn(String isbn) {
		List<Author_Book> matched_list = new ArrayList<Author_Book>();
		
		if(!author_book_list.isEmpty()) {
			for(Author_Book ab : author_book_list) {
				if(isbn.equals(ab.getIsbn()))
					matched_list.add(ab);
			}	
		}	
		return matched_list;
	}

	@Override
	public Boolean isAssociationExist(Author_Book author_book) {
		
		if(!author_book_list.isEmpty()) {
			for(Author_Book ab : author_book_list) {
				if(ab.getId() == author_book.getId() && author_book.getIsbn().equals(ab.getIsbn()))
					return Boolean.TRUE;
			}	
		}
		return Boolean.FALSE;
	}

	
	
	

}
