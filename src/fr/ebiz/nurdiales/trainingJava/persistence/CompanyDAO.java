package fr.ebiz.nurdiales.trainingJava.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.model.Company;

public class CompanyDAO {
	private static final BasicConnector bc = BasicConnector.getInstance();
	private static final String COMPANY_BY_ID = "SELECT * FROM company WHERE id=?";

	public static Company getCompanyById(int id) throws SQLException {
		if (bc.getMap().isEmpty() || !bc.getMap().containsKey(id)) {
			PreparedStatement ps = BasicConnector.prepareStatement(COMPANY_BY_ID);
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

}
