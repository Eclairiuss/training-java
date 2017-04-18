package fr.ebiz.nurdiales.trainingJava.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCSingleton {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCSingleton.class);
    private String DB_URL;
    private String DB_USERNAME;
    private String DB_PASSWORD;

    private Connection DB_CONNECTION;

    /**
     * <<<<<<< HEAD:src/main/java/fr/ebiz/nurdiales/trainingJava/database/JDBCSingleton.java
     * Constructor of JDBCSingleton who init all value and connect to DataBase.
     * =======
     * Constructor.
     * >>>>>>> c9b01a48d449a4286b2074292ae686b75bc0a9a4:src/fr/ebiz/nurdiales/trainingJava/database/JDBCSingleton.java
     */
    public JDBCSingleton() {
        DB_URL = "jdbc:mysql://localhost:3306/computer-database-db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        DB_USERNAME = "admincdb";
        DB_PASSWORD = "qwerty1234";

        DB_CONNECTION = connectToDB();
    }

    /**
     * Method for connect the singleton and the program to DB.
     * @return Return the connection to the DB.
     */
    private Connection connectToDB() {
        LOGGER.debug("Init driver ");
        try {
            LOGGER.debug("Succes driver ?");
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.debug("Error Connection");
        }
        return null;
    }

    /**
     * @author eclairiuss
     */
    private static class JDBCSingletonManagerHolder {
        private static final JDBCSingleton INSTANCE = new JDBCSingleton();
    }

    /**
     * Return the singleton from the handler.
     *
     * @return return the Singleton.
     */
    public static JDBCSingleton getInstance() {
        return JDBCSingletonManagerHolder.INSTANCE;
    }

    /**
     * <<<<<<< HEAD:src/main/java/fr/ebiz/nurdiales/trainingJava/database/JDBCSingleton.java
     * Try to disconnect the JDBCSingleton of the database.
     *
     * @throws SQLException Error in SQL.
     *                      =======
     *                      Disconnect the Singleton from the database.
     * @throws SQLException Exception of sql request.
     *                      >>>>>>> c9b01a48d449a4286b2074292ae686b75bc0a9a4:src/fr/ebiz/nurdiales/trainingJava/database/JDBCSingleton.java
     */
    public void disconnectToDB() throws SQLException {
        LOGGER.debug("Try close connection");
        DB_CONNECTION.close();
        LOGGER.debug("Connection closed");
    }

    /**
     * <<<<<<< HEAD:src/main/java/fr/ebiz/nurdiales/trainingJava/database/JDBCSingleton.java
     * Method who create a PreparedStatement with a String who connais instructions.
     *
     * @param q String who contain the request to complete.
     * @param q request sql to put into preparedStatement.
     * @return preparedStatement with q sql request.
     * @throws SQLException Error in SQL.
     *                      =======
     *                      Make a prepared statement from the sql request q string.
     * @throws SQLException Exception of sql request.
     *                      >>>>>>> c9b01a48d449a4286b2074292ae686b75bc0a9a4:src/fr/ebiz/nurdiales/trainingJava/database/JDBCSingleton.java
     */
    public PreparedStatement prepareStatement(String q) throws SQLException {
        LOGGER.debug("PrepareStatement asked");
        return DB_CONNECTION.prepareStatement(q);
    }
}
