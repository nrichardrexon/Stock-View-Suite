<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.ResultSet, java.sql.Statement" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body style="background-image: url('images/background1.jpg'); color: #ffffff;">
    <div class="container">
        <h1 style="color: #00bfff;">Warehouse Product List</h1>
        <table>
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Stock Quantity</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Database connection details
                    String dbURL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12741091";
                    String dbUser = "sql12741091";
                    String dbPassword = "your_password_here";  // Replace with your actual password

                    Connection conn = null;
                    Statement stmt = null;
                    ResultSet rs = null;

                    try {
                        // Load the MySQL JDBC driver
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                        stmt = conn.createStatement();
                        String sql = "SELECT product_id, product_name, category, price, stock_quantity FROM products";
                        rs = stmt.executeQuery(sql);

                        // Display each product in a table row
                        while (rs.next()) {
                            int productId = rs.getInt("product_id");
                            String productName = rs.getString("product_name");
                            String category = rs.getString("category");
                            double price = rs.getDouble("price");
                            int stockQuantity = rs.getInt("stock_quantity");
                %>
                <tr>
                    <td><%= productId %></td>
                    <td><%= productName %></td>
                    <td><%= category %></td>
                    <td>$<%= price %></td>
                    <td><%= stockQuantity %></td>
                </tr>
                <%
                        }
                    } catch (Exception e) {
                        // Display an error message if the database connection or query fails
                        out.println("<tr><td colspan='5' style='color: red;'>Error retrieving product data. Please try again later.</td></tr>");
                        e.printStackTrace();
                    } finally {
                        // Close the resources to prevent memory leaks
                        if (rs != null) try { rs.close(); } catch (Exception e) { /* ignored */ }
                        if (stmt != null) try { stmt.close(); } catch (Exception e) { /* ignored */ }
                        if (conn != null) try { conn.close(); } catch (Exception e) { /* ignored */ }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
