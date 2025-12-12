package com.pluralsight.NorthwindTradersAPI6.dao;

import com.pluralsight.NorthwindTradersAPI6.models.Category;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCategoryDao implements CategoryDao {

    private final DataSource dataSource;

    public JdbcCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName FROM Categories";

        try (Connection c = dataSource.getConnection();
             PreparedStatement s = c.prepareStatement(sql);
             ResultSet rs = s.executeQuery()) {

            while (rs.next()) {
                categories.add(
                        new Category(
                                rs.getInt("CategoryID"),
                                rs.getString("CategoryName")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getById(int id) {
        String sql = "SELECT CategoryID, CategoryName FROM Categories WHERE CategoryID = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {

            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return new Category(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category insert(Category category) {
        String sql = "INSERT INTO Categories (CategoryName) VALUES (?)";
        try (Connection c = dataSource.getConnection();
             PreparedStatement s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            s.setString(1, category.getCategoryName());
            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();
            if (keys.next()) {
                category.setCategoryId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void update(int id, Category category) {
        String sql = "UPDATE Categories SET CategoryName = ? WHERE CategoryID = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {

            s.setString(1, category.getCategoryName());
            s.setInt(2, id);
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Exercise 6
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Categories WHERE CategoryID = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {

            s.setInt(1, id);
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
