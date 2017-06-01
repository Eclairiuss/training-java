package fr.ebiz.nurdiales.trainingjava.cli;

import fr.ebiz.nurdiales.trainingjava.core.Company;
import fr.ebiz.nurdiales.trainingjava.core.Parameters;
import fr.ebiz.nurdiales.trainingjava.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component("companyCLI")
public class CompanyCLI extends PageCLI {
    private static Logger logger = LoggerFactory.getLogger(CompanyCLI.class);
    private CompanyService companyService;

    /**
     * Constructor of CompanyCLI, make a new Page for print companies.
     * @param companyService .
     */
    @Autowired
    public CompanyCLI(CompanyService companyService) {
        super();
        params  = (new Parameters.Builder()).page(0).size(10).build();
        this.companyService = companyService;
    }

    @Override
    public void printEntities(Scanner sc) {
        logger.debug("start of printCompanies");
        boolean exitWanted = false;
        while (!exitWanted) {
            System.out.println("Page " + params.getPage() + " : ");

            List<Company> cl;
            if (params.getNameCompany() == null) {
                cl = companyService.getAll(params.getPage(), params.getSize());
            } else {
                cl = companyService.getAll(params.getNameCompany(), params.getPage(), params.getSize());
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
        companyService.delete(Integer.parseInt(tmp));
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
