package com.pluralsight.NorthwindTradersAPI6.controllers;
import com.pluralsight.NorthwindTradersAPI6.dao.CategoryDao;
import com.pluralsight.NorthwindTradersAPI6.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/categories", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {
        return categoryDao.insert(category);
    }

    @RequestMapping(path = "/categories/{categoryId}", method = RequestMethod.PUT)
    public void updateCategory(@PathVariable int categoryId, @RequestBody Category category) {
        categoryDao.update(categoryId, category);
    }
}