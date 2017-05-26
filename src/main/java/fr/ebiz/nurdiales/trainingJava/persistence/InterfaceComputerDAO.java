package fr.ebiz.nurdiales.trainingJava.persistence;

import fr.ebiz.nurdiales.trainingJava.exceptions.DAOComputerException;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.model.Page;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;

import java.util.List;

public interface InterfaceComputerDAO {
    /**
     * Methode to create a new listComputers in the database.
     * @param computer Computer to create in the database, id don't need because the
     *            database generate it.
     * @throws DAOComputerException TODO.
     */
    void create(Computer computer) throws DAOComputerException;

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param id Id of the listComputers to delete.
     * @throws DAOComputerException TODO.
     */
    void delete(Integer id) throws DAOComputerException;

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param ids List of Ids of computers to delete.
     * @throws DAOComputerException TODO.
     */
    void delete(Integer[] ids) throws DAOComputerException;

    /**
     * Methode to delete all computers who has i for computerId.
     * @param i TODO.
     * @throws DAOComputerException TODO.
     */
    void deleteByCompany(Integer i) throws DAOComputerException;

    /**
     * Method to update a listComputers in the database.
     * @param computer Computer to update in the database, the id of c must be in DB.
     * @throws DAOComputerException TODO.
     */
    void update(Computer computer) throws DAOComputerException;

    /**
     * Method to find a listComputers in the database by his id.
     * @param id of the researched listComputers.
     * @return the researched listComputers.
     * @throws DAOComputerException TODO.
     */
    Computer getComputer(Integer id) throws DAOComputerException;

    /**
     * Général method for get all computers corresponding to parameters.
     * @param params contains all search arguments.
     * @param list of companies.
     * @return list of corresponding Computer.
     * @throws DAOComputerException TODO.
     */
    Page listComputers(Parameters params, List<Company> list) throws DAOComputerException;
}
