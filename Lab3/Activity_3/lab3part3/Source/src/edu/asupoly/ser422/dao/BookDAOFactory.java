package edu.asupoly.ser422.dao;

import java.util.Properties;

import edu.asupoly.ser422.dao.impl.BookDAORdbmsImpl;


public class BookDAOFactory {
    private BookDAOFactory() {}

    public static BookDAO getInstance() {
	if (__bookservice == null) {
		
		__bookservice = (BookDAO) new BookDAORdbmsImpl();
	}
	return __bookservice;
    }

    private static BookDAO __bookservice;
    
	static {
		try {
			Properties dbProperties = new Properties();
			Class<?> initClass = null;
			dbProperties.load(BookDAOFactory.class.getClassLoader().getResourceAsStream("lab3.properties"));
			String authorDAOImpl = dbProperties.getProperty("bookDAOImpl");
			initClass = Class.forName(authorDAOImpl);
			__bookservice = (BookDAO)initClass.newInstance();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}
    
}
