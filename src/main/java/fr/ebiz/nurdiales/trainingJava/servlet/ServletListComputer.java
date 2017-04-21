package fr.ebiz.nurdiales.trainingJava.servlet;

import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("")
public class ServletListComputer extends HttpServlet {
    private ComputerManager manager;

    private static final String ACTION = "ACTION";
    private static final String DELETE = "delete";
    private static final String SEARCH = "search";
    private static final String DELETESELECTED = "selection";
    private static final String LIST_COMPUTER_VIEW = "/WEB-INF/dashboard.jsp";

    /**
     * BANANAExplication.
     * @param request  BANANARequest.
     * @param response BANANAResponse.
     * @throws javax.servlet.ServletException BANANAServletException.
     * @throws IOException                    BANANAIOException.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Parameters params = new Parameters();
        params.setSize(10);
        int count = 0;
        String sPage = request.getParameter("page");
        String sSize = request.getParameter("size");
        if (sPage != null) {
            try {
                int tmp = (int) Float.parseFloat(sPage) - 1;
                params.setPage((tmp < 0) ? 0 : tmp);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (sSize != null) {
            try {
                int tmp = Integer.parseInt(sSize);
                params.setSize((tmp < 1) ? 1 : tmp);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        params.setName(request.getParameter(SEARCH));
        List<Computer> listComputers = null;
        manager = new ComputerManager();
        try {
            listComputers = manager.getAll(params);
        } catch (ComputerDAOException | SQLException e) {
            e.printStackTrace();
        }

        try {
            count = manager.getCount(params);
        } catch (ComputerDAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("numberComputers", count);
        request.setAttribute("computers", listComputers);
        request.setAttribute("page", params.getPage() + 1);
        request.setAttribute("size", params.getSize());
        request.setAttribute("name", params.getName());
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
                manager.delete(ids);
            } catch (ComputerDAOException | IllegalArgumentException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        }
        response.sendRedirect("");
    }
}
