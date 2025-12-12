package com.pluralsight.Northwind.Traders.API5.dao;

import com.pluralsight.Northwind.Traders.API5.models.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    Product getById(int id);
    Product insert(Product product);
    void update(int id, Product product);
}
