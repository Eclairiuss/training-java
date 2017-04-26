package fr.ebiz.nurdiales.trainingJava.servlet;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.Company;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;
import fr.ebiz.nurdiales.trainingJava.Computer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/edit_computer")
public class ServletEditComputer extends HttpServlet {
    private ComputerManager computerManager;
    private CompanyManager companyManager;

    private static final String DASHBOARD_REDIRECTION = "./";
    private static final String EDIT_COMPUTER_VIEW = "/WEB-INF/edit_computer.jsp";
    private static final String CREATE_COMPUTER_REDIRECTION = "./add_computer";

    private static final String ID = "id";
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
        String sId = request.getParameter(ID);
        Company company = null;
        try {
            company = companyManager.get(request.getParameter(COMPANY_ID));
            computer.setName(request.getParameter(NAME));
            computer.setIntroduced(request.getParameter(INTRODUCED));
            computer.setDiscontinued(request.getParameter(DISCONTINUED));
            computer.setCompany(company);
            if (sId != null) {
                computer.setId(Integer.parseInt(sId));
                computerManager.update(computer);
            } else {
                computerManager.add(computer);
            }
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
        int id = 0;
        computerManager = new ComputerManager();
        companyManager = new CompanyManager();
        String sId = request.getParameter("id");
        if (sId != null) {
            try {
                id = Integer.parseInt(sId);
                Computer computer = computerManager.get(id);
                List<Company> companies = companyManager.getAll();
                request.setAttribute("computer", computer);
                request.setAttribute("companies", companies);
                this.getServletContext().getRequestDispatcher(EDIT_COMPUTER_VIEW).forward(request, response);
            } catch (NumberFormatException | ComputerDAOException | CompanyDAOException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        } else {
            response.sendRedirect(CREATE_COMPUTER_REDIRECTION);
        }
    }
}
