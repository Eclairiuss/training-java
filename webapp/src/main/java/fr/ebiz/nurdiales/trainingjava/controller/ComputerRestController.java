package fr.ebiz.nurdiales.trainingjava.controller;

import fr.ebiz.nurdiales.trainingjava.core.Parameters;
import fr.ebiz.nurdiales.trainingjava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingjava.mapper.ToDTO;
import fr.ebiz.nurdiales.trainingjava.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/api/computers")
public class ComputerRestController {
    private final ComputerService computerService;

    /**
     * Contructor.
     * @param computerService .
     */
    @Autowired
    public ComputerRestController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ComputerDTO>> listAllComputers() {
        List<ComputerDTO> computerDTOs = ToDTO.computerListToDTOs(computerService.getAll(Parameters.builder()).getComputers());
        if (computerDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(computerDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable("id") String id) {
        ComputerDTO computerDTO;
        try {
            computerDTO = computerService.get(Integer.parseInt(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(computerDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/pages/{page}/size/{size}", method = RequestMethod.GET)
    public ResponseEntity<List<ComputerDTO>> listComputersByPage(@PathVariable("page") int page,
                                                                 @PathVariable("size") int size) {
        List<ComputerDTO> computerDTOS = ToDTO.computerListToDTOs(computerService.getAll(Parameters.builder().page(page).size(size)).getComputers());
        if (computerDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(computerDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> createComputer(@RequestBody ComputerDTO computerDTO, UriComponentsBuilder ucBuilder) {
        int insert = computerService.add(computerDTO);
        if (insert < 0) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ComputerDTO> updateComputer(@PathVariable("id") String id,
                                                      @RequestBody ComputerDTO computerDTO) {
        if (id.equals(computerDTO.getId())) {
            int update = computerService.update(computerDTO);
            if (update > 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteComputer(@PathVariable("id") String id) {
        int delete = computerService.delete(id);
        if (delete > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<ComputerDTO> createComputerError() {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}