package fr.ebiz.nurdiales.trainingJava.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class JDBCSingleton {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCSingleton.class);
    private static DataSource datasource;
    private String DB_URL;
    private String DB_USERNAME;
    private String DB_PASSWORD;
    private int Max_Pool_Size;

    /**
     * Constructor of JDBCSingleton who init all value and connect to DataBase.
     */
    public JDBCSingleton() {
        DB_URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
        DB_USERNAME = "admincdb";
        DB_PASSWORD = "qwerty1234";

        Max_Pool_Size = 10;
    }

    /**
     *  .
     * @return .
     */
    public DataSource getDataSource() {
        if (datasource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setUsername(DB_USERNAME);
            config.setPassword(DB_PASSWORD);
            config.setMaximumPoolSize(Max_Pool_Size);
            config.setAutoCommit(false);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            datasource = new HikariDataSource(config);
        }
        return datasource;
    }

    /**
     * Method who create a connection to the database.
     * @return Connection to a database.
     */
    private Connection connectToDB() {
        try {
            Connection conn = getDataSource().getConnection();
            return conn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @author eclairiuss
     */
    private static class JDBCSingletonManagerHolder {
        private static final JDBCSingleton INSTANCE = new JDBCSingleton();
    }

    /**
     * Return the singleton from the handler.
     * @return return the Singleton.
     */
    public static JDBCSingleton getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return JDBCSingletonManagerHolder.INSTANCE;
        } catch (ExceptionInInitializerError | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
