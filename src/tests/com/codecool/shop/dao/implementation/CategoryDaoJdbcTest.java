package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.DataSourceFactory;
import com.codecool.shop.model.Category;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoJdbcTest {
    private static DataSource dataSource;
    private static CategoryDaoJdbc categoryDao;
    final int CATEGORY_ID = 1;

    @BeforeAll
    public static void prepare() {
        dataSource = DataSourceFactory.getPostgreSQLTestDataSource();
        categoryDao = new CategoryDaoJdbc(dataSource);
    }

    @BeforeEach
    public void clearTable() {
        dropTable();
        createTable();
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
    @Disabled
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
    @Disabled
    public void should_returnNull_when_gettingRemovedCategory() {
        Category category = new Category("Tablet");
        categoryDao.add(category);
        categoryDao.remove(CATEGORY_ID);

        Category categoryFromDB = categoryDao.get(CATEGORY_ID);

        assertNull(categoryFromDB);
    }

    @Order(5)
    @Test
    @Disabled
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

    @Order(6)
    @Test
    public void should_throwRuntimeException_when_addingCategoryNotExistingTable() {
        Category category = new Category("tablet");
        dropTable();

        Executable executable = () -> categoryDao.add(category);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(7)
    @Test
    public void should_throwRuntimeException_when_updatingCategoryNotExistingTable() {
        Category category = new Category("tablet");
        category.setId(CATEGORY_ID);
        dropTable();

        Executable executable = () -> categoryDao.update(category);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(8)
    @Test
    public void should_throwRuntimeException_when_removingCategoryNotExistingTable() {
        dropTable();

        Executable executable = () -> categoryDao.remove(CATEGORY_ID);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(9)
    @Test
    public void should_throwRuntimeException_when_gettingCategoryNotExistingTable() {
        dropTable();

        Executable executable = () -> categoryDao.get(CATEGORY_ID);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(10)
    @Test
    public void should_throwRuntimeException_when_gettingAllCategoriesNotExistingTable() {
        dropTable();

        Executable executable = () -> categoryDao.getAll();

        assertThrows(RuntimeException.class, executable);
    }


    @AfterAll
    public static void complete() {
        dropTable();
    }

    private static void createTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "CREATE TABLE IF NOT EXISTS product_category (\"id\" serial PRIMARY KEY, \"name\" text NOT NULL);";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while creating test table.", error);
        }
    }

    private static void dropTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DROP TABLE IF EXISTS product_category CASCADE;";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while deleting test table.", error);
        }
    }
}