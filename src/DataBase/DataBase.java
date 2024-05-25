package DataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBase {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
            Properties prop = loadProperties();
            String url = prop.getProperty("dburl");

                conn = DriverManager.getConnection(url, prop);
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {

        try(FileInputStream fis = new FileInputStream("db.properties")) {
            Properties prop = new Properties();
            prop.load(fis);
            return prop;
        }
        catch (IOException e) {
            throw new DBException(e.getMessage());
        }
    }
}
