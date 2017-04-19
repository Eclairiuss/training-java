package fr.ebiz.nurdiales.trainingJava.service;

import java.sql.SQLException;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.dao.ComputerDAO;
import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

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
     * Get all computer in the DB from the "page"'s page who contains "pageSize"
     * computers.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> getAll(int page, int pageSize) throws ComputerDAOException {
        List<Computer> computers = null;
        try {
            computers = computerDAO.requestAllComputers(page, pageSize);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return computers;
    }

    /**
     * Get all computer in the DB from the "page"'s page who contains "pageSize"
     * computers and where name of computer equals "name".
     * @param name Name of the company computer in the DB.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> getAll(String name, int page, int pageSize)
            throws ComputerDAOException {
        List<Computer> computers = null;
        try {
            computers = computerDAO.requestAllComputersByCompanyName(name, page, pageSize);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return computers;
    }

    /**
     * Method to find all computer, (but get just part), who have a special
     * company.
     * @param idCompany Id of the company of the computers searched.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> getAll(int idCompany, int page, int pageSize)
            throws ComputerDAOException {
        List<Computer> computers = null;
        try {
            computers = computerDAO.requestAllComputersByCompanyID(idCompany, page, pageSize);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return computers;
    }

    /**
     * Method to find all computer, (but get just part), who have special name.
     * @param name String who must be contain by the entities searched.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> getAllByName(String name, int page, int pageSize) throws ComputerDAOException {
        List<Computer> computers = null;
        try {
            computers = computerDAO.requestAllComputersByName(name, page, pageSize);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return computers;
    }

    /**
     * @param idCompany Id of the company to found. And get all num.
     * @param name String who must be contain by the entities searched.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> getAll(int idCompany, String name, int page, int pageSize)
            throws ComputerDAOException {
        List<Computer> computers = null;
        try {
            computers = computerDAO.requestAllComputersByCompanyIDAndName(idCompany, name, page, pageSize);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return computers;
    }

    /**
     * Method to get all computers by name, and company name, in the page.
     * @param companyName String who must be contain by composent's company.
     * @param name String who must be contain by the entities searched.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> getAll(String companyName, String name, int page,
                                 int pageSize) throws ComputerDAOException {
        List<Computer> computers = null;
        try {
            computers = computerDAO.requestAllComputersByCompanyNameAndName(companyName, name, page, pageSize);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return computers;
    }

    /**
     * Method to get all computers by name, or by, companyName, or by company in
     * the page. id in the page's page sized with pageSize.
     * @param companyName String who must be contain by composent's company.
     * @param companyId Id who must be equal to composent's company id.
     * @param name String who must be contain by the entities searched.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> getAll(String companyName, int companyId, String name, int page, int pageSize)
            throws ComputerDAOException {
        List<Computer> computers = null;
        try {
            computers = computerDAO.saladeTomateOignon(companyName, companyId, name, page, pageSize);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return computers;
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
            computer = computerDAO.getComputerById(id);
        } catch (SQLException | CompanyDAOException e) {
            e.printStackTrace();
            throw new ComputerDAOException();
        }
        return computer;
    }
}
