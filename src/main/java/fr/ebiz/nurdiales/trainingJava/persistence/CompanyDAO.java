package fr.ebiz.nurdiales.trainingJava.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.model.Company;

public class CompanyDAO {
	// private static final BasicConnector bc = BasicConnector.getInstance();
	private static final String COMPANY_TABLE = "company ";
	private static final String COMPANY_NAME = "name ";
	private static final String COMPANY_ID = "id "
	private static final String SELECT = "SELECT * FROM " + COMPANY_TABLE;
	private static final String LIKE = "LIKE '%?%' ";
	private static final String LIMIT_OFFSET = "LIMIT ? OFFSET ? ";
	private static final String ALL_COMPANIES_REQUEST = SELECT;
	private static final String ALL_COMPANIES_REQUEST_BETWEEN = SELECT + LIMIT_OFFSET;
	private static final String COMPANIES_BY_NAME = SELECT + "WHERE " + COMPANY_NAME + LIKE;
	private static final String COMPANIES_BY_NAME_BETWEEN = SELECT + "WHERE " + COMPANY_NAME + LIKE + LIMIT_OFFSET;
	private static final String COMPANY_BY_ID_REQUEST = SELECT + "WHERE " + COMPANY_ID + " =? ";
	private static Logger logger;

	public static Company companyById(int id) throws SQLException {
		PreparedStatement ps = BasicConnector.prepareStatement(COMPANY_BY_ID_REQUEST);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Company c = new Company(rs.getInt(COMPANY_ID), rs.getString(COMPANY_NAME));
			return c;
		}
		return null;
	}

	public static List<Company> allCompanies() throws SQLException {
		List<Company> list = new ArrayList<Company>();
		PreparedStatement ps = BasicConnector.prepareStatement(ALL_COMPANIES_REQUEST);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			list.add(new Company(rs.getInt(COMPANY_ID), rs.getString(COMPANY_NAME)));
		}
		return list;
	}

	public static List<Company> allCompanies(int page, int pageSize) throws SQLException {
		List<Company> list = new ArrayList<Company>();
		PreparedStatement ps = BasicConnector.prepareStatement(ALL_COMPANIES_REQUEST_BETWEEN);
		ps.setInt(1, pageSize);
		ps.setInt(2, pageSize * page);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			list.add(new Company(rs.getInt(COMPANY_ID), rs.getString(COMPANY_NAME)));
		}
		return list;
	}

	public static List<Company> allCompaniesByName(String name) throws SQLException {
		List<Company> list = new ArrayList<Company>();
		if (!name.contains("'")) {
			PreparedStatement ps = BasicConnector.prepareStatement(COMPANIES_BY_NAME);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Company(rs.getInt(COMPANY_ID), rs.getString(COMPANY_NAME)));
			}
		}
		return list;
	}

	public static List<Company> allCompaniesByName(String name, int page, int pageSize) throws SQLException {
		List<Company> list = new ArrayList<Company>();
		if (!name.contains("'")) {
			PreparedStatement ps = BasicConnector.prepareStatement(COMPANIES_BY_NAME_BETWEEN);
			ps.setString(1, name);
			ps.setInt(2, pageSize);
			ps.setInt(3, pageSize * page);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Company(rs.getInt(COMPANY_ID), rs.getString(COMPANY_NAME)));
			}
		}
		return list;
	}

	public static void initLogger() {
		logger = LoggerFactory.getLogger(CompanyDAO.class);
	}
}
