package TourCompany;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import Pojo.*;
import Util.DatabaseConnectionManager;



@WebServlet("/TCAddTourController")
public class TCAddTourController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TCAddTourController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Extract properties from the request
        String companyIdStr = request.getParameter("company_id");
        String title = request.getParameter("title");
        String description = request.getParameter("descreption");
        String imageUrl = request.getParameter("image_url");
        String priceStr = request.getParameter("price");
        String numberOfPersonsStr = request.getParameter("number_of_persons");
        String location = request.getParameter("location");
        String numberOfDaysStr = request.getParameter("number_of_days");
        String departureDateStr = request.getParameter("departure_date");

        // Convert fields to appropriate types
        int companyId = Integer.parseInt(companyIdStr);
        int price = Integer.parseInt(priceStr);
        int numberOfPersons = Integer.parseInt(numberOfPersonsStr);
        int numberOfDays = Integer.parseInt(numberOfDaysStr);
        Date departureDate = null;

        try {
            departureDate = new SimpleDateFormat("yyyy-MM-dd").parse(departureDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid date format.\"}");
            return;
        }

        Connection con = null;

        try {
            con = DatabaseConnectionManager.getConnection();

            // Insert into the tour table
            String insertQuery = "INSERT INTO tour (company_id, title, descreption, image_url, price, number_of_persons, location, number_of_days, departure_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setInt(1, companyId);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, imageUrl);
            pstmt.setInt(5, price);
            pstmt.setInt(6, numberOfPersons);
            pstmt.setString(7, location);
            pstmt.setInt(8, numberOfDays);
            pstmt.setDate(9, new java.sql.Date(departureDate.getTime()));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                out.println("{\"message\": \"Tour added successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("{\"error\": \"Failed to add tour.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"An error occurred while adding the tour: " + e.getMessage() + "\"}");
        } finally {
            DatabaseConnectionManager.closeConnection();
        }
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
