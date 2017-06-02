package fr.ebiz.nurdiales.trainingjava.controller;

import fr.ebiz.nurdiales.trainingjava.mapper.ToDTO;
import fr.ebiz.nurdiales.trainingjava.service.CompanyService;
import fr.ebiz.nurdiales.trainingjava.service.ComputerService;
import fr.ebiz.nurdiales.trainingjava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingjava.validator.ComputerDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class EditComputerController {
    private static final String PAGE_NAME = "edit_computer";
    private static final String URL = "/" + PAGE_NAME;
    private static final String DASHBOARD_REDIRECT = "redirect:./dashboard";
    private static final String CREATE_COMPUTER = "redirect:./add_computer";

    private static final String ID = "id";

    private static final String FORM = "formComputer";
    private static final String LIST = "companies";

    private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerController.class);

    private ComputerService computerService;
    private CompanyService companyService;
    private ComputerDTOValidator computerDTOValidator;

    /**
     * Default constructor.
     * @param computerService .
     * @param companyService .
     * @param computerDTOValidator .
     */
    public EditComputerController(ComputerService computerService, CompanyService companyService, ComputerDTOValidator computerDTOValidator) {
        this.computerService = computerService;
        this.companyService = companyService;
        this.computerDTOValidator = computerDTOValidator;
    }

    /**
     * Inject validator for a computer.
     * @param binder holder of validator.
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(computerDTOValidator);
    }



    @RequestMapping(value = {URL}, method = RequestMethod.GET)
    protected String doGet(@RequestParam(value = ID) int id, ModelMap model) {
        model.addAttribute(LIST, companyService.getAll());
        if (id < 1) {
            return CREATE_COMPUTER;
        }
        model.addAttribute(FORM, ToDTO.toDTO(computerService.get(id)));
        return PAGE_NAME;
    }


    @RequestMapping(value = {URL}, method = RequestMethod.POST)
    protected String doPost(@ModelAttribute(FORM) @Validated ComputerDTO form, BindingResult result, ModelMap model) {
        if (!result.hasErrors()) {
            if (form.getId() != null && form.getId() > 0) {
                computerService.update(form.toComputer());
            } else {
                computerService.add(form);
            }
            return DASHBOARD_REDIRECT;
        }
        model.addAttribute(FORM, form);
        model.addAttribute(LIST, companyService.getAll());
        return PAGE_NAME;
    }
}
