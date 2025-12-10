package com.pluralsight.Northwind.Traders.API3.dao;
import com.pluralsight.Northwind.Traders.API3.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCategoryDao {
    @Component
    public class jdbcCategoryDao implements CategoryDao {

        private DataSource dataSource;

        @Autowired
        public jdbcCategoryDao(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        public List<Category> getAll() {
            List<Category> categories = new ArrayList<>();
            String sql = "SELECT CategoryID, CategoryName FROM categories;";

            try (Connection connection = dataSource.getConnection();
                 Statement stmt = connection.createStatement();
                 ResultSet rows = stmt.executeQuery(sql)) {

                while (rows.next()) {
                    Category c = new Category();
                    c.setId(rows.getInt("CategoryID"));
                    c.setName(rows.getString("CategoryName"));
                    categories.add(c);
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            return categories;
        }

        @Override
        public Category getById(int id) {
            Category c = null;
            String sql = "SELECT CategoryID, CategoryName FROM categories WHERE CategoryID = ?;";

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet row = stmt.executeQuery();

                if (row.next()) {
                    c = new Category();
                    c.setId(row.getInt("CategoryID"));
                    c.setName(row.getString("CategoryName"));
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            return c;
        }
    }
}
