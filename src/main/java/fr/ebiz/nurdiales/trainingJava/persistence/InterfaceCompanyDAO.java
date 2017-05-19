package fr.ebiz.nurdiales.trainingJava.persistence;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Company;

import java.util.List;

/**
 * Created by eclairiuss on 18/05/17.
 */
public interface InterfaceCompanyDAO {

    /**
     * Method for get in data base a company corresponding to a id, if no match
     * return null.
     * @param id Id of the company to search in the database.
     * @return The company corresponding to the id.
     * @throws CompanyDAOException Error in SQL.
     */
    Company companyById(int id) throws CompanyDAOException;

    /**
     * Method for get all companies in database, dangerous if many.
     * @return All companies in DB.
     * @throws CompanyDAOException Error in SQL.
     */
    List<Company> allCompanies() throws CompanyDAOException;

    /**
     * Method for get pageSize companies,
     * [pageSize*page->(pageSize*(page+1))-1].
     * @param page Number of the page (start = 0).
     * @param pageSize Number of entities wanted.
     * @return pageSize companies in the page's page.
     * @throws CompanyDAOException Error in SQL.
     */
    List<Company> allCompanies(int page, int pageSize) throws CompanyDAOException;

    /**
     * Methode to delete a company in the database by his id.
     * @param i Id of company to delete.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws CompanyDAOException Error in the CompanyDAO SQL.
     */
    int delete(int i) throws CompanyDAOException;

    /**
     * Method for get pageSize companies who have name look like the parameter,
     * not case sensitive, [pageSize*page->(pageSize*(page+1))-1].
     * @param name Sting who must be contain in wanted companies
     * @param page Number of the current page.
     * @param pageSize Size of the page.
     * @return The pageSize companies who the name contains the parameter.
     * @throws CompanyDAOException Error in SQL.
     */
    List<Company> allCompaniesByName(String name, int page, int pageSize) throws CompanyDAOException;

    /**
     * Method for get all companies who have name look like the parameter, not
     * case sensitive.
     * @param name String who must be contain in wanted companies.
     * @return The list of companies who the name contains the parameter.
     * @throws CompanyDAOException Error in SQL.
     */
    List<Company> allCompaniesByName(String name) throws CompanyDAOException;
}
