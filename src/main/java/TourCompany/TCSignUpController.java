package TourCompany;



import jakarta.servlet.ServletException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.io.InputStream;
import Util.DatabaseConnectionManager;
import Pojo.*;
import Util.*;
/**
 * Servlet implementation class TCSignUp
 */

@WebServlet(name = "TCSignUp", urlPatterns = { "/TCSignUp" })
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 10,      // 10 MB
  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)

public class TCSignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TCSignUpController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");


        
        Part filePart = request.getPart("file");
        String companyName = request.getParameter("companyName");
        String companyCNIC = request.getParameter("companyCNIC");
        //userId.bankName,accountHolder,accountNumber
        String a =  request.getParameter("userId");
        String b =  request.getParameter("bankName");
        String c =  request.getParameter("accountHolder");
        String d =  request.getParameter("accountNumber");
        String p = "pending";
       
        String dateTimePrefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = filePart.getSubmittedFileName();
        fileName = dateTimePrefix + "__" + fileName;
       System.out.println(fileName+"\n"+companyName+"\n"+companyCNIC+"usrId"+a+"\n"+"BNkNm"+b+"\n"+"accntHoldrNma"+c+"\naccoutnO"+d);
       String savePath = "D:\\Smstr_7_\\collaborat\\Documents";
       String filePath = savePath + File.separator + fileName;
       File file = new File(filePath);
       System.out.println(filePath);
       try (InputStream input = filePart.getInputStream(); 
    		   OutputStream output = new FileOutputStream(file)) {
           byte[] buffer = new byte[1024];
           int length;
           while ((length = input.read(buffer)) > 0) {
               output.write(buffer, 0, length);
           }
           response.getWriter().print("The file uploaded successfully.");
           String sql = "INSERT INTO tour_company (documents, statas, account_holder, bank_name, user_id, cnic, company_name, account_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
           int rowsInserted=-1;
           try 
                 {
        	   con = DatabaseConnectionManager.getConnection();
        	   PreparedStatement statement=null;
        	   try {
        	   statement = con.prepareStatement(sql);

               statement.setString(1, filePath);
               statement.setString(2, p);
               statement.setString(3, c);
               statement.setString(4, b);
               statement.setInt(5, Integer.parseInt(a));
               statement.setString(6, companyCNIC);
               statement.setString(7, companyName);
               statement.setString(8, d);
        	 
               rowsInserted = statement.executeUpdate();
        	   }
        	   catch(Exception e) {e.printStackTrace();}
               if (rowsInserted > 0) {
                   response.getWriter().print("The file uploaded and data inserted successfully.");
               } else {
                   response.getWriter().print("Failed to insert data into the database.");
               }

       } catch (IOException e) {
           e.printStackTrace();
           response.getWriter().print("Failed to upload the file.");
       }
       } catch (IOException e) {
           e.printStackTrace();
           response.getWriter().print("Failed to upload the file.");
       }
        
    

    }

	 protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Set CORS headers
	        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	        response.setHeader("Access-Control-Allow-Methods", "POST");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    }
	
}
