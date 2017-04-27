package fr.ebiz.nurdiales.trainingJava.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.database.JDBCSingleton;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.mapper.CompanyMapper;
import fr.ebiz.nurdiales.trainingJava.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompanyDAO {
    private static final String COMPANY_TABLE = "company";
    private static final String COMPUTER_TABLE = "computer";
    private static final String COMPUTER_COMPANY = "company_id";
    private static final String COMPANY_NAME = "name";
    private static final String COMPANY_ID = "id";
    private static final String SELECT = " SELECT * FROM " + COMPANY_TABLE + " ";
    private static final String LIKE = " LIKE ? ";
    private static final String LIMIT_OFFSET = " LIMIT ? OFFSET ? ";
    private static final String CHANGE_COMPANY = " UPDATE " + COMPUTER_TABLE + " SET " + COMPUTER_COMPANY + " = ? WHERE "
                                                         + COMPUTER_COMPANY + " = ? ";;
    private static final String ALL_COMPANIES_REQUEST = SELECT;
    private static final String ALL_COMPANIES_REQUEST_BETWEEN = SELECT + LIMIT_OFFSET;
    private static final String COMPANIES_BY_NAME = SELECT + " WHERE " + COMPANY_NAME + LIKE;
    private static final String COMPANIES_BY_NAME_BETWEEN = SELECT + " WHERE " + COMPANY_NAME + LIKE + LIMIT_OFFSET;
    private static final String COMPANY_BY_ID_REQUEST = SELECT + " WHERE " + COMPANY_ID + "=? ";
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    /**
     * Method for get in data base a company corresponding to a id, if no match
     * return null.
     * @param id Id of the company to search in the database.
     * @return The company corresponding to the id.
     * @throws SQLException Error in SQL.
     */
    public Company companyById(int id) throws SQLException {
        System.out.println(id);
        Connection connection = getConnexion();
        Company c = null;
        PreparedStatement ps = connection.prepareStatement(COMPANY_BY_ID_REQUEST);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            c = new Company(rs.getInt(COMPANY_ID), rs.getString(COMPANY_NAME));
        }
        connection.commit();
        connection.close();
        return c;
    }

    /**
     * Method for get all companies in database, dangerous if many.
     * @return All companies in DB.
     * @throws SQLException Error in SQL.
     */
    public List<Company> allCompanies() throws SQLException {
        List<Company> list = new ArrayList<Company>();
        Connection connection = getConnexion();
        list = CompanyMapper.map2Object(connection.prepareStatement(ALL_COMPANIES_REQUEST).executeQuery());
        connection.commit();
        connection.close();
        return list;
    }

    /**
     * Method for get pageSize companies,
     * [pageSize*page->(pageSize*(page+1))-1].
     * @param page Number of the page (start = 0).
     * @param pageSize Number of entities wanted.
     * @return pageSize companies in the page's page.
     * @throws SQLException Error in SQL.
     */
    public List<Company> allCompanies(int page, int pageSize) throws SQLException {
        Connection connection = getConnexion();
        List<Company> list = new ArrayList<Company>();

        PreparedStatement ps = connection.prepareStatement(ALL_COMPANIES_REQUEST_BETWEEN);
        ps.setInt(1, pageSize);
        ps.setInt(2, pageSize * page);
        list = CompanyMapper.map2Object(ps.executeQuery());
        connection.commit();
        connection.close();
        return list;
    }

    /**
     * Method for get all companies who have name look like the parameter, not
     * case sensitive.
     * @param name String who must be contain in wanted companies.
     * @return The list of companies who the name contains the parameter.
     * @throws SQLException Error in SQL.
     */
    public List<Company> allCompaniesByName(String name) throws SQLException {
        Connection connection = getConnexion();
        List<Company> list = new ArrayList<Company>();
        if (!name.contains("%") && !name.contains("'")) {
            PreparedStatement ps = connection.prepareStatement(COMPANIES_BY_NAME);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            list = CompanyMapper.map2Object(rs);
        }
        connection.commit();
        connection.close();
        return list;
    }

    /**
     * Method for get pageSize companies who have name look like the parameter,
     * not case sensitive, [pageSize*page->(pageSize*(page+1))-1].
     * @param name Sting who must be contain in wanted companies
     * @param page Number of the current page.
     * @param pageSize Size of the page.
     * @return The pageSize companies who the name contains the parameter.
     * @throws SQLException Error in SQL.
     */
    public List<Company> allCompaniesByName(String name, int page, int pageSize) throws SQLException {
        Connection connection = getConnexion();
        List<Company> list = new ArrayList<Company>();
        if (!name.contains("%")) {
            PreparedStatement ps = connection.prepareStatement(COMPANIES_BY_NAME_BETWEEN);
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, pageSize * page);
            ResultSet rs = ps.executeQuery();
            list = CompanyMapper.map2Object(rs);
        }
        connection.commit();
        connection.close();
        return list;
    }

    /**
     * Method who create a PreparedStatement with a String who connais instructions.
     * @return preparedStatement with q sql request.
     * @throws SQLException Exception of sql request.
     */
    public Connection getConnexion() throws SQLException {
        JDBCSingleton connection = JDBCSingleton.getInstance();
        return connection.getDataSource().getConnection();
    }

    /**
     * Methode to delete a company in the database by his id.
     * @param i Id of company to delete.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws ComputerDAOException Error in the CompanyDAO SQL.
     */
    public int delete(int i) throws ComputerDAOException {
        int retour = 0;
        Connection connection = null;
        try {
            connection = getConnexion();
            connection.setSavepoint();
            PreparedStatement ps = connection.prepareStatement(CHANGE_COMPANY);
            ps.setString(1, null);
            ps.setInt(2, i);
            retour = ps.executeUpdate();
            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM " + COMPANY_TABLE + " WHERE id=?");
            ps2.setInt(1, i);
            retour = ps2.executeUpdate();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                throw new ComputerDAOException(e.getMessage());
            }
        }
        return retour;
    }
}
