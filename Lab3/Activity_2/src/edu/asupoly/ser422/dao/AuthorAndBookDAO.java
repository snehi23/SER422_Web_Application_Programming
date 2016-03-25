package edu.asupoly.ser422.dao;

import java.util.List;

import edu.asupoly.ser422.model.Author_Book;


public interface AuthorAndBookDAO {
	
	public void create(Author_Book author_book); 
	public List<Author_Book> retrieve(); 
	public void deletebyId(Integer id);
	public void deletebyIsbn(String isbn);
	public void delete(Author_Book author_book);
	public List<Author_Book> findById(Integer id); 
	public List<Author_Book> findByIsbn(String isbn); 
	public Boolean isAssociationExist(Author_Book author_book);

}
