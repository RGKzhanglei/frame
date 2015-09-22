package com.example.business.mybusiness.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
	
	private static DBHelper instance;
	
	private static final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\raw\\" + "datas.db";
	
	public static synchronized DBHelper getInstance() {
		if (null == instance) {
			instance = new DBHelper();
		}
		return instance;
	}
	
	private DBHelper() {
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      
	      c = DriverManager.getConnection(DB_URL);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE COMPANY " +
	                   "(ID INT PRIMARY KEY     NOT NULL," +
	                   " NAME           TEXT    NOT NULL, " + 
	                   " AGE            INT     NOT NULL, " + 
	                   " ADDRESS        CHAR(50), " + 
	                   " SALARY         REAL)"; 
	      stmt.executeUpdate(sql);
	      c.commit();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	}
	
	public void insert(String table, Object value) {
		Statement stmt = null;
		Connection c;
		try {
			c = DriverManager.getConnection(DB_URL);
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			String sql = "INSERT INTO "
					+ table 
					+ " (ID,NAME,AGE,ADDRESS,SALARY) " 
					+ "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
		    c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(String table, Object value) {
		Statement stmt = null;
		Connection c;
		try {
			c = DriverManager.getConnection(DB_URL);
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			String sql = "INSERT INTO "
					+ table 
					+ " (ID,NAME,AGE,ADDRESS,SALARY) " 
					+ "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
		    c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object select(String table, String where) {
		Object object = null;
		return object;
	}
	
	public void delete(String table, String where) {
		
	}

}
