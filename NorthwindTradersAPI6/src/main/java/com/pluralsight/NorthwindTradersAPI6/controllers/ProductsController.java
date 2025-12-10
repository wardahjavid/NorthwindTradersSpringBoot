package com.pluralsight.NorthwindTradersAPI6.controllers;

import com.pluralsight.NorthwindTradersAPI6.dao.ProductDao;
import com.pluralsight.NorthwindTradersAPI6.models.Product;
import com.pluralsight.NorthwindTradersAPI6.dao.ProductDao;
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

    @RequestMapping(path = "/products", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productDao.insert(product);
    }

    @RequestMapping(path = "/products/{productId}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable int productId, @RequestBody Product product) {
        productDao.update(productId, product);
    }

    @RequestMapping(path = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id) {
        dao.delete(id);
    }

}