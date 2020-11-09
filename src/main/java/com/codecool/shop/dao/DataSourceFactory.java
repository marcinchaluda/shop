package com.codecool.shop.dao;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataSourceFactory {
    public static DataSource getPostgreSQLShopDataSource() {
        try {
            Properties properties = new Properties();
            ClassLoader loader = DataSourceFactory.class.getClassLoader();
            File file = new File(loader.getResource("connection.properties").getFile());
            FileInputStream in = new FileInputStream(file);
            properties.load(in);
            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setURL(properties.getProperty("jdbc.url"));
            dataSource.setUser(properties.getProperty("jdbc.username"));
            dataSource.setPassword(properties.getProperty("jdbc.password"));
            return dataSource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
