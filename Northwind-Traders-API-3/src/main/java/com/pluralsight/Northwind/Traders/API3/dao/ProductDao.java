package com.pluralsight.Northwind.Traders.API3.dao;

import com.pluralsight.Northwind.Traders.API3.model.Product;
import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    Product getById(int id);
}
