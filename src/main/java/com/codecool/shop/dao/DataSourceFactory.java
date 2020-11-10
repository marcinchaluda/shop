package com.codecool.shop.dao;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataSourceFactory {

//    private static final Properties properties = new Properties();
//    private static final ClassLoader loader = DataSourceFactory.class.getClassLoader();
//    private static final File file = new File(loader.getResource("connection.properties").getFile());
    private static final PGSimpleDataSource dataSource = new PGSimpleDataSource();

    public static DataSource getPostgreSQLShopDataSource() throws IOException {
//                    System.out.println(file.getAbsolutePath());
//            properties.load(new FileInputStream(file));
        dataSource.setURL("jdbc:postgresql://localhost:5432/codecool_shop");
        dataSource.setUser("wolczi");
        dataSource.setPassword("marcinf1");
        return dataSource;
    }

//    public static DataSource getPostgreSQLTestDataSource() {
//        try {
//            properties.load(new FileInputStream(file));
//            dataSource.setURL(properties.getProperty("jdbc.test.url"));
//            dataSource.setUser(properties.getProperty("jdbc.test.username"));
//            dataSource.setPassword(properties.getProperty("jdbc.test.password"));
//            return dataSource;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
