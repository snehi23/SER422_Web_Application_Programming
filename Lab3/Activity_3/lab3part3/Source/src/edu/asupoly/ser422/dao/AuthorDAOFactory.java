package edu.asupoly.ser422.dao;

import java.util.Properties;

import edu.asupoly.ser422.dao.impl.AuthorDAORdbmsImpl;


public class AuthorDAOFactory {
    private AuthorDAOFactory() {}

    public static AuthorDAO getInstance() {
	if (__authorservice == null) {
		
		__authorservice = (AuthorDAO) new AuthorDAORdbmsImpl();
	}
	return __authorservice;
    }

    private static AuthorDAO __authorservice;
    
	static {
		try {
			Properties dbProperties = new Properties();
			Class<?> initClass = null;
			dbProperties.load(AuthorDAOFactory.class.getClassLoader().getResourceAsStream("lab3.properties"));
			String authorDAOImpl = dbProperties.getProperty("authorDAOImpl");
			initClass = Class.forName(authorDAOImpl);
			__authorservice = (AuthorDAO)initClass.newInstance();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}
    
}
