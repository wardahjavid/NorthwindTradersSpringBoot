package com.pluralsight.Northwind.Traders.API4.dao;
import com.pluralsight.Northwind.Traders.API4.models.Category;
import java.util.List;

public interface CategoryDao {
    List<Category> getAll();
    Category getById(int id);

    Category insert(Category category);  // NEW
}