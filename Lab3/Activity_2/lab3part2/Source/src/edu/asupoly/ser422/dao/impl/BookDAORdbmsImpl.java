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

import edu.asupoly.ser422.dao.BookDAO;
import edu.asupoly.ser422.model.Author;
import edu.asupoly.ser422.model.Book;

public class BookDAORdbmsImpl implements BookDAO{

	private static String __jdbcUrl;
	private static String __jdbcUser;
	private static String __jdbcPasswd;
	private static String __jdbcDriver;
	
	static {
		try {
			Properties dbProperties = new Properties();
			dbProperties.load(AuthorDAORdbmsImpl.class.getClassLoader().getResourceAsStream("rdbm.properties"));
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
	public void create(Book book) {
		
		Connection conn = connectionManager("org.postgresql.Driver");
		Statement stmt = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Books (isbn, publisher, title, publish_year) VALUES (?,?,?,?)");
			ps.setString(1, book.getIsbn());
			ps.setString(2, book.getPublisher());
			ps.setString(3, book.getTitle());
			ps.setInt(4, book.getPublishYear());
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
	public List<Book> retrieve() {
		
		Connection conn = connectionManager(__jdbcDriver);
		Statement stmt = null;
		ResultSet rs = null;
		List<Book> books = new ArrayList<Book>();
		
		try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery("Select isbn, publisher, title, publish_year from Books");
		while (rs.next()) {
			books.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
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
		return books;
		
	}

	@Override
	public void update(Book book) {
		
		Connection conn = connectionManager("org.postgresql.Driver");
		Statement stmt = null;
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE Books SET publisher = ?, title = ?, publish_year = ? WHERE isbn = ?");
			ps.setString(1, book.getPublisher());
			ps.setString(2, book.getTitle());
			ps.setInt(3, book.getPublishYear());
			ps.setString(4, book.getIsbn());
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
	public void delete(String isbn) {
		
		Connection conn = connectionManager("org.postgresql.Driver");
		Statement stmt = null;
		try {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM Books WHERE isbn = ?");
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
	public Book findByPrimaryKey(String isbn) {
		
		Connection conn = connectionManager(__jdbcDriver);
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
		PreparedStatement ps = conn.prepareStatement("Select isbn, publisher, title, publish_year from Books where isbn = ?");
		ps.setString(1, isbn);	
		rs = ps.executeQuery();	
		if(rs.next())
			return new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
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
		return null;
		
	}

	@Override
	public List<Book> findBooksByTitle(String title) {
			
		Connection conn = connectionManager(__jdbcDriver);
		Statement stmt = null;
		ResultSet rs = null;
		List<Book> books = new ArrayList<Book>();
		
		try {
		stmt = conn.createStatement();
		if(title == null)
			title = "";
		rs = stmt.executeQuery("Select isbn, publisher, title, publish_year from Books where title LIKE "+"'"+title+'%'+"'");
	
		while (rs.next()) {
			books.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
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
		return books;
		
	}

	@Override
	public Boolean isBookExist(String isbn) {
		if(findByPrimaryKey(isbn) != null)
			return Boolean.TRUE;
		
		return Boolean.FALSE;
	}

}
