package fr.ebiz.nurdiales.trainingJava.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import fr.ebiz.nurdiales.trainingJava.model.Company;

public class BasicConnector {

	private Connection conn = null;
	private Map<Integer, Integer> map = new HashMap<>();
	private LinkedList<Company> companies = new LinkedList<Company>();
	private static final String url = "jdbc:mysql://localhost:3306/computer-database-db", username = "admincdb",
			password = "qwerty1234", DATABASE_DRIVER = "com.mysql.jdbc.Driver";

	private BasicConnector() {
	}

	private static BasicConnector INSTANCE = new BasicConnector();

	public static BasicConnector getInstance() {
		return INSTANCE;
	}

	public static void connectToDB() throws ClassNotFoundException, SQLException {
		System.out.println("Connecting database...");
		Class.forName(DATABASE_DRIVER);
		INSTANCE.conn = DriverManager.getConnection(url, username, password);
		System.out.println("Database connected!");
	}

	public void disconnectToDB() throws SQLException {
		conn.close();
	}

	public static PreparedStatement prepareStatement(String q) throws SQLException {
		return INSTANCE.conn.prepareStatement(q);
	}

	public Map<Integer, Integer> getMap() {
		return map;
	}

	public LinkedList<Company> getCompanies() {
		return companies;
	}

	public void incrMap(int key, int value) {
		map.replace(key, map.get(key) + value);
	}
}
