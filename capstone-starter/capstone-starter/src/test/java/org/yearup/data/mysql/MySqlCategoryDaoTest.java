package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Category;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlCategoryDaoTest extends BaseDaoTestClass {

    private MySqlCategoryDao dao;

    @BeforeEach
    public void setup() {
        dao = new MySqlCategoryDao(dataSource);
    }

    @Test
    public void getById_shouldReturn_theCorrectCategory() {
        //Arrange
        int categoryId = 1;
        Category expectedCategory = new Category() {
            {
                setCategoryId(1);
                setName("Electronics");
                setDescription("Explore the latest gadgets and electronic devices.");
            }
        };
        //Act
        var actual = dao.getById(categoryId);
        //Assert
        assertEquals(expectedCategory.getCategoryId(), actual.getCategoryId());


    }

    @Test
    public void updateCategoryById_shouldUpdate_CorrectCategory() {
        // Arrange
        int categoryId = 2;
        Category existingCategory = new Category(categoryId, "Fashion", "Discover trendy clothing and accessories for men and women.");
        Category updatedCategory = new Category(categoryId, "New Category", "Updated description");

        // Create an instance of the MySqlCategoryDao class
        MySqlCategoryDao mySqlCategoryDao = new MySqlCategoryDao(dataSource);

        // Act
        mySqlCategoryDao.update(categoryId, updatedCategory);

        // Assert
        Category actualCategory = mySqlCategoryDao.getById(categoryId);
        assertEquals(updatedCategory.getCategoryId(), actualCategory.getCategoryId());
        assertEquals(updatedCategory.getName(), actualCategory.getName());
        assertEquals(updatedCategory.getDescription(), actualCategory.getDescription());
    }
}

