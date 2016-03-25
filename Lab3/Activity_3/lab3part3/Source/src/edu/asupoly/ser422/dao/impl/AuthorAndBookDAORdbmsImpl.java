package edu.asupoly.ser422.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.asupoly.ser422.dao.AuthorAndBookDAO;
import edu.asupoly.ser422.model.Author_Book;

public class AuthorAndBookDAORdbmsImpl implements AuthorAndBookDAO {
	
	private static String __jdbcUrl;
	private static String __jdbcUser;
	private static String __jdbcPasswd;
	private static String __jdbcDriver;
	
	static {
		try {
			Properties dbProperties = new Properties();
			dbProperties.load(AuthorAndBookDAORdbmsImpl.class.getClassLoader().getResourceAsStream("rdbm.properties"));
			__jdbcUrl    = dbProperties.getProperty("jdbcUrl");
			__jdbcUser   = dbProperties.getProperty("jdbcUser");
			__jdbcPasswd = dbProperties.getProperty("jdbcPasswd");
			__jdbcDriver = dbProperties.getProperty("jdbcDriver");
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}
	
	public Connection connectionManager(String driverUrl) {
		
		Connection conn = null;
		try {
				Class.forName(driverUrl);	
			try {
				return conn = DriverManager.getConnection(__jdbcUrl, __jdbcUser, __jdbcPasswd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch(ClassNotFoundException cne) {
			cne.printStackTrace();
		}
		return conn;

	}

	@Override
	public void create(Author_Book author_book) {
		Connection conn = connectionManager("org.postgresql.Driver");
		Statement stmt = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Authors_Books (id, isbn) VALUES (?,?)");
			ps.setInt(1, author_book.getId());
			ps.setString(2, author_book.getIsbn());
			ps.executeUpdate();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
		} finally { 
			try {
				if (stmt != null) { stmt.close(); }
		} catch (Exception e2) { e2.printStackTrace(); }
		finally {
			try {
				if (conn != null) { conn.close(); }
			} catch (Exception e3) { e3.printStackTrace(); }
		}
	}
		
	}

	@Override
	public List<Author_Book> retrieve() {
		Connection conn = connectionManager(__jdbcDriver);
		Statement stmt = null;
		ResultSet rs = null;
		List<Author_Book> authors_books = new ArrayList<Author_Book>();
		
		try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery("Select id, isbn from Authors_Books");
		while (rs.next()) {
			authors_books.add(new Author_Book(rs.getInt(1), rs.getString(2)));
		}
		} catch (SQLException sqe) {
			sqe.printStackTrace();
		} finally { 
			try {
				if (stmt != null) { stmt.close(); }
		} catch (Exception e2) { e2.printStackTrace(); }
		finally {
			try {
				if (conn != null) { conn.close(); }
			} catch (Exception e3) { e3.printStackTrace(); }
		}
	}
		return authors_books;
		
	}

	@Override
	public void deletebyId(Integer id) {
		Connection conn = connectionManager("org.postgresql.Driver");
		Statement stmt = null;
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Authors_Books WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
		} finally { 
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}		
	}

	@Override
	public void deletebyIsbn(String isbn) {
		Connection conn = connectionManager("org.postgresql.Driver");
		Statement stmt = null;
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Authors_Books WHERE isbn = ?");
			ps.setString(1, isbn);
			ps.executeUpdate();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
		} finally { 
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		
	}

	@Override
	public void delete(Author_Book author_book) {
		Connection conn = connectionManager("org.postgresql.Driver");
		Statement stmt = null;
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Authors_Books WHERE id = ? and isbn = ?");
			ps.setInt(1, author_book.getId());
			ps.setString(2, author_book.getIsbn());
			ps.executeUpdate();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
		} finally { 
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		
	}

	@Override
	public List<Author_Book> findById(Integer id) {
		Connection conn = connectionManager(__jdbcDriver);
		Statement stmt = null;
		ResultSet rs = null;
		List<Author_Book> authors_books = new ArrayList<Author_Book>();
		
		try {	
		PreparedStatement ps = conn.prepareStatement("Select id, isbn from Authors_Books where id = ?");
		ps.setInt(1, id);	
		rs = ps.executeQuery();
		while (rs.next()) {
			authors_books.add(new Author_Book(rs.getInt(1), rs.getString(2)));
		}	
		} catch (SQLException sqe) {
			sqe.printStackTrace();
		} finally { 
			try {
				if (stmt != null) { stmt.close(); }
		} catch (Exception e2) { e2.printStackTrace(); }
		finally {
			try {
				if (conn != null) { conn.close(); }
			} catch (Exception e3) { e3.printStackTrace(); }
		}
		}		
		return authors_books;	
	}

	@Override
	public List<Author_Book> findByIsbn(String isbn) {
		Connection conn = connectionManager(__jdbcDriver);
		Statement stmt = null;
		ResultSet rs = null;
		List<Author_Book> authors_books = new ArrayList<Author_Book>();
		
		try {	
		PreparedStatement ps = conn.prepareStatement("Select id, isbn from Authors_Books where isbn = ?");
		ps.setString(1, isbn);	
		rs = ps.executeQuery();
		while (rs.next()) {
			authors_books.add(new Author_Book(rs.getInt(1), rs.getString(2)));
		}	
		} catch (SQLException sqe) {
			sqe.printStackTrace();
		} finally { 
			try {
				if (stmt != null) { stmt.close(); }
		} catch (Exception e2) { e2.printStackTrace(); }
		finally {
			try {
				if (conn != null) { conn.close(); }
			} catch (Exception e3) { e3.printStackTrace(); }
		}
		}		
		return authors_books;
	}

	@Override
	public Boolean isAssociationExist(Author_Book author_book) {
		
		Connection conn = connectionManager(__jdbcDriver);
		Statement stmt = null;
		ResultSet rs = null;
		
		try {	
		PreparedStatement ps = conn.prepareStatement("Select id, isbn from Authors_Books where id = ? and isbn = ?");
		ps.setInt(1, author_book.getId());
		ps.setString(2, author_book.getIsbn());	
		rs = ps.executeQuery();
		if(rs.next())
			return Boolean.TRUE;	
		
		return Boolean.FALSE;
		} catch (SQLException sqe) {
			sqe.printStackTrace();
		} finally { 
			try {
				if (stmt != null) { stmt.close(); }
		} catch (Exception e2) { e2.printStackTrace(); }
		finally {
			try {
				if (conn != null) { conn.close(); }
			} catch (Exception e3) { e3.printStackTrace(); }
		}
		}		
		return Boolean.FALSE;
	}

	

}
