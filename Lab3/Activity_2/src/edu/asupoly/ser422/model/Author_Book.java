package edu.asupoly.ser422.model;

public class Author_Book {
	
	private int id;
	private String isbn;
	
	public Author_Book(int id, String isbn) {
		super();
		this.id = id;
		this.isbn = isbn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public String toString() {
		return "Author_Book [id=" + id + ", isbn=" + isbn + "]";
	}
	
}
