package fr.ebiz.nurdiales.trainingjava.controller;

import fr.ebiz.nurdiales.trainingjava.dto.CompanyDTO;
import fr.ebiz.nurdiales.trainingjava.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/api/companies")
public class CompanyRestController {
    @Autowired
    CompanyService companyService;

    /**
     *
     * @return list of companies
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<CompanyDTO> getCompanies() {
        return companyService.getAll();
    }

    /**
     * @param id of companie to find.
     * @return a companyDTO.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public CompanyDTO findCompany(@PathVariable Integer id) {
        return companyService.get(id);
    }
}
