package com.warehouse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12741091";
    private static final String DB_USER = "sql12741091";
    private static final String DB_PASSWORD = "your_password_here"; // Replace with your actual password

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve username and password from login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Database connection and query
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM employees WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Authentication successful, create a session
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);

                        // Redirect to ProductEdit.jsp
                        response.sendRedirect("ProductEdit.jsp");
                    } else {
                        // Authentication failed, redirect back to login with error message
                        request.setAttribute("errorMessage", "Invalid username or password");
                        request.getRequestDispatcher("EmployeeLogin.html").forward(request, response);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing your login. Please try again.");
            request.getRequestDispatcher("EmployeeLogin.html").forward(request, response);
        }
    }
}
