package controller;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import view.CompanyView;
import view.ComputerView;
import view.MainView;
import view.PageView;
import fr.ebiz.nurdiales.trainingjava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingjava.service.CompanyService;
import fr.ebiz.nurdiales.trainingjava.service.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.Scanner;

@Component("cli")
public class CLIController {
    private static Logger logger = LoggerFactory.getLogger(CLIController.class);

    private static final String EXIT = "exit",
                                ALLCOMPUTERS = "computers",
                                ALLCOMPANIES = "companies",
                                DETAILS = "details",
                                UPDATE = "update",
                                NEW = "new",
                                ID = "id",
                                SEPARATOR = " ",
                                DATE_FORMAT = "AAAA-MM-JJ",
                                SEPARATOR2 = "=";


    private static final String URI_API = "http://localhost:8080/api/";

    private static final String URI_API_COMPUTER = URI_API + "computers";

    private static final String URI_API_COMPANY = URI_API + "companies";

    private Scanner sc;

    private PageView pageView;
    private MainView mainView;
    private CompanyView companyView;
    private ComputerView computerView;

    private ComputerService computerService;
    private CompanyService companyService;

    /**
     * Contructor.
     * @param mainView .
     * @param companyView .
     * @param computerView .
     * @param computerService .
     * @param companyService .
     */
    @Autowired
    public CLIController(MainView mainView,
                         CompanyView companyView,
                         ComputerView computerView,
                         ComputerService computerService,
                         CompanyService companyService) {
        this.mainView = mainView;
        this.companyView = companyView;
        this.computerView = computerView;
        this.companyService = companyService;
        this.computerService = computerService;
    }

    /**
     * Main function with main loop for the CLIController.
     */
    public void mainCLI() {
        sc = new Scanner(System.in);
        boolean wantContinue = true;

        while (wantContinue) {
            mainView.mainMenu();
            String[] line = sc.nextLine().split(SEPARATOR);

            switch (line[0].toLowerCase()) {
                case EXIT:
                    wantContinue = false;
                    break;
                case ALLCOMPUTERS:
                    pageView = computerView;
                    pageView.printEntities(sc);
                    break;
                case ALLCOMPANIES:
                    pageView = companyView;
                    pageView.printEntities(sc);
                    break;
                case DETAILS:
                    switch (line[1].split(SEPARATOR2)[0]) {
                        case ID:
                            try {
                                ComputerDTO computer = computerService
                                                            .get(Integer.parseInt(line[1].split(SEPARATOR2)[1]));
                                System.out.println(computer);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case NEW:
                    newComputer(sc);
                    break;
                case UPDATE:
                    updateComputer(sc);
                    break;
                default:
                    break;
            }
        }

        sc.close();
    }

    /**
     * Called when user want delete a listComputers. Ask the ID of listComputers to
     * delete.
     * @param sc Scanner for the CLIController output.
     */
    private void deleteComputer(Scanner sc) {
        System.out.print("ID of listComputers to delete : ");
        String l = sc.nextLine();
        computerService.delete(Integer.parseInt(l));
    }

    /**
     * Convert a String to a sql.Date.
     * @param s String to convert.
     * @return Date from the s content.
     */
    public static Date stringToDate(String s) {
        return Date.valueOf(s);
    }

    /**
     * Called when user want create a new user.
     * @param sc Scanner for the CLIController output.
     */
    private void newComputer(Scanner sc) {
        Client client = getAuthenticatedClient("admin", "admin");
        ComputerDTO computer = new ComputerDTO();
        while (computer.getName().isEmpty()) {
            mainView.name();
            computer.setName(sc.nextLine());
        }
        mainView.introduced();
        try {
            computer.setIntroduced(sc.nextLine());
        } catch (IllegalArgumentException e) {
            logger.debug("Date of Introduced is invalid");
        }
        mainView.discontinued();
        try {
            computer.setDiscontinued(sc.nextLine());
        } catch (IllegalArgumentException e) {
            logger.debug("Date of Introduced is invalid");
        }
        mainView.companyId();
        computer.setCompanyId(Integer.parseInt(sc.nextLine()));
        logger.debug(computer.toString());
        computerService.add(computer);

        client.target(URI_API_COMPUTER + "/")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(computer, MediaType.APPLICATION_JSON), ComputerDTO.class);
    }

    /**
     * Called when a user want change a listComputers. Ask first the listComputers id.
     * @param sc Scanner for the CLIController output.
     */
    private void updateComputer(Scanner sc) {
        Client client = getAuthenticatedClient("admin", "admin");
        ComputerDTO computer = new ComputerDTO();
        boolean isInteger = true;
        String s;
        while (computer.getId() == 0) {
            mainView.id();
            s = sc.nextLine();
            try {
                computer = computerService.get(Integer.parseInt(s));
            } catch (IllegalArgumentException e) {
                isInteger = false;
            }
            LoggerFactory.getLogger(CLIController.class);
        }
        if (isInteger) {
            mainView.name();
            s = sc.nextLine();
            if (s.isEmpty()) {
                computer.setName(null);
            } else {
                computer.setName(s);
            }
            mainView.introduced();
            try {
                computer.setIntroduced(sc.nextLine());
            } catch (IllegalArgumentException e) {
                logger.debug("Date of Introduced is invalid");
            }
            mainView.discontinued();
            try {
                computer.setDiscontinued(sc.nextLine());
            } catch (IllegalArgumentException e) {
                logger.debug("Date of Introduced is invalid");
            }
            mainView.companyId();
            try {
                computer.setCompanyId(Integer.parseInt(sc.nextLine()));
            } catch (IllegalArgumentException e) {
                logger.debug("CompanyID is invalid");
            }
            computerService.update(computer);
            client.target(URI_API_COMPUTER + "/")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(computer, MediaType.APPLICATION_JSON), ComputerDTO.class);
        }
    }


    /**
     * Get an authenticated client.
     * @param user username.
     * @param passwd password.
     * @return client.
     */
    private Client getAuthenticatedClient(String user, String passwd) {
        Client client = ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, passwd);
        return client.register(feature);
    }
}