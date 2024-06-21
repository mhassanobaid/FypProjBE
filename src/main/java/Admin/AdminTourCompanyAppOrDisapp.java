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
import java.sql.SQLException;
import Pojo.*;
import Util.DatabaseConnectionManager;



@WebServlet("/AdminTourCompanyAppOrDisapp")
public class AdminTourCompanyAppOrDisapp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminTourCompanyAppOrDisapp() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        int companyId = Integer.parseInt(request.getParameter("company_id"));

        if (action == null || action.isEmpty() || companyId <= 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid parameters\"}");
            return;
        }

        Connection con = null;

        try {
            con = DatabaseConnectionManager.getConnection();
            if ("approve".equalsIgnoreCase(action)) {
                // Update tour_company status to 'approve'
                String updateCompanyStatusQuery = "UPDATE tour_company SET status = 'approve' WHERE id = ?";
                PreparedStatement updateCompanyStatusStmt = con.prepareStatement(updateCompanyStatusQuery);
                updateCompanyStatusStmt.setInt(1, companyId);

                int updateCount = updateCompanyStatusStmt.executeUpdate();

                if (updateCount > 0) {
                    // Fetch user_id from tour_company
                    String fetchUserIdQuery = "SELECT user_id FROM tour_company WHERE id = ?";
                    PreparedStatement fetchUserIdStmt = con.prepareStatement(fetchUserIdQuery);
                    fetchUserIdStmt.setInt(1, companyId);
                    ResultSet rs = fetchUserIdStmt.executeQuery();

                    if (rs.next()) {
                        int userId = rs.getInt("user_id");

                        // Update user_type to 1 for the fetched user_id
                        String updateUserTypeQuery = "UPDATE users SET user_type = 1 WHERE id = ?";
                        PreparedStatement updateUserTypeStmt = con.prepareStatement(updateUserTypeQuery);
                        updateUserTypeStmt.setInt(1, userId);
                        updateUserTypeStmt.executeUpdate();

                        out.println("{\"message\": \"Company approved and user type updated.\"}");
                    } else {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.println("{\"error\": \"User ID not found for the company.\"}");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.println("{\"error\": \"Failed to update company status.\"}");
                }
            } else if ("disapprove".equalsIgnoreCase(action)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"Company disapproved.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"Invalid action.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"An error occurred while processing the request: " + e.getMessage() + "\"}");
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
