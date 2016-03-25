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

import edu.asupoly.ser422.dao.AuthorDAO;
import edu.asupoly.ser422.model.Author;
import edu.asupoly.ser422.model.Book;

public class AuthorDAORdbmsImpl implements AuthorDAO {
	
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
	public void create(Author author) {
		
		Connection conn = connectionManager("org.postgresql.Driver");
		Statement stmt = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Authors (id, first_name, last_name) VALUES (?,?,?)");
			ps.setInt(1, author.getId());
			ps.setString(2, author.getFirstName());
			ps.setString(3, author.getLastName());
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
	public List<Author> retrieve() {
	
		Connection conn = connectionManager(__jdbcDriver);
		Statement stmt = null;
		ResultSet rs = null;
		List<Author> authors = new ArrayList<Author>();
		
		try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery("Select id, first_name, last_name from Authors");
		while (rs.next()) {
			authors.add(new Author(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
		return authors;
	}

	@Override
	public void update(Author author) {

		Connection conn = connectionManager("org.postgresql.Driver");
		Statement stmt = null;
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE Authors SET first_name = ?, last_name = ? WHERE id = ?");
			ps.setString(1, author.getFirstName());
			ps.setString(2, author.getLastName());
			ps.setInt(3, author.getId());
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
	public void delete(Integer id) {
		
		Connection conn = connectionManager("org.postgresql.Driver");
		Statement stmt = null;
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Authors WHERE id = ?");
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
	public Author findByPrimaryKey(Integer id) {
		
		Connection conn = connectionManager(__jdbcDriver);
		Statement stmt = null;
		ResultSet rs = null;
		
		try {	
		PreparedStatement ps = conn.prepareStatement("Select id, first_name, last_name from Authors where id = ?");
		ps.setInt(1, id);	
		rs = ps.executeQuery();
		if(rs.next())
			return new Author(rs.getInt(1), rs.getString(2), rs.getString(3));
		else
			return null;
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
	public Boolean isAuthorExist(Integer id) {
	
		if(findByPrimaryKey(id) != null)
			return Boolean.TRUE;
		
		return Boolean.FALSE;
	}

}
