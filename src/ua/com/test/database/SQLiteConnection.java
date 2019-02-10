package ua.com.test.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final Logger log = LoggerFactory.getLogger(SQLiteConnection.class);

    private static final String URL = "jdbc:sqlite:AnywhereTest.db";
    private static final String DRIVER = "org.sqlite.SQLiteConnection";

    private static Connection connection = null;

    private SQLiteConnection() {

    }

    private static void loadDriver() {
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException e) {
            log.error("Error loading database driver", e);
        }
    }

    private static void loadConnection() {
        try {
            connection = DriverManager.getConnection(URL);
        }
        catch (SQLException e) {
            log.error("Error creating database connection", e);
        }
    }


    public static Connection getConnection() {
        if (connection == null) {
            loadDriver();
            loadConnection();
        }
        return connection;
    }
}