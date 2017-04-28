package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.Date;
import java.util.Scanner;

import fr.ebiz.nurdiales.trainingJava.database.JDBCSingleton;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;

public class CLI {
    private static Logger logger = LoggerFactory.getLogger(CLI.class);
    private static final String EXIT = "exit", ALLCOMPUTERS = "computers", ALLCOMPANIES = "companies",
            DELETE = "delete", DETAILS = "details", UPDATE = "update", NEW = "new", ID = "id",
            ID_COMPANY = "company_id", NAME = "name", DATE_OF_DISCONTINUED = "date_of_discontinued",
            DATE_OF_INTRODUCED = "date_of_introduced", SEPARATOR = " ", DATE_FORMA = "AAAA-MM-JJ", SEPARATOR2 = "=";
    private static Scanner sc;
    private static PageCLI p;
    private static ComputerManager computerManager;
    private static CompanyManager companyManager;

    /**
     * Main function with main loop for the CLI.
     * @throws ComputerDAOException ComputerDAO fails to execute a request.
     * @throws CompanyDAOException CompanyDAO fails to execute a request.
     */
    public void mainCLI() throws ComputerDAOException, CompanyDAOException {

        JDBCSingleton connection = JDBCSingleton.getInstance();

        sc = new Scanner(System.in);
        computerManager = new ComputerManager();
        companyManager = new CompanyManager();

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
                                Computer computer = computerManager
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
     * Called when user want delete a computer. Ask the ID of computer to
     * delete.
     * @param sc Scanner for the CLI output.
     */
    private void deleteComputer(Scanner sc) {
        System.out.print("ID of computer to delete : ");
        String l = sc.nextLine();
        try {
            computerManager.delete(Integer.parseInt(l));
        } catch (ComputerDAOException e) {
            e.printStackTrace();
        }
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
     * @throws ComputerDAOException ComputerDAO fails to execute a request.
     */
    private void newComputer(Scanner sc) throws ComputerDAOException {
        Computer computer = new Computer(0, "", null, null, null);
        while (computer.getName().equals("")) {
            System.out.print("Name : ");
            computer.setName(sc.nextLine());
        }
        System.out.print("Date of Introduced (" + DATE_FORMA + ") : ");
        try {
            computer.setIntroduced(stringToDate(sc.nextLine()));
        } catch (IllegalArgumentException e) {
            logger.debug("Date of Introduced is invalid");
        }
        System.out.print("Date of Discontinued (" + DATE_FORMA + ") : ");
        try {
            computer.setDiscontinued(stringToDate(sc.nextLine()));
        } catch (IllegalArgumentException e) {
            logger.debug("Date of Introduced is invalid");
        }
        System.out.print("Id of Company : ");
        try {
            computer.setCompany(companyManager.get(Integer.parseInt(sc.nextLine())));
        } catch (Exception e) {
            computer.setCompany(null);
        }
        logger.debug(computer.toString());
        computerManager.add(computer);
    }

    /**
     * Called when a user want change a computer. Ask first the computer id.
     * @param sc Scanner for the CLI output.
     * @throws ComputerDAOException ComputerDAO fails to execute a request.
     */
    private void updateComputer(Scanner sc) throws ComputerDAOException {
        Computer computer = new Computer(0, "", null, null, null);
        boolean isInteger = true;
        String s;
        while (computer.getId() == 0) {
            System.out.print("Id : ");
            s = sc.nextLine();
            try {
                computer = computerManager.get(Integer.parseInt(s));
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
                computer.setIntroduced(stringToDate(sc.nextLine()));
            } catch (IllegalArgumentException e) {
                logger.debug("Date of Introduced is invalid");
            }
            System.out.print("Date of Discontinued (" + DATE_FORMA + ") : ");
            try {
                computer.setDiscontinued(stringToDate(sc.nextLine()));
            } catch (IllegalArgumentException e) {
                logger.debug("Date of Introduced is invalid");
            }
            System.out.print("Id of Company : ");
            try {
                computer.setCompany(companyManager.get(Integer.parseInt(sc.nextLine())));
            } catch (Exception e) {
            }
            computerManager.update(computer);
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
        System.out.println("Show computer details : " + DETAILS + SEPARATOR + ID + SEPARATOR2 + ID);
        System.out.println("Create a computer : " + NEW);
        System.out.println("Update a computer : " + UPDATE);
        System.out.println("Exit and close connexion : " + EXIT);

        return sc.nextLine();
    }
}