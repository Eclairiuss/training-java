package fr.ebiz.nurdiales.trainingJava.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.database.JDBCSingleton;
import fr.ebiz.nurdiales.trainingJava.mapper.ComputerMapper;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
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
    private static final String COUNT = " SELECT COUNT(*) FROM " + COMPUTER_TABLE;
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
    private static final String DELETE_COMPUTERS = " DELETE FROM " + COMPUTER_TABLE + " WHERE ";
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private CompanyManager companyManager;

    /**
     * Constructor of ComputerDAO, create a companyManager.
     */
    public ComputerDAO() {
        companyManager = new CompanyManager();
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
        if (c.getName() != null) {
            c.setName("default");
        }
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
     * Methode to delete a computer in the database by his id.
     * @param ids List of Ids of computers to delete.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws SQLException Error in SQL.
     */
    public int delete(int[] ids) throws SQLException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        StringBuffer idsSB = new StringBuffer();
        boolean isntFirst = false;
        for (int i : ids) {
            if (isntFirst) {
                idsSB.append(" OR ");
            } else {
                isntFirst = true;
            }
            idsSB.append("(" + COMPUTER_ID + "=" + i + ")");
        }
        PreparedStatement ps = connection.prepareStatement(DELETE_COMPUTERS + idsSB.toString());
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
        return 0;
    }

    /**
     * Method to get the number of computers in the database.
     * @param params list of parameters for the search.
     * @return int corresponding to number of computers in the DB.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public int getCount(Parameters params) throws SQLException, CompanyDAOException {
        int retour = 0;
        JDBCSingleton connection = JDBCSingleton.getInstance();
        StringBuffer companies = testCompany(params);
        String nameLike = testNameLike(params);

        boolean searchCompany = companies != null;
        boolean searchName = nameLike != null;

        if (params.getPage() < 0) {
            params.setPage(0);
        }

        if (params.getSize() < 1) {
            params.setSize(1);
        }

        PreparedStatement ps = connection.prepareStatement(COUNT
                                                                   + ((searchName || searchCompany) ? " WHERE " : "")
                                                                   + ((searchCompany) ? companies.toString() : "")
                                                                   + ((searchName && searchCompany) ? " AND " : "")
                                                                   + ((searchName) ? nameLike : ""));
        ResultSet rs = ps.executeQuery();
        rs.next();
        retour = rs.getInt("count(*)");
        return retour;
    }

    /**
     * Method to find a computer in the database by his id.
     * @param id of the researched computer.
     * @return the researched computer.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public Computer getById(int id) throws SQLException, CompanyDAOException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        PreparedStatement ps = connection.prepareStatement(BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return ComputerMapper.toObject(rs);
    }

    /**
     * Général method for get all computers corresponding to parameters.
     * @param params contains all search arguments.
     * @return list of corresponding Computer.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public List<Computer> getAll(Parameters params) throws SQLException, CompanyDAOException {
        List<Computer> retour = new ArrayList<Computer>();
        JDBCSingleton connection = JDBCSingleton.getInstance();
        StringBuffer companies = testCompany(params);
        String nameLike = testNameLike(params);

        boolean searchCompany = companies != null;
        boolean searchName = nameLike != null;

        if (params.getPage() < 0) {
            params.setPage(0);
        }

        if (params.getSize() < 1) {
            params.setSize(1);
        }

        PreparedStatement ps = connection.prepareStatement(SELECT
                                                                   + ((searchName || searchCompany) ? " WHERE " : "")
                                                                   + ((searchCompany) ? companies.toString() : "")
                                                                   + ((searchName && searchCompany) ? " AND " : "")
                                                                   + ((searchName) ? nameLike : "")
                                                                   + " LIMIT " + params.getSize() + " OFFSET " + params.getSize() * params.getPage());
        ResultSet rs = ps.executeQuery();
        return ComputerMapper.map2Object(rs);
    }

    /**
     * test if a company is wanted and return the string for search.
     * @param params parameters who must contain a string for nameCompany or a idCompany != 0, else return null.
     * @return null if no company searched.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    private StringBuffer testCompany(Parameters params) throws CompanyDAOException {
        StringBuffer companies = new StringBuffer();
        boolean retour = false;
        if (params.getIdCompany() != 0) {
            retour = true;
            companies.append(COMPUTER_COMPANY + "=" + companyManager.get(params.getIdCompany()).getId());
        } else if (params.getNameCompany() != null) {
            boolean isntFirst = false;
            List<Company> tmp = companyManager.getAll(params.getNameCompany());
            for (Company c : tmp) {
                if (isntFirst) {
                    companies.append(" OR ");
                } else {
                    companies.append("( ");
                    isntFirst = true;
                }
                companies.append(COMPUTER_COMPANY + "=" + c.getId());
            }
            if (!tmp.isEmpty()) {
                retour = true;
                companies.append(" )");
            }
        }
        return retour ? companies : null;
    }

    /**
     * test if name isn't null and prepare the String.
     * @param params parameters who must contain a string for name else the method return null.
     * @return String corresponding to name wanted.
     */
    private String testNameLike(Parameters params) {
        String nameLike = null;
        if (params.getName() != null) {
            if (!params.getName().contains("%") && !params.getName().contains("'")) {
                nameLike = " name LIKE '%" + params.getName() + "%' ";
            }
        }
        return nameLike;
    }
}