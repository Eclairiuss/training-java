package fr.ebiz.nurdiales.trainingJava.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

public class ComputerDAO {
	private static final String ALL_COMPUTERS_REQUEST_BETWEEN = "SELECT * FROM computer WHERE id > ? AND id < ?",
			COMPUTER_BY_ID = "SELECT * FROM computer WHERE id=?",
			COMPUTER_BY_COMPANY = "SELECT * FROM computer WHERE company_id=?",
			INSERT_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) values (?,?,?,?)",
			UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?; ",
			DELETE_COMPUTER = "DELETE FROM computer WHERE id=?";

	public static List<Computer> requestAllComputers(int min, int max) throws SQLException {
		List<Computer> retour = new ArrayList<Computer>();
		PreparedStatement ps = BasicConnector.prepareStatement(ALL_COMPUTERS_REQUEST_BETWEEN);
		ps.setInt(1, min);
		ps.setInt(2, max);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			retour.add(new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"),
					rs.getDate("discontinued"), CompanyDAO.getCompanyById(rs.getInt("company_id"))));
		}
		return retour;
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
