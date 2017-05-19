package fr.ebiz.nurdiales.trainingJava.persistence;

import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.mapper.ComputerMapper;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static fr.ebiz.nurdiales.trainingJava.model.Parameters.ElementTri.ID;

@Component
public class ComputerDAO {
    public static final String COMPUTER_TABLE = "computer";
    public static final String COMPUTER_ID = COMPUTER_TABLE + "." + "id";
    public static final String COMPUTER_NAME = COMPUTER_TABLE + "." + "name";
    public static final String COMPUTER_INTRODUCED = COMPUTER_TABLE + "." + "introduced";
    public static final String COMPUTER_DISCONTINUED = COMPUTER_TABLE + "." + "discontinued";
    public static final String COMPUTER_COMPANY = COMPUTER_TABLE + "." + "company_id";
    public static final String NAME_COMPANY = "company_name";

    private static final String SORT_BY = " ORDER BY ";
    private static final String INVERT = " DESC ";

    private static final String SELECT = "SELECT " + COMPUTER_ID + ", "
                                                 + COMPUTER_NAME + ", "
                                                 + COMPUTER_INTRODUCED + ", "
                                                 + COMPUTER_DISCONTINUED + ", "
                                                 + COMPUTER_COMPANY + ", "
                                                 + CompanyDAO.COMPANY_NAME
                                                 + " as "
                                                 + NAME_COMPANY
                                                 + " FROM " + COMPUTER_TABLE
                                                 + " LEFT JOIN " + CompanyDAO.COMPANY_TABLE
                                                 + " ON " + CompanyDAO.COMPANY_ID
                                                 + "=" + COMPUTER_COMPANY;

    private static final String COUNT = " SELECT COUNT(*) FROM " + COMPUTER_TABLE;

    private static final String BY_ID = SELECT + " WHERE " + COMPUTER_ID + "=?";
    private static final String INSERT_COMPUTER = " INSERT INTO " + COMPUTER_TABLE + "(" + COMPUTER_NAME + ","
                                                          + COMPUTER_INTRODUCED + "," + COMPUTER_DISCONTINUED + "," + COMPUTER_COMPANY + ") values (?,?,?,?)";
    private static final String UPDATE_COMPUTER_NOT_DATES = " UPDATE " + COMPUTER_TABLE + " SET " + COMPUTER_NAME + "=?, "
                                                                    + COMPUTER_COMPANY + "=? WHERE "
                                                                    + COMPUTER_ID + "=? ";
    private static final String UPDATE_COMPUTER_FULL = " UPDATE " + COMPUTER_TABLE + " SET " + COMPUTER_NAME + "=?, "
                                                               + COMPUTER_INTRODUCED + "=?, " + COMPUTER_DISCONTINUED + "=?, " + COMPUTER_COMPANY + "=? WHERE "
                                                               + COMPUTER_ID + "=? ";
    private static final String DELETE_COMPUTER = " DELETE FROM " + COMPUTER_TABLE + " WHERE id=?";
    private static final String DELETE_COMPUTERS = " DELETE FROM " + COMPUTER_TABLE + " WHERE ";

    @Autowired
    private ComputerMapper computerMapper;
    @Autowired
    private DataSource datasource;

