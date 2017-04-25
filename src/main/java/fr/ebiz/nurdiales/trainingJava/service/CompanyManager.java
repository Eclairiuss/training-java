package fr.ebiz.nurdiales.trainingJava.service;

import java.sql.SQLException;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.dao.CompanyDAO;
import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Company;

public class CompanyManager {
    // private static final Logger logger =
    // LoggerFactory.getLogger(CompanyManager.class);
    private CompanyDAO companyDAO;

    /**
     * Constructor for CompanyManager, create a new CompanyDAO.
     */
    public CompanyManager() {
        this.companyDAO = new CompanyDAO();
    }

    /**
     * Method for get in data base a company corresponding to a id, if no match
     * return null.
     * @param id Id of the company to search in the database.
     * @return The company corresponding to the id.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
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

    /**
     * Method for get all companies in database, dangerous if many.
     * @return All companies in DB.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
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

    /**
     * Method for get pageSize companies,
     * [pageSize*page->(pageSize*(page+1))-1].
     * @param page Number of the page (start = 0).
     * @param pageSize Number of entities wanted.
     * @return pageSize companies in the page's page.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
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

    /**
     * Method for get all companies who have name look like the parameter, not
     * case sensitive.
     * @param name String who must be contain in wanted companies.
     * @return The list of companies who the name contains the parameter.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
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

    /**
     * Method for get pageSize companies who have name look like the parameter,
     * not case sensitive, [pageSize*page->(pageSize*(page+1))-1].
     * @param name Sting who must be contain in wanted companies
     * @param page Number of the current page.
     * @param pageSize Size of the page.
     * @return The pageSize companies who the name contains the parameter.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
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