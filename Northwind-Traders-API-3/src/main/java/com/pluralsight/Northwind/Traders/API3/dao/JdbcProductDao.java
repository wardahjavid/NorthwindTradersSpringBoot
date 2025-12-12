package com.pluralsight.Northwind.Traders.API3.dao;

import com.pluralsight.Northwind.Traders.API3.model.Product;
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
        String sql = "SELECT ProductID, ProductName, CategoryID FROM Products";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rows = statement.executeQuery(sql)) {

            while (rows.next()) {
                Product p = new Product();
                p.setId(rows.getInt("ProductID"));
                p.setName(rows.getString("ProductName"));
                p.setCategoryId(rows.getInt("CategoryID"));
                products.add(p);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return products;
    }

    @Override
    public Product getById(int id) {
        Product p = null;
        String sql = "SELECT ProductID, ProductName, CategoryID FROM Products WHERE ProductID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet row = stmt.executeQuery()) {
                if (row.next()) {
                    p = new Product();
                    p.setId(row.getInt("ProductID"));
                    p.setName(row.getString("ProductName"));
                    p.setCategoryId(row.getInt("CategoryID"));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return p;
    }
}