    /**
     * Methode to add a new computer in the database.
     * @param c Computer to add in the database, id don't need because the
     *            database generate it.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    public int add(ComputerDTO c) throws ComputerDAOException {
        Connection connection = null;
        int retour = 0;
        if (c.getName() == null) {
            c.setName("default");
        }
        if (c.toComputer().checkDates()) {
            c.setIntroduced(null);
            c.setDiscontinued(null);
        }
        PreparedStatement ps = null;
        try {
            connection = getConnexion();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(INSERT_COMPUTER);
            ps.setString(1, c.getName());
            ps.setString(2, c.getIntroduced());
            ps.setString(3, c.getDiscontinued());
            ps.setString(4, c.getCompanyId());
            retour = ps.executeUpdate();
        } catch (SQLException e) {
            throw new ComputerDAOException(e.getMessage());
        }
        return retour;
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
        Connection connection = null;
        int retour = 0;
        try {
            connection = getConnexion();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(DELETE_COMPUTER);
            ps.setInt(1, id);
            retour = ps.executeUpdate();
        } catch (SQLException e) {
            throw new ComputerDAOException(e.getMessage());
        }
        return retour;
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
    public int delete(int[] ids) throws ComputerDAOException {
        Connection connection = null;
        StringBuffer idsSB = new StringBuffer();
        int retour = 0;
        boolean isntFirst = false;
        for (int i : ids) {
            if (isntFirst) {
                idsSB.append(" OR ");
            } else {
                isntFirst = true;
            }
            idsSB.append("(" + COMPUTER_ID + "=" + i + ")");
        }
        try {
            connection = getConnexion();
            connection.setAutoCommit(false);
            System.out.println(idsSB.toString());
            retour = connection.prepareStatement(DELETE_COMPUTERS + idsSB.toString()).executeUpdate();
        } catch (SQLException e) {
            throw new ComputerDAOException(e.getMessage());
        }
        return retour;
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
    public int update(ComputerDTO c) throws ComputerDAOException {
        Connection connection = null;
        boolean check = c.toComputer().checkDates();
        int retour = 0;
        if (c.getName() != null) {
            try {
                connection = getConnexion();
                connection.setAutoCommit(false);

                PreparedStatement ps = connection.prepareStatement(check ? UPDATE_COMPUTER_FULL : UPDATE_COMPUTER_NOT_DATES);
                ps.setString(1, c.getName());
                ps.setString(2, c.getIntroduced());
                ps.setString(3, c.getDiscontinued());
                ps.setString(check ? 4 : 2, c.getCompanyId());
                ps.setString(check ? 5 : 3, c.getId());

                retour = ps.executeUpdate();
            } catch (SQLException e) {
                throw new ComputerDAOException(e.getMessage());
            }
        }
        return retour;
    }

    /**
     * Method to get the number of computers in the database.
     * @param params list of parameters for the search.
     * @param list of companies.
     * @return int corresponding to number of computers in the DB.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    public int getCount(Parameters params, List<Company> list) throws ComputerDAOException {
        StringBuilder companies = testCompany(params, list);
        String nameLike = testNameLike(params);
        Connection connection = null;

        boolean searchCompany = companies != null;
        boolean searchName = nameLike != null;

        if (params.getPage() < 0) {
            params.setPage(0);
        }

        if (params.getSize() < 1) {
            params.setSize(1);
        }

        try {
            connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(COUNT
                                                                       + ((searchName || searchCompany) ? " WHERE " : "")
                                                                       + ((searchCompany) ? companies.toString() : "")
                                                                       + ((searchName && searchCompany) ? " AND " : "")
                                                                       + ((searchName) ? nameLike : ""));
            ResultSet rs = ps.executeQuery();
            rs.next();
            int retour = rs.getInt("count(*)");
            return retour;
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                throw new ComputerDAOException(e.getMessage());
            }
        }
    }

    /**
     * Method to find a computer in the database by his id.
     * @param id of the researched computer.
     * @return the researched computer.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    public Computer getById(int id) throws ComputerDAOException {
        Connection connection = null;
        try {
            connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(BY_ID);
            ps.setInt(1, id);
            Computer c = computerMapper.toObject(ps.executeQuery());
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ComputerDAOException(e.getMessage());
        }
    }

    /**
     * Général method for get all computers corresponding to parameters.
     * @param params contains all search arguments.
     * @param list of companies.
     * @return list of corresponding Computer.
     * @throws ComputerDAOException Error in CompanyDAO SQL.
     */
    public List<Computer> getAll(Parameters params, List<Company> list) throws ComputerDAOException {
        List<Computer> retour = new ArrayList<Computer>();
        StringBuilder companies = testCompany(params, list);
        String nameLike = testNameLike(params);

        boolean searchCompany = companies != null;
        boolean searchName = nameLike != null;

        if (params.getPage() < 0) {
            params.setPage(0);
        }

        if (params.getSize() < 1) {
            params.setSize(1);
        }
        Connection connection = null;
        try {
            connection = getConnexion();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ComputerDAOException(e.getMessage());
        }
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SELECT
                                                     + ((searchName || searchCompany) ? " WHERE " : "")
                                                     + ((searchCompany) ? companies.toString() : "")
                                                     + ((searchName && searchCompany) ? " AND " : "")
                                                     + ((searchName) ? nameLike : "")
                                                     + sorted(params)
                                                     + " LIMIT " + params.getSize() + " OFFSET " + params.getSize() * params.getPage());
            retour = computerMapper.map2Object(ps.executeQuery());
            return retour;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ComputerDAOException(e.getMessage());
        }
    }

    /**
     * Look if params need special sort.
     * @param params all params of the request.
     * @return the end of the request with special sort if needed.
     */
    private String sorted(Parameters params) {
        StringBuffer retour = new StringBuffer();
        if (params == null || params.getTrierPar() == null) {
            return retour.toString();
        }
        if (params.isReversed() || params.getTrierPar() != ID) {
            retour.append(SORT_BY);
            switch (params.getTrierPar()) {
                case ID :
                    retour.append(COMPUTER_ID);
                    break;
                case NAME:
                    retour.append(COMPUTER_NAME);
                    break;
                case INTRODUCED:
                    retour.append(COMPUTER_INTRODUCED);
                    break;
                case DISCONTINUED:
                    retour.append(COMPUTER_DISCONTINUED);
                    break;
                case COMPANY:
                    retour.append(COMPUTER_COMPANY);
                    break;
                default:
                    retour.append(COMPUTER_ID);
                    break;
            }
        }
        if (params.isReversed()) {
            retour.append(INVERT);
        }
        return retour.toString();
    }

    /**
     * test if a company is wanted and return the string for search.
     * @param params parameters who must contain a string for nameCompany or a idCompany != 0, else return null.
     * @param list of companies.
     * @return null if no company searched.
     * @throws ComputerDAOException Error in CompanyDAO SQL.
     */
    private StringBuilder testCompany(Parameters params, List<Company> list) throws ComputerDAOException {
        StringBuilder companies = new StringBuilder();
        boolean retour = false;
        if (params.getIdCompany() != 0) {
            retour = true;
            for (Company c : list) {
                if (c.getId() == params.getIdCompany()) {
                    companies.append(COMPUTER_COMPANY + "=" + c.getId());
                }
            }
        } else if (params.getNameCompany() != null) {
            boolean isntFirst = false;
            for (Company c : list) {
                if (c.getName().contains(params.getNameCompany())) {
                    if (isntFirst) {
                        companies.append(" OR ");
                    } else {
                        companies.append("( ");
                        isntFirst = true;
                    }
                    companies.append(COMPUTER_COMPANY + "=" + c.getId());
                }
            }
            if (!list.isEmpty()) {
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
                nameLike = " " + COMPUTER_NAME + " LIKE '%" + params.getName() + "%' ";
            }
        }
        return nameLike;
    }

    /**
     * Method who create a PreparedStatement with a String who connais instructions.
     * @return preparedStatement with q sql request.
     * @throws SQLException Exception of sql request.
     */
    public Connection getConnexion() throws SQLException {
        return DataSourceUtils.getConnection(datasource);
    }

    /**
     * Method who get the first object from PreparedStatement.
     * @param ps PreparedStatement.
     * @return Computer.
     * @throws SQLException Exception of sql request.
     * @throws CompanyDAOException Error in CompanyDAO SQL.
     */
    public Computer preparedStatementToObject(PreparedStatement ps) throws SQLException, CompanyDAOException {
        return computerMapper.toObject(ps.executeQuery());
    }
}