package com.pluralsight.Northwind.Traders.API4.dao;

import com.pluralsight.Northwind.Traders.API4.models.Product;
import java.util.List;

public interface ProductDao {

    List<Product> getAll();

    Product getById(int id);

    Product insert(Product product);     // for POST
}
