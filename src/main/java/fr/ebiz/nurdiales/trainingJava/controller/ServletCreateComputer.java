package fr.ebiz.nurdiales.trainingJava.controller;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.CompanyDTO;
import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;
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
public class ServletCreateComputer {
    private static final String DASHBOARD_REDIRECTION = "redirect:./dashboard";
    private static final String PAGE_NAME = "/add_computer";
    private static final String NAME = "name";
    private static final String LANGUAGE = "language";
    private static final String INTRODUCED = "introduced";
    private static final String DISCONTINUED = "discontinued";
    private static final String COMPANY_ID = "companyId";

    @Autowired
    private ComputerManager computerManager;
    @Autowired
    private CompanyManager companyManager;

    @RequestMapping(value = {PAGE_NAME}, method = RequestMethod.POST)
    protected ModelAndView doPost(ComputerDTO computer, String language) throws ServletException, IOException {
        ModelAndView mav = new ModelAndView(DASHBOARD_REDIRECTION);
        mav.addObject(LANGUAGE, language);
        try {
            computerManager.add(computer);
        } catch (ComputerDAOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return mav;
    }

    @RequestMapping(value = {PAGE_NAME}, method = RequestMethod.GET)
    protected ModelAndView doGet(@RequestParam Map<String, String> request) throws ServletException, IOException {
        ModelAndView mav = new ModelAndView();
        try {
            List<CompanyDTO> companies = companyManager.getAll();
            mav.addObject("companies", companies);
        } catch (CompanyDAOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        mav.addObject(LANGUAGE, request.get(LANGUAGE));
        mav.setViewName(PAGE_NAME);
        return mav;
    }

    public void setComputerManager(ComputerManager computerManager) {
        this.computerManager = computerManager;
    }

    public ComputerManager getComputerManager() {
        return computerManager;
    }

    public void setCompanyManager(CompanyManager companyManager) {
        this.companyManager = companyManager;
    }

    public CompanyManager getCompanyManager() {
        return companyManager;
    }
}
