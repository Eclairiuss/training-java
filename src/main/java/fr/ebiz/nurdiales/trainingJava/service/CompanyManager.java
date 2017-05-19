package fr.ebiz.nurdiales.trainingJava.service;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.DAOException;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyManager {
    // private static final Logger logger =
    // LoggerFactory.getLogger(CompanyManager.class);
    @Autowired
    private CompanyDAO companyDAO;

    /**
     * Constructor for CompanyManager.
     */
    public CompanyManager() {

    }

    /**
     * Method for get in data base a company corresponding to a id, if no match
     * return null.
     * @param id Id of the company to search in the database.
     * @return The company corresponding to the id.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public Company get(int id) throws CompanyDAOException {
        Company company = null;
        if (id > 0) {
            company = companyDAO.companyById(id);
        }
        return company;
    }

    /**
     * Method for get in data base a company corresponding to a id, if no match
     * return null.
     * @param sId Id of the company to search in the database.
     * @return The company corresponding to the id.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public Company get(String sId) throws CompanyDAOException {
        try {
            int id = Integer.parseInt(sId);
            return companyDAO.companyById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Method for get all companies in database, dangerous if many.
     * @return All companies in DB.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public List<Company> getAll() throws CompanyDAOException {
        List<Company> companies = null;
        companies = companyDAO.allCompanies();
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
    @Transactional(rollbackFor = {DAOException.class})
    public List<Company> getAll(int page, int pageSize) throws CompanyDAOException {
        List<Company> companies = null;
        companies = companyDAO.allCompanies(page, pageSize);
        return companies;
    }

    /**
     * Method for get all companies who have name look like the parameter, not
     * case sensitive.
     * @param name String who must be contain in wanted companies.
     * @return The list of companies who the name contains the parameter.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public List<Company> getAll(String name) throws CompanyDAOException {
        List<Company> companies = null;
        companies = companyDAO.allCompaniesByName(name);
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
    @Transactional(rollbackFor = {DAOException.class})
    public List<Company> getAll(String name, int page, int pageSize) throws CompanyDAOException {
        List<Company> companies = null;
        companies = companyDAO.allCompaniesByName(name, page, pageSize);
        return companies;
    }

    /**
     * Methode to delete a company in the database by his id.
     * @param i Id of company to delete.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws CompanyDAOException Error in the CompanyDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public int delete(int i) throws CompanyDAOException {
        return companyDAO.delete(i);
    }
}
