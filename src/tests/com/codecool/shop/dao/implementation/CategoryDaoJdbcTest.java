package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.DataSourceFactory;
import com.codecool.shop.model.Category;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CategoryDaoJdbcTest {
    private static DataSource dataSource;
    private CategoryDaoJdbc categoryDao;
    final int CATEGORY_ID = 1;

    @BeforeEach
    public void prepare() {
        dataSource = DataSourceFactory.getPostgreSQLTestDataSource();
        categoryDao = new CategoryDaoJdbc(dataSource);
        clearCategoryTableToDefaultState();
    }

    @Order(1)
    @Test
    public void should_categoryInsertedIntoDatabaseEqualsThatFromDatabase_when_addCategoryToDatabase() {
        Category category = new Category("Tablet");
        categoryDao.add(category);

        Category categoryFromDB = categoryDao.get(CATEGORY_ID);

        assertEquals(category, categoryFromDB);
    }

    @Order(2)
    @Test
    public void should_updatedCategoryEqualsThatFromDatabase_when_modifyExistingCategory() {
        Category category = new Category("Tablet");
        categoryDao.add(category);

        category.setName("Phone");
        categoryDao.update(category);

        Category categoryFromDB = categoryDao.get(CATEGORY_ID);

        assertEquals(category, categoryFromDB);
    }

    @Order(3)
    @Test
    public void should_returnNull_when_gettingNotExistingCategory() {
        Category category = categoryDao.get(CATEGORY_ID);

        assertNull(category);
    }

    @Order(4)
    @Test
    public void should_returnNull_when_gettingRemovedCategory() {
        Category category = new Category("Tablet");
        categoryDao.add(category);
        categoryDao.remove(CATEGORY_ID);

        Category categoryFromDB = categoryDao.get(CATEGORY_ID);

        assertNull(categoryFromDB);
    }

    @Order(5)
    @Test
    public void should_returnNull_when_updatingNotExistingCategoryAndGettingItFromDatabase() {
        Category category = new Category("Tablet");

        categoryDao.update(category);
        Category categoryFromDB = categoryDao.get(CATEGORY_ID);

        assertNull(categoryFromDB);
    }

    @Order(5)
    @Test
    public void should_returnListOfCategoryOfSize3_when_getAllCategoriesAfterAdding3() {
        categoryDao.add(new Category("Tablet"));
        categoryDao.add(new Category("Phone"));
        categoryDao.add(new Category("Device"));

        List<Category> categories = categoryDao.getAll();

        assertEquals(3, categories.size());
    }

    @AfterAll
    public static void complete() {
        clearCategoryTableToDefaultState();
    }

    private static void clearCategoryTableToDefaultState() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "TRUNCATE TABLE product_category RESTART IDENTITY CASCADE;";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while clearing test table.", error);
        }
    }
}