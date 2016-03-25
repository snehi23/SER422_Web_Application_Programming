package edu.asupoly.ser422.dao;

import java.util.List;

import edu.asupoly.ser422.model.Author;
import edu.asupoly.ser422.model.Book;

public interface AuthorDAO {
	
	public void create(Author author); 
	public List<Author> retrieve(); 
	public void update(Author author);
	public void delete(Integer id);
	public Author findByPrimaryKey(Integer id); 
	public Boolean isAuthorExist(Integer id);

}
