package fr.ebiz.nurdiales.trainingJava.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class JDBCSingleton {
	// private static final Logger logger = =
	// LoggerFactory.getLogger(BasicConnector.class);
	// LoggerFactory.getLogger(BasicConnector.class);
	private String DB_URL;
	private String DB_DRIVER;
	private String DB_USERNAME;
	private String DB_PASSWORD;

	private Connection DB_CONNECTION;

	public JDBCSingleton() {
		DB_URL = "jdbc:mysql://localhost:3306/computer-database-db";
		DB_DRIVER = "com.mysql.jdbc.Driver";
		DB_USERNAME = "admincdb";
		DB_PASSWORD = "qwerty1234";

		DB_CONNECTION = connectToDB();
	}

	private Connection connectToDB() {
		// logger.debug("Init driver ");
		try {
			Class.forName(DB_DRIVER);
			// logger.debug("Succes driver ?");
			return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			// logger.debug("Succes connection");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			// logger.debug("Error Connection");
		}
		return null;
	}
	
	private static class JDBCSingletonManagerHolder{
		private final static JDBCSingleton instance = new JDBCSingleton();
	}
	
	public static JDBCSingleton getInstance() {
		return JDBCSingletonManagerHolder.instance;
	}

	public void disconnectToDB() throws SQLException {
		// logger.debug("Try close connection");
		DB_CONNECTION.close();
		// logger.debug("Connection closed");
	}

	public PreparedStatement prepareStatement(String q) throws SQLException {
		// logger.debug("PrepareStatement asked");
		return DB_CONNECTION.prepareStatement(q);
	}
}
