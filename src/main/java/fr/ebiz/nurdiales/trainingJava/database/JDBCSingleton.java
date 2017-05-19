package fr.ebiz.nurdiales.trainingJava.database;

import java.sql.Connection;
import java.sql.SQLException;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

public class JDBCSingleton extends DataSourceTransactionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCSingleton.class);
    private static JDBCSingleton singleton = new JDBCSingleton();
    /**
     *  .
     * @return .
     */
    public DataSource getDataSource() {
        if (singleton.getDataSource() == null) {
            HikariConfig config = new HikariConfig("/hikari.properties");
            // try {
            //     config.getDataSourceProperties().load(new FileInputStream("/hikari2.properties"));
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
            singleton.setDataSource(new HikariDataSource(config));
        }
        return singleton.getDataSource();
    }

    /**
     * Method who create a connection to the database.
     * @return Connection to a database.
     */
    private Connection connectToDB() {
        try {
            Connection conn = singleton.getDataSource().getConnection();
            return conn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Return the singleton from the handler.
     * @return return the Singleton.
     */
    public static JDBCSingleton getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return singleton;
        } catch (ExceptionInInitializerError | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
