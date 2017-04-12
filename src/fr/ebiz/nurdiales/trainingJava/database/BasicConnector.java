package fr.ebiz.nurdiales.trainingJava.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicConnector {

	private static BasicConnector INSTANCE = new BasicConnector();
	private static Logger logger;

	private Connection conn = null;

	// private Map<Integer, Integer> map = new HashMap<>();
	// private LinkedList<Company> companies = new LinkedList<Company>();

	private static final String url = "jdbc:mysql://localhost:3306/computer-database-db",
			DATABASE_DRIVER = "com.mysql.jdbc.Driver";

	private BasicConnector() {
	}

	public static void connectToDB(String username, String password) throws ClassNotFoundException, SQLException {
		logger.debug("Init driver");
		Class.forName(DATABASE_DRIVER);
		logger.debug("Succes driver ?");
		INSTANCE.conn = DriverManager.getConnection(url, username, password);
		logger.debug("Succes connection");
	}

	public static void disconnectToDB() throws SQLException {
		logger.debug("Try close connection");
		INSTANCE.conn.close();
		logger.debug("Connection closed");
	}

	public static PreparedStatement prepareStatement(String q) throws SQLException {
		logger.debug("PrepareStatement asked");
		return INSTANCE.conn.prepareStatement(q);
	}

	// public Map<Integer, Integer> getMap() {
	// return map;
	// }
	//
	// public LinkedList<Company> getCompanies() {
	// return companies;
	// }
	//
	// public void incrMap(int key) {
	// map.replace(key, map.get(key) + 1);
	// }

	public static void initLogger() {
		logger = LoggerFactory.getLogger(BasicConnector.class);
	}
}
