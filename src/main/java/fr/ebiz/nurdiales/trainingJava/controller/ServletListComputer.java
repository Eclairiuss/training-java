package fr.ebiz.nurdiales.trainingJava.controller;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;
import fr.ebiz.nurdiales.trainingJava.util.Trad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("")
public class ServletListComputer extends HttpServlet {
    @Autowired
    ComputerManager computerManager;

    private static final String ACTION = "ACTION";
    private static final String DELETE = "delete";
    private static final String SEARCH = "search";
    private static final String ORDER = "order";
    private static final String NAME = "name";
    private static final String SIZE = "size";
    private static final String PAGE = "page";
    private static final String DELETESELECTED = "selection";
    private static final String LIST_COMPUTER_VIEW = "/WEB-INF/dashboard.jsp";

    /**
     * Constructor.
     */
    public ServletListComputer() { }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    public ComputerManager getComputerManager() {
        return computerManager;
    }

    public void setComputerManager(ComputerManager computerManager) {
        this.computerManager = computerManager;
    }

    /**
     * BANANAExplication.
     * @param request  BANANARequest.
     * @param response BANANAResponse.
     * @throws javax.servlet.ServletException BANANAServletException.
     * @throws IOException                    BANANAIOException.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sSize = request.getParameter(SIZE);
        String sPage = request.getParameter(PAGE);
        String sSearch = request.getParameter(SEARCH);
        String sTri = request.getParameter(ORDER);
        Parameters params = (new Parameters.Builder())
                                    .page(Trad.stringToInt(sPage) - 1)
                                    .size(Trad.stringToInt(sSize))
                                    .name(sSearch)
                                    .build();
        params.parseTri(sTri);
        int count = -1;
        List<Computer> listComputers = null;

        try {
            listComputers = computerManager.getAll(params);
            count = computerManager.getCount(params);
        } catch (ComputerDAOException | CompanyDAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("numberComputers", count);
        request.setAttribute("computers", listComputers);
        request.setAttribute(PAGE, params.getPage() + 1);
        request.setAttribute(SIZE, params.getSize());
        request.setAttribute(SEARCH, sSearch);
        request.setAttribute(ORDER, sTri);
        this.getServletContext().getRequestDispatcher(LIST_COMPUTER_VIEW).forward(request, response);
    }

    /**
     * BANANAExplication.
     * @param request  BANANARequest.
     * @param response BANANAResponse.
     * @throws javax.servlet.ServletException BANANAServletException.
     * @throws IOException                    BANANAIOException.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String difference = request.getParameter(ACTION);
        if (difference.equals(DELETE)) {
            String[] idsString = request.getParameter(DELETESELECTED).split(",");
            int[] ids = new int[idsString.length];
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
        response.sendRedirect("./?" + ORDER + "=" + request.getParameter(ORDER)
                                      + "&"  + SIZE + "=" + request.getParameter(SIZE)
                                      + "&"  + PAGE + "=" + request.getParameter(PAGE)
                                      + "&"  + SEARCH + "=" + request.getParameter(SEARCH));
    }
}
