package fr.ebiz.nurdiales.trainingJava.service;

import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.DAOException;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComputerManager {
    // private static Logger logger =
    // LoggerFactory.getLogger(ComputerManager.class);
    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private CompanyDAO companyDAO;

    /**
     * Constructor of ComputerManager, create the computerDAO.
     */
    public ComputerManager() {

    }

    /**
     * Method to add a new computer in the database.
     * @param c Computer to add in the database, id don't need because the
     *            database generate it.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public int add(Computer c) throws ComputerDAOException {
        return computerDAO.add(new ComputerDTO(c));
    }

    /**
     * Methode to delete a computer in the database by his id.
     * @param id Id of the computer to delete.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public int delete(int id) throws ComputerDAOException {
        return computerDAO.delete(id);
    }

    /**
     * Methode to delete a computer in the database by his id.
     * @param ids List of Ids of computers to delete.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public int delete(int[] ids) throws ComputerDAOException {
        return computerDAO.delete(ids);
    }

    /**
     * Method to update a computer in the database.
     * @param c Computer to update in the database, the id of c must be in DB.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public int update(Computer c) throws ComputerDAOException {
        return computerDAO.update(new ComputerDTO(c));
    }

    /**
     * Method to find a computer in the database by his id.
     * @param id of the researched computer.
     * @return the researched computer.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public Computer get(int id) throws ComputerDAOException {
        Computer retour = computerDAO.getById(id);
        return retour;
    }

    /**
     * Method to get the number of computers in the database.
     * @param params contains all search arguments.
     * @return int corresponding to number of computers in the DB.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     * @throws CompanyDAOException Error in the CompanyDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public int getCount(Parameters params) throws ComputerDAOException, CompanyDAOException {
        List<Company> list = companyDAO.allCompanies();
        int retour = computerDAO.getCount(params, list);
        return retour;
    }

    /**
     * Général method for get all computers corresponding to parameters.
     * @param params contains all search arguments.
     * @return list of corresponding Computer.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     * @throws CompanyDAOException Error in the CompanyDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public List<Computer> getAll(Parameters params) throws ComputerDAOException, CompanyDAOException {
        List<Company> list = companyDAO.allCompanies();
        List<Computer> retour = null;
        retour = computerDAO.getAll(params, list);
        return retour;
    }
}
