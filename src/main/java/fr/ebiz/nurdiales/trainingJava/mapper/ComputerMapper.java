package fr.ebiz.nurdiales.trainingJava.mapper;

import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ComputerMapper implements RowMapper<Computer> {
    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Computer c = new Computer();
        c.setId(rs.getInt(ComputerDAO.COMPUTER_ID));
        c.setName(rs.getString(ComputerDAO.COMPUTER_NAME));
        c.setIntroduced(rs.getString(ComputerDAO.COMPUTER_INTRODUCED));
        c.setDiscontinued(rs.getString(ComputerDAO.COMPUTER_DISCONTINUED));
        c.setCompany(new Company(rs.getInt(ComputerDAO.COMPUTER_COMPANY), ""));
        return c;
    }
}
