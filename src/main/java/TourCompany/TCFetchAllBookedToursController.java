package TourCompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Util.DatabaseConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Util.DatabaseConnectionManager;
import Pojo.*;


@WebServlet("/TCFetchAllBookedToursController")
public class TCFetchAllBookedToursController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TCFetchAllBookedToursController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Extract company_id parameter from the request
        String companyIdStr = request.getParameter("company_id");
        if (companyIdStr == null || companyIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Company ID is required.\"}");
            return;
        }

        int companyId;
        try {
            companyId = Integer.parseInt(companyIdStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid Company ID.\"}");
            return;
        }

        Connection con = null;

        try {
            con = DatabaseConnectionManager.getConnection();

            /*
             * user_id int(11) PK 
tour_id int(11) PK 
booked_at timestamp 
statas varchar(50) 
tourist_going int(11) 
price int(11) 
image_url varchar(255) 
title varchar(100) 
location varchar(100) 
departure_date date 
price_per_tourist int(11) 
number_of_days int(11) 
company_id int(11)
             * */
            // Fetch booked tours from the user_tour_booking table where company_id matches
            String selectQuery = "SELECT ub.booked_at, ub.tourist_going, ub.price, ub.image_url, ub.title, ub.location, ub.departure_date, ub.price_per_tourist, ub.number_of_days, u.fname, u.lname, u.contact_no " +
                                 "FROM user_tour_booking ub " +
                                 "INNER JOIN users u ON ub.user_id = u.id " +
                                 "WHERE ub.company_id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectQuery);
            pstmt.setInt(1, companyId);

            ResultSet rs = pstmt.executeQuery();
            List<Map<String, Object>> bookedTours = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> bookedTour = new HashMap<>();
                bookedTour.put("booked_at", rs.getTimestamp("booked_at").toString());
                bookedTour.put("tourist_going", rs.getInt("tourist_going"));
                bookedTour.put("price", rs.getInt("price"));
                bookedTour.put("image_url", rs.getString("image_url"));
                bookedTour.put("title", rs.getString("title"));
                bookedTour.put("location", rs.getString("location"));
                bookedTour.put("departure_date", rs.getDate("departure_date").toString());
                bookedTour.put("price_per_tourist", rs.getInt("price_per_tourist"));
                bookedTour.put("number_of_days", rs.getInt("number_of_days"));
                bookedTour.put("username", rs.getString("fname") + " " + rs.getString("lname"));
                bookedTour.put("phone_no", rs.getString("contact_no"));

                bookedTours.add(bookedTour);
            }

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(bookedTours);
            out.println(json);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"An error occurred while fetching the booked tours: " + e.getMessage() + "\"}");
        } finally {
            DatabaseConnectionManager.closeConnection();
        }
    }
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
