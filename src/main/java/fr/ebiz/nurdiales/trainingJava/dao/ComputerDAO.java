package fr.ebiz.nurdiales.trainingJava.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.database.JDBCSingleton;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;

public class ComputerDAO {
    private static final String COMPUTER_TABLE = "computer";
    private static final String COMPUTER_ID = "id";
    private static final String COMPUTER_NAME = "name";
    private static final String COMPUTER_INTRODUCED = "introduced";
    private static final String COMPUTER_DISCONTINUED = "discontinued";
    private static final String COMPUTER_COMPANY = "company_id";

    private static final String SELECT = " SELECT * FROM " + COMPUTER_TABLE;
    private static final String LIKE = " LIKE ?";
    private static final String LIMIT_OFFSET = " LIMIT ? OFFSET ? ";

    private static final String ALL = SELECT + LIMIT_OFFSET;

    private static final String BY_COMPANY_ID = SELECT + " WHERE " + COMPUTER_COMPANY + "=? " + LIMIT_OFFSET;
    private static final String BY_COMPANY_NAME_AND_NAME = SELECT + " WHERE (?) AND + " + COMPUTER_NAME + LIKE
            + LIMIT_OFFSET;
    private static final String BY_COMPANY_ID_AND_NAME = SELECT + " WHERE + " + COMPUTER_COMPANY + "=? AND "
            + COMPUTER_NAME + LIKE + LIMIT_OFFSET;
    private static final String BY_NAME = SELECT + " WHERE " + COMPUTER_NAME + LIKE + LIMIT_OFFSET;
    private static final String BY_ID = SELECT + " WHERE " + COMPUTER_ID + "=?";
    private static final String INSERT_COMPUTER = " INSERT INTO " + COMPUTER_TABLE + "(" + COMPUTER_NAME + ","
            + COMPUTER_INTRODUCED + "," + COMPUTER_DISCONTINUED + "," + COMPUTER_COMPANY + ") values (?,?,?,?)";
    private static final String UPDATE_COMPUTER = " UPDATE " + COMPUTER_TABLE + " SET " + COMPUTER_NAME + "=?, "
            + COMPUTER_INTRODUCED + "=?, " + COMPUTER_DISCONTINUED + "=?, " + COMPUTER_COMPANY + "=? WHERE "
            + COMPUTER_ID + "=? ";
    private static final String DELETE_COMPUTER = " DELETE FROM " + COMPUTER_TABLE + " WHERE id=?";
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private CompanyManager companyManager;

    /**
     * Constructor of ComputerDAO, create a companyManager.
     */
    public ComputerDAO() {
        companyManager = new CompanyManager();
    }

