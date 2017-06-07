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
     * @return .
     */
    int add(ComputerDTO c);

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param id Id of the listComputers to delete.
     * @return .
     */
    int delete(int id);

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param ids List of Ids of computers to delete.
     * @return .
     */
    int delete(int[] ids);

    /**
     * Method to update a listComputers in the database.
     * @param c Computer to update in the database, the id of c must be in DB.
     * @return .
     */
    int update(Computer c);

    /**
     * Method to find a listComputers in the database by his id.
     * @param id of the researched listComputers.
     * @return ComputerDTO corresponding to the id.
     */
    ComputerDTO get(int id);

    /**
     * Général method for get all computers corresponding to parameters.
     * @param params contains all search arguments.
     * @return list of corresponding Computer.
     */
    Page getAll(Parameters params);

    /**
     * Alternative methode for update .
     * @param computerDTO .
     * @return .
     */
    int update(ComputerDTO computerDTO);

    /**
     * Alternative methode for delete.
     * @param id .
     * @return .
     */
    int delete(String id);
}
