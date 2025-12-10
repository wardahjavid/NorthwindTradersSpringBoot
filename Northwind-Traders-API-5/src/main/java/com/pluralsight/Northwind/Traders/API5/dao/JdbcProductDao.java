package com.pluralsight.Northwind.Traders.API5.dao;
import com.pluralsight.Northwind.Traders.API5.dao.ProductDao;
import com.pluralsight.Northwind.Traders.API5.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

    private final DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";

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
        String sql = "SELECT * FROM Products WHERE ProductID = ?";
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

    @Override
    public Product insert(Product product) {
        String sql = "INSERT INTO Products (ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCategoryId());
            statement.setDouble(3, product.getUnitPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    product.setProductId(generatedId);
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }

            }
            @Override
            public void update(int id, Product product) {
                String sql = "UPDATE products " +
                        "SET ProductName = ?, CategoryID = ?, UnitPrice = ? " +
                        "WHERE ProductID = ?";

                try (Connection conn = dataSource.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setString(1, product.getName());
                    stmt.setInt(2, product.getCategoryId());
                    stmt.setDouble(3, product.getPrice());
                    stmt.setInt(4, id);

                    stmt.executeUpdate();

                } catch (Exception ex) {
                    System.out.println("Error updating product: " + ex.getMessage());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }