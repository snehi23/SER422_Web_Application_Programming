package edu.asupoly.ser422.dao;

import java.util.Properties;

import edu.asupoly.ser422.dao.impl.AuthorAndBookDAORdbmsImpl;


public class AuthorAndBookDAOFactory {
    private AuthorAndBookDAOFactory() {}

    public static AuthorAndBookDAO getInstance() {
	if (__authorbookservice == null) {
		
		__authorbookservice = (AuthorAndBookDAO) new AuthorAndBookDAORdbmsImpl();
	}
	return __authorbookservice;
    }

    private static AuthorAndBookDAO __authorbookservice;
    
	static {
		try {
			Properties dbProperties = new Properties();
			Class<?> initClass = null;
			dbProperties.load(AuthorAndBookDAOFactory.class.getClassLoader().getResourceAsStream("lab3.properties"));
			String authorAndBookDAOImpl = dbProperties.getProperty("authorAndBookDAOImpl");
			initClass = Class.forName(authorAndBookDAOImpl);
			__authorbookservice = (AuthorAndBookDAO)initClass.newInstance();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}
    
}
