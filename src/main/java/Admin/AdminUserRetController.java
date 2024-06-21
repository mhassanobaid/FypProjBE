package Admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//////////////Admin show users in admin dashbaord tables

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.io.InputStream;

import Pojo.*;
import Util.*;



//aik kaam kren ke DatabaseConnectonManager k class ko again is package me bna len? yr ak mint ruko to sai

@WebServlet("/users")
public class AdminUserRetController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
	
	
	
	public static String extractPropertyValue(StringBuilder jsonBuilder, String propertyName) {
        int propertyIndex = jsonBuilder.indexOf(propertyName);
        if (propertyIndex == -1) {
            return null; // Property not found
        }

        int valueStartIndex = jsonBuilder.indexOf(":", propertyIndex) + 1;
        int valueEndIndex = jsonBuilder.indexOf(",", valueStartIndex);
        if (valueEndIndex == -1) {
            valueEndIndex = jsonBuilder.indexOf("}", valueStartIndex);
        }

        String propertyValue = jsonBuilder.substring(valueStartIndex, valueEndIndex).trim();
        // Remove surrounding double quotes if present
        if (propertyValue.startsWith("\"") && propertyValue.endsWith("\"")) {
            propertyValue = propertyValue.substring(1, propertyValue.length() - 1);
        }
        return propertyValue;
    }
	
	
	
	protected void browseTours(HttpServletResponse response, String location, int price, int tourists) throws IOException, SQLException 
	{
		try {
		    con = DatabaseConnectionManager.getConnection();
		    
		    String query = null;
		    
		    if (location != null && price > 0 && tourists > 0) {
		    	query = "SELECT * FROM tour WHERE departure_date > CURDATE() AND location = ? AND price >= ? AND number_of_persons >= ?"; 
		    	
	            System.out.println("asd");
	        }
		    else
		    	query = "SELECT * FROM tour WHERE departure_date > CURDATE() AND number_of_persons >= 1";
		    PreparedStatement pstmt = con.prepareStatement(query);
//		    Statement stmt = con.createStatement();
//		    ResultSet rs = stmt.executeQuery(query);
		    if (location != null && price > 0 && tourists > 0) {
	            pstmt.setString(1, location);
	            pstmt.setInt(2, price);
	            pstmt.setInt(3, tourists);
	            System.out.println("asd");
	            ResultSet rs = pstmt.executeQuery();  
	            ObjectMapper mapperr = new ObjectMapper();
	            List<Map<String, Object>> filteredTours = new ArrayList<>();
	            while (rs.next()) {
	                int priced = rs.getInt("price");
	                int numberOfPersons = rs.getInt("number_of_persons");
	                
	                // Check if the tour meets the conditions
	                if (price <= priced && numberOfPersons >= tourists) {
	                    // Add the tour to the filtered list
	                    Map<String, Object> tourMap = new HashMap<>();
	                    ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
	                    int columnCount = rsmd.getColumnCount();
	                    for (int i = 1; i <= columnCount; i++) {
	                        String columnName = rsmd.getColumnName(i);
	                        Object value = rs.getObject(i);
	                        tourMap.put(columnName, value);
	                    }
	                    filteredTours.add(tourMap);
	                }
	            }
	            response.setStatus(HttpServletResponse.SC_OK);
			    response.setContentType("application/json");
			    response.getWriter().print(mapperr.writeValueAsString(filteredTours));
	        }
		    else {
	        ResultSet rs = pstmt.executeQuery();

		    // Convert ResultSet to JSON
		    ObjectMapper mapper = new ObjectMapper();
		    List<Map<String, Object>> toursDataList = new ArrayList<>();
		    ResultSetMetaData rsmd = (ResultSetMetaData)rs.getMetaData();
		    int columnCount = rsmd.getColumnCount();
		    while (rs.next()) {
		        Map<String, Object> tourMap = new HashMap<>();
		        for (int i = 1; i <= columnCount; i++) {
		            String columnName = rsmd.getColumnName(i);
		            Object value = rs.getObject(i);
		            tourMap.put(columnName, value);
		        }
		        toursDataList.add(tourMap);
		    }
		    

		    // Send JSON response
		    response.setStatus(HttpServletResponse.SC_OK);
		    response.setContentType("application/json");
		    response.getWriter().print(mapper.writeValueAsString(toursDataList));}
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().println("Error occurred: " + e.getMessage());
		}

	}
	
	protected void browseBookdTours(int uidd,HttpServletResponse response) throws IOException, SQLException
	{
	          try {
	        	  con = DatabaseConnectionManager.getConnection();
	  	        
	  	        // First, fetch the tour IDs associated with the user from the user_favorite_tour table
	  	        String favTourQuery = "SELECT * FROM user_tour_booking WHERE user_id = ?";
	  	      PreparedStatement favTourStmt = con.prepareStatement(favTourQuery);
		        favTourStmt.setInt(1, uidd);
		        ResultSet bkTourResultSet = favTourStmt.executeQuery();
		        ObjectMapper mapper = new ObjectMapper();
		        List<Map<String, Object>> toursDataList = new ArrayList<>();
		        ResultSetMetaData rsmd = (ResultSetMetaData) bkTourResultSet.getMetaData();
		        int columnCount = rsmd.getColumnCount();
		        while (bkTourResultSet.next()) {
		            Map<String, Object> tourMap = new HashMap<>();
		            for (int i = 1; i <= columnCount; i++) {
		                String columnName = rsmd.getColumnName(i);
		                Object value = bkTourResultSet.getObject(i);
		                tourMap.put(columnName, value);
		            }
		            
		            toursDataList.add(tourMap);
		        }
		        for (Map<String, Object> tourData : toursDataList) {
		            // Iterate over each entry in the map
		            for (Map.Entry<String, Object> entry : tourData.entrySet()) {
		                String key = entry.getKey();
		                Object value = entry.getValue();
		                // Do something with the key and value
		                System.out.println("Nach: " + key + ", Nache: " + value);
		            }
		        }
		        
		        response.setStatus(HttpServletResponse.SC_OK);
		        response.setContentType("application/json");
		        response.getWriter().print(mapper.writeValueAsString(toursDataList));
	        	
		        
		        
	          }catch(Exception e)
	          {
	        	  e.printStackTrace();
	        	  response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	              response.getWriter().print("fetching tours.");
	          }
	}
	
	protected void browseFavTours(int uid,HttpServletResponse response) throws IOException, SQLException
	{
		System.out.println("Me browseFavTours me userid hn:-"+uid);
		try {
			con = DatabaseConnectionManager.getConnection();
	        
	        // First, fetch the tour IDs associated with the user from the user_favorite_tour table
	        String favTourQuery = "SELECT tour_id FROM user_tour_favorite WHERE user_id = ?";
	        PreparedStatement favTourStmt = con.prepareStatement(favTourQuery);
	        favTourStmt.setInt(1, uid);
	        ResultSet favTourResultSet = favTourStmt.executeQuery();
	        
	        List<Integer> tourIds = new ArrayList<>();
	        while (favTourResultSet.next()) {
	            int tourId = favTourResultSet.getInt("tour_id");
	            tourIds.add(tourId);
	        }
	        
	        // Now, select all tours from the tour table based on the fetched tour IDs
	        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM tour WHERE tourid IN (");
	        for (int i = 0; i < tourIds.size(); i++) {
	            if (i != 0) {
	                queryBuilder.append(",");
	            }
	            queryBuilder.append("?");
	        }
	        queryBuilder.append(")");
	        
	        PreparedStatement tourStmt = con.prepareStatement(queryBuilder.toString());
	        for (int i = 0; i < tourIds.size(); i++) {
	            tourStmt.setInt(i + 1, tourIds.get(i));
	        }
	        if (!tourIds.isEmpty()) {
	            // Prepare and execute the SQL query
	        	ResultSet tourResultSet = tourStmt.executeQuery();
	        	
	        	// Process the fetched tour data
		        ObjectMapper mapper = new ObjectMapper();
		        List<Map<String, Object>> toursDataList = new ArrayList<>();
		        ResultSetMetaData rsmd = (ResultSetMetaData) tourResultSet.getMetaData();
		        int columnCount = rsmd.getColumnCount();
		        while (tourResultSet.next()) {
		            Map<String, Object> tourMap = new HashMap<>();
		            for (int i = 1; i <= columnCount; i++) {
		                String columnName = rsmd.getColumnName(i);
		                Object value = tourResultSet.getObject(i);
		                tourMap.put(columnName, value);
		            }
		            
		            toursDataList.add(tourMap);
		        }
		        
		        for (Map<String, Object> tourData : toursDataList) {
		            // Iterate over each entry in the map
		            for (Map.Entry<String, Object> entry : tourData.entrySet()) {
		                String key = entry.getKey();
		                Object value = entry.getValue();
		                // Do something with the key and value
		                System.out.println("Key: " + key + ", Value: " + value);
		            }
		        }
		        response.setStatus(HttpServletResponse.SC_OK);
		        response.setContentType("application/json");
		        response.getWriter().print(mapper.writeValueAsString(toursDataList));	}
		        
	         else {
	            // Handle the case when no tour IDs are found
	        	System.out.println("no tour IDs are found");	        }
	        
	        
	        
	        
		}
	        // Send JSON response
	      
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	protected void AddReview(int tourId,int userId,int rating,String comment, HttpServletResponse response) throws IOException, SQLException
	{
		 PrintWriter out = response.getWriter();
		 Connection con = null;
	        PreparedStatement pstmt = null;
	        try {
	            con = DatabaseConnectionManager.getConnection();
	            String sql = "INSERT INTO user_tour_review (userid, tourid, rating, comments) VALUES (?, ?, ?, ?)";
	            pstmt = con.prepareStatement(sql);
	            pstmt.setInt(1, userId);
	            pstmt.setInt(2, tourId);
	            pstmt.setInt(3, rating);
	            pstmt.setString(4, comment);

	            int rowsAffected = pstmt.executeUpdate();

	            if (rowsAffected > 0) {
	            	 response.setStatus(HttpServletResponse.SC_OK);
	                out.print("{\"message\": \"Review submitted successfully!\"}");
	            } else {
	                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                out.print("{\"error\": \"Failed to submit review.\"}");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            out.print("{\"error\": \"Error occurred: " + e.getMessage() + "\"}");
	        }
		
	}
		protected void browssToursCompanyForReview(int tid,int userid, HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    ObjectMapper mapper = new ObjectMapper();
	    PrintWriter out = response.getWriter();
	    try {

	        con = DatabaseConnectionManager.getConnection();
	        String query = "SELECT company_id FROM tour WHERE tourid = ?";


	        stmt = con.prepareStatement(query);
	        stmt.setInt(1, tid);
	        rs = stmt.executeQuery();


	        if (rs.next()) {
	            int companyId = rs.getInt("company_id");

	            // Check if the userid and tourid are present in user_tour_review
	            query = "SELECT * FROM user_tour_review WHERE userid = ? AND tourid = ?";
	            stmt = con.prepareStatement(query);
	            stmt.setInt(1, userid);
	            stmt.setInt(2, tid);
	            rs = stmt.executeQuery();

	            if (!rs.next()) { // If the entry is not found in user_tour_review

	                // Retrieve company_name from tour_company table
	                query = "SELECT company_name FROM tour_company WHERE id = ?";
	                stmt = con.prepareStatement(query);
	                stmt.setInt(1, companyId);
	                rs = stmt.executeQuery();

	                if (rs.next()) {
	                    String companyName = rs.getString("company_name");

	                    // Create JSON response
	                    ObjectNode jsonResponse = mapper.createObjectNode();
	                    jsonResponse.put("companyName", companyName);
	                    out.print(mapper.writeValueAsString(jsonResponse));
	                } else {
	                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	                    out.print("{\"error\": \"Company not found\"}");
	                }
	            } else {
	                response.setStatus(HttpServletResponse.SC_CONFLICT); // Indicates conflict (already reviewed)
	                out.print("{\"error\": \"Tour already reviewed\"}");
	            }
	        } else {
	            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	            out.print("{\"error\": \"Tour not found\"}");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().println("Error occurred: " + e.getMessage());
	    }
	}
	
	protected void browseCompanies(HttpServletResponse response) throws IOException, SQLException {
	    try {
	        con = DatabaseConnectionManager.getConnection();
	        String query = "SELECT * FROM tour_company"; // Select all columns from the tour_company table
	        
	     
	        PreparedStatement pstmt = con.prepareStatement(query);

	         ResultSet rs = pstmt.executeQuery();

	        // Convert ResultSet to JSON
	        ObjectMapper mapper = new ObjectMapper();
	        List<Map<String, Object>> companiesDataList = new ArrayList<>();
	        ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
	        int columnCount = rsmd.getColumnCount();
	        while (rs.next()) {
	            Map<String, Object> companyMap = new HashMap<>();
	            for (int i = 1; i <= columnCount; i++) {
	                String columnName = rsmd.getColumnName(i);
	                Object value = rs.getObject(i);
	                companyMap.put(columnName, value);
	            }
	           System.out.println(companyMap);
	            companiesDataList.add(companyMap);
	        }

	        // Send JSON response
	        response.setStatus(HttpServletResponse.SC_OK);
	        response.setContentType("application/json");
	        response.getWriter().print(mapper.writeValueAsString(companiesDataList));
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().println("Error occurred: " + e.getMessage());
	    }
	}

	
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
    	 response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	        response.setHeader("Access-Control-Allow-Methods", "POST");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	        
	        	        
    	response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Connect to MySQL database
        	con = DatabaseConnectionManager.getConnection();
            Statement stmt = con.createStatement();
            
            // Execute query to fetch user data
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE email != 'admin@example.com'");

            // Convert ResultSet to JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> rows = new ArrayList<>();
            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                rows.add(row);
            }

            // Send JSON response
            out.println(mapper.writeValueAsString(rows));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Error occurred: " + e.getMessage());
        }
    }
    
    
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	        response.setHeader("Access-Control-Allow-Methods", "POST");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	        StringBuilder requestData = new StringBuilder();
		       
	        // Read incoming data
	        try {
	        
	        BufferedReader reader = request.getReader();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            requestData.append(line);
	        }
	       System.out.println(requestData.toString());
	        System.out.println(requestData.getClass().getName());
	        
	        String action = extractPropertyValue(requestData, "action");
	        if (action != null && action.equals("retrieveTours")) {
	            try {
	            	System.out.println("retricdant");
	            	String a = extractPropertyValue(requestData, "location");
	            	String b = extractPropertyValue(requestData, "money");
//	            	int bb = Integer.parseInt(b);
	            	String c = extractPropertyValue(requestData, "tourists");
//	            	int cc = Integer.parseInt(c);
	            	
//	            	System.out.println("lc:-"+a+"Mon:-"+b+"tr:-"+c);
//	            	browseTours(response,a,bb,cc);
	            	if (a != null && !a.isEmpty() && b != null && !b.isEmpty() && c != null && !c.isEmpty()) {
	            	    try {
	            	        int bb = Integer.parseInt(b);
	            	        int cc = Integer.parseInt(c);
	            	        System.out.println("lc:-" + a + " Mon:-" + bb + " tr:-" + cc);
	            	        browseTours(response, a, bb, cc);
	            	    } catch (NumberFormatException e) {
	            	        // Handle parsing error
	            	        System.out.println("Error parsing money or tourists: " + e.getMessage());
	            	    }
	            	} else {
	            		browseTours(response, null, -1, 0);
	            	    // "money" or "tourists" not present in request, handle accordingly
	            	    System.out.println("money or tourists not present in request");
	            	}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            return;
	        }
	        else if(action != null && action.equals("retrieveImages"))
	        {
	        	try {
	        		System.out.println("retric Images");
	        		
	        	}catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        	return;
	        }
	        else if(action != null && action.equals("retrieveTComp")) {
	        	
	        	try {
	        		System.out.println("retric Companies");
	        		browseCompanies(response);
	        		
	        	}catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        	return;
	        	
	        }
	        else if(action!=null && action.equals("retrieveFavTours")) {
	        	
	        	try {
	        		System.out.println("retric Fav Tour");
	        		String b = extractPropertyValue(requestData, "userId");
	        		int uidd = Integer.parseInt(b);
	        		browseFavTours(uidd,response);
	        		
	        	}catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        }
	        else if(action!=null && action.equals("retrieveBookdTours"))
	        {
	        	try {
	        		System.out.println("retric BOOKED Tour");
	        		String b = extractPropertyValue(requestData, "userId");
	        		int uidd = Integer.parseInt(b);
	        		browseBookdTours(uidd,response);
	        		
	        	}catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        }
	        
	        else if(action!=null && action.equals("retrieveCompanyName"))
	        {
	        	try {
	        		System.out.println("retric tour company Tour");
	        		String b = extractPropertyValue(requestData, "tourid");
	        		String c = extractPropertyValue(requestData, "userid");
	        		int uidd = Integer.parseInt(b);
	        		int cidd = Integer.parseInt(c);
	        		System.out.println("CHA JAO"+uidd+"user Cah "+cidd);
	        		browssToursCompanyForReview(uidd,cidd,request,response);
//	        		browseBookdTours(uidd,response);
	        		
	        	}catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        }
	        
	        else if(action!=null && action.equals("submitReview"))
	        {
	        	try {
	        		System.out.println("Submit review me hen");
//	        		int tourId = Integer.parseInt(request.getParameter("tourId"));
//	                int userId = Integer.parseInt(request.getParameter("userid"));
//	                int rating = Integer.parseInt(request.getParameter("rating"));
//	                
	        		String a = extractPropertyValue(requestData, "tourid");
	        		String b = extractPropertyValue(requestData, "userid");
	        		String c = extractPropertyValue(requestData, "comnt");
	        		String d = extractPropertyValue(requestData, "rting");
//	                String comment = request.getParameter("comment");
	                System.out.println("SUBMT REVIEW ME "+a+"usid"+b+"ratig"+d+"coment"+c);
	                int tid = Integer.parseInt(a);
	                int uid = Integer.parseInt(b);
	                int rt = Integer.parseInt(d);
	                
	        		AddReview(tid,uid,rt,c,response);
//	        		browseBookdTours(uidd,response);
	        		
	        	}catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        }
	       
	        else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Invalid action");
                System.out.println("dsda");
            }
	        }catch(Exception e)
	        {
	        	System.out.println(e);
	        }
	        


	        
	        
    }
    
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set CORS headers
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
         }
    
}
