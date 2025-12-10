package com.pluralsight.NorthwindTradersAPI6.dao;

import com.pluralsight.NorthwindTradersAPI6.models.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAll();
    Category getById(int id);
    Category insert(Category category);
    void update(int id, Category category);

    void delete(int id);    // NEW
}
