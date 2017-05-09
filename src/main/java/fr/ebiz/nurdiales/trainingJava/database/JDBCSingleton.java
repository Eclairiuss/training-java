package fr.ebiz.nurdiales.trainingJava.database;

// import java.io.FileInputStream;
// import java.io.IOException;
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

    /**
     *  .
     * @return .
     */
    public DataSource getDataSource() {
        if (datasource == null) {
            HikariConfig config = new HikariConfig("/hikari.properties");
            // try {
            //     config.getDataSourceProperties().load(new FileInputStream("/hikari2.properties"));
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
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
