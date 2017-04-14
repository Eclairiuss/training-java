package fr.ebiz.nurdiales.trainingJava.cli;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;

public class CompanyCLI extends PageCLI {
    private static Logger logger = LoggerFactory.getLogger(CompanyCLI.class);
    private CompanyManager companyManager;

    public CompanyCLI() {
        super();
        page = 0;
        SIZE_PAGE = 10;
        companyManager = new CompanyManager();
    }

    public void printEntities(Scanner sc) throws CompanyDAOException {
        logger.debug("start of printCompanies");
        boolean exitWanted = false;
        while (!exitWanted) {
            System.out.println("Page " + page + " : ");

            List<Company> cl;
            if (nameCompany == null) {
                cl = companyManager.allCompanies(page, SIZE_PAGE);
            } else {
                cl = companyManager.allCompaniesByName(nameCompany, page, SIZE_PAGE);
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
        this.nameCompany = name;
    }

    @Override
    protected String menuPage(Scanner sc) {
        System.out.println("Next Page : r");
        System.out.println("Previous Page : l");
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
