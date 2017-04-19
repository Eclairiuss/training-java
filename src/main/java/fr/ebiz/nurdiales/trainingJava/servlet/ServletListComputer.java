package fr.ebiz.nurdiales.trainingJava.servlet;

import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("")
public class ServletListComputer extends HttpServlet {
    private ComputerManager manager;

    private static final String LIST_COMPUTER_VIEW = "/WEB-INF/dashboard.jsp";

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        int page = 0;
        int size = 10;
        String sPage = request.getParameter("page");
        String sSize = request.getParameter("size");
        if (sPage != null) {
            try {
                page = Integer.parseInt(sPage);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (sSize != null) {
            try {
                size = Integer.parseInt(sSize);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        List<Computer> listComputers = null;
        manager = new ComputerManager();
        try {
            listComputers = manager.getAll(page, size);
        } catch (ComputerDAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("computers", listComputers);
        this.getServletContext().getRequestDispatcher(LIST_COMPUTER_VIEW).forward(request, response);
    }
}
