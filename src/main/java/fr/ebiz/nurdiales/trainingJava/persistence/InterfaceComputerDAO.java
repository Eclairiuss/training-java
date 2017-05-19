package fr.ebiz.nurdiales.trainingJava.persistence;

import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.CompanyDTO;
import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.model.Page;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;

import javax.sql.DataSource;
import java.util.List;

public interface InterfaceComputerDAO {
    /**
     * TODO.
     * @param datasource TODO.
     */
    void setDatasource(DataSource datasource);

    /**
     * Methode to create a new listComputers in the database.
     * @param c Computer to create in the database, id don't need because the
     *            database generate it.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    void create(ComputerDTO c) throws ComputerDAOException;

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param id Id of the listComputers to delete.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    void delete(Integer id) throws ComputerDAOException;

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param ids List of Ids of computers to delete.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    void delete(Integer[] ids) throws ComputerDAOException;

    /**
     * TODO.
     * @param i TODO.
     * @throws ComputerDAOException TODO.
     */
    void deleteByCompany(Integer i) throws ComputerDAOException;

    /**
     * Method to update a listComputers in the database.
     * @param c Computer to update in the database, the id of c must be in DB.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    void update(ComputerDTO c) throws ComputerDAOException;

    /**
     * Method to find a listComputers in the database by his id.
     * @param id of the researched listComputers.
     * @return the researched listComputers.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    ComputerDTO getComputer(Integer id) throws ComputerDAOException;

    /**
     * Général method for get all computers corresponding to parameters.
     * @param params contains all search arguments.
     * @param list of companies.
     * @return list of corresponding Computer.
     * @throws ComputerDAOException Error in CompanyDAO SQL.
     */
    Page listComputers(Parameters params, List<CompanyDTO> list) throws ComputerDAOException;
}
