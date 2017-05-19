package fr.ebiz.nurdiales.trainingJava.controller;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.CompanyDTO;
import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@WebServlet("/edit_computer")
public class ServletEditComputer extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletEditComputer.class);
    @Autowired
    private ComputerManager computerManager;
    @Autowired
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
     * Constructor.
     */
    public ServletEditComputer() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
    /**
     * TODO.
     * @param request TODO.
     * @param response TODO.
     * @throws javax.servlet.ServletException TODO.
     * @throws IOException TODO.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerDTO computer = new ComputerDTO();
        String sId = request.getParameter(ID);
        CompanyDTO company = null;
        request.getParameterMap().forEach((k, v) -> {
            StringBuilder tmp = new StringBuilder();
            for (String s : v) {
                tmp.append(", " + s);
            }
            LOGGER.info(k + " : " + tmp.toString());
        });
        try {
            company = companyManager.get(request.getParameter(COMPANY_ID));
            computer.setName(request.getParameter(NAME));
            computer.setIntroduced(request.getParameter(INTRODUCED));
            computer.setDiscontinued(request.getParameter(DISCONTINUED));
            computer.setCompanyId(company.getId());
            computer.setCompanyName(company.getName());
            if (sId != null) {
                computer.setId(Integer.parseInt(sId));
                computerManager.update(computer.toComputer());
            } else {
                computerManager.add(computer.toComputer());
            }
            response.sendRedirect(DASHBOARD_REDIRECTION);
        } catch (CompanyDAOException | ComputerDAOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /**
     * TODO.
     * @param request TODO.
     * @param response TODO.
     * @throws javax.servlet.ServletException TODO.
     * @throws IOException TODO.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = 0;
        String sId = request.getParameter("id");
        List<CompanyDTO> companies = null;
        try {
            companies = companyManager.getAll();
        } catch (CompanyDAOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        request.setAttribute("companies", companies);
        if (sId != null) {
            try {
                id = Integer.parseInt(sId);
                ComputerDTO computer = computerManager.get(id);
                for (CompanyDTO company : companies) {
                    if (company.getId().equals(computer.getCompanyId())) {
                        computer.setCompanyName(company.getName());
                        break;
                    }
                }
                request.setAttribute("computer", computer);
                this.getServletContext().getRequestDispatcher(EDIT_COMPUTER_VIEW).forward(request, response);
            } catch (NumberFormatException | ComputerDAOException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        } else {
            response.sendRedirect(CREATE_COMPUTER_REDIRECTION);
        }
    }
}
