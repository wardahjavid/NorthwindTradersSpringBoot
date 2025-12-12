package com.pluralsight.Northwind.Traders.API5.dao;

import com.pluralsight.Northwind.Traders.API5.models.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAll();
    Category getById(int id);
    Category insert(Category category);

    // ✅ Exercise 5
    void update(int id, Category category);
}
