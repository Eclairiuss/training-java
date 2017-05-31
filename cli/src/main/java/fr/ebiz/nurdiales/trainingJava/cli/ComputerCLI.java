package fr.ebiz.nurdiales.trainingJava.cli;

import fr.ebiz.nurdiales.trainingJava.core.Computer;
import fr.ebiz.nurdiales.trainingJava.core.Page;
import fr.ebiz.nurdiales.trainingJava.core.Parameters;
import fr.ebiz.nurdiales.trainingJava.service.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("computerCLI")
public class ComputerCLI extends PageCLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageCLI.class);
    ComputerService computerService;

    /**
     * Constructor of ComputerCLI, make a new Page for print computers.
     * @param computerService .
     */
    @Autowired
    public ComputerCLI(ComputerService computerService) {
        super();
        params = new Parameters.Builder().page(0).size(10).build();
        this.computerService = computerService;
    }

    @Override
    public void printEntities(Scanner sc) {
        LOGGER.debug("start of printComputers");
        params = new Parameters.Builder().size(10).build();
        boolean exitWanted = false;
        while (!exitWanted) {
            System.out.println("Page " + params.getPage() + " : ");
            Page page;
            page = computerService.getAll(params);
            for (Computer c : page.getComputers()) {
                System.out.println(c.toString());
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
        computerService.delete(Integer.parseInt(tmp));
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
