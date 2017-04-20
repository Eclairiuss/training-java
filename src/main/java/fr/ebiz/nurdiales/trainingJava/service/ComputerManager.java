package fr.ebiz.nurdiales.trainingJava.service;

import java.sql.SQLException;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.dao.ComputerDAO;
import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;

public class ComputerManager {
    // private static Logger logger =
    // LoggerFactory.getLogger(ComputerManager.class);
    private ComputerDAO computerDAO;

    /**
     * Constructor of ComputerManager, create the computerDAO.
     */
    public ComputerManager() {
        this.computerDAO = new ComputerDAO();
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
    public int add(Computer c) throws ComputerDAOException {
        int result = 0;
        try {
            result = computerDAO.add(c);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return result;
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
    public int delete(int id) throws ComputerDAOException {
        int result = 0;
        try {
            result = computerDAO.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return result;
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
    public int update(Computer c) throws ComputerDAOException {
        int result = 0;
        try {
            result = computerDAO.update(c);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return result;
    }

    /**
     * Method to find a computer in the database by his id.
     * @param id of the researched computer.
     * @return the researched computer.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    public Computer get(int id) throws ComputerDAOException {
        Computer computer = null;
        try {
            computer = computerDAO.getById(id);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return computer;
    }

    /**
     * Method to get the number of computers in the database.
     * @param params contains all search arguments.
     * @return int corresponding to number of computers in the DB.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    public int getCount(Parameters params) throws ComputerDAOException {
        try {
            return computerDAO.getCount(params);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
    }

    /**
     * Général method for get all computers corresponding to parameters.
     * @param params contains all search arguments.
     * @return list of corresponding Computer.
     * @throws SQLException Error in SQL.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    public List<Computer> getAll(Parameters params) throws SQLException, ComputerDAOException {
        try {
            return computerDAO.getAll(params);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
    }
}
