package fr.ebiz.nurdiales.trainingJava.webapp;

import fr.ebiz.nurdiales.trainingJava.service.CompanyService;
import fr.ebiz.nurdiales.trainingJava.service.ComputerService;
import fr.ebiz.nurdiales.trainingJava.binding.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.binding.validator.ComputerDTOValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ServletCreateComputer {
    private static final String DASHBOARD_REDIRECT = "redirect:./dashboard";
    private static final String PAGE_NAME = "/add_computer";

    private static final String LIST = "companies";
    private static final String FORM = "formComputer";

    private ComputerService computerService;
    private CompanyService companyService;
    private ComputerDTOValidator computerDTOValidator;

    /**
     * Default constructor.
     * @param computerService .
     * @param companyService .
     * @param computerDTOValidator .
     */
    public ServletCreateComputer(ComputerService computerService, CompanyService companyService, ComputerDTOValidator computerDTOValidator) {
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

    @RequestMapping(value = {PAGE_NAME}, method = RequestMethod.GET)
    protected String doGet(ModelMap model) {
        model.addAttribute(LIST, companyService.getAll());
        model.addAttribute(FORM, new ComputerDTO());
        return "." + PAGE_NAME;
    }

    @RequestMapping(value = {PAGE_NAME}, method = RequestMethod.POST)
    protected String doPost(@Validated ComputerDTO form, BindingResult result, ModelMap model) {
        String error = null;
        if (form == null) {
            return "500";
        }
        // Insert or Update if there are no id for the computer
        if (!result.hasErrors()) {
            computerService.add(form);
            return DASHBOARD_REDIRECT;
        }
        if (form.getName() == null || form.getName().trim().equals("")) {
            model.addAttribute("NameError", true);
        }
        if (!form.toComputer().checkDates()) {
            model.addAttribute("DatesError", true);
        }
        model.addAttribute(FORM, form);
        model.addAttribute(LIST, companyService.getAll());
        return "." + PAGE_NAME;
    }
}
