package fr.ebiz.nurdiales.trainingjava.persistence;

import fr.ebiz.nurdiales.trainingjava.core.Company;
import fr.ebiz.nurdiales.trainingjava.core.Computer;
import fr.ebiz.nurdiales.trainingjava.core.Page;
import fr.ebiz.nurdiales.trainingjava.core.Parameters;

import java.util.List;

public interface ComputerDAO {
    /**
     * Methode to create a new listComputers in the database.
     * @param computer Computer to create in the database, id don't need because the
     *            database generate it.
     * @return long.
     */
    int create(Computer computer);

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param id Id of the listComputers to delete.
     * @return long.
     */
    int delete(int id);

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param ids List of Ids of computers to delete.
     * @return long.
     */
    int delete(int[] ids);

    /**
     * Methode to delete all computers who has i for computerId.
     * @param id .
     * @return long.
     */
    int deleteByCompany(int id);

    /**
     * Method to update a listComputers in the database.
     * @param computer Computer to update in the database, the id of c must be in DB.
     * @return long.
     */
    int update(Computer computer);

    /**
     * Method to find a listComputers in the database by his id.
     * @param id of the researched listComputers.
     * @return the researched listComputers.
     */
    Computer getComputer(int id);

    /**
     * Général method for get all computers corresponding to parameters.
     * @param params contains all search arguments.
     * @param list of companies.
     * @return list of corresponding Computer.
     */
    Page listComputers(Parameters params, List<Company> list);
}
