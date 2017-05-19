package fr.ebiz.nurdiales.trainingJava.cli;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class CompanyCLI extends PageCLI {
    private static Logger logger = LoggerFactory.getLogger(CompanyCLI.class);
    private CompanyManager companyManager;

    /**
     * Constructor of CompanyCLI, make a new Page for print companies.
     */
    public CompanyCLI() {
        super();
        params  = (new Parameters.Builder()).page(0).size(10).build();
        companyManager = new CompanyManager();
    }

    @Override
    public void printEntities(Scanner sc) throws CompanyDAOException {
        logger.debug("start of printCompanies");
        boolean exitWanted = false;
        while (!exitWanted) {
            System.out.println("Page " + params.getPage() + " : ");

            List<Company> cl;
            if (params.getNameCompany() == null) {
                cl = companyManager.getAll(params.getPage(), params.getSize());
            } else {
                cl = companyManager.getAll(params.getNameCompany(), params.getPage(), params.getSize());
            }
            for (Company c : cl) {
                System.out.println(c);
            }
            exitWanted = printChoicesAndGet(sc, false);

        }
        logger.debug("end of printCompanies");
    }

    @Override
    protected void setName(String name) {
        params.setNameCompany(name);
    }

    @Override
    protected void delete(String tmp) {
        try {
            companyManager.delete(Integer.parseInt(tmp));
        } catch (CompanyDAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String menuPage(Scanner sc) {
        System.out.println("Next Page : r");
        System.out.println("Previous Page : l");
        System.out.println("Delete : d");
        System.out.println("Search by name : name");
        System.out.println("Exit : exit");
        return sc.nextLine();
    }

    @Override
    protected boolean companyName(Scanner sc) {
        return false;
    }

    @Override
    protected boolean companyId(Scanner sc) {
        return false;
    }
}
