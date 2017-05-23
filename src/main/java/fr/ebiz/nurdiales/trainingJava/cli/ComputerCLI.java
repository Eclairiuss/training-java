package fr.ebiz.nurdiales.trainingJava.cli;

import fr.ebiz.nurdiales.trainingJava.exceptions.DAOCompanyException;
import fr.ebiz.nurdiales.trainingJava.exceptions.DAOComputerException;
import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.model.Page;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import fr.ebiz.nurdiales.trainingJava.service.ComputerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ComputerCLI extends PageCLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageCLI.class);
    ComputerServiceImpl computerServiceImpl;

    /**
     * Constructor of ComputerCLI, make a new Page for print computers.
     */
    public ComputerCLI() {
        super();
        params = new Parameters.Builder().page(0).size(10).build();
        computerServiceImpl = new ComputerServiceImpl();
    }

    @Override
    public void printEntities(Scanner sc) throws DAOComputerException, DAOCompanyException {
        LOGGER.debug("start of printComputers");
        params = new Parameters.Builder().size(10).build();
        boolean exitWanted = false;
        while (!exitWanted) {
            System.out.println("Page " + params.getPage() + " : ");
            Page page;
            page = computerServiceImpl.getAll(params);
            for (ComputerDTO c : page.getComputers()) {
                System.out.println(c);
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
        computerServiceImpl.delete(Integer.parseInt(tmp));
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
