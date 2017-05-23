package fr.ebiz.nurdiales.trainingJava.persistence;

import fr.ebiz.nurdiales.trainingJava.exceptions.DAOCompanyException;
import fr.ebiz.nurdiales.trainingJava.mapper.CompanyMapper;
import fr.ebiz.nurdiales.trainingJava.model.CompanyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class CompanyDAO implements InterfaceCompanyDAO {
    public static final String COMPANY_TABLE = "company";
    public static final String COMPANY_NAME = COMPANY_TABLE + "." + "name";
    public static final String COMPANY_ID = COMPANY_TABLE + "." + "id";

    private static final String SELECT = " SELECT * FROM " + COMPANY_TABLE + " ";
    private static final String BY_ID = SELECT + " WHERE " + COMPANY_ID + "=? ";
    private static final String INSERT = "INSERT INTO " + COMPANY_TABLE + "(" + COMPANY_NAME + ") values ? ";
    private static final String UPDATE = "UPDATE " + COMPANY_TABLE + " set " + COMPANY_NAME + "=? WHERE " + COMPANY_ID + "=? ";
    private static final String DELETE = "DELETE FROM " + COMPANY_TABLE + " WHERE " + COMPANY_ID + "=?";

    private static final String LIKE = " LIKE ? ";
    private static final String LIMIT_OFFSET = " LIMIT ? OFFSET ? ";

    private static final String SELECT_WITH_LIMIT_OFFSET = SELECT + LIMIT_OFFSET;

    private static final String BY_NAME = SELECT + " WHERE " + COMPANY_NAME + LIKE;
    private static final String BY_NAME_WITH_LIMIT_OFFSET = BY_NAME + LIMIT_OFFSET;


    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    private List<CompanyDTO> companies = null;

    private CompanyMapper companyMapper;
    private DataSource datasource;

    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor.
     * @param datasource to connect.
     * @param companyMapper used to turn resultsSet to computer.
     */
    @Autowired
    public CompanyDAO(DataSource datasource, CompanyMapper companyMapper) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
        this.companyMapper = companyMapper;
    }

    @Override
    public void create(String name) throws DAOCompanyException {
        jdbcTemplate.update(INSERT, name);
    }

    @Override
    public CompanyDTO getCompany(Integer id) throws DAOCompanyException {
        CompanyDTO company = null;
        if (id != null && id != 0) {
            company = jdbcTemplate.queryForObject(BY_ID, new Object[]{id}, companyMapper);
        }
        return company;
    }

    @Override
    public List<CompanyDTO> listCompanies() throws DAOCompanyException {
        if (companies == null) {
            companies = jdbcTemplate.query(SELECT, companyMapper);
        }
        return companies;
    }

    @Override
    public void delete(Integer id) throws DAOCompanyException {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void update(CompanyDTO company) throws DAOCompanyException {
        jdbcTemplate.update(UPDATE, company.getName(), company.getId());
    }

    @Override
    public List<CompanyDTO> listCompanies(Integer page, Integer size) throws DAOCompanyException {
        List<CompanyDTO> list = jdbcTemplate.query(SELECT_WITH_LIMIT_OFFSET, new Object[]{page, size}, companyMapper);
        return list;
    }

    @Override
    public List<CompanyDTO> listCompanies(String name) throws DAOCompanyException {
        List<CompanyDTO> list = jdbcTemplate.query(BY_NAME, new Object[]{name}, companyMapper);
        return list;
    }

    @Override
    public List<CompanyDTO> listCompanies(String name, Integer page, Integer size) throws DAOCompanyException {
        List<CompanyDTO> list = jdbcTemplate.query(BY_NAME_WITH_LIMIT_OFFSET, new Object[]{name, page, size}, companyMapper);
        return list;
    }
}
