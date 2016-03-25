package edu.asupoly.ser422.dao;

import java.util.List;

import edu.asupoly.ser422.model.Author;
import edu.asupoly.ser422.model.Book;

public interface BookDAO {
	
	public void create(Book book); 
	public List<Book> retrieve(); 
	public void update(Book book);
	public void delete(String isbn);
	public Book findByPrimaryKey(String isbn); 
	public List<Book> findBooksByTitle(String title); 
	public Boolean isBookExist(String isbn);

}
