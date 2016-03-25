package edu.asupoly.ser422.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.asupoly.ser422.dao.AuthorDAO;
import edu.asupoly.ser422.model.Author;
import edu.asupoly.ser422.model.Author_Book;

public class AuthorDAOSimpleImpl implements AuthorDAO{

	private ArrayList<Author> authors = null;
	
	public AuthorDAOSimpleImpl() {
		authors = new ArrayList<Author>();
	}
	
	@Override
	public void create(Author author) {
		authors.add(new Author(author.getId(), author.getFirstName(), author.getLastName()));		
	}

	@Override
	public List<Author> retrieve() {
		return authors;		
	}

	@Override
	public void update(Author author) {
		if(!authors.isEmpty()) {
			for(Author a : authors) {
				if(a.getId() == author.getId()) {
					a.setFirstName(author.getFirstName());
					a.setLastName(author.getLastName());
				}	
			}
		}
	}

	@Override
	public void delete(Integer id) {
		List<Author> deepCopy = new ArrayList<Author>(authors);
		
		if(!deepCopy.isEmpty()) {
			for(Author a : deepCopy) {
				if(a.getId() == id)
					authors.remove(a);
			}	
		}
	}

	@Override
	public Author findByPrimaryKey(Integer id) {
		if(!authors.isEmpty()) {
			for(Author a : authors) {
				if(a.getId() == id)
					return a;
			}	
		}	
		return null;
	}

	@Override
	public Boolean isAuthorExist(Integer id) {
		
		if(!authors.isEmpty()) {
			for(Author a : authors) {
				if(a.getId() == id)
					return Boolean.TRUE;
			}	
		}	
		return Boolean.FALSE;
	}

}
