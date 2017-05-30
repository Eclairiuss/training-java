package fr.ebiz.nurdiales.trainingJava.cli;

import fr.ebiz.nurdiales.trainingJava.binding.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.core.Computer;
import fr.ebiz.nurdiales.trainingJava.service.CompanyServiceImpl;
import fr.ebiz.nurdiales.trainingJava.service.ComputerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.Scanner;

public class CLI {
    private static Logger logger = LoggerFactory.getLogger(CLI.class);
    private static final String EXIT = "exit", ALLCOMPUTERS = "computers", ALLCOMPANIES = "companies",
            DELETE = "delete", DETAILS = "details", UPDATE = "update", NEW = "new", ID = "id",
            ID_COMPANY = "company_id", NAME = "name", DATE_OF_DISCONTINUED = "date_of_discontinued",
            DATE_OF_INTRODUCED = "date_of_introduced", SEPARATOR = " ", DATE_FORMA = "AAAA-MM-JJ", SEPARATOR2 = "=";
    private static Scanner sc;
    private static PageCLI p;
    @Autowired
    private static ComputerServiceImpl computerServiceImpl;
    @Autowired
    private static CompanyServiceImpl companyServiceImpl;

    /**
     * Main function with main loop for the CLI.
     */
    public void mainCLI() {
        sc = new Scanner(System.in);

        boolean wantContinue = true;
        while (wantContinue) {
            String[] l = mainMenu().split(SEPARATOR);
            switch (l[0].toLowerCase()) {
                case EXIT:
                    wantContinue = false;
                    break;
                case ALLCOMPUTERS:
                    p = new ComputerCLI();
                    p.printEntities(sc);
                    break;
                case ALLCOMPANIES:
                    p = new CompanyCLI();
                    p.printEntities(sc);
                    break;
                case DETAILS:
                    switch (l[1].split(SEPARATOR2)[0]) {
                        case ID:
                            try {
                                Computer computer = computerServiceImpl
                                                            .get(Integer.parseInt(l[1].split(SEPARATOR2)[1]));
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
     * @param sc Scanner for the CLI output.
     */
    private void deleteComputer(Scanner sc) {
        System.out.print("ID of listComputers to delete : ");
        String l = sc.nextLine();
        computerServiceImpl.delete(Integer.parseInt(l));
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
     * @param sc Scanner for the CLI output.
     */
    private void newComputer(Scanner sc) {
        ComputerDTO computer = new ComputerDTO();
        while (computer.getName().equals("")) {
            System.out.print("Name : ");
            computer.setName(sc.nextLine());
        }
        System.out.print("Date of Introduced (" + DATE_FORMA + ") : ");
        try {
            computer.setIntroduced(sc.nextLine());
        } catch (IllegalArgumentException e) {
            logger.debug("Date of Introduced is invalid");
        }
        System.out.print("Date of Discontinued (" + DATE_FORMA + ") : ");
        try {
            computer.setDiscontinued(sc.nextLine());
        } catch (IllegalArgumentException e) {
            logger.debug("Date of Introduced is invalid");
        }
        System.out.print("Id of Company : ");
        computer.setCompanyId(Integer.parseInt(sc.nextLine()));

        logger.debug(computer.toString());
        computerServiceImpl.add(computer);
    }

    /**
     * Called when a user want change a listComputers. Ask first the listComputers id.
     * @param sc Scanner for the CLI output.
     */
    private void updateComputer(Scanner sc) {
        Computer computer = new Computer();
        boolean isInteger = true;
        String s;
        while (computer.getId() == 0) {
            System.out.print("Id : ");
            s = sc.nextLine();
            try {
                computer = computerServiceImpl.get(Integer.parseInt(s));
            } catch (IllegalArgumentException e) {
                isInteger = false;
            }
            LoggerFactory.getLogger(CLI.class);
        }
        if (isInteger) {
            System.out.print("Name : ");
            s = sc.nextLine();
            if (s.equals("")) {
                computer.setName(null);
            } else {
                computer.setName(s);
            }
            System.out.print("Date of Introduced (" + DATE_FORMA + ") : ");
            try {
                computer.setIntroduced(sc.nextLine());
            } catch (IllegalArgumentException e) {
                logger.debug("Date of Introduced is invalid");
            }
            System.out.print("Date of Discontinued (" + DATE_FORMA + ") : ");
            try {
                computer.setDiscontinued(sc.nextLine());
            } catch (IllegalArgumentException e) {
                logger.debug("Date of Introduced is invalid");
            }
            System.out.print("Id of Company : ");
            try {
                computer.setCompanyId(Integer.parseInt(sc.nextLine()));
            } catch (IllegalArgumentException e) {
                logger.debug("CompanyID is invalid");
            }
            computerServiceImpl.update(computer);
        }
    }

    /**
     * Main menu of the CLI. Read the next line of the user.
     * @return Next line of the user.
     */
    public static String mainMenu() {
        System.out.println("What do you want doing ?");
        System.out.println();
        System.out.println("List of computers : " + ALLCOMPUTERS);
        System.out.println("List of companies : " + ALLCOMPANIES);
        System.out.println("Show listComputers details : " + DETAILS + SEPARATOR + ID + SEPARATOR2 + ID);
        System.out.println("Create a listComputers : " + NEW);
        System.out.println("Update a listComputers : " + UPDATE);
        System.out.println("Exit and close connexion : " + EXIT);

        return sc.nextLine();
    }


    /**
     * main function for run the CLI.
     * @param args Arguments of the program launch.
     */
    public static void main(String[] args) {
        //TODO
        if (args.length > 0) {
            if (args[0].equals("-debug")) {
                System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
            }
        }
        (new CLI()).mainCLI();
    }
}