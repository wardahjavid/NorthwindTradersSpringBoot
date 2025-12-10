package com.pluralsight.Northwind.Traders.API4.dao;
import com.pluralsight.Northwind.Traders.API4.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

    private DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT ProductID, ProductName, CategoryID, UnitPrice FROM Products";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                int categoryID = resultSet.getInt("CategoryID");
                double unitPrice = resultSet.getDouble("UnitPrice");
                Product product = new Product();
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT ProductID, ProductName, CategoryID, UnitPrice FROM Products WHERE ProductID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int productID = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    int categoryID = resultSet.getInt("CategoryID");
                    double unitPrice = resultSet.getDouble("UnitPrice");
                    Product product = new Product();
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Product insert() {
        return insert((Product) null);
    }

    @Override
    public Product insert(Product product) {
        return null;
    }

    @Override
    public Product insert(Product product) {
        String sql = "INSERT INTO products (ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getProductId());
            stmt.setDouble(3, product.getProductPrice());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                product.setId(keys.getInt(1));
            }

        } catch (Exception ex) {
            System.out.println("Error inserting product: " + ex.getMessage());
        }

        return product;
    }

}