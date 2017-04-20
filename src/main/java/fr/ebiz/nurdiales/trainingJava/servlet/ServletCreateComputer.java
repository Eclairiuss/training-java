package fr.ebiz.nurdiales.trainingJava.servlet;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ebiz on 20/04/17.
 */
@WebServlet("/add_computer")
public class ServletCreateComputer extends HttpServlet {
    private ComputerManager computerManager;
    private CompanyManager companyManager;

    private static final String DASHBOARD_REDIRECTION = "../";
    private static final String CREATE_COMPUTER_VIEW = "/WEB-INF/add_computer.jsp";

    private static final String NAME = "computerName";
    private static final String INTRODUCED = "introduced";
    private static final String DISCONTINUED = "discontinued";
    private static final String COMPANY_ID = "companyId";

    /**
     * BANANAExplication.
     * @param request BANANARequest.
     * @param response BANANAResponse.
     * @throws javax.servlet.ServletException BANANAServletException.
     * @throws IOException BANANAIOException.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        computerManager = new ComputerManager();
        companyManager = new CompanyManager();
        Computer computer = new Computer();
        Company company = null;
        try {
            company = companyManager.get(request.getParameter(COMPANY_ID));
            computer.setName(request.getParameter(NAME));
            computer.setIntroduced(request.getParameter(INTRODUCED));
            computer.setDiscontinued(request.getParameter(DISCONTINUED));
            computer.setCompany(company);
            computerManager.add(computer);
            response.sendRedirect(DASHBOARD_REDIRECTION);
        } catch (CompanyDAOException | ComputerDAOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /**
     * BANANAExplication.
     * @param request BANANARequest.
     * @param response BANANAResponse.
     * @throws javax.servlet.ServletException BANANAServletException.
     * @throws IOException BANANAIOException.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        companyManager = new CompanyManager();
        try {
            List<Company> companies = companyManager.getAll();
            request.setAttribute("companies", companies);
        } catch (CompanyDAOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        this.getServletContext().getRequestDispatcher(CREATE_COMPUTER_VIEW).forward(request, response);
    }
}
