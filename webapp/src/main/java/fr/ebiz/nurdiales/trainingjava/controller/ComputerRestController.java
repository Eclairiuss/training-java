package fr.ebiz.nurdiales.trainingjava.controller;

import fr.ebiz.nurdiales.trainingjava.core.Parameters;
import fr.ebiz.nurdiales.trainingjava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingjava.dto.PageDTO;
import fr.ebiz.nurdiales.trainingjava.mapper.ToDTO;
import fr.ebiz.nurdiales.trainingjava.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/computers")
public class ComputerRestController {
    @Autowired
    private ComputerService computerService;

    private static final String ORDER = "order";
    private static final String SIZE = "size";
    private static final String PAGE = "page";
    private static final String SEARCH = "search";

    @RequestMapping(method = RequestMethod.GET)
    public PageDTO getComputers(@RequestParam(value = ORDER, defaultValue = "") String reqOrder,
                                @RequestParam(value = SIZE, defaultValue = "10") int size,
                                @RequestParam(value = PAGE, defaultValue = "0") int page,
                                @RequestParam(value = SEARCH, defaultValue = "") String search) {
        Parameters params = Parameters.builder().size(size).page(page).name(search).nameCompany(search);
        params.parseSortingElement(reqOrder);
        return ToDTO.toDTO(computerService.getAll(params));
    }

    /**
     * @param id of computer to find
     * @return a computerDTO
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ComputerDTO findComputer(@PathVariable Integer id) {
        return computerService.get(id);
    }

    /**
     * @param computerDTO to add
     * @return a response entity 200 for ok!
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody @Validated ComputerDTO computerDTO) {
        computerService.add(computerDTO);
        return new ResponseEntity(computerDTO, HttpStatus.CREATED);

    }

    /**
     * @param computerDTO to update
     * @return a response entity 200 for ok!
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody @Validated ComputerDTO computerDTO) {
        computerService.update(computerDTO);
        return new ResponseEntity(computerDTO, HttpStatus.CREATED);
    }

    /**
     * @param id of computer to delete
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void delete(@PathVariable Integer id) {
        computerService.delete(id);
    }
}