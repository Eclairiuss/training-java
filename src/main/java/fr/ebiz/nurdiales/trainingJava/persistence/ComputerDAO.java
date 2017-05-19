package fr.ebiz.nurdiales.trainingJava.persistence;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.mapper.ComputerMapper;
import fr.ebiz.nurdiales.trainingJava.model.CompanyDTO;
import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.model.Page;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static fr.ebiz.nurdiales.trainingJava.model.Parameters.ElementTri.ID;

@Component
public class ComputerDAO implements InterfaceComputerDAO {
    public static final String COMPUTER_TABLE = "computer";
    public static final String COMPUTER_ID = COMPUTER_TABLE + "." + "id";
    public static final String COMPUTER_NAME = COMPUTER_TABLE + "." + "name";
    public static final String COMPUTER_INTRODUCED = COMPUTER_TABLE + "." + "introduced";
    public static final String COMPUTER_DISCONTINUED = COMPUTER_TABLE + "." + "discontinued";
    public static final String COMPUTER_COMPANY = COMPUTER_TABLE + "." + "company_id";
//    public static final String NAME_COMPANY = "company_name";


    private static final String SELECT = "SELECT "
//            + "(" + COMPUTER_ID
//            + ", " + COMPUTER_NAME
//            + ", " + COMPUTER_INTRODUCED
//            + ", " + COMPUTER_DISCONTINUED
//            + ", " + COMPUTER_COMPANY
//            + ", " + CompanyDAO.COMPANY_NAME
//            + ")"
//            + " as "
//            + NAME_COMPANY
            + "* FROM " + COMPUTER_TABLE
//            + " LEFT JOIN " + CompanyDAO.COMPANY_TABLE
//            + " ON " + CompanyDAO.COMPANY_ID
//            + "=" + COMPUTER_COMPANY
            + "";

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

    private static final String SORT_BY = " ORDER BY ";
    private static final String INVERT = " DESC ";

    @Autowired
    private ComputerMapper computerMapper;
    @Autowired
    private DataSource datasource;
    private JdbcTemplate jdbcTemplate;

    /**
     * Constuctor.
     * @param datasource form Spring.
     */
    public ComputerDAO(DataSource datasource) {
        setDatasource(datasource);
    }

    @Autowired
    @Override
    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public void create(ComputerDTO c) throws ComputerDAOException {
        jdbcTemplate.update(INSERT_COMPUTER, new Object[] {c.getName(), c.getIntroduced(), c.getDiscontinued(), c.getCompanyId()});
    }

    @Override
    public void delete(Integer id) throws ComputerDAOException {
        jdbcTemplate.update(DELETE_COMPUTER, id);
    }

    @Override
    public void delete(Integer[] ids) throws ComputerDAOException {
        for (Integer i : ids) {
            delete(i);
        }
    }


    @Override
    public void deleteByCompany(Integer i) throws ComputerDAOException {
        Connection connection = null;
        StringBuffer idsSB = new StringBuffer();
        int retour = 0;
        boolean isntFirst = false;
        idsSB.append("(" + COMPUTER_COMPANY + "=" + i + ")");
        try {
            connection = getConnexion();
            connection.setAutoCommit(false);
            System.out.println(idsSB.toString());
            retour = connection.prepareStatement(DELETE_COMPUTERS + idsSB.toString()).executeUpdate();
        } catch (SQLException e) {
            throw new ComputerDAOException(e.getMessage());
        }
    }


    @Override
    public void update(ComputerDTO c) throws ComputerDAOException {
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
                ps.setInt(check ? 4 : 2, c.getCompanyId());
                ps.setInt(check ? 5 : 3, c.getId());

                retour = ps.executeUpdate();
            } catch (SQLException e) {
                throw new ComputerDAOException(e.getMessage());
            }
        }
    }

    @Override
    public ComputerDTO getComputer(Integer id) throws ComputerDAOException {
        ComputerDTO c = jdbcTemplate.queryForObject(BY_ID, new Object[] {id}, new ComputerMapper());
        return c;
    }

    @Override
    public Page listComputers(Parameters params, List<CompanyDTO> list) throws ComputerDAOException {
        Page retour = new Page();
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

        retour.setComputers(jdbcTemplate.query(SELECT
                + ((searchName || searchCompany) ? " WHERE " : "")
                + ((searchCompany) ? companies.toString() : "")
                + ((searchName && searchCompany) ? " AND " : "")
                + ((searchName) ? nameLike : "")
                + sorted(params)
                + " LIMIT " + params.getSize() + " OFFSET " + params.getSize() * params.getPage(), new ComputerMapper()));

        retour.setQuantity(jdbcTemplate.queryForObject(COUNT
                + ((searchName || searchCompany) ? " WHERE " : "")
                + ((searchCompany) ? companies.toString() : "")
                + ((searchName && searchCompany) ? " AND " : "")
                + ((searchName) ? nameLike : ""), Integer.class));

        return retour;
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
    private StringBuilder testCompany(Parameters params, List<CompanyDTO> list) throws ComputerDAOException {
        StringBuilder companies = new StringBuilder();
        boolean retour = false;
        if (params.getIdCompany() != 0) {
            retour = true;
            for (CompanyDTO c : list) {
                if (c.getId() == params.getIdCompany()) {
                    companies.append(COMPUTER_COMPANY + "=" + c.getId());
                }
            }
        } else if (params.getNameCompany() != null) {
            boolean isntFirst = false;
            for (CompanyDTO c : list) {
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
    public ComputerDTO preparedStatementToObject(PreparedStatement ps) throws SQLException, CompanyDAOException {
        return computerMapper.mapRow(ps.executeQuery(), 0);
    }
}