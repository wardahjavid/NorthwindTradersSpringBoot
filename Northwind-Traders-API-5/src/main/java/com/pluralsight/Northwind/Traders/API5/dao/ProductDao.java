package com.pluralsight.Northwind.Traders.API5.dao;

import com.pluralsight.Northwind.Traders.API5.models.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    Product getById(int id);
    Product insert(Product product);

    // ✅ Exercise 5
    void update(int id, Product product);
}
