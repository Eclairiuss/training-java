package fr.ebiz.nurdiales.trainingJava.mapper;

import fr.ebiz.nurdiales.trainingJava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebiz on 20/04/17.
 */
public class ComputerMapper {
    /**
     * Map a list of CompanyDTO from a list of Company.
     * @param computers list of company.
     * @return list of CompanyDTO corresponding to computers.
     */
    public static List<ComputerDTO> map2DTO(List<Computer> computers) {
        List<ComputerDTO> list = new ArrayList<ComputerDTO>();
        computers.forEach(c -> {
            list.add(new ComputerDTO(c));
        });
        return list;
    }
}
