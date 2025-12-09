package com.pluralsight.NorthWindTradersAPI2.controllers;

import com.pluralsight.NorthWindTradersAPI2.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private List<Product> products = new ArrayList<>();

    public ProductsController() {
        products.add(new Product(1, "Chai", 1, 18.00));
        products.add(new Product(2, "Chang", 1, 19.00));
        products.add(new Product(3, "Aniseed Syrup", 2, 10.00));
        products.add(new Product(4, "Chef Anton's Cajun Seasoning", 2, 22.00));
        products.add(new Product(5, "Ikura", 3, 31.00));
    }

    // GET all products
    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double maxPrice
    ) {
        return products.stream()
                .filter(p -> name == null || p.getProductName().toLowerCase().contains(name.toLowerCase()))
                .filter(p -> categoryId == null || p.getCategoryId() == categoryId)
                .filter(p -> maxPrice == null || p.getUnitPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // GET product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return products.stream()
                .filter(p -> p.getProductId() == id)
                .findFirst()
                .orElse(null);
    }
}
