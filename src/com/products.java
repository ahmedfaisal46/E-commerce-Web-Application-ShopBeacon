package com;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class products
 */
@WebServlet("/products")
public class products extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public products() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {

			// 1. Load the properties file
			Properties props = new Properties();
			// props.load(new FileInputStream("demo.properties"));
			props.load(new FileInputStream("C:/Users/workspace/OnlineShoppingStore/src/com/demo.properties"));
			// 2. Read the props
			
			String theUser = props.getProperty("user");
			String thePassword = props.getProperty("password");
			String theDburl = props.getProperty("dburl");

			System.out.println("Connecting to database...");
			System.out.println("Database URL: " + theDburl);
			System.out.println("User: " + theUser);

			// 3. Get a connection to database
			Class.forName("com.mysql.jdbc.Driver");
			myConn = DriverManager.getConnection(theDburl, theUser, thePassword);

			System.out.println("\nSuccess from products!\n");

			// 4. Create a statement
			myStmt = myConn.createStatement();

			
			// 5. Execute SQL query
			String getPhoneName = getPhone(request);
			System.out.println(setPhone(request));
			
			myRs = myStmt.executeQuery("select * from phone where phonename ='" + getPhoneName  +"'");

			// 6. Process the result set
			while (myRs.next()) {
				System.out.println(myRs.getString("phonename"));
				String result = myRs.getString("phonename");
				request.setAttribute("phonename", result);
				System.out.println(myRs.getString("picture"));
				String result2 = myRs.getString("picture");
				request.setAttribute("picture", result2);
				System.out.println(myRs.getString("price"));
				String result3 = myRs.getString("price");
				request.setAttribute("price", result3);
			}
			
			
			request.getRequestDispatcher("products.jsp").forward(request, response);

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	protected static String setPhone(HttpServletRequest request) {
		Enumeration<String> categories = request.getParameterNames();
		Object category = categories.nextElement();
		String categoryName = (String) category;
		
		return categoryName;
	
	}
	
	protected static String getPhone(HttpServletRequest request) {
		String selectPhone = "";
		if (setPhone(request).contains("sony")) {
			selectPhone = "sony";
		}
		else if(setPhone(request).contains("oneplus")) {
			selectPhone = "oneplus";
		}
		else if(setPhone(request).contains("samsung")) {
			selectPhone = "samsung";
		}
		else if(setPhone(request).contains("iphone")) {
			selectPhone = "iphone";
		}
		else if(setPhone(request).contains("htc")) {
			selectPhone = "htc";
		}
		else if(setPhone(request).contains("windows")) {
			selectPhone = "windows";
		}
		else if(setPhone(request).contains("nokia")) {
			selectPhone = "nokia";
		}
		else if(setPhone(request).contains("motorola")) {
			selectPhone = "motorola";
		}
		else if(setPhone(request).contains("lenovo")) {
			selectPhone = "lenovo";
		}
		else if(setPhone(request).contains("lg")) {
			selectPhone = "lg";
		}
		return selectPhone;
	}


}
