<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.ResultSet, java.sql.Statement, java.sql.PreparedStatement" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Management</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body style="background-image: url('images/background4.jpg'); color: #ffffff;">
    <div class="container">
        <h1 style="color: #00bfff;">Product Management</h1>
        <table>
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Stock Quantity</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Database connection details
                    String dbURL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12741091";
                    String dbUser = "sql12741091";
                    String dbPassword = "your_password_here"; // Replace with actual password

                    Connection conn = null;
                    Statement stmt = null;
                    ResultSet rs = null;

                    try {
                        // Load the MySQL JDBC driver
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM products";
                        rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            int productId = rs.getInt("product_id");
                            String productName = rs.getString("product_name");
                            String category = rs.getString("category");
                            double price = rs.getDouble("price");
                            int stockQuantity = rs.getInt("stock_quantity");
                %>
                <tr>
                    <form action="ProductServlet" method="post">
                        <td><%= productId %></td>
                        <input type="hidden" name="product_id" value="<%= productId %>">
                        <td><input type="text" name="product_name" value="<%= productName %>"></td>
                        <td><input type="text" name="category" value="<%= category %>"></td>
                        <td><input type="number" name="price" step="0.01" value="<%= price %>"></td>
                        <td><input type="number" name="stock_quantity" value="<%= stockQuantity %>"></td>
                        <td>
                            <button type="submit" name="action" value="update" class="action-btn">Update</button>
                            <button type="submit" name="action" value="delete" class="action-btn">Delete</button>
                        </td>
                    </form>
                </tr>
                <%
                        }
                    } catch (Exception e) {
                        out.println("<tr><td colspan='6' style='color: red;'>Error retrieving product data.</td></tr>");
                        e.printStackTrace();
                    } finally {
                        if (rs != null) try { rs.close(); } catch (Exception e) { /* ignored */ }
                        if (stmt != null) try { stmt.close(); } catch (Exception e) { /* ignored */ }
                        if (conn != null) try { conn.close(); } catch (Exception e) { /* ignored */ }
                    }
                %>
            </tbody>
        </table>

        <h2 style="color: #00bfff;">Add New Product</h2>
        <form action="ProductServlet" method="post" class="add-product-form">
            <label for="product_name">Product Name</label>
            <input type="text" id="product_name" name="product_name" required>

            <label for="category">Category</label>
            <input type="text" id="category" name="category" required>

            <label for="price">Price</label>
            <input type="number" id="price" name="price" step="0.01" required>

            <label for="stock_quantity">Stock Quantity</label>
            <input type="number" id="stock_quantity" name="stock_quantity" required>

            <button type="submit" name="action" value="add" class="add-btn">Add Product</button>
        </form>
    </div>
</body>
</html>
