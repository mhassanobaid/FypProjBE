package Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import Util.DatabaseConnectionManager;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import Pojo.*;
import Util.DatabaseConnectionManager;


//////////////Admin show users in admin dashbaord tables


/**
 * Servlet implementation class AdminTourRetController
 */
@WebServlet("/AdminTourRetController")
public class AdminTourRetController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Connection con;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminTourRetController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<Map<String, Object>> tours = new ArrayList<>();

        try {
            // Establish database connection
            con = DatabaseConnectionManager.getConnection();

            // Prepare SQL query
            String query = "SELECT * FROM tour";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            // Process the result set
            ObjectMapper mapper = new ObjectMapper();
            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                tours.add(row);
            }

            // Send JSON response
            out.println(mapper.writeValueAsString(tours));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Error occurred: " + e.getMessage());
        } finally {
            // Close the connection
            DatabaseConnectionManager.closeConnection();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * @see HttpServlet#doOptions(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}