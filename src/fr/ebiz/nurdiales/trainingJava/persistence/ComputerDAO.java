package fr.ebiz.nurdiales.trainingJava.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.database.JDBCSingleton;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

public class ComputerDAO {
	private static final String COMPUTER_TABLE = "computer ";
	private static final String COMPUTER_ID = "id ";
	private static final String COMPUTER_NAME = "name ";
	private static final String COMPUTER_INTRODUCED = "introduced ";
	private static final String COMPUTER_DISCONTINUED = "discontinued ";
	private static final String COMPUTER_COMPANY = "company_id ";
	private static final String SELECT = "SELECT * FROM " + COMPUTER_TABLE;
	private static final String LIKE = "LIKE '%?%' ";
	private static final String LIMIT_OFFSET = "LIMIT ? OFFSET ? ";
	private static final String ALL = SELECT + LIMIT_OFFSET;
	private static final String BY_COMPANY_NAME = SELECT + "WHERE ? " + LIMIT_OFFSET;
	private static final String BY_COMPANY_ID = SELECT + "WHERE " + COMPUTER_COMPANY + "+=? " + LIMIT_OFFSET;
	private static final String BY_COMPANY_NAME_AND_NAME = SELECT + "WHERE (?) AND + " + COMPUTER_NAME + LIKE+ LIMIT_OFFSET;
	private static final String BY_COMPANY_ID_AND_NAME = SELECT + "WHERE + " + COMPUTER_COMPANY + "=? AND "	+ COMPUTER_NAME + LIKE + LIMIT_OFFSET;
	private static final String BY_NAME = SELECT + "WHERE " + COMPUTER_NAME + LIKE + LIMIT_OFFSET;
	private static final String BY_ID = SELECT + "WHERE " + COMPUTER_ID + "=?";
	private static final String INSERT_COMPUTER = "INSERT INTO " + COMPUTER_TABLE + "(" + COMPUTER_NAME + "," + COMPUTER_INTRODUCED + "," + COMPUTER_DISCONTINUED + "," + COMPUTER_COMPANY + ") values (?,?,?,?)";
	private static final String UPDATE_COMPUTER = "UPDATE " + COMPUTER_TABLE + "SET " + COMPUTER_NAME + "=?, " + COMPUTER_INTRODUCED + "=?, " + COMPUTER_DISCONTINUED + "=?, " + COMPUTER_COMPANY + "=? WHERE "+ COMPUTER_ID + "=? ";
	private static final String DELETE_COMPUTER = "DELETE FROM " + COMPUTER_TABLE + "WHERE id=?";
	// private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	public List<Computer> requestAllComputers(int page, int pageSize) throws SQLException {
		JDBCSingleton connection = JDBCSingleton.getInstance();
		List<Computer> retour = new ArrayList<Computer>();
		PreparedStatement ps = connection.prepareStatement(ALL);
		ps.setInt(1, pageSize);
		ps.setInt(2, pageSize * page);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			retour.add(
					new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME), rs.getDate(COMPUTER_INTRODUCED),
							rs.getDate(COMPUTER_DISCONTINUED), CompanyDAO.companyById(rs.getInt(COMPUTER_COMPANY))));
		}
		return retour;
	}

	public List<Computer> requestAllComputersByCompanyName(String name, int page, int pageSize) throws SQLException {
		JDBCSingleton connection = JDBCSingleton.getInstance();
		List<Computer> retour = new ArrayList<Computer>();
		{
			List<Company> companies = CompanyDAO.allCompaniesByName(name);
			StringBuffer idCompanies = new StringBuffer();
			boolean notFirst = false;
			for (Company company : companies) {
				if (notFirst) {
					idCompanies.append(" OR ");
				}
				idCompanies.append(COMPUTER_ID + "=" + company.getId());
				if (!notFirst) {
					notFirst = true;
				}
			}
			PreparedStatement ps = connection.prepareStatement(BY_COMPANY_NAME);
			ps.setString(1, idCompanies.toString());
			ps.setInt(2, pageSize);
			ps.setInt(3, pageSize * page);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
						rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
						CompanyDAO.companyById(rs.getInt(COMPUTER_COMPANY))));
			}
		}
		return retour;
	}

	public List<Computer> requestAllComputersByCompanyID(int idCompany, int page, int pageSize) throws SQLException {
		JDBCSingleton connection = JDBCSingleton.getInstance();
		List<Computer> retour = new ArrayList<Computer>();
		PreparedStatement ps = connection.prepareStatement(BY_COMPANY_ID);
		ps.setInt(1, idCompany);
		ps.setInt(2, pageSize);
		ps.setInt(3, pageSize * page);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			retour.add(
					new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME), rs.getDate(COMPUTER_INTRODUCED),
							rs.getDate(COMPUTER_DISCONTINUED), CompanyDAO.companyById(rs.getInt(COMPUTER_COMPANY))));
		}
		return retour;
	}

	public List<Computer> requestAllComputersByName(String name, int page, int pageSize) throws SQLException {
		JDBCSingleton connection = JDBCSingleton.getInstance();
		List<Computer> retour = new ArrayList<Computer>();
		if (!name.contains("'")) {
			PreparedStatement ps = connection.prepareStatement(BY_NAME);
			ps.setString(1, name);
			ps.setInt(2, pageSize);
			ps.setInt(3, pageSize * page);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
						rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
						CompanyDAO.companyById(rs.getInt(COMPUTER_COMPANY))));
			}
		}
		return retour;
	}

	public List<Computer> requestAllComputersByCompanyIDAndName(int idCompany, String name, int page, int pageSize)
			throws SQLException {
		JDBCSingleton connection = JDBCSingleton.getInstance();
		List<Computer> retour = new ArrayList<Computer>();
		if (!name.contains("'")) {
			PreparedStatement ps = connection.prepareStatement(BY_COMPANY_ID_AND_NAME);
			ps.setInt(1, idCompany);
			ps.setString(2, name);
			ps.setInt(3, pageSize);
			ps.setInt(4, pageSize * page);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
						rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
						CompanyDAO.companyById(rs.getInt(COMPUTER_COMPANY))));
			}
		}
		return retour;
	}

	public List<Computer> requestAllComputersByCompanyNameAndName(String companyName, String name, int page,
			int pageSize) throws SQLException {
		JDBCSingleton connection = JDBCSingleton.getInstance();
		List<Computer> retour = new ArrayList<Computer>();
		if (!name.contains("'"))
			;
		{
			List<Company> companies = CompanyDAO.allCompaniesByName(companyName);
			StringBuffer idCompanies = new StringBuffer();
			boolean notFirst = false;
			for (Company company : companies) {
				if (notFirst) {
					idCompanies.append(" OR ");
				}
				idCompanies.append(COMPUTER_ID + "=" + company.getId());
				if (!notFirst) {
					notFirst = true;
				}
			}
			PreparedStatement ps = connection.prepareStatement(BY_COMPANY_NAME_AND_NAME);
			ps.setString(1, idCompanies.toString());
			ps.setString(2, name);
			ps.setInt(3, pageSize);
			ps.setInt(4, pageSize * page);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
						rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
						CompanyDAO.companyById(rs.getInt(COMPUTER_COMPANY))));
			}
		}
		return retour;
	}

	public List<Computer> saladeTomateOignon(String companyName, int companyId, String name, int page, int pageSize)
			throws SQLException {
		List<Computer> retour = new ArrayList<Computer>();
		if (!name.contains("'")) {
			if (companyId == 0)
				return requestAllComputersByCompanyNameAndName(companyName, name, page, pageSize);

		}
		return retour;
	}

	public int Add(Computer c) throws SQLException {
		JDBCSingleton connection = JDBCSingleton.getInstance();
		PreparedStatement ps = connection.prepareStatement(INSERT_COMPUTER);
		ps.setString(1, c.getName());
		;
		if (c.checkDates()) {
			ps.setDate(2, c.getDateOfIntroduced());
			ps.setDate(3, c.getDateOfDiscontinued());
		} else {
			ps.setDate(2, null);
			ps.setDate(3, null);
		}

		try {
			ps.setInt(4, c.getCompany().getId());
		} catch (Exception e) {
			ps.setString(4, null);
		}
		return ps.executeUpdate();
	}

	public int delete(int id) throws SQLException {
		JDBCSingleton connection = JDBCSingleton.getInstance();
		PreparedStatement ps = connection.prepareStatement(DELETE_COMPUTER);
		ps.setInt(1, id);
		return ps.executeUpdate();
	}

	public int update(Computer c) throws SQLException {
		JDBCSingleton connection = JDBCSingleton.getInstance();
		PreparedStatement ps = connection.prepareStatement(UPDATE_COMPUTER);
		ps.setString(1, c.getName());
		if (c.checkDates()) {
			ps.setDate(2, c.getDateOfIntroduced());
			ps.setDate(3, c.getDateOfDiscontinued());
		} else {
			ps.setDate(2, null);
			ps.setDate(3, null);
		}
		try {
			ps.setInt(4, c.getCompany().getId());
		} catch (Exception e) {
			ps.setString(4, null);
		}
		ps.setInt(5, c.getId());
		return ps.executeUpdate();
	}

	public Computer getComputerById(int id) throws SQLException {
		JDBCSingleton connection = JDBCSingleton.getInstance();
		PreparedStatement ps = connection.prepareStatement(BY_ID);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME), rs.getDate(COMPUTER_INTRODUCED),
				rs.getDate(COMPUTER_DISCONTINUED), CompanyDAO.companyById(rs.getInt(COMPUTER_COMPANY)));

	}
}