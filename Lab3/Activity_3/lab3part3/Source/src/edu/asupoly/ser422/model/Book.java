package edu.asupoly.ser422.model;

public class Book {
	
	private String isbn;
	private String publisher;
	private String title;
	private int publishYear;
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	public Book(String isbn, String publisher, String title, int publishYear) {
		super();
		this.isbn = isbn;
		this.publisher = publisher;
		this.title = title;
		this.publishYear = publishYear;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", publisher=" + publisher + ", title=" + title + ", publishYear=" + publishYear
				+ "]";
	}

}
