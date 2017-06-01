package fr.ebiz.nurdiales.trainingjava.controller;

import fr.ebiz.nurdiales.trainingjava.core.Page;
import fr.ebiz.nurdiales.trainingjava.core.Parameters;
import fr.ebiz.nurdiales.trainingjava.service.ComputerService;
import fr.ebiz.nurdiales.trainingjava.controller.util.Parse;
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
    private static final String PAGE_NAME = "/dashboard";
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

    @RequestMapping(value = {"", "/", PAGE_NAME}, method = RequestMethod.GET)
    protected String doGet(ModelMap model, @RequestParam Map<String, String> request) {
        String sSize = request.get(SIZE);
        String sPage = request.get(PAGE);
        String sSearch = request.get(SEARCH);
        String sTri = request.get(ORDER);

        Parameters params = (new Parameters.Builder())
                                    .page(Parse.stringToInt(sPage) - 1)
                                    .size(Parse.stringToInt(sSize))
                                    .name(sSearch)
                                    .nameCompany(sSearch)
                                    .build();
        params.parseTri(sTri);

        Page page;

        page = computerService.getAll(params);

        model.addAttribute("numberComputers", page.getQuantity());
        model.addAttribute("computers", page.getComputers());
        model.addAttribute(PAGE, params.getPage() + 1);
        model.addAttribute(SIZE, params.getSize());
        model.addAttribute(SEARCH, sSearch);
        model.addAttribute(ORDER, sTri);
        return "." + PAGE_NAME;
    }


    @RequestMapping(value = {"", "/", PAGE_NAME}, method = RequestMethod.POST)
    protected String doPost(ModelMap model, @RequestParam Map<String, String> request) {
//        String[] idsString = request.get(DELETESELECTED).split(",");
//        int[] ids = new int[idsString.length];
//        for (int i = 0; i < idsString.length; ++i) {
//            ids[i] = Integer.parseInt(idsString[i]);
//        }
//        computerService.delete(ids);
        return doGet(model, request);
    }
}
