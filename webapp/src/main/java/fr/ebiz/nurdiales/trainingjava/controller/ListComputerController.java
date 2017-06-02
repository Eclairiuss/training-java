package fr.ebiz.nurdiales.trainingjava.controller;

import fr.ebiz.nurdiales.trainingjava.core.Page;
import fr.ebiz.nurdiales.trainingjava.core.Parameters;
import fr.ebiz.nurdiales.trainingjava.service.ComputerService;
import fr.ebiz.nurdiales.trainingjava.core.util.Parse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ListComputerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListComputerController.class);
    private static final String PAGE_NAME = "dashboard";
    private static final String URL = "/" + PAGE_NAME;
    private static final String SEARCH = "search";
    private static final String ORDER = "order";
    private static final String SIZE = "size";
    private static final String PAGE = "page";
    private static final String DELETESELECTED = "selection";


    ComputerService computerService;

    /**
     * Default constructor.
     * @param computerService .
     */
    @Autowired
    public ListComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @RequestMapping(value = {"", "/", URL}, method = RequestMethod.GET)
    protected String doGet(ModelMap model, @RequestParam Map<String, String> request) {
        Page page;
        String sTri = request.get(ORDER);
        //Set params for search
        Parameters params = Parameters.builder()
                .page(Parse.stringToInt(request.get(PAGE)) - 1) //page start to 1 in the view.
                .size(Parse.stringToInt(request.get(SIZE)))
                .name(request.get(SEARCH));
        params = params.nameCompany(params.getName());
        params.parseSortingElement(sTri);

        page = computerService.getAll(params);

        model.addAttribute("numberComputers", page.getQuantity());
        model.addAttribute("computers", page.getComputers());
        model.addAttribute(PAGE, params.getPage() + 1);
        model.addAttribute(SIZE, params.getSize());
        model.addAttribute(SEARCH, params.getName());
        model.addAttribute(ORDER, sTri);
        return PAGE_NAME;
    }
}
