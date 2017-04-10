package fr.ebiz.nurdiales.trainingJava.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

public class ComputerDAO {
	private static final String ALL_COMPUTER_REQUEST = "SELECT * FROM computer",
			COMPUTER_BY_COMPANY = "SELECT * FROM computer WHERE company_id=";

	public void requestAllComputers() throws SQLException {
		// List<Computer> retour = new ArrayList<Computer> ();
		ResultSet rs = BasicConnector.executeQuery(ALL_COMPUTER_REQUEST);
		
		while (rs.next()) {
			// retour.add(
			System.out.println(new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"),
					rs.getDate("discontinued"), CompanyDAO.getCompanyById(rs.getInt("company_id"))));
		}
		// return null;
	}
}
