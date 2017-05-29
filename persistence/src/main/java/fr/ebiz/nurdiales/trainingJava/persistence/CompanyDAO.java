package fr.ebiz.nurdiales.trainingJava.persistence;

import fr.ebiz.nurdiales.trainingJava.core.Company;

import java.util.List;

public interface CompanyDAO {
    /**
     * create a company in the database, from a name.
     * @param company the new company.
     * @return long
     */
    int create(Company company);

    /**
     * Get a company from an id.
     * @param id of the company.
     * @return the company corresponding.
     */
    Company getCompany(Integer id);

    /**
     * Get the list of all companies.
     * @return the list of all companies.
     */
    List<Company> listCompanies();

    /**
     * Delete the company corresponding to the given id.
     * @param id of the company to delete.
     * @return long
     */
    int delete(Integer id);

    /**
     * Update the name of company with the given id and set it's name with the given name.
     * @param company to update.
     * @return long
     */
    int update(Company company);

    /**
     * Method for get pageSize companies,
     * [pageSize*page->(pageSize*(page+1))-1].
     * @param page Number of the page (start = 0).
     * @param pageSize Number of entities wanted.
     * @return pageSize companies in the page's page.
     */
    List<Company> listCompanies(Integer page, Integer pageSize);

    /**
     * Method for get all companies who have name look like the parameter, not
     * case sensitive.
     * @param name String who must be contain in wanted companies.
     * @return The list of companies who the name contains the parameter.
     */
    List<Company> listCompanies(String name);

    /**
     * Method for get pageSize companies who have name look like the parameter,
     * not case sensitive, [pageSize*page->(pageSize*(page+1))-1].
     * @param name Sting who must be contain in wanted companies
     * @param page Number of the current page.
     * @param pageSize Size of the page.
     * @return The pageSize companies who the name contains the parameter.
     */
    List<Company> listCompanies(String name, Integer page, Integer pageSize);

}
