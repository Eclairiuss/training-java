package fr.ebiz.nurdiales.trainingJava.mapper;

import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ComputerMapper implements RowMapper<ComputerDTO> {
    @Override
    public ComputerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ComputerDTO c = new ComputerDTO();
        c.setId(rs.getInt(ComputerDAO.COMPUTER_ID));
        c.setName(rs.getString(ComputerDAO.COMPUTER_NAME));
        c.setIntroduced(rs.getString(ComputerDAO.COMPUTER_INTRODUCED));
        if (c.getIntroduced() != null) {
            c.setIntroduced(c.getIntroduced().split(" ")[0]);
        }
        c.setDiscontinued(rs.getString(ComputerDAO.COMPUTER_DISCONTINUED));
        if (c.getDiscontinued() != null) {
            c.setDiscontinued(c.getDiscontinued().split(" ")[0]);
        }
        c.setCompanyId(rs.getInt(ComputerDAO.COMPUTER_COMPANY));
        return c;
    }
}
