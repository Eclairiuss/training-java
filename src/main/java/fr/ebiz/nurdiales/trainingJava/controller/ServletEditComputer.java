package fr.ebiz.nurdiales.trainingJava.controller;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.CompanyDTO;
import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
public class ServletEditComputer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletEditComputer.class);
    @Autowired
    private ComputerManager computerManager;
    @Autowired
    private CompanyManager companyManager;

    private static final String PAGE_NAME = "/edit_computer";
    private static final String DASHBOARD_REDIRECTION = "redirect:./dashboard";
    private static final String CREATE_COMPUTER_REDIRECTION = "./add_computer";

    private static final String LANGUAGE = "language";
    private static final String ID = "id";
    private static final String NAME = "computerName";
    private static final String INTRODUCED = "introduced";
    private static final String DISCONTINUED = "discontinued";
    private static final String COMPANY_ID = "companyId";

    @RequestMapping(value = {PAGE_NAME}, method = RequestMethod.POST)
    protected ModelAndView doPost(@RequestParam Map<String, String> request, String language) throws ServletException, IOException {
        ModelAndView mav = new ModelAndView(DASHBOARD_REDIRECTION);
        mav.addObject(LANGUAGE, language);
        ComputerDTO computer = new ComputerDTO();
        String sId = request.get(ID);
        CompanyDTO company = null;
        try {
            company = companyManager.get(request.get(COMPANY_ID));
            computer.setName(request.get(NAME));
            computer.setIntroduced(request.get(INTRODUCED));
            computer.setDiscontinued(request.get(DISCONTINUED));
            if (company != null) {
                computer.setCompanyId(company.getId());
                computer.setCompanyName(company.getName());
            }
            if (sId != null) {
                computer.setId(Integer.parseInt(sId));
                computerManager.update(computer);
            } else {
                computerManager.add(computer);
            }
        } catch (CompanyDAOException | ComputerDAOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return mav;
    }

    @RequestMapping(value = {PAGE_NAME}, method = RequestMethod.GET)
    protected ModelAndView doGet(@RequestParam Map<String, String> request) throws ServletException, IOException {
        ModelAndView mav = new ModelAndView();
        int id = 0;
        String sId = request.get("id");
        List<CompanyDTO> companies = null;
        try {
            companies = companyManager.getAll();
        } catch (CompanyDAOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        mav.addObject("companies", companies);
        if (sId != null) {
            try {
                id = Integer.parseInt(sId);
                ComputerDTO computer = computerManager.get(id);
                for (CompanyDTO company : companies) {
                    if (company.getId().equals(computer.getCompanyId())) {
                        computer.setCompanyName(company.getName());
                        break;
                    }
                }
                mav.addObject("computer", computer);
                mav.setViewName(PAGE_NAME);
            } catch (NumberFormatException | ComputerDAOException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        } else {
            mav.setViewName(CREATE_COMPUTER_REDIRECTION);
        }
        mav.addObject(LANGUAGE, request.get(LANGUAGE));
        return mav;
    }
}
