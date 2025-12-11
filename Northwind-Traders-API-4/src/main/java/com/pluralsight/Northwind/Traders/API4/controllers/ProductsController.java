package com.pluralsight.Northwind.Traders.API4.controllers;

import com.pluralsight.Northwind.Traders.API4.dao.ProductDao;
import com.pluralsight.Northwind.Traders.API4.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductsController {

    private final ProductDao productDao;

    @Autowired
    public ProductsController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    @RequestMapping(path = "/products/{productId}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int productId) {
        return productDao.getById(productId);
    }

    // POST – Exercise API 4
    @RequestMapping(path = "/products", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productDao.insert(product);
    }
}
