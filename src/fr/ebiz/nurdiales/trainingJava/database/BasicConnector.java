package fr.ebiz.nurdiales.trainingJava.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

public class BasicConnector {
	private Connection conn = null;
	private static Map<Integer, Integer> map = new HashMap<>();
	private static LinkedList<Company> companies = new LinkedList<Company>();
	private static final String url = "jdbc:mysql://localhost:3306/computer-database-db", username = "admincdb",
			password = "qwerty1234", DATABASE_DRIVER = "com.mysql.jdbc.Driver",
			ALL_COMPUTER_REQUEST = "SELECT id, name, introduced, discontinued, company_id FROM computer",
			COMPANY_BY_ID = "SELECT * FROM company WHERE id=";

	public void connectToDB() throws ClassNotFoundException, SQLException {
		System.out.println("Connecting database...");
		Class.forName(DATABASE_DRIVER);
		conn = DriverManager.getConnection(url, username, password);
		System.out.println("Database connected!");
	}

	public void disconnectToDB() throws SQLException {
		conn.close();
	}

	public void requestAllComputers() throws SQLException {
//		List<Computer> retour = new ArrayList<Computer> ();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(ALL_COMPUTER_REQUEST);
		while (rs.next()) {
//			retour.add(
			System.out.println(
					new Computer(rs.getInt("id"), 
								 rs.getString("name"), 
								 rs.getDate("introduced"),
								 rs.getDate("discontinued"), 
								 getCompanyById(rs.getInt("company_id"))
								 )
					);
		}
//		return null;
	}

	public Company getCompanyById(int id) throws SQLException {
		if(map.isEmpty() || !map.containsKey(id))
		{
			Statement stmt = conn.createStatement();
			ResultSet r = stmt.executeQuery(COMPANY_BY_ID + id);
			if(r.next())
			{
				Company c = new Company(r.getInt("id"),r.getString("name"));
				map.put(id, 1);
				companies.add(c);
				return c;
			}
		}
		else {
			for(Company c : companies){
				if(id == c.getId())
					{
						map.replace(id, map.get(id)+1);
						return c;
					}
			}
		}
		return null;
	}
}
