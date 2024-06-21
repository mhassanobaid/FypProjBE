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
import Util.*;
import Pojo.*;



@WebServlet("/TCFetchAllToursController")
public class TCFetchAllToursController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TCFetchAllToursController() {
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

            // Fetch tours from the tour table where company_id matches
            String selectQuery = "SELECT * FROM tour WHERE company_id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectQuery);
            pstmt.setInt(1, companyId);

            ResultSet rs = pstmt.executeQuery();
            List<Map<String, Object>> tours = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> tour = new HashMap<>();
                tour.put("tourid", rs.getInt("tourid"));
                tour.put("company_id", rs.getInt("company_id"));
                tour.put("title", rs.getString("title"));
                tour.put("descreption", rs.getString("descreption"));
                tour.put("image_url", rs.getString("image_url"));
                tour.put("price", rs.getInt("price"));
                tour.put("number_of_persons", rs.getInt("number_of_persons"));
                tour.put("location", rs.getString("location"));
                tour.put("number_of_days", rs.getInt("number_of_days"));
                tour.put("departure_date", rs.getDate("departure_date").toString());

                tours.add(tour);
            }

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(tours);
            out.println(json);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"An error occurred while fetching the tours: " + e.getMessage() + "\"}");
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