    /**
     * Method to find all computer, (but get just part).
     * @param page Page to get elements.
     * @param pageSize Size of a page.
     * @return List of all computers in the page to get.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public List<Computer> requestAllComputers(int page, int pageSize) throws SQLException, CompanyDAOException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        List<Computer> retour = new ArrayList<Computer>();
        PreparedStatement ps = connection.prepareStatement(ALL);
        if (ps != null) {
            ps.setInt(1, pageSize);
            ps.setInt(2, pageSize * page);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
                                               rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
                                               companyManager.get(rs.getInt(COMPUTER_COMPANY))));
            }
        }
        return retour;
    }

    /**
     * Method to find all computer, (but get just part), who have a special
     * company.
     * @param companyName String who must be contain by composent's company.
     * @param page Page to get elements.
     * @param pageSize Size of a page.
     * @return List of all computers in the page to get by company name.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public List<Computer> requestAllComputersByCompanyName(String companyName, int page, int pageSize)
            throws SQLException, CompanyDAOException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        List<Computer> retour = new ArrayList<Computer>();

        List<Company> companies = companyManager.getAll(companyName);
        System.out.println(companies);
        StringBuffer idCompanies = new StringBuffer();
        boolean notFirst = false;
        for (Company company : companies) {
            if (notFirst) {
                idCompanies.append(" OR ");
            }
            idCompanies.append(COMPUTER_COMPANY + " = " + company.getId());
            if (!notFirst) {
                notFirst = true;
            }
        }
        PreparedStatement ps = connection.prepareStatement(SELECT + " WHERE " + idCompanies + LIMIT_OFFSET);
        ps.setInt(1, pageSize);
        ps.setInt(2, pageSize * page);
        System.out.println(ps);
        ResultSet rs = ps.executeQuery();
        System.out.println(rs);
        while (rs.next()) {
            retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
                    rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
                    companyManager.get(rs.getInt(COMPUTER_COMPANY))));
        }
        System.out.println(retour);

        return retour;
    }

    /**
     * Method to find all computer, (but get just part), who have a special
     * company.
     * @param idCompany Id of the company of the computers searched.
     * @param page Page to get elements.
     * @param pageSize Size of a page.
     * @return List of all computers in the page to get by company id.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public List<Computer> requestAllComputersByCompanyID(int idCompany, int page, int pageSize)
            throws SQLException, CompanyDAOException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        List<Computer> retour = new ArrayList<Computer>();
        PreparedStatement ps = connection.prepareStatement(BY_COMPANY_ID);
        ps.setInt(1, idCompany);
        ps.setInt(2, pageSize);
        ps.setInt(3, pageSize * page);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
                    rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
                    companyManager.get(rs.getInt(COMPUTER_COMPANY))));
        }
        return retour;
    }

    /**
     * Method to find all computer, (but get just part), who have special name.
     * @param name String who must be contain by the entities searched name.
     * @param page Page to get elements.
     * @param pageSize Size of a page.
     * @return List of all computers in the page to get by computer name.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public List<Computer> requestAllComputersByName(String name, int page, int pageSize)
            throws SQLException, CompanyDAOException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        List<Computer> retour = new ArrayList<Computer>();
        if (!name.contains("%")) {
            PreparedStatement ps = connection.prepareStatement(BY_NAME);
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, pageSize * page);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
                        rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
                        companyManager.get(rs.getInt(COMPUTER_COMPANY))));
            }
        }
        return retour;
    }

    /**
     * @param idCompany Id of the company to found. And get all num.
     * @param name String who must be contain by the entities searched.
     * @param page Page to get elements.
     * @param pageSize Size of a page.
     * @return List of all computers in the page to get.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public List<Computer> requestAllComputersByCompanyIDAndName(int idCompany, String name, int page, int pageSize)
            throws SQLException, CompanyDAOException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        List<Computer> retour = new ArrayList<Computer>();
        if (!name.contains("%")) {
            PreparedStatement ps = connection.prepareStatement(BY_COMPANY_ID_AND_NAME);
            ps.setInt(1, idCompany);
            ps.setString(2, "%" + name + "%");
            ps.setInt(3, pageSize);
            ps.setInt(4, pageSize * page);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
                        rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
                        companyManager.get(rs.getInt(COMPUTER_COMPANY))));
            }
        }
        return retour;
    }

    /**
     * Methode to get all computers by name, or by, companyName, or by company
     * id in the page's page sized with pageSize.
     * @param name String who must be contain by the entities searched name.
     * @param companyName String who must be contain by composent's company.
     * @param page Page to get elements.
     * @param pageSize Size of a page.
     * @return List of all computers in the page to get.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public List<Computer> requestAllComputersByCompanyNameAndName(String companyName, String name, int page,
            int pageSize) throws SQLException, CompanyDAOException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        List<Computer> retour = new ArrayList<Computer>();
        if (!name.contains("%")) {
            List<Company> companies = companyManager.getAll(companyName);
            StringBuffer idCompanies = new StringBuffer();
            boolean notFirst = false;
            for (Company company : companies) {
                if (notFirst) {
                    idCompanies.append(" OR ");
                }
                idCompanies.append(COMPUTER_ID + " = " + company.getId());
                if (!notFirst) {
                    notFirst = true;
                }
            }
            PreparedStatement ps = connection.prepareStatement(BY_COMPANY_NAME_AND_NAME);
            ps.setString(1, idCompanies.toString());
            ps.setString(2, "%" + name + "%");
            ps.setInt(3, pageSize);
            ps.setInt(4, pageSize * page);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
                        rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
                        companyManager.get(rs.getInt(COMPUTER_COMPANY))));
            }
        }
        return retour;
    }

    /**
     * Methode to get all computers by name, or by, companyName, or by company
     * id in the page's page sized with pageSize.
     * @param companyName String who must be contain by composent's company.
     * @param companyId Id who must be equal to composent's company id.
     * @param name String who must be contain by the entities searched name.
     * @param page Page to get elements.
     * @param pageSize Size of a page.
     * @return List of all computers in the page to get.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public List<Computer> saladeTomateOignon(String companyName, int companyId, String name, int page, int pageSize)
            throws SQLException, CompanyDAOException {
        List<Computer> retour = new ArrayList<Computer>();
        if (!name.contains("%")) {
            if (companyId == 0) {
                return requestAllComputersByCompanyNameAndName(companyName, name, page, pageSize);
            }

        }
        return retour;
    }

    /**
     * Methode to add a new computer in the database.
     * @param c Computer to add in the database, id don't need because the
     *            database generate it.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws SQLException Error in SQL.
     */
    public int add(Computer c) throws SQLException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        PreparedStatement ps = connection.prepareStatement(INSERT_COMPUTER);
        ps.setString(1, c.getName());
        if (c.checkDates()) {
            ps.setDate(2, c.getIntroduced());
            ps.setDate(3, c.getDiscontinued());
        } else {
            ps.setDate(2, null);
            ps.setDate(3, null);
        }

        try {
            ps.setInt(4, c.getCompany().getId());
        } catch (Exception e) {
            ps.setString(4, null);
        }
        return ps.executeUpdate();
    }

    /**
     * Methode to delete a computer in the database by his id.
     * @param id Id of the computer to delete.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws SQLException Error in SQL.
     */
    public int delete(int id) throws SQLException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        PreparedStatement ps = connection.prepareStatement(DELETE_COMPUTER);
        ps.setInt(1, id);
        return ps.executeUpdate();
    }

    /**
     * Method to update a computer in the database.
     * @param c Computer to update in the database, the id of c must be in DB.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws SQLException Error in SQL.
     */
    public int update(Computer c) throws SQLException {
        if (c.getName() != null) {
            JDBCSingleton connection = JDBCSingleton.getInstance();
            PreparedStatement ps = connection.prepareStatement(UPDATE_COMPUTER);
            ps.setString(1, c.getName());
            if (c.checkDates()) {
                ps.setDate(2, c.getIntroduced());
                ps.setDate(3, c.getDiscontinued());
            } else {
                ps.setDate(2, null);
                ps.setDate(3, null);
            }
            try {
                ps.setInt(4, c.getCompany().getId());
            } catch (Exception e) {
                ps.setString(4, null);
            }
            ps.setInt(5, c.getId());
            return ps.executeUpdate();
        }
    }

    /**
     * Method to find a computer in the database by his id.
     * @param id of the researched computer.
     * @return the researched computer.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public Computer getComputerById(int id) throws SQLException, CompanyDAOException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        PreparedStatement ps = connection.prepareStatement(BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME), rs.getDate(COMPUTER_INTRODUCED),
                rs.getDate(COMPUTER_DISCONTINUED), companyManager.get(rs.getInt(COMPUTER_COMPANY)));

    }
}