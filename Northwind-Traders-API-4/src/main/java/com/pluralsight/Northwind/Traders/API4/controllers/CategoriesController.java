package com.pluralsight.Northwind.Traders.API4.controllers;
import com.pluralsight.Northwind.Traders.API4.dao.CategoryDao;
import com.pluralsight.Northwind.Traders.API4.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriesController {
    private final CategoryDao categoryDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryDao.getAll();
    }


    @RequestMapping(path = "/categories/{categoryId}", method = RequestMethod.GET)
    public Category getCategoryById(@PathVariable int categoryId) {
        return categoryDao.getById(categoryId);
    }
}