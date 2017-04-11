package fr.ebiz.nurdiales.trainingJava.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

public class ComputerDAO {
	private static final String ALL_COMPUTER_REQUEST = "SELECT * FROM computer",
			ALL_COMPUTERS_REQUEST_BETWEEN = "SELECT * FROM computer WHERE id > ? AND id < ?",
			COMPUTER_BY_ID = "SELECT * FROM computer WHERE id=?",
			COMPUTER_BY_COMPANY = "SELECT * FROM computer WHERE company_id=?",
			INSERT_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) values (?,?,?,?)",
			UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?; ",
			DELETE_COMPUTER = "DELETE FROM computer WHERE id=?";

	public void requestAllComputers() throws SQLException {
		// List<Computer> retour = new ArrayList<Computer> ();
		PreparedStatement ps = BasicConnector.prepareStatement(ALL_COMPUTER_REQUEST);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			// retour.add(
			System.out.println(new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"),
					rs.getDate("discontinued"), CompanyDAO.getCompanyById(rs.getInt("company_id"))));
		}
		// return null;
	}	
	
	public static void requestAllComputers(int id1, int id2) throws SQLException {
		PreparedStatement ps = BasicConnector.prepareStatement(ALL_COMPUTERS_REQUEST_BETWEEN);
		ps.setInt(1, id1);
		ps.setInt(2, id2);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"),
					rs.getDate("discontinued"), CompanyDAO.getCompanyById(rs.getInt("company_id"))));
		}
	}

	public int Add(Computer c) throws SQLException {
		PreparedStatement ps = BasicConnector.prepareStatement(INSERT_COMPUTER);
		ps.setString(1, c.getName());
		ps.setDate(2, c.getDateOfIntroduced());
		ps.setDate(3, c.getDateOfDiscontinued());
		try {
			ps.setInt(4, c.getManufacturer().getId());
		} catch (Exception e) {
			ps.setString(4, null);
		}
		return ps.executeUpdate();
	}

	public int delete(int id) throws SQLException {
		PreparedStatement ps = BasicConnector.prepareStatement(DELETE_COMPUTER);
		ps.setInt(1, id);
		return ps.executeUpdate();
	}

	public int update(Computer c) throws SQLException {
		PreparedStatement ps = BasicConnector.prepareStatement(UPDATE_COMPUTER);
		ps.setString(1, c.getName());
		ps.setDate(2, c.getDateOfIntroduced());
		ps.setDate(3, c.getDateOfDiscontinued());
		try {
			ps.setInt(4, c.getManufacturer().getId());
		} catch (Exception e) {
			ps.setString(4, null);
		}
		ps.setInt(5, c.getId());
		return ps.executeUpdate();
	}

	public Computer getComputerById(int id) throws SQLException {
		PreparedStatement ps = BasicConnector.prepareStatement(COMPUTER_BY_ID);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"), rs.getDate("discontinued"),
				CompanyDAO.getCompanyById(rs.getInt("company_id")));

	}
}

// id=574, name=iPhone 4S, dateOfIntroduced=2011-10-14, dateOfDiscontinued=null,
// manufacturer=Company [id=1, name=Apple Inc.]
