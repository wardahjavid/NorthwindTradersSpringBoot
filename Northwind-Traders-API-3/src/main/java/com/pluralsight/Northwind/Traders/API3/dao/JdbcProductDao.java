package com.pluralsight.Northwind.Traders.API3.dao;
import com.pluralsight.Northwind.Traders.API3.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao {
    @Component
    public class jdbcProductDao implements ProductDao {

        private DataSource dataSource;

        @Autowired
        public jdbcProductDao(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        public List<Product> getAll() {
            List<Product> products = new ArrayList<>();
            String sql = "SELECT ProductID, ProductName, CategoryID FROM products;";

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
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            return products;
        }

        @Override
        public Product getById(int id) {
            Product p = null;
            String sql = "SELECT ProductID, ProductName, CategoryID FROM products WHERE ProductID = ?;";

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet row = stmt.executeQuery();

                if (row.next()) {
                    p = new Product();
                    p.setId(row.getInt("ProductID"));
                    p.setName(row.getString("ProductName"));
                    p.setCategoryId(row.getInt("CategoryID"));
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            return p;
        }
    }
}
