package fr.ebiz.nurdiales.trainingJava.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.model.Company;

public class CompanyDAO {
	private static final BasicConnector bc = BasicConnector.getInstance();
	private static final String ALL_COMPANIES_REQUEST = "SELECT * FROM company";
	private static final String ALL_COMPANIES_REQUEST_BETWEEN = "SELECT * FROM company LIMIT ? OFFSET ?";
	private static final String COMPANY_BY_ID_REQUEST = "SELECT * FROM company WHERE id=?";

	public static Company getCompanyById(int id) throws SQLException {
		if (bc.getMap().isEmpty() || !bc.getMap().containsKey(id)) {
			PreparedStatement ps = BasicConnector.prepareStatement(COMPANY_BY_ID_REQUEST);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Company c = new Company(rs.getInt("id"), rs.getString("name"));
				bc.getMap().put(id, 1);
				bc.getCompanies().add(c);
				return c;
			}
		} else {
			for (Company c : bc.getCompanies()) {
				if (id == c.getId()) {
					bc.getMap().replace(id, bc.getMap().get(id) + 1);
					return c;
				}
			}
		}
		return null;
	}

	public static List<Company> requestAllCompanies() throws SQLException {
		List<Company> list = new ArrayList<>();
		PreparedStatement ps = BasicConnector.prepareStatement(ALL_COMPANIES_REQUEST);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			list.add(new Company(rs.getInt("id"), rs.getString("name")));
		}
		return list;
	}
	
	public static List<Company> requestAllCompanies(int page, int pageSize) throws SQLException {
		List<Company> list = new ArrayList<>();
		PreparedStatement ps = BasicConnector.prepareStatement(ALL_COMPANIES_REQUEST_BETWEEN);
		ps.setInt(1, pageSize);
		ps.setInt(2, pageSize*page);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			list.add(new Company(rs.getInt("id"), rs.getString("name")));
		}
		return list;
	}
}
