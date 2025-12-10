package com.pluralsight.Northwind.Traders.API3.controllers;

import com.pluralsight.Northwind.Traders.API3.dao.ProductDao;
import com.pluralsight.Northwind.Traders.API3.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductsController {

    private final ProductDao dao;

    @Autowired
    public ProductsController(ProductDao dao) {
        this.dao = dao;
    }

    @GetMapping("/products")
    public List<Product> getAll() {
        return dao.getAll();
    }

    @GetMapping("/products/{id}")
    public Product getById(@PathVariable int id) {
        return dao.getById(id);
    }
}
