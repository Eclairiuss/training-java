package fr.ebiz.nurdiales.trainingJava.service;

import java.sql.SQLException;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public class ComputerManager {
    // private static Logger logger =
    // LoggerFactory.getLogger(ComputerManager.class);
    private ComputerDAO computerDAO;

    /**
     * Defaut constructor for the manager.
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
    public List<Computer> requestAllComputers(int page, int pageSize) throws ComputerDAOException {
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
    public List<Computer> requestAllComputersByCompanyName(String name, int page, int pageSize)
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
     * @param idCompany ID of the company computer.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> requestAllComputersByCompanyID(int idCompany, int page, int pageSize)
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
     * @param name Name of the computer in the DB.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> requestAllComputersByName(String name, int page, int pageSize) throws ComputerDAOException {
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
     * @param idCompany
     * @param name Name of the computer in the DB.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> requestAllComputersByCompanyIDAndName(int idCompany, String name, int page, int pageSize)
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
     * @param companyName
     * @param name Name of the computer in the DB.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> requestAllComputersByCompanyNameAndName(String companyName, String name, int page,
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
     * Search in the database all computers who verify those conditions in the
     * DB.
     * @param companyName Name of the company who make the computer in the DB.
     * @param companyId Id of the company who make the computer in the DB.
     * @param name Name of the computer in the DB.
     * @param page Current page (start at 0).
     * @param pageSize Size a the page.
     * @return The list of all computers who verify parameters.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public List<Computer> saladeTomateOignon(String companyName, int companyId, String name, int page, int pageSize)
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
     * Add a computer in the database from the computer pass in parameter.
     * @param c Computer to add in DB.
     * @return {@link ComputerDAO#add(Computer)}.
     * @throws ComputerDAOException Error in the ComputerDAO.
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
     * Delete a computer in the database from an id.
     * @param id ID of the computer to delete in DB.
     * @return {@link ComputerDAO#delete}.
     * @throws ComputerDAOException Error in the ComputerDAO.
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
     * update the database with updated computer.
     * @param c Computer with update values to put in the DB.
     * @return {@link ComputerDAO#update}.
     * @throws ComputerDAOException Error in the ComputerDAO.
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
     * Get a computer in database with the specific id give in parameter.
     * @param id ID of the researched Computer in DB.
     * @return the good computer if it exists else null.
     * @throws ComputerDAOException Error in the ComputerDAO.
     */
    public Computer getComputerById(int id) throws ComputerDAOException {
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
