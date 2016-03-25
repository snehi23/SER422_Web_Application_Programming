package edu.asupoly.ser422.model;

public class Author {
	public Author(int id, String lname, String fname) {
		__id = id;
		__lastName  = lname;
		__firstName = fname;
	}
	public int getAuthorID() {
		return __id;
	}
	public String getLastName() {
		return __lastName;
	}
	public String getFirstName() {
		return __firstName;
	}
	
	private int    __id;
	private String __lastName;
	private String __firstName;
	
	public int get__id() {
		return __id;
	}
	public String get__lastName() {
		return __lastName;
	}
	public String get__firstName() {
		return __firstName;
	}
	
	
}
