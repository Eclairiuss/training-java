package fr.ebiz.nurdiales.trainingjava.service;

import fr.ebiz.nurdiales.trainingjava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingjava.core.Computer;
import fr.ebiz.nurdiales.trainingjava.core.Page;
import fr.ebiz.nurdiales.trainingjava.core.Parameters;

public interface ComputerService {
    /**
     * Method to create a new listComputers in the database.
     * @param c Computer to create in the database, id don't need because the
     *            database generate it.
     */
    void add(ComputerDTO c);

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param id Id of the listComputers to delete.
     */
    void delete(int id);

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param ids List of Ids of computers to delete.
     */
    void delete(int[] ids);

    /**
     * Method to update a listComputers in the database.
     * @param c Computer to update in the database, the id of c must be in DB.
     */
    void update(Computer c);

    /**
     * Method to find a listComputers in the database by his id.
     * @param id of the researched listComputers.
     * @return ComputerDTO corresponding to the id.
     */
    Computer get(int id);

    /**
     * Général method for get all computers corresponding to parameters.
     * @param params contains all search arguments.
     * @return list of corresponding Computer.
     */
    Page getAll(Parameters params);
}
