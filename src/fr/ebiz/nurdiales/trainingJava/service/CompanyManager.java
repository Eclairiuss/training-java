package fr.ebiz.nurdiales.trainingJava.service;

import java.sql.SQLException;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;

public class CompanyManager {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ComputerManager.class);
	private CompanyDAO companyDAO;

	public CompanyManager() {
		this.companyDAO = new CompanyDAO();
	}

	public Company companyById(int id) throws CompanyDAOException {
		Company company = null;
		try {
			company = companyDAO.companyById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CompanyDAOException();
		}
		return company;
	}

	public List<Company> allCompanies() throws CompanyDAOException {
		List<Company> companies = null;
		try {
			companies = companyDAO.allCompanies();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CompanyDAOException();
		}
		return companies;
	}

	public List<Company> allCompanies(int page, int pageSize) throws CompanyDAOException {
		List<Company> companies = null;
		try {
			companies = companyDAO.allCompanies(page, pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CompanyDAOException();
		}
		return companies;
	}

	public List<Company> allCompaniesByName(String name) throws CompanyDAOException {
		List<Company> companies = null;
		try {
			companies = companyDAO.allCompaniesByName(name);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CompanyDAOException();
		}
		return companies;
	}

	public List<Company> allCompaniesByName(String name, int page, int pageSize) throws CompanyDAOException {
		List<Company> companies = null;
		try {
			companies = companyDAO.allCompaniesByName(name, page, pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CompanyDAOException();
		}
		return companies;
	}
}
