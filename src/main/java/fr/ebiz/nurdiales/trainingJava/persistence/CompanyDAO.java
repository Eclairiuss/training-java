package fr.ebiz.nurdiales.trainingJava.persistence;

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

    @Autowired
    private DataSource datasource;

    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor.
     * @param datasource to connect.
     */
    public CompanyDAO(DataSource datasource) {
        setDatasource(datasource);
    }

    @Autowired
    @Override
    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public void create(String name) {
        jdbcTemplate.update(INSERT, name);
    }

    @Override
    public CompanyDTO getCompany(Integer id) {
        CompanyDTO company = jdbcTemplate.queryForObject(BY_ID, new Object[]{id}, new CompanyMapper());
        return company;
    }

    @Override
    public List<CompanyDTO> listCompanies() {
        if (companies == null) {
            companies = jdbcTemplate.query(SELECT, new CompanyMapper());
        }
        return companies;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void update(CompanyDTO company) {
        jdbcTemplate.update(UPDATE, company.getName(), company.getId());
    }

    @Override
    public List<CompanyDTO> listCompanies(Integer page, Integer size) {
        List<CompanyDTO> list = jdbcTemplate.query(SELECT_WITH_LIMIT_OFFSET, new Object[]{page, size}, new CompanyMapper());
        return list;
    }

    @Override
    public List<CompanyDTO> listCompanies(String name) {
        List<CompanyDTO> list = jdbcTemplate.query(BY_NAME, new Object[]{name}, new CompanyMapper());
        return list;
    }

    @Override
    public List<CompanyDTO> listCompanies(String name, Integer page, Integer size) {
        List<CompanyDTO> list = jdbcTemplate.query(BY_NAME_WITH_LIMIT_OFFSET, new Object[]{name, page, size}, new CompanyMapper());
        return list;
    }
}
