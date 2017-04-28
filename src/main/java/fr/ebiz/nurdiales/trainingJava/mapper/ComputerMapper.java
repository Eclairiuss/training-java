package fr.ebiz.nurdiales.trainingJava.mapper;

import fr.ebiz.nurdiales.trainingJava.dao.ComputerDAO;
import fr.ebiz.nurdiales.trainingJava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ebiz on 20/04/17.
 */
public class ComputerMapper {
    /**
     * List of all computer in the rs.
     * @param rs ResultSet form a search.
     * @return Computers from rs.
     * @throws SQLException Error in SQL.
     */
    public static List<Computer> map2Object(ResultSet rs) throws SQLException {
        List<Computer> retour = new ArrayList<Computer>();
        while (rs.next()) {
            retour.add(makeComputer(rs));
        }
        return retour;
    }

    /**
     * Map a list of ComputerDTO from a list of Company.
     * @param computers list of computer.
     * @return list of ComputerDTO corresponding to computers.
     */
    public static List<ComputerDTO> map2DTO(List<Computer> computers) {
        List<ComputerDTO> list = computers.stream().map(c -> new ComputerDTO(c)).collect(Collectors.toList());
        return list;
    }


    /**
     * Get the first object from the ResultSet.
     * @param rs ResultSet form a search.
     * @return Computer corresponding to the first object from rs.
     * @throws SQLException Error in SQL.
     */
    public static Computer toObject(ResultSet rs) throws SQLException {
        Computer c = null;
        if (rs.next()) {
            c = makeComputer(rs);
        }
        return c;
    }

    /**
     * Get the first object from the ResultSet.
     * @param rs ResultSet form a search already set on the next input.
     * @return Computer corresponding to the first object from rs.
     * @throws SQLException Error in SQL.
     */
    public static Computer makeComputer(ResultSet rs) throws SQLException {
        ComputerDTO c = new ComputerDTO();

        c.setId(rs.getString(ComputerDAO.COMPUTER_ID));
        c.setName(rs.getString(ComputerDAO.COMPUTER_NAME));
        c.setIntroduced(rs.getString(ComputerDAO.COMPUTER_INTRODUCED));
        c.setDiscontinued(rs.getString(ComputerDAO.COMPUTER_DISCONTINUED));
        c.setCompanyId(rs.getString(ComputerDAO.COMPUTER_COMPANY));
        c.setCompanyName(rs.getString(ComputerDAO.NAME_COMPANY));

        return c.toComputer();
    }
}
