package com.pluralsight.Northwind.Traders.API4.dao;

import com.pluralsight.Northwind.Traders.API4.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCategoryDao implements CategoryDao {

    private final DataSource dataSource;

    @Autowired
    public JdbcCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName FROM Categories";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int categoryID = resultSet.getInt("CategoryID");
                String categoryName = resultSet.getString("CategoryName");
                Category category = new Category(categoryID, categoryName);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public Category getById(int id) {
        String sql = "SELECT CategoryID, CategoryName FROM Categories WHERE CategoryID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int categoryID = resultSet.getInt("CategoryID");
                    String categoryName = resultSet.getString("CategoryName");
                    return new Category(categoryID, categoryName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Category insert(Category category) {
        String sql = "INSERT INTO Categories (CategoryName) VALUES (?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, category.getCategoryName());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                category.setCategoryId(keys.getInt(1));
            }
        } catch (Exception ex) {
            System.out.println("Error inserting category: " + ex.getMessage());
        }

        return category;
    }
}
