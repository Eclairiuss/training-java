package fr.ebiz.nurdiales.trainingjava.dto;

import java.util.List;

/**
 * Created by eclairiuss on 08/06/17.
 */
public class PageDTO {
    private List<ComputerDTO> computersDTO;
    private Long nbrComputers;

    /**
     * Contructor.
     * @param computerDTOS .
     * @param nbrComputers .
     */
    public PageDTO(List<ComputerDTO> computerDTOS, Long nbrComputers) {
        this.computersDTO = computerDTOS;
        this.nbrComputers = nbrComputers;
    }

    public List<ComputerDTO> getComputersDTO() {
        return computersDTO;
    }

    public Long getNbrComputers() {
        return nbrComputers;
    }
}
