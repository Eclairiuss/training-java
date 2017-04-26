package fr.ebiz.nurdiales.trainingJava.mapper;

import fr.ebiz.nurdiales.trainingJava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.Computer;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ebiz on 20/04/17.
 */
public class ComputerMapper {
    private static final String COMPUTER_ID = "id";
    private static final String COMPUTER_NAME = "name";
    private static final String COMPUTER_INTRODUCED = "introduced";
    private static final String COMPUTER_DISCONTINUED = "discontinued";
    private static final String COMPUTER_COMPANY = "company_id";
    private static final CompanyManager COMPANY_MANAGER = new CompanyManager();

    /**
     * List of all computer in the rs.
     * @param rs ResultSet form a search.
     * @return Computers from rs.
     * @throws SQLException Error in SQL.
     * @throws CompanyDAOException Error in the companyDAO.
     */
    public static List<Computer> map2Object(ResultSet rs) throws SQLException, CompanyDAOException {
        List<Computer> retour = new ArrayList<Computer>();
        while (rs.next()) {
            retour.add(new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
                                           rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
                                           COMPANY_MANAGER.get(rs.getInt(COMPUTER_COMPANY))));
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
     * @throws CompanyDAOException Error in the companyDAO.
     */
    public static Computer toObject(ResultSet rs) throws SQLException, CompanyDAOException {
        Computer c = null;
        if (rs.next()) {
            c = new Computer(rs.getInt(COMPUTER_ID), rs.getString(COMPUTER_NAME),
                                    rs.getDate(COMPUTER_INTRODUCED), rs.getDate(COMPUTER_DISCONTINUED),
                                    COMPANY_MANAGER.get(rs.getInt(COMPUTER_COMPANY)));
        }
        return c;
    }
}
