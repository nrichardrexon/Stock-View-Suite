package com.warehouse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12741091";
    private static final String DB_USER = "sql12741091";
    private static final String DB_PASSWORD = "your_password_here"; // Replace with your actual password

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve action parameter to determine which operation to perform
        String action = request.getParameter("action");
        
        // Perform the corresponding action based on the "action" parameter
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if ("add".equals(action)) {
                addProduct(request, conn);
            } else if ("update".equals(action)) {
                updateProduct(request, conn);
            } else if ("delete".equals(action)) {
                deleteProduct(request, conn);
            }
            
            // Redirect back to the ProductEdit.jsp page after operation
            response.sendRedirect("ProductEdit.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ProductEdit.jsp?error=An error occurred while processing your request.");
        }
    }

    // Method to add a new product
    private void addProduct(HttpServletRequest request, Connection conn) throws Exception {
        String productName = request.getParameter("product_name");
        String category = request.getParameter("category");
        double price = Double.parseDouble(request.getParameter("price"));
        int stockQuantity = Integer.parseInt(request.getParameter("stock_quantity"));

        String sql = "INSERT INTO products (product_name, category, price, stock_quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productName);
            stmt.setString(2, category);
            stmt.setDouble(3, price);
            stmt.setInt(4, stockQuantity);
            stmt.executeUpdate();
        }
    }

    // Method to update an existing product
    private void updateProduct(HttpServletRequest request, Connection conn) throws Exception {
        int productId = Integer.parseInt(request.getParameter("product_id"));
        String productName = request.getParameter("product_name");
        String category = request.getParameter("category");
        double price = Double.parseDouble(request.getParameter("price"));
        int stockQuantity = Integer.parseInt(request.getParameter("stock_quantity"));

        String sql = "UPDATE products SET product_name = ?, category = ?, price = ?, stock_quantity = ? WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productName);
            stmt.setString(2, category);
            stmt.setDouble(3, price);
            stmt.setInt(4, stockQuantity);
            stmt.setInt(5, productId);
            stmt.executeUpdate();
        }
    }

    // Method to delete a product
    private void deleteProduct(HttpServletRequest request, Connection conn) throws Exception {
        int productId = Integer.parseInt(request.getParameter("product_id"));

        String sql = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        }
    }
}
