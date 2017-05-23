package fr.ebiz.nurdiales.trainingJava.service;

import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.model.Page;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;

/**
 * Created by eclairiuss on 24/05/17.
 */
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
    void delete(Integer[] ids);

    /**
     * Method to update a listComputers in the database.
     * @param c Computer to update in the database, the id of c must be in DB.
     */
    void update(ComputerDTO c);

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
}
