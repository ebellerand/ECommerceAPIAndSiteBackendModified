package org.yearup.controllers;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// add the annotations to make this a REST controller
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests
@CrossOrigin
@RequestMapping("/categories")
@RestController
public class CategoriesController {
    private CategoryDao categoryDao;
    private ProductDao productDao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao, DataSource dataSource) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.dataSource = dataSource;
    }

    // create an Autowired controller to inject the categoryDao and ProductDao

    // add the appropriate annotation for a get action
    @GetMapping
    public List<Category> getAll() {
        // find and return all categories
        List<Category> allCategories = new ArrayList<>();

        String query = "SELECT * FROM categories";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int categoryid = resultSet.getInt("category_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Category category = new Category(categoryid, name, description);
                allCategories.add(category);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCategories;
    }

    // add the appropriate annotation for a get action

    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {
        Category category = null;


        String query = "SELECT * FROM categories WHERE category_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    category = extractCategoryFromResultSet(resultSet);
                    return category;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // get the category by id
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        // get a list of product by categoryId
        return null;
    }

    // add annotation to call this method for a POST action
    // add annotation to ensure that only an ADMIN can call this function
    public Category addCategory(@RequestBody Category category) {
        // insert the category
        return null;
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        // update the category by id
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    public void deleteCategory(@PathVariable int id) {
        // delete the category by id
    }

    private Category extractCategoryFromResultSet(ResultSet resultSet) throws Exception {

            int id = resultSet.getInt("category_id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Category category = new Category(id, name, description);
            return category;
    }
}
