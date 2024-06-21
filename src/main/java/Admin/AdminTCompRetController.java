package Admin;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.result.ResultSetMetaData;


import Pojo.*;
import Util.DatabaseConnectionManager;

/**
 * Servlet implementation class AdminTCompRet
 */
public class AdminTCompRetController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
    /**
     * @see HttpServlet#HttpServlet()
     */
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	 response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	        response.setHeader("Access-Control-Allow-Methods", "POST");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	        
	        	        
    	response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Database connection parameters
        
        List<TourCompany> tourCompanies = new ArrayList<>();

        try {
            // Load the MySQL driver
        	con = DatabaseConnectionManager.getConnection();

            // Connect to the database
            

            // Create a statement
            Statement statement = con.createStatement();

            // Execute the query
            String query = "SELECT * FROM tour_company";
            ResultSet rs = statement.executeQuery(query);

            // Process the result set
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
            out.println("Error occr red: " + e.getMessage());
        }

        // Convert the list to JSON
    
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	 protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Set CORS headers
	        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	        response.setHeader("Access-Control-Allow-Methods", "POST");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	         }

}
