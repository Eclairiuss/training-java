package fr.ebiz.nurdiales.trainingJava.mapper;

import fr.ebiz.nurdiales.trainingJava.model.CompanyDTO;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ebiz on 20/04/17.
 */
@Component
public class CompanyMapper implements RowMapper<CompanyDTO> {
    @Override
    public CompanyDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        CompanyDTO company = new CompanyDTO();
        company.setId(resultSet.getInt(CompanyDAO.COMPANY_ID));
        company.setName(resultSet.getString(CompanyDAO.COMPANY_NAME));
        return company;
    }
}
