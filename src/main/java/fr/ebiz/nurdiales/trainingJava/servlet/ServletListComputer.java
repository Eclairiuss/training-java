package fr.ebiz.nurdiales.trainingJava.servlet;

import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/ServletListComputer")
public class ServletListComputer extends HttpServlet {
    private ComputerManager manager;

    private static final String LIST_COMPUTER_VIEW = "/WEB-INF/computer_list.jsp";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletListComputer() {
        super();
    }

    /**
     * TODO.
     * @param request TODO.
     * @param response TODO.
     * @throws javax.servlet.ServletException TODO.
     * @throws IOException TODO.
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    /**
     * TODO.
     * @param request TODO.
     * @param response TODO.
     * @throws javax.servlet.ServletException TODO.
     * @throws IOException TODO.
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        System.out.println("page : " + page);
        System.out.println("page : " + size);

        List<Computer> listComputers = null;
        manager = new ComputerManager();
        try {
            listComputers = manager.requestAllComputers(page, size);
        } catch (ComputerDAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("computers", listComputers);
        this.getServletContext().getRequestDispatcher(LIST_COMPUTER_VIEW).forward(request, response);
    }
}
