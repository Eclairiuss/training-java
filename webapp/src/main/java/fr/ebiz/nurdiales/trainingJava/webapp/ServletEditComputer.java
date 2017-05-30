package fr.ebiz.nurdiales.trainingJava.webapp;

import fr.ebiz.nurdiales.trainingJava.service.CompanyService;
import fr.ebiz.nurdiales.trainingJava.service.ComputerService;
import fr.ebiz.nurdiales.trainingJava.binding.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.binding.validator.ComputerDTOValidator;
import fr.ebiz.nurdiales.trainingJava.core.Company;
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

import java.util.List;
import java.util.Map;


@Controller
public class ServletEditComputer {
    private static final String PAGE_NAME = "/edit_computer";
    private static final String DASHBOARD_REDIRECT = "redirect:./dashboard";
    private static final String CREATE_COMPUTER = "redirect:./add_computer";

    private static final String ID = "id";

    private static final String FORM = "formComputer";
    private static final String LIST = "companies";

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletEditComputer.class);

    private ComputerService computerService;
    private CompanyService companyService;
    private ComputerDTOValidator computerDTOValidator;

    /**
     * Default constructor.
     * @param computerService .
     * @param companyService .
     * @param computerDTOValidator .
     */
    public ServletEditComputer(ComputerService computerService, CompanyService companyService, ComputerDTOValidator computerDTOValidator) {
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
    protected String doGet(ModelMap model, @RequestParam Map<String, String> request) {
        ComputerDTO computer = null;
        List<Company> companies = null;
        int id = 0;

        String sId = request.get(ID);

        companies = companyService.getAll();

        model.addAttribute(LIST, companies);
        if (sId != null) {
            try {
                id = Integer.parseInt(sId);
            } catch (IllegalArgumentException e) {
                model.addAttribute("message", "Error : Id must be a integer");
                return "500";
            }
            if (id > 0) {
                computer = new ComputerDTO(computerService.get(id));
                for (Company company : companies) {
                    if (company.getId() == computer.getCompanyId()) {
                        computer.setCompanyName(company.getName());
                        break;
                    }
                }
                model.addAttribute("computer", computer);
            } else {
                return CREATE_COMPUTER;
            }
        } else {
            return CREATE_COMPUTER;
        }
        model.addAttribute(FORM, computer);
        return "." + PAGE_NAME;
    }


    @RequestMapping(value = {PAGE_NAME}, method = RequestMethod.POST)
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
        return "." + PAGE_NAME;
    }
}
