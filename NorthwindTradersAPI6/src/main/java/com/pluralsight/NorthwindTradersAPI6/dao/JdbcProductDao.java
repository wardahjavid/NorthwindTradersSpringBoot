package com.pluralsight.NorthwindTradersAPI6.dao;

import com.pluralsight.NorthwindTradersAPI6.models.Product;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

    private final DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT ProductID, ProductName, CategoryID, UnitPrice FROM Products";

        try (Connection c = dataSource.getConnection();
             PreparedStatement s = c.prepareStatement(sql);
             ResultSet rs = s.executeQuery()) {

            while (rs.next()) {
                products.add(
                        new Product(
                                rs.getInt("ProductID"),
                                rs.getString("ProductName"),
                                rs.getInt("CategoryID"),
                                rs.getDouble("UnitPrice")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT * FROM Products WHERE ProductID = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {

            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getInt("CategoryID"),
                        rs.getDouble("UnitPrice")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product insert(Product product) {
        String sql = "INSERT INTO Products (ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)";
        try (Connection c = dataSource.getConnection();
             PreparedStatement s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            s.setString(1, product.getProductName());
            s.setInt(2, product.getCategoryId());
            s.setDouble(3, product.getUnitPrice());
            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();
            if (keys.next()) {
                product.setProductId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void update(int id, Product product) {
        String sql = "UPDATE Products SET ProductName=?, CategoryID=?, UnitPrice=? WHERE ProductID=?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {

            s.setString(1, product.getProductName());
            s.setInt(2, product.getCategoryId());
            s.setDouble(3, product.getUnitPrice());
            s.setInt(4, id);
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Products WHERE ProductID = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {

            s.setInt(1, id);
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
