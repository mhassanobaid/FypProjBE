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
import java.util.HashMap;
import java.util.Map;
import Pojo.*;
import Util.DatabaseConnectionManager;


@WebServlet("/TCManageTour")
public class TCManageTourController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String tourIdStr = request.getParameter("tourid");
        if (tourIdStr == null || tourIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Tour ID is required.\"}");
            return;
        }

        int tourId;
        try {
            tourId = Integer.parseInt(tourIdStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid Tour ID.\"}");
            return;
        }

        Connection connection = null;
        try {
            connection = DatabaseConnectionManager.getConnection();
            String query = "SELECT * FROM tour WHERE tourid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Map<String, Object> tourDetails = new HashMap<>();
                tourDetails.put("tourid", resultSet.getInt("tourid"));
                tourDetails.put("company_id", resultSet.getInt("company_id"));
                tourDetails.put("title", resultSet.getString("title"));
                tourDetails.put("description", resultSet.getString("descreption"));
                tourDetails.put("image_url", resultSet.getString("image_url"));
                tourDetails.put("price", resultSet.getInt("price"));
                tourDetails.put("number_of_persons", resultSet.getInt("number_of_persons"));
                tourDetails.put("location", resultSet.getString("location"));
                tourDetails.put("number_of_days", resultSet.getInt("number_of_days"));
                tourDetails.put("departure_date", resultSet.getDate("departure_date").toString());

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(tourDetails);
                out.println(json);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"error\": \"Tour not found.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"Database error.\"}");
        } finally {
            DatabaseConnectionManager.closeConnection();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Fetch and convert parameters
        String tourIdStr = request.getParameter("tourid");
        String companyIdStr = request.getParameter("company_id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("image_url");
        String priceStr = request.getParameter("price");
        String numberOfPersonsStr = request.getParameter("number_of_persons");
        String location = request.getParameter("location");
        String numberOfDaysStr = request.getParameter("number_of_days");
        String departureDateStr = request.getParameter("departure_date");

        if (tourIdStr == null || companyIdStr == null || title == null || description == null ||
            imageUrl == null || priceStr == null || numberOfPersonsStr == null || location == null ||
            numberOfDaysStr == null || departureDateStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"All fields are required.\"}");
            return;
        }

        int tourId, companyId, price, numberOfPersons, numberOfDays;
        java.sql.Date departureDate;
        try {
            tourId = Integer.parseInt(tourIdStr);
            companyId = Integer.parseInt(companyIdStr);
            price = Integer.parseInt(priceStr);
            numberOfPersons = Integer.parseInt(numberOfPersonsStr);
            numberOfDays = Integer.parseInt(numberOfDaysStr);
            departureDate = java.sql.Date.valueOf(departureDateStr);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid input data.\"}");
            return;
        }

        Connection connection = null;
        try {
            connection = DatabaseConnectionManager.getConnection();
            String updateQuery = "UPDATE tour SET company_id = ?, title = ?, description = ?, image_url = ?, price = ?, number_of_persons = ?, location = ?, number_of_days = ?, departure_date = ? WHERE tourid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, companyId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, imageUrl);
            preparedStatement.setInt(5, price);
            preparedStatement.setInt(6, numberOfPersons);
            preparedStatement.setString(7, location);
            preparedStatement.setInt(8, numberOfDays);
            preparedStatement.setDate(9, departureDate);
            preparedStatement.setInt(10, tourId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Tour updated successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"error\": \"Tour not found.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"Database error.\"}");
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
