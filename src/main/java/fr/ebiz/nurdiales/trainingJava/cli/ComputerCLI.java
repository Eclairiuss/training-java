package fr.ebiz.nurdiales.trainingJava.cli;

import java.util.List;
import java.util.Scanner;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;

public class ComputerCLI extends PageCLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageCLI.class);
    ComputerManager computerManager;

    /**
     * Constructor of ComputerCLI, make a new Page for print computers.
     */
    public ComputerCLI() {
        super();
        params = new Parameters.Builder().page(0).size(10).build();
        computerManager = new ComputerManager();
    }

    @Override
    public void printEntities(Scanner sc) throws ComputerDAOException, CompanyDAOException {
        LOGGER.debug("start of printComputers");
        params = new Parameters.Builder().size(10).build();
        boolean exitWanted = false;
        while (!exitWanted) {
            System.out.println("Page " + params.getPage() + " : ");
            List<Computer> cl;
            cl = computerManager.getAll(params);
            for (Computer c : cl) {
                System.out.println(new ComputerDTO(c));
            }
            exitWanted = printChoicesAndGet(sc, true);
        }
        LOGGER.debug("end of printComputers");
    }

    @Override
    protected void setName(String name) {
        params.setName(name);
    }

    @Override
    protected void delete(String tmp) {
        try {
            computerManager.delete(Integer.parseInt(tmp));
        } catch (ComputerDAOException e) {
            LOGGER.debug("Fail delete");
            e.printStackTrace();
        }
    }

    @Override
    protected String menuPage(Scanner sc) {
        System.out.println("Next Page : r");
        System.out.println("Previous Page : l");
        System.out.println("Search by name : name");
        System.out.println("Search by companyId : companyid");
        System.out.println("Search by companyName : companyname");
        System.out.println("Delete : d");
        System.out.println("Exit : exit");
        return sc.nextLine();
    }

    @Override
    protected boolean companyName(Scanner sc) {
        System.out.print("Company Name : ");
        String tmp = sc.nextLine();
        tmp = tmp.equals("") ? null : tmp;
        params.setNameCompany(tmp);
        return true;
    }

    @Override
    protected boolean companyId(Scanner sc) {
        System.out.print("Company Id : ");
        String tmp = sc.nextLine();
        try {
            int id = tmp.equals("") ? 0 : Integer.parseInt(tmp);
            params.setIdCompany(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Not an Id");
        }
        return true;
    }
}
