package fr.ebiz.nurdiales.trainingJava.controller;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Page;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;
import fr.ebiz.nurdiales.trainingJava.util.Trad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

@Controller
public class ServletListComputer {
    @Autowired
    ComputerManager computerManager;

    private static final String PAGE_NAME = "/dashboard";
    private static final String ACTION = "ACTION";
    private static final String DELETE = "delete";
    private static final String SEARCH = "search";
    private static final String ORDER = "order";
    private static final String NAME = "name";
    private static final String SIZE = "size";
    private static final String PAGE = "page";
    private static final String DELETESELECTED = "selection";

    public ComputerManager getComputerManager() {
        return computerManager;
    }

    public void setComputerManager(ComputerManager computerManager) {
        this.computerManager = computerManager;
    }

    @RequestMapping(value = {"", "/", PAGE_NAME}, method = RequestMethod.GET)
    protected ModelAndView doGet(@RequestParam Map<String, String> request) throws ServletException, IOException {
        ModelAndView mav = new ModelAndView();
        String sSize = request.get(SIZE);
        String sPage = request.get(PAGE);
        String sSearch = request.get(SEARCH);
        String sTri = request.get(ORDER);

        Parameters params = (new Parameters.Builder())
                                    .page(Trad.stringToInt(sPage) - 1)
                                    .size(Trad.stringToInt(sSize))
                                    .name(sSearch)
                                    .build();
        params.parseTri(sTri);

        Page page;

        try {
            page = computerManager.getAll(params);
        } catch (ComputerDAOException | CompanyDAOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

        mav.addObject("numberComputers", page.getQuantity());
        mav.addObject("computers", page.getComputers());
        mav.addObject(PAGE, params.getPage() + 1);
        mav.addObject(SIZE, params.getSize());
        mav.addObject(SEARCH, sSearch);
        mav.addObject(ORDER, sTri);
        mav.setViewName(PAGE_NAME);
        return mav;
    }


    @RequestMapping(value = {PAGE_NAME}, method = RequestMethod.POST)
    protected ModelAndView doPost(@RequestParam Map<String, String> request) throws ServletException, IOException {
        String difference = request.get(ACTION);
        if (difference.equals(DELETE)) {
            String[] idsString = request.get(DELETESELECTED).split(",");
            Integer[] ids = new Integer[idsString.length];
            try {
                for (int i = 0; i < idsString.length; ++i) {
                    ids[i] = Integer.parseInt(idsString[i]);
                }
                computerManager.delete(ids);
            } catch (ComputerDAOException | IllegalArgumentException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        }
        return doGet(request);
    }
}
