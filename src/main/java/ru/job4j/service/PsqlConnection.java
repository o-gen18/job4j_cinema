package ru.job4j.service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PsqlConnection {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlConnection.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlConnection() {
        Properties cfg = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream in = classLoader.getResourceAsStream("./db.properties");
        try {
            cfg.load(in);
        } catch (IOException e) {
            LOG.error("IOException: ", e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            LOG.error("ClassNotFoundException", e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final PsqlConnection INST = new PsqlConnection();
    }

    public static PsqlConnection instOf() {
        return Lazy.INST;
    }

    public Connection getConnection() throws SQLException {
        return pool.getConnection();
    }
}