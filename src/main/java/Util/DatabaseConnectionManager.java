package Util;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;








public class DatabaseConnectionManager {

	 private static final String URL = "jdbc:mysql://127.0.0.1/aas";
	    private static final String USERNAME = "root";
	    private static final String PASSWORD = "root";
	    private static Connection connection;
	    
	  
	    public static Connection getConnection() {
	    	 try {
	             Class.forName("com.mysql.cj.jdbc.Driver");
	         } catch (ClassNotFoundException e) {
	             e.printStackTrace();
	             throw new IllegalStateException("Unable to load JDBC driver class", e);
	         }
	        if (connection == null) {
	            try {
	                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return connection;
	    }
	    
	    public static void closeConnection() {
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
//	    public static void main(String args[]) throws JsonProcessingException
//	    {
//	    	ObjectMapper mp = new ObjectMapper();
//	    	User u = new User("ali","hassan","ah@gmail.com","12345678","0321211111");
//	    	System.out.println(mp.writeValueAsString(u));
//	    	
//	    }
	
	
}