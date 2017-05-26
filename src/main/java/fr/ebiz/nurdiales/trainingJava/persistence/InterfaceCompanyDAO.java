package fr.ebiz.nurdiales.trainingJava.persistence;

import fr.ebiz.nurdiales.trainingJava.exceptions.DAOCompanyException;
import fr.ebiz.nurdiales.trainingJava.model.Company;

import java.util.List;

public interface InterfaceCompanyDAO {
    /**
     * create a company in the database, from a name.
     * @param name of the new company.
     * @throws DAOCompanyException TODO.
     */
    void create(String name) throws DAOCompanyException;

    /**
     * Get a company from an id.
     * @param id of the company.
     * @return the company corresponding.
     * @throws DAOCompanyException TODO.
     */
    Company getCompany(Integer id) throws DAOCompanyException;

    /**
     * Get the list of all companies.
     * @return the list of all companies.
     * @throws DAOCompanyException TODO.
     */
    List<Company> listCompanies() throws DAOCompanyException;

    /**
     * Delete the company corresponding to the given id.
     * @param id of the company to delete.
     * @throws DAOCompanyException TODO.
     */
    void delete(Integer id) throws DAOCompanyException;

    /**
     * Update the name of company with the given id and set it's name with the given name.
     * @param company to update.
     * @throws DAOCompanyException TODO.
     */
    void update(Company company) throws DAOCompanyException;

    /**
     * Method for get pageSize companies,
     * [pageSize*page->(pageSize*(page+1))-1].
     * @param page Number of the page (start = 0).
     * @param pageSize Number of entities wanted.
     * @return pageSize companies in the page's page.
     * @throws DAOCompanyException Error in SQL.
     */
    List<Company> listCompanies(Integer page, Integer pageSize) throws DAOCompanyException;

    /**
     * Method for get all companies who have name look like the parameter, not
     * case sensitive.
     * @param name String who must be contain in wanted companies.
     * @return The list of companies who the name contains the parameter.
     * @throws DAOCompanyException TODO.
     */
    List<Company> listCompanies(String name) throws DAOCompanyException;

    /**
     * Method for get pageSize companies who have name look like the parameter,
     * not case sensitive, [pageSize*page->(pageSize*(page+1))-1].
     * @param name Sting who must be contain in wanted companies
     * @param page Number of the current page.
     * @param pageSize Size of the page.
     * @return The pageSize companies who the name contains the parameter.
     * @throws DAOCompanyException TODO.
     */
    List<Company> listCompanies(String name, Integer page, Integer pageSize) throws DAOCompanyException;

}
