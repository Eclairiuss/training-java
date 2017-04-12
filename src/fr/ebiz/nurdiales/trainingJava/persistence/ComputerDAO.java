package fr.ebiz.nurdiales.trainingJava.persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.nurdiales.trainingJava.cli.CLI;
import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

public class ComputerDAO {
	private static final String SELECT = "SELECT * FROM computer ";
	private static final String COMPUTERS_REQUEST = SELECT + "LIMIT ? OFFSET ?";
	private static final String COMPUTERS_BY_COMPANY_NAME = SELECT + "WHERE ? LIMIT ? OFFSET ?";
	private static final String COMPUTERS_BY_COMPANY_NAME_AND_NAME = SELECT
			+ "WHERE (?) AND name LIKE '%?%' LIMIT ? OFFSET ?";
	private static final String COMPUTERS_BY_COMPANY_ID = SELECT + "WHERE company_id=? LIMIT ? OFFSET ?";
	private static final String COMPUTERS_BY_COMPANY_ID_AND_NAME = SELECT
			+ "WHERE company_id=? AND name LIKE '%?%' LIMIT ? OFFSET ?";
	private static final String COMPUTERS_BY_NAME = SELECT + "WHERE name LIKE '%?%' LIMIT ? OFFSET ?";
	private static final String COMPUTER_BY_ID = SELECT + "WHERE id=?";
	private static final String INSERT_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) values (?,?,?,?)";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?; ";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?";
	private static Logger logger;

	public static List<Computer> requestAllComputers(int page, int pageSize) throws SQLException {
		List<Computer> retour = new ArrayList<Computer>();
		PreparedStatement ps = BasicConnector.prepareStatement(COMPUTERS_REQUEST);
		ps.setInt(1, pageSize);
		ps.setInt(2, pageSize * page);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			retour.add(new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"),
					rs.getDate("discontinued"), CompanyDAO.getCompanyById(rs.getInt("company_id"))));
		}
		return retour;
	}

	public static List<Computer> requestAllComputersByCompanyName(String name, int page, int pageSize)
			throws SQLException {
		List<Computer> retour = new ArrayList<>();
		{
			List<Company> companies = CompanyDAO.requestAllCompaniesByName(name);
			StringBuffer idCompanies = new StringBuffer();
			boolean notFirst = false;
			for (Company company : companies) {
				if (notFirst) {
					idCompanies.append(" OR ");
				}
				idCompanies.append("id=" + company.getId());
				if (!notFirst) {
					notFirst = true;
				}
			}
			PreparedStatement ps = BasicConnector.prepareStatement(COMPUTERS_BY_COMPANY_NAME);
			ps.setString(1, idCompanies.toString());
			ps.setInt(2, pageSize);
			ps.setInt(3, pageSize * page);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retour.add(new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"),
						rs.getDate("discontinued"), CompanyDAO.getCompanyById(rs.getInt("company_id"))));
			}
		}
		return retour;
	}

	public static List<Computer> requestAllComputersByCompanyID(int idCompany, int page, int pageSize)
			throws SQLException {
		List<Computer> retour = new ArrayList<Computer>();
		PreparedStatement ps = BasicConnector.prepareStatement(COMPUTERS_BY_COMPANY_ID);
		ps.setInt(1, idCompany);
		ps.setInt(2, pageSize);
		ps.setInt(3, pageSize * page);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			retour.add(new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"),
					rs.getDate("discontinued"), CompanyDAO.getCompanyById(rs.getInt("company_id"))));
		}
		return retour;
	}

	public static List<Computer> requestAllComputersByName(String name, int page, int pageSize) throws SQLException {
		List<Computer> retour = new ArrayList<Computer>();
		if (!name.contains("'")) {
			PreparedStatement ps = BasicConnector.prepareStatement(COMPUTERS_BY_NAME);
			ps.setString(1, name);
			ps.setInt(2, pageSize);
			ps.setInt(3, pageSize * page);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retour.add(new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"),
						rs.getDate("discontinued"), CompanyDAO.getCompanyById(rs.getInt("company_id"))));
			}
		}
		return retour;
	}

	public static List<Computer> requestAllComputersByCompanyIDAndName(int idCompany, String name, int page,
			int pageSize) throws SQLException {
		List<Computer> retour = new ArrayList<Computer>();
		if (!name.contains("'")) {
			PreparedStatement ps = BasicConnector.prepareStatement(COMPUTERS_BY_COMPANY_ID_AND_NAME);
			ps.setInt(1, idCompany);
			ps.setString(2, name);
			ps.setInt(3, pageSize);
			ps.setInt(4, pageSize * page);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retour.add(new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"),
						rs.getDate("discontinued"), CompanyDAO.getCompanyById(rs.getInt("company_id"))));
			}
		}
		return retour;
	}

	public static List<Computer> requestAllComputersByCompanyNameAndName(String companyName, String name, int page,
			int pageSize) throws SQLException {
		List<Computer> retour = new ArrayList<>();
		if (!name.contains("'"))
			;
		{
			List<Company> companies = CompanyDAO.requestAllCompaniesByName(companyName);
			StringBuffer idCompanies = new StringBuffer();
			boolean notFirst = false;
			for (Company company : companies) {
				if (notFirst) {
					idCompanies.append(" OR ");
				}
				idCompanies.append("id=" + company.getId());
				if (!notFirst) {
					notFirst = true;
				}
			}
			PreparedStatement ps = BasicConnector.prepareStatement(COMPUTERS_BY_COMPANY_NAME_AND_NAME);
			ps.setString(1, idCompanies.toString());
			ps.setString(2, name);
			ps.setInt(3, pageSize);
			ps.setInt(4, pageSize * page);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retour.add(new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"),
						rs.getDate("discontinued"), CompanyDAO.getCompanyById(rs.getInt("company_id"))));
			}
		}
		return retour;
	}

	public static List<Computer> saladeTomateOignon(String companyName, int companyId, String name, int page,
			int pageSize) throws SQLException {
		List<Computer> retour = new ArrayList<>();
		if (!name.contains("'")) {
			if (companyId == 0)
				return requestAllComputersByCompanyNameAndName(companyName, name, page, pageSize);

		}
		return retour;
	}

	public static int Add(Computer c) throws SQLException {
		PreparedStatement ps = BasicConnector.prepareStatement(INSERT_COMPUTER);
		ps.setString(1, c.getName());
		Date d1 = c.getDateOfIntroduced();
		Date d2 = c.getDateOfDiscontinued();
		if (c.checkDates()) {
			ps.setDate(2, c.getDateOfIntroduced());
			ps.setDate(3, c.getDateOfDiscontinued());
		} else {
			ps.setDate(2, null);
			ps.setDate(3, null);
		}

		try {
			ps.setInt(4, c.getManufacturer().getId());
		} catch (Exception e) {
			ps.setString(4, null);
		}
		return ps.executeUpdate();
	}

	public static int delete(int id) throws SQLException {
		PreparedStatement ps = BasicConnector.prepareStatement(DELETE_COMPUTER);
		ps.setInt(1, id);
		return ps.executeUpdate();
	}

	public static int update(Computer c) throws SQLException {
		PreparedStatement ps = BasicConnector.prepareStatement(UPDATE_COMPUTER);
		ps.setString(1, c.getName());
		if (c.checkDates()) {
			ps.setDate(2, c.getDateOfIntroduced());
			ps.setDate(3, c.getDateOfDiscontinued());
		} else {
			ps.setDate(2, null);
			ps.setDate(3, null);
		}
		try {
			ps.setInt(4, c.getManufacturer().getId());
		} catch (Exception e) {
			ps.setString(4, null);
		}
		ps.setInt(5, c.getId());
		return ps.executeUpdate();
	}

	public static Computer getComputerById(int id) throws SQLException {
		PreparedStatement ps = BasicConnector.prepareStatement(COMPUTER_BY_ID);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return new Computer(rs.getInt("id"), rs.getString("name"), rs.getDate("introduced"), rs.getDate("discontinued"),
				CompanyDAO.getCompanyById(rs.getInt("company_id")));

	}

	public static void initLogger() {
		logger = LoggerFactory.getLogger(ComputerDAO.class);
	}
}

// id=574, name=iPhone 4S, dateOfIntroduced=2011-10-14, dateOfDiscontinued=null,
// manufacturer=Company [id=1, name=Apple Inc.]
