package com.imconstantine.netexam.database.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcSession.class);

    private Connection connection;

    public JdbcSession() {
        super();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(JdbcUtils.URL, JdbcUtils.USER, JdbcUtils.PASSWORD);
        } catch (ClassNotFoundException | SQLException exception) {
            LOGGER.info("Can't create connection", exception);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException exception) {
            LOGGER.info("Can't close connection", exception);
        }
    }

}
