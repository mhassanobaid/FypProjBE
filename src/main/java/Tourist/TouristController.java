package Tourist;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Pojo.*;
import Util.*;

/**
 * Servlet implementation class Demo
 */
public class TouristController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TouristController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served aoioit: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
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
	
	
	int insertUser(User u) throws Exception
	{
		  
		  
		  int rs;
	       // EXECUTE QUERIES
		  
		  String contact_no = u.getPhoneNo();
//		  int cN = Integer.parseInt(contact_no);
		  int user_type=0;
		  String query = "INSERT INTO users (fname, lname, email, password, contact_no, user_type) VALUES (?, ?, ?, ?, ?, ?)";
		    
		    try (PreparedStatement pstmt = con.prepareStatement(query)) {
		        pstmt.setString(1, u.getFirstName());
		        pstmt.setString(2, u.getLastName());
		        pstmt.setString(3,u.getEmail());
		        pstmt.setString(4, u.getPassword());
		        pstmt.setString(5,contact_no);
		        pstmt.setInt(6,user_type);
		        

		         rs = pstmt.executeUpdate();
		        System.out.println(rs);}
	       
	  	 
	  	 return rs;
	}
	
	
	
//	int insertTourBooking(String Uid, String Tid, String Tg, String Tp, String img, String Ttle, String Tlc, String Tdpd) throws Exception {
//	    int rs = 0;
//
//	    // Parse input parameters
//	    int uid = Integer.parseInt(Uid);
//	    int tid = Integer.parseInt(Tid);
//	    int tg = Integer.parseInt(Tg);
//	    int tp = Integer.parseInt(Tp);
//	    String status = "booked";
//
//	    try {
//	        con.setAutoCommit(false); // Disable auto-commit to start a transaction
//
//	        // Execute decrement query to update number_of_persons in the tour table
//	        String decrementQuery = "UPDATE tour SET number_of_persons = number_of_persons - ? WHERE tourid = ?";
//	        try (PreparedStatement decrementStmt = con.prepareStatement(decrementQuery)) {
//	            decrementStmt.setInt(1, tg);
//	            decrementStmt.setInt(2, tid);
//	            int adf = decrementStmt.executeUpdate();
//	            System.out.println("Rows updated in tour table: " + adf);
//	        }
//
//	        // Execute insert query to add booking record to user_tour_booking table
//	        String insertQuery = "INSERT INTO user_tour_booking (user_id, tour_id, status, tourist_going, price, image_url, title, location, departure_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//	        try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
//	            pstmt.setInt(1, uid);
//	            pstmt.setInt(2, tid);
//	            pstmt.setString(3, status);
//	            pstmt.setInt(4, tg);
//	            pstmt.setInt(5, tp);
//	            pstmt.setString(6, img);
//	            pstmt.setString(7, Ttle);
//	            pstmt.setString(8, Tlc);
//	            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	            java.util.Date utilDate = dateFormat.parse(Tdpd);
//	            Date sqlDate = new Date(utilDate.getTime());
//	            pstmt.setDate(9, sqlDate);
//
//	            rs = pstmt.executeUpdate();
//	            System.out.println("Rows inserted in user_tour_booking table: " + rs);
//
//	            con.commit(); // Commit the transaction
//	        }
//	    } catch (Exception e) {
//	        con.rollback(); // Rollback the transaction in case of an exception
//	        throw e; // Re-throw the exception
//	    } finally {
//	        con.setAutoCommit(true); // Enable auto-commit to restore default behavior
//	    }
//
//	    return rs;
//	}

	
	
	int insertTourBooking(String Uid,String Tid,String Tg,String Tp,String ig, String ttl, String tl, String tdd,String pptrst,int noprson) throws Exception
	{
		con = DatabaseConnectionManager.getConnection();
		
		  int rs;
	       // EXECUTE QUERIES
//		  int uid = Integer.parseInt(Uid);                                                                                                                                                                                                                                                                                                                                                                                                                       );
		  int uid = Integer.parseInt(Uid);
		  int tid = Integer.parseInt(Tid);
		  
		  
		  int tg = Integer.parseInt(Tg);
		  int tp = Integer.parseInt(Tp);
		  int pricPrTrst = Integer.parseInt(pptrst);
		  String status = "booked";
		  int adf;
		  Date sqlDate = null;
		  try {
	            // Parse the string into a java.util.Date object
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            java.util.Date utilDate = dateFormat.parse(tdd);
	            
	            // Convert java.util.Date to java.sql.Date
	            sqlDate = new Date(utilDate.getTime());
	            
	            // Now you can store sqlDate in your MySQL column
	            //System.out.println("SQL Date: " + sqlDate);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		  String decrementQuery = "UPDATE tour SET number_of_persons = number_of_persons - ? WHERE tourid = ?";
		    try (PreparedStatement decrementStmt = con.prepareStatement(decrementQuery)) {
		        decrementStmt.setInt(1, tg);
		        decrementStmt.setInt(2, tid);
		        adf = decrementStmt.executeUpdate();
		    }
		    
		    System.out.println(adf);
		   String query = "INSERT INTO user_tour_booking (user_id, tour_id, statas, tourist_going, price,image_url,title,location,departure_date,price_per_tourist,number_of_days) VALUES (?, ?, ?, ?, ?,?,?,?,?,?,?)";
		    
		    try (PreparedStatement pstmt = con.prepareStatement(query)) {
		        pstmt.setInt(1, uid);
		        pstmt.setInt(2, tid);
		        
		        pstmt.setString(3, status);
		        pstmt.setInt(4,tg);
		        pstmt.setInt(5,tp);
		        pstmt.setString(6,ig);
		        pstmt.setString(7,ttl);
		        pstmt.setString(8,tl);
		        pstmt.setDate(9, sqlDate);
		        pstmt.setInt(10,pricPrTrst);
		        pstmt.setInt(11,noprson);
		         rs = pstmt.executeUpdate();
		        System.out.println(rs);}
	       
	  	 
	  	 return rs;
	}
	

	
	
	int setFavorite(int userId, int tourId) {
		
		String sql = "INSERT INTO user_tour_favorite (user_id, tour_id) " +
                "VALUES (?, ?)";
		int rowsAffected=-1;
		try {
			 PreparedStatement preparedStatement = con.prepareStatement(sql);
		        preparedStatement.setInt(1, userId);
		        preparedStatement.setInt(2, tourId);
		        rowsAffected = preparedStatement.executeUpdate();
		        
		        
		        // Close the connection
		        preparedStatement.close();
		}catch(Exception e)
		{
			System.out.print(e);
		}
		return rowsAffected;
		
	}
		
		
	
	
	boolean login(String email, String password) throws SQLException {
		
	    String query = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
	    try (PreparedStatement pstmt = con.prepareStatement(query)) {
	        pstmt.setString(1, email);
	        pstmt.setString(2, password);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count == 1; // If count is 1, login is successful
	            }
	        }
	    }
	    return false; // Login failed
	}

	
	
	protected void handleCheckUserId(HttpServletResponse response) throws IOException, SQLException {
	    try {
	        con = DatabaseConnectionManager.getConnection();
	        String query = "SELECT id, fname, lname, email, password, contact_no, user_type FROM users";
	        List<User> userDataList = new ArrayList<>();

	        try (PreparedStatement pstmt = con.prepareStatement(query)) {
	            try (ResultSet rs = pstmt.executeQuery()) {
	                while (rs.next()) {
	                	int aj = rs.getInt("user_type");
	                	
	                	String uTyp = aj+"";
	                	
	                    User user = new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"),rs.getString("contact_no") ,uTyp);
	                    userDataList.add(user);
	                }
	            }
	            
	        }

	        // Serialize user data list to JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        String userListJson = objectMapper.writeValueAsString(userDataList);

	        // Set HTTP response status and content type
	        response.setStatus(HttpServletResponse.SC_OK);
	        response.setContentType("application/json");

	        // Send JSON response to client-side
	        response.getWriter().print(userListJson);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().println("Error occurred: " + e.getMessage());
	    }
	}
	
	
	
	
	  protected void handleLogin(StringBuilder requestData, HttpServletResponse response) throws IOException, SQLException {
	        String email = extractPropertyValue(requestData, "email");
	        String password = extractPropertyValue(requestData, "password");
	        System.out.println(email+password);
	        try {
	            con = DatabaseConnectionManager.getConnection();
	            boolean loggedIn = login(email, password);
	            System.out.println(loggedIn);
	            if (loggedIn) {
	            	String query = "SELECT id,fname, lname, email, password, contact_no, user_type FROM users WHERE email = ? AND password = ?";
	                try (PreparedStatement pstmt = con.prepareStatement(query)) {
	                    pstmt.setString(1, email);
	                    pstmt.setString(2, password);
	                   // List<User> userDataList = new ArrayList<>();
	                    try (ResultSet rs = pstmt.executeQuery()) {
	                        if (rs.next()) {
	                            // Initialize User object with fetched data
	                        	int tu = rs.getInt("user_type");
	                        	String utt = ""+tu;
	                            User user = new User(rs.getInt("id"),rs.getString("fname"),rs.getString("lname"),rs.getString("email"),rs.getString("password"),rs.getString("contact_no"),utt);
	                            	                            // Serialize User object to JSON
	                            System.out.println("\n"+user+"\n\n");
	                            //userDataList.add(user);
	                            //Map<String, List<User>> resultMap = new HashMap<>();
	                            //resultMap.put("results", userDataList);

	                            ObjectMapper objectMapper = new ObjectMapper();
	                            String userJson = objectMapper.writeValueAsString(user);
	                            System.out.println(userJson);
	                            
	                            // Send JSON response
	                            response.setStatus(HttpServletResponse.SC_OK);
	                            response.setContentType("application/json");
	                            response.getWriter().print(userJson);
	                          
	                        } else {
	                        	 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                             response.setContentType("text/plain");
	                             response.getWriter().println("Login failed");
	                        }
	                    }
	                }
	                
	                
	                
	            } else {
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                response.getWriter().println("Login failed");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().println("Error occurred: " + e.getMessage());
	        }
	    }
	  
	
	  
	  
	  
	  private int updateUserProfile(int userId, String email, String password) throws SQLException {
		    String query = "UPDATE users SET email = ?, password = ? WHERE id = ?";
		    try (PreparedStatement pstmt = con.prepareStatement(query)) {
		        pstmt.setString(1, email);
		        pstmt.setString(2, password);
		        pstmt.setInt(3, userId);
		        return pstmt.executeUpdate();
		    }
		}
	  private boolean emailExists(String email) throws SQLException {
		    String query = "SELECT COUNT(*) FROM users WHERE email = ?";
		    try (PreparedStatement pstmt = con.prepareStatement(query)) {
		        pstmt.setString(1, email);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                int count = rs.getInt(1);
		                return count > 0;
		            }
		        }
		    }
		    return false;
		}
	  
	  
	  private boolean isEmailUnique(String email, int userId) throws SQLException {
		    String query = "SELECT COUNT(*) FROM users WHERE email = ? AND id != ?";
		    try (PreparedStatement pstmt = con.prepareStatement(query)) {
		        pstmt.setString(1, email);
		        pstmt.setInt(2, userId);
		        ResultSet rs = pstmt.executeQuery();
		        if (rs.next()) {
		            return rs.getInt(1) == 0;
		        }
		        return false;
		    }
		}
	  
	  
	  
	  protected void handleManageProfile(StringBuilder requestData, HttpServletResponse response) throws IOException, SQLException {
		    String email = extractPropertyValue(requestData, "email");
		    String password = extractPropertyValue(requestData, "password");
		    String userId = extractPropertyValue(requestData, "usrid");
            int usd = Integer.parseInt(userId);
		    try {
		        con = DatabaseConnectionManager.getConnection();
		        boolean jk = isEmailUnique(email, usd);
		        System.out.println(jk);
		        if (jk) {
		            int sc = updateUserProfile(usd, email, password);

		            if (sc > 0) {
		                response.setStatus(HttpServletResponse.SC_OK);
		                response.getWriter().println("Profile updated successfully");
		            } else {
		                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		                response.getWriter().println("Profile update failed");
		            }
		        } else {
		        	System.out.println("waha duplicate");
		            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		            response.getWriter().println("Email already exists. Please enter a new email.");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        response.getWriter().println("Error occurred: " + e.getMessage());
		    }
		}
	
	 protected void handleSignup(StringBuilder requestData, HttpServletResponse response) throws IOException, SQLException {
	        String firstName = extractPropertyValue(requestData, "firstname");
	        String lastName = extractPropertyValue(requestData, "lastname");
	        String email = extractPropertyValue(requestData, "email");
	        String password = extractPropertyValue(requestData, "password");
	        String phone = extractPropertyValue(requestData, "phone");

	        
          
	        try {
	            con = DatabaseConnectionManager.getConnection();
	            if (emailExists(email)) {
	                // Email already exists, send error response
	                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                response.getWriter().println("Email already exists. Please enter a new email.");
	                return;
	            }
	            User u = new User(firstName, lastName, email, password, phone);
	            int sc = insertUser(u);

	            if (sc > 0) {
	                response.setStatus(HttpServletResponse.SC_OK);
	                response.getWriter().println("Signup successful");
	            } else {
	                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                response.getWriter().println("Signup failed");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().println("Error occurred: " + e.getMessage());
	        }
	    }

	 
	 protected void handlebookTour(StringBuilder requestData, HttpServletResponse response) throws IOException, SQLException {
		 	String Tid = extractPropertyValue(requestData, "tourId");
		 	String Uid = extractPropertyValue(requestData, "userid");
		 	String Ttle = extractPropertyValue(requestData, "title");
		 	String Tlc = extractPropertyValue(requestData, "location");
		 	String Tppt = extractPropertyValue(requestData, "price");
		 	String Tdpd = extractPropertyValue(requestData, "departure_date");
		 	String TnoDys = extractPropertyValue(requestData, "number_of_days");
		 	String Tgoing = extractPropertyValue(requestData, "touristsValue");
		 	String Tp = extractPropertyValue(requestData, "totalPrice");
		 	String img = extractPropertyValue(requestData, "image_url");
		 	int TnoDays = Integer.parseInt(TnoDys);
//	        String Ba = extractPropertyValue(requestData, "bookedAt");
	        
	        String Tg = extractPropertyValue(requestData, "touristsValue");
	        

//	        User u = new User(firstName, lastName, email, password, phone);
       System.out.println("IN handlebook Tor function TOR ID"+Tid+"\nUsER ID"+Uid+"\nTOURIST Going"+Tgoing+"\nTotal Price"+Tp+"\n"+"IMAGE"+img+"\n"+"Title"+Ttle+"\nLocation"+Tlc+"Departure Date"+Tdpd+"\nTour No  of Days\n"+TnoDys);
	        try {
	            //
	            int sc = insertTourBooking(Uid,Tid,Tg,Tp,img,Ttle,Tlc,Tdpd,Tppt,TnoDays);

	            if (sc > 0) {
	                response.setStatus(HttpServletResponse.SC_OK);
	                response.getWriter().println("TourBookedSuccessful successful");
	            } else {
	                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                response.getWriter().println("Signup failed");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().println("Error occurred: " + e.getMessage());
	        }
	    }
	

	 
	 private boolean searchTour(int tourist, String location, int price) throws SQLException {
		    String query = "SELECT COUNT(*) AS count FROM tour WHERE location = ? AND price <= ? AND number_of_persons = ?";
		    try (PreparedStatement pstmt = con.prepareStatement(query)) {
		        pstmt.setString(1, location);
		        pstmt.setInt(2, price);
		        pstmt.setInt(3, tourist);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                int tourCount = rs.getInt("count");
		                return tourCount > 0;
		            }
		        }
		    }
		    return false;
		}

	 
	 protected void handleSearchTour(StringBuilder requestData, HttpServletResponse response) throws IOException {
		    String tourist = extractPropertyValue(requestData, "tourists");
		    int trist = Integer.parseInt(tourist);
		    String money = extractPropertyValue(requestData, "money");
		    int pais = Integer.parseInt(money);
		    String locaatio = extractPropertyValue(requestData, "location");

		    Connection con = null;
		    try {
		        con = DatabaseConnectionManager.getConnection();

		        String query = "SELECT * FROM tour WHERE location = ? AND price <= ? AND number_of_persons=?";
		        try (PreparedStatement pstmt = con.prepareStatement(query)) {
		            pstmt.setString(1, locaatio);
		            pstmt.setInt(2, pais);
		            pstmt.setInt(3, trist);

		            try (ResultSet rs = pstmt.executeQuery()) {
		                List<Tour> tourList = new ArrayList<>();
		                while (rs.next()) {
		                    Tour tour = new Tour(rs.getInt("tourid"), rs.getInt("company_id"), rs.getString("title"),
		                            rs.getString("descreption"), rs.getString("image_url"), rs.getInt("price"),
		                            rs.getInt("number_of_persons"), rs.getString("location"), rs.getInt("number_of_days"),
		                            rs.getDate("departure_date"));
		                    System.out.println(tour);
		                    tourList.add(tour);
		                }

		                ObjectMapper objectMapper = new ObjectMapper();
		                String toursJson = objectMapper.writeValueAsString(tourList);

		                response.setStatus(HttpServletResponse.SC_OK);
		                response.setContentType("application/json");
		                response.getWriter().print(toursJson);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        response.getWriter().println("Error occurred: " + e.getMessage());
		    } 
		}


	 
	 protected void handleDeleteTour(StringBuilder requestData, HttpServletResponse response,int tg,int tdi) throws IOException, SQLException {
		    String tourId = extractPropertyValue(requestData, "tourId");
		    String userId = extractPropertyValue(requestData, "userId");
         System.out.println("Wo id jo delete hone"+tourId+" user id is "+userId);
		    try {
		        con = DatabaseConnectionManager.getConnection();
		        
		        String updateQuery = "UPDATE tour SET number_of_persons = number_of_persons + ? WHERE tourid = ?";
		        try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
		            updateStmt.setInt(1, tg);
		            updateStmt.setInt(2, tdi);
		            int rowsUpdated = updateStmt.executeUpdate();
		            if (rowsUpdated == 0) {
		                // Tour not found
		                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		                response.getWriter().println("Tour not found");
		                return;
		            }
		        }
		        String query = "DELETE FROM user_tour_booking WHERE tour_id = ? and user_id = ?";
		        try (PreparedStatement pstmt = con.prepareStatement(query)) {
		            pstmt.setString(1, tourId);
		            pstmt.setString(2, userId);
		            int rowsDeleted = pstmt.executeUpdate();
		            if (rowsDeleted > 0) {
		                response.setStatus(HttpServletResponse.SC_OK);
		                System.out.println("deleted");
		                response.getWriter().println("Tour deleted successfully");
		            } else {
		                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		                response.getWriter().println("Tour not found");
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        response.getWriter().println("Error occurred: " + e.getMessage());
		    }
		}

	 protected void handleSetFavorite(StringBuilder requestData, HttpServletResponse response) throws IOException, SQLException {
		 String usrId = extractPropertyValue(requestData, "userId");
		 String img = extractPropertyValue(requestData, "image_url");
	        String lc = extractPropertyValue(requestData, "location");
	        String pric = extractPropertyValue(requestData, "price");
	        String prsnGoing = extractPropertyValue(requestData, "number_of_persons");
	        String tourId = extractPropertyValue(requestData, "tourid");
	        String dsc = extractPropertyValue(requestData, "descreption");
	        String dptDat = extractPropertyValue(requestData, "departure_date");
	        String cId = extractPropertyValue(requestData, "company_id");
	        String dysLeft = extractPropertyValue(requestData, "number_of_days");
	        
	        
	        System.out.println("Userid"+usrId+"\nimgUrl"+img+"\nlocation"+lc+"\nPrice"+pric+"\nTouristGoing"+prsnGoing+"\ntorId"+tourId+"\nDescript:"+dsc+"\nDepartureDat"+dptDat+"\nCompanyIdcid"+cId+"\nDysLeft"+dysLeft+"\n");
	        
               try {
	            con = DatabaseConnectionManager.getConnection();
	            int usId = Integer.parseInt(usrId);
	            int trId = Integer.parseInt(tourId);
	            int favoriteIn = setFavorite(usId, trId);
	            if(favoriteIn>0)
	            {
	            	response.setStatus(HttpServletResponse.SC_OK);
	                response.getWriter().write("Data inserted successfully. Rows affected: ");
	            }
	            else
	            {
	            	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                response.getWriter().write("Error occurred while inserting data into the database.");
	            }
               }catch(Exception e)
               {
            	   System.out.print(e);
               }
	            
//	            System.out.println(loggedIn);
//	            if (loggedIn) {
//	            	String query = "SELECT id,fname, lname, email, password, contact_no, user_type FROM users WHERE email = ? AND password = ?";
//	                try (PreparedStatement pstmt = con.prepareStatement(query)) {
//	                    pstmt.setString(1, email);
//	                    pstmt.setString(2, password);
//	                   // List<User> userDataList = new ArrayList<>();
//	                    try (ResultSet rs = pstmt.executeQuery()) {
//	                        if (rs.next()) {
//	                            // Initialize User object with fetched data
//	                        	int tu = rs.getInt("user_type");
//	                        	String utt = ""+tu;
//	                            User user = new User(rs.getInt("id"),rs.getString("fname"),rs.getString("lname"),rs.getString("email"),rs.getString("password"),rs.getString("contact_no"),utt);
//	                            	                            // Serialize User object to JSON
//	                            System.out.println("\n"+user+"\n\n");
//	                            //userDataList.add(user);
//	                            //Map<String, List<User>> resultMap = new HashMap<>();
//	                            //resultMap.put("results", userDataList);
//
//	                            ObjectMapper objectMapper = new ObjectMapper();
//	                            String userJson = objectMapper.writeValueAsString(user);
//	                            System.out.println(userJson);
//	                            
//	                            // Send JSON response
//	                            response.setStatus(HttpServletResponse.SC_OK);
//	                            response.setContentType("application/json");
//	                            response.getWriter().print(userJson);
//	                          
//	                        } else {
//	                        	 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	                             response.setContentType("text/plain");
//	                             response.getWriter().println("Login failed");
//	                        }
//	                    }
//	                }
//	                
//	                
//	                
//	            } else {
//	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	                response.getWriter().println("Login failed");
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	            response.getWriter().println("Error occurred: " + e.getMessage());
//	        }
	    }
	  
	 protected void fetchReview(int companyId,HttpServletResponse response) throws IOException, SQLException
		{
	        PrintWriter out = response.getWriter();
	        ObjectMapper mapper = new ObjectMapper();
             
	        try {
	            // Get tours for the given company ID
	        	con = DatabaseConnectionManager.getConnection();
	            String query = "SELECT tourid,title FROM tour WHERE company_id = ?";
	            PreparedStatement stmt = con.prepareStatement(query);
	            stmt.setInt(1, companyId);
	            ResultSet rs = stmt.executeQuery();

	            ArrayNode toursArray = mapper.createArrayNode();

	            while (rs.next()) {
	                int tourId = rs.getInt("tourid");
                    String tourTt = rs.getString("title");
	                // Get reviews for each tour
	                String reviewQuery = "SELECT userid, comments, rating FROM user_tour_review WHERE tourid = ?";
	                PreparedStatement reviewStmt = con.prepareStatement(reviewQuery);
	                reviewStmt.setInt(1, tourId);
	                ResultSet reviewRs = reviewStmt.executeQuery();

	                while (reviewRs.next()) {
	                    int userId = reviewRs.getInt("userid");

	                    // Fetch user's full name
	                    String userQuery = "SELECT fname, lname FROM users WHERE id = ?";
	                    PreparedStatement userStmt = con.prepareStatement(userQuery);
	                    userStmt.setInt(1, userId);
	                    ResultSet userRs = userStmt.executeQuery();

	                    String fullName = "";
	                    if (userRs.next()) {
	                        String fname = userRs.getString("fname");
	                        String lname = userRs.getString("lname");
	                        fullName = fname + " " + lname;
	                    }

	                    ObjectNode reviewJson = mapper.createObjectNode();
	                    reviewJson.put("tourId", tourId);
	                    reviewJson.put("tourTitle", tourTt);
	                    reviewJson.put("userId", userId);
	                    reviewJson.put("userFullName", fullName); // Add full name to JSON
	                    reviewJson.put("comments", reviewRs.getString("comments"));
	                    reviewJson.put("rating", reviewRs.getDouble("rating"));

	                    toursArray.add(reviewJson);

	                    // Close the user result set and statement
	                    userRs.close();
	                    userStmt.close();
	                }

	                // Close the review result set and statement
	                reviewRs.close();
	                reviewStmt.close();
	            }

	            // Send JSON response
	            response.setContentType("application/json");
	            out.print(mapper.writeValueAsString(toursArray));

	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            out.print("{\"error\": \"An  error occurred while retrieving tour reviews.\"}");
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
	        
	        
	        
	        if (action != null && action.equals("signup")) {
                try {
                	System.out.println("signup");
					handleSignup(requestData, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else if (action != null && action.equals("login")) {
                try {
                	System.out.println("loginAb");
					handleLogin(requestData, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else if (action != null && action.equals("tS")) {
                try {
                	
                     // Parse JSON data using Jackson ObjectMapper
                  
                     // Access individual properties from requestData object
                                       
                	System.out.println("TS");
                	handleSearchTour(requestData,response);
                	
                	   
//             		handleSearchTour(requestData, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else if (action != null && action.equals("bookTour")) {
                try {
                	
                     // Parse JSON data using Jackson ObjectMapper
                  
                     // Access individual properties from requestData object
                                       
                	System.out.println("bookedTour(*_*)-");
                	handlebookTour(requestData, response);
                	
                	   
//             		handleSearchTour(requestData, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else if (action != null && action.equals("checkUserId")) {
                try {
                	
                     // Parse JSON data using Jackson ObjectMapper
                  
                     // Access individual properties from requestData object
                                       
                	System.out.println("checkUserID(``__``)-");
                	handleCheckUserId(response);
//                	handlebookTour(requestData, response);
                	
                	   
//             		handleSearchTour(requestData, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else if (action != null && action.equals("cancelTour")) {
                try {
                	 
                     // Parse JSON data using Jackson ObjectMapper
                  
                     // Access individual properties from requestData object
                                       
                	System.out.println("cancelling Tour(``__``)-");
                	String bmn = extractPropertyValue(requestData, "touristGoing");
                    String bmna = extractPropertyValue(requestData, "tourId");
                	int td = Integer.parseInt(bmna);
                	int tg = Integer.parseInt(bmn);
                	System.out.println("Zra baat maan\n"+bmn+"\n");
                	handleDeleteTour(requestData,response,tg,td);
                	
                	//handleCheckUserId(response);
//                	handlebookTour(requestData, response);
                	
                	   
//             		handleSearchTour(requestData, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
	        
            else if (action != null && action.equals("saveFavorite")) {
                try {
                	
                     // Parse JSON data using Jackson ObjectMapper
                  
                     // Access individual properties from requestData object
                                       
                	System.out.println("Favouriting Tour(``__``)-");
                	handleSetFavorite(requestData, response);
                	
                	
                	//handleCheckUserId(response);
//                	handlebookTour(requestData, response);
                	
                	   
//             		handleSearchTour(requestData, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
	        
            else if (action != null && action.equals("manageProfile")) {
                try {
                	
                     // Parse JSON data using Jackson ObjectMapper
                  
                     // Access individual properties from requestData object
                                       
                	System.out.println("Manage Profile (``__``)-");
                	handleManageProfile(requestData, response);
                	
                	
                	//handleCheckUserId(response);
//                	handlebookTour(requestData, response);
                	
                	   
//             		handleSearchTour(requestData, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
	        
	        
            else if (action != null && action.equals("fetchReview")) {
                try {
                	
                     // Parse JSON data using Jackson ObjectMapper
                  
                     // Access individual properties from requestData object
                                       
                	System.out.println("Fetvch Review me(``__``)-");
                	String bmn = extractPropertyValue(requestData, "companyId");
                	int tre = Integer.parseInt(bmn);
                	//handleManageProfile(requestData, response);
                	System.out.println("Fetvch Review me ke compnyId:- "+tre);
                	
                	 fetchReview(tre,response);
                	//handleCheckUserId(response);
//                	handlebookTour(requestData, response);
                	
                	   
//             		handleSearchTour(requestData, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
	        
	        
	        
            
	        
        
	        
	        
            else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Invalid action");
                System.out.println("dsda");
            }
	        
//	        String firstName = extractPropertyValue(requestData, "firstname");
//	        String lastName = extractPropertyValue(requestData, "lastname");
//	        String email = extractPropertyValue(requestData, "email");
//	        String password = extractPropertyValue(requestData, "password");
//	        String phone = extractPropertyValue(requestData, "phone");
//
//	        User u = new User(firstName,lastName,email,password,phone);
//	        System.out.println(u);
//	        int sc=0;
//	        try  {
//con = DatabaseConnectionManager.getConnection();
//	        	
//	        	 sc = insertUser(u);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            // Handle database connection or query execution errors
//	        }
//	        if(sc>0)
//	        	System.out.println("Successsfulla");
//	        else
//	        	System.out.println("fail");
//	        
	} catch (IOException e) {
        e.printStackTrace();
    }
	       
	        
	    	   
	        
	}
	  protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Set CORS headers
	        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	        response.setHeader("Access-Control-Allow-Methods", "POST");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    }
}
