package fr.ebiz.nurdiales.trainingJava.persistence;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.mapper.CompanyMapper;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Component
public class CompanyDAO {
    public static final String COMPANY_TABLE = "company";
    public static final String COMPANY_NAME = COMPANY_TABLE + "." + "name";
    public static final String COMPANY_ID = COMPANY_TABLE + "." + "id";

    private static final String SELECT = " SELECT * FROM " + COMPANY_TABLE + " ";
    private static final String LIKE = " LIKE ? ";
    private static final String LIMIT_OFFSET = " LIMIT ? OFFSET ? ";
    private static final String CHANGE_COMPANY = " UPDATE " + ComputerDAO.COMPUTER_TABLE + " SET " + ComputerDAO.COMPUTER_COMPANY + " = ? WHERE "
                                                         + ComputerDAO.COMPUTER_COMPANY + " = ? ";;
    private static final String ALL_COMPANIES_REQUEST = SELECT;
    private static final String ALL_COMPANIES_REQUEST_BETWEEN = SELECT + LIMIT_OFFSET;
    private static final String COMPANIES_BY_NAME = SELECT + " WHERE " + COMPANY_NAME + LIKE;
    private static final String COMPANIES_BY_NAME_BETWEEN = SELECT + " WHERE " + COMPANY_NAME + LIKE + LIMIT_OFFSET;
    private static final String COMPANY_BY_ID_REQUEST = SELECT + " WHERE " + COMPANY_ID + "=? ";
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private DataSource datasource;

    List<Company> list = null;

    /**
     * Method for get in data base a company corresponding to a id, if no match
     * return null.
     * @param id Id of the company to search in the database.
     * @return The company corresponding to the id.
     * @throws CompanyDAOException Error in SQL.
     */
    public Company companyById(int id) throws CompanyDAOException {
        Company c = null;
        Connection connection = null;
        try {
            connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(COMPANY_BY_ID_REQUEST);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                c = new Company(rs.getInt(COMPANY_ID), rs.getString(COMPANY_NAME));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CompanyDAOException(e.getMessage());
        }
        return c;
    }

    /**
     * Method for get all companies in database, dangerous if many.
     * @return All companies in DB.
     * @throws CompanyDAOException Error in SQL.
     */
    public List<Company> allCompanies() throws CompanyDAOException {
        Connection connection = null;
        try {
            connection = getConnexion();
            if (list == null) {
                list = companyMapper.map2Object(connection.prepareStatement(ALL_COMPANIES_REQUEST).executeQuery());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CompanyDAOException(e.getMessage());
        }
        return list;
    }

    /**
     * Method for get pageSize companies,
     * [pageSize*page->(pageSize*(page+1))-1].
     * @param page Number of the page (start = 0).
     * @param pageSize Number of entities wanted.
     * @return pageSize companies in the page's page.
     * @throws CompanyDAOException Error in SQL.
     */
    public List<Company> allCompanies(int page, int pageSize) throws CompanyDAOException {
        Connection connection = null;
        List<Company> list = new ArrayList<Company>();
        try {
            connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(ALL_COMPANIES_REQUEST_BETWEEN);
            ps.setInt(1, pageSize);
            ps.setInt(2, pageSize * page);
            list = companyMapper.map2Object(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CompanyDAOException(e.getMessage());
        }
        return list;
    }

    /**
     * Method for get all companies who have name look like the parameter, not
     * case sensitive.
     * @param name String who must be contain in wanted companies.
     * @return The list of companies who the name contains the parameter.
     * @throws CompanyDAOException Error in SQL.
     */
    public List<Company> allCompaniesByName(String name) throws CompanyDAOException {
        Connection connection = null;
        List<Company> list = new ArrayList<Company>();
        if (!name.contains("%") && !name.contains("'")) {
            try {
                connection = getConnexion();
                PreparedStatement ps = connection.prepareStatement(COMPANIES_BY_NAME);
                ps.setString(1, "%" + name + "%");
                ResultSet rs = ps.executeQuery();
                list = companyMapper.map2Object(rs);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new CompanyDAOException(e.getMessage());
            }
        }
        return list;
    }

    /**
     * Method for get pageSize companies who have name look like the parameter,
     * not case sensitive, [pageSize*page->(pageSize*(page+1))-1].
     * @param name Sting who must be contain in wanted companies
     * @param page Number of the current page.
     * @param pageSize Size of the page.
     * @return The pageSize companies who the name contains the parameter.
     * @throws CompanyDAOException Error in SQL.
     */
    public List<Company> allCompaniesByName(String name, int page, int pageSize) throws CompanyDAOException {
        Connection connection = null;
        try {
            connection = getConnexion();
            List<Company> list = new ArrayList<Company>();
            if (!name.contains("%")) {
                PreparedStatement ps = connection.prepareStatement(COMPANIES_BY_NAME_BETWEEN);
                ps.setString(1, "%" + name + "%");
                ps.setInt(2, pageSize);
                ps.setInt(3, pageSize * page);
                ResultSet rs = ps.executeQuery();
                list = companyMapper.map2Object(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CompanyDAOException(e.getMessage());
        }
        return list;
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
     * Methode to delete a company in the database by his id.
     * @param i Id of company to delete.
     * @return Executes the SQL statement in this PreparedStatement object,
     *         which must be an SQL Data Manipulation Language (DML) statement,
     *         such as INSERT, UPDATE or DELETE; or an SQL statement that
     *         returns nothing, such as a DDL statement.
     * @throws CompanyDAOException Error in SQL.
     */
    public int delete(int i) throws CompanyDAOException {
        int retour = 0;
        Connection connection = null;
        try {
            connection = getConnexion();
            PreparedStatement ps = connection.prepareStatement(CHANGE_COMPANY);
            ps.setString(1, null);
            ps.setInt(2, i);
            retour = ps.executeUpdate();
            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM " + COMPANY_TABLE + " WHERE id=?");
            ps2.setInt(1, i);
            retour = ps2.executeUpdate();
            list = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CompanyDAOException(e.getMessage());
        }
        return retour;
    }
}
