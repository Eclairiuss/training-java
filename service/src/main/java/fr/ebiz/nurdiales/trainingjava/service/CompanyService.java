package fr.ebiz.nurdiales.trainingjava.service;

import fr.ebiz.nurdiales.trainingjava.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {
        /**
         * Method for get in data base a company corresponding to a id, if no match
         * return null.
         * @param id Id of the company to search in the database.
         * @return The company corresponding to the id.
         */
        CompanyDTO get(int id);

        /**
         * Method for get in data base a company corresponding to a id, if no match
         * return null.
         * @param sId Id of the company to search in the database.
         * @return The company corresponding to the id.
         */
        CompanyDTO get(String sId);

        /**
         * Method for get all companies in database, dangerous if many.
         * @return All companies in DB.
         */
        List<CompanyDTO> getAll();

        /**
         * Method for get pageSize companies,
         * [pageSize*page->(pageSize*(page+1))-1].
         * @param page Number of the page (start = 0).
         * @param pageSize Number of entities wanted.
         * @return pageSize companies in the page's page.
         */
        List<CompanyDTO> getAll(int page, int pageSize);

        /**
         * Method for get all companies who have name look like the parameter, not
         * case sensitive.
         * @param name String who must be contain in wanted companies.
         * @return The list of companies who the name contains the parameter.
         */
        List<CompanyDTO> getAll(String name);

        /**
         * Method for get pageSize companies who have name look like the parameter,
         * not case sensitive, [pageSize*page->(pageSize*(page+1))-1].
         * @param name Sting who must be contain in wanted companies
         * @param page Number of the current page.
         * @param pageSize Size of the page.
         * @return The pageSize companies who the name contains the parameter.
         */
        List<CompanyDTO> getAll(String name, int page, int pageSize);

        /**
         * Methode to delete a company in the database by his id.
         * @param i Id of company to delete.
         * @return .
         */
        int delete(int i);

        /**
         * Alternative methode for delete.
         * @param id .
         * @return .
         */
        int delete(String id);
}
