package fr.ebiz.nurdiales.trainingjava.controller;

import fr.ebiz.nurdiales.trainingjava.core.Page;
import fr.ebiz.nurdiales.trainingjava.core.Parameters;
import fr.ebiz.nurdiales.trainingjava.service.ComputerService;
import fr.ebiz.nurdiales.trainingjava.controller.util.Parse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class DeleteComputerController {

    private static final String PAGE_NAME = "/delete_computer";
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
    public DeleteComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }

    /**
     * Delete computers selected.
     * @param model .
     * @param request .
     * @param selection .
     * @return Redirection.
     */
    @PostMapping(PAGE_NAME)
    protected String doPost(ModelMap model, @RequestParam Map<String, String> request, @RequestParam(value = "selection") String selection) {

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

        if (selection != null) {
            String[] idsString = selection.split(",");
            int[] ids = new int[idsString.length];
            for (int i = 0; i < idsString.length; ++i) {
                ids[i] = Integer.parseInt(idsString[i]);
            }
            computerService.delete(ids);
        }
        return "redirect:./dashboard";
    }
}
