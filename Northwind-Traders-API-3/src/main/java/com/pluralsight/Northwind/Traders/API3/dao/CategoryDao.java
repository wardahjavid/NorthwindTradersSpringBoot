package com.pluralsight.Northwind.Traders.API3.dao;

import com.pluralsight.Northwind.Traders.API3.model.Category;
import java.util.List;

public interface CategoryDao {
    List<Category> getAll();
    Category getById(int id);
}
