package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.Date;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;

public abstract class PageCLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageCLI.class);
    protected int SIZE_PAGE = 10;
    protected int page;
    protected int idComputer;
    protected int idCompany;
    protected String nameComputer;
    protected String nameCompany;
    protected Date introduced;
    protected Date discontinued;

    /**
     * modify page cursor to the next page.
     */
    private void nextPage() {
        page += 1;
    }

    /**
     * modify page cursor to the previous page if the cursor isn't to start.
     */
    private void previousPage() {
        page = (page == 0) ? 0 : page - 1;
    }

    /**
     * Default function for print entities that the page contains.
     * @param sc Scanner for the CLI output.
     * @throws ComputerDAOException ComputerDAO fails to execute a request.
     * @throws CompanyDAOException CompanyDAO fails to execute a request.
     */
    public abstract void printEntities(Scanner sc) throws ComputerDAOException, CompanyDAOException;

    /**
     * Default function for get companies who have similar name if it's
     * necessary for a request.
     * @param sc Scanner for the CLI output.
     * @return true if necessary for a request.
     */
    protected abstract boolean companyName(Scanner sc);

    /**
     * Default function for get companies who have same id if it's necessary for
     * a request.
     * @param sc Scanner for the CLI output.
     * @return true if necessary for a request.
     */
    protected abstract boolean companyId(Scanner sc);

    /**
     * Default function for print menu (inside page context) and scan user
     * choice.
     * @param sc Scanner for the CLI output.
     * @return the user choice.
     */
    protected abstract String menuPage(Scanner sc);

    /**
     * Default function for set the name of searched entity.
     * @param name String for search entities by names
     */
    protected abstract void setName(String name);

    /**
     * Fonction for print the menu and analyse the user answer.
     * @param sc Scanner for the CLI output.
     * @param isPageComputers true if need companyid and companyname options.
     * @return true if exit wanted by user, false else when user do good
     *         command.
     */
    protected boolean printChoicesAndGet(Scanner sc, boolean isPageComputers) {
        String tmp = null;
        while (true) {
            switch (menuPage(sc)) {
            case "exit":
                return true;
            case "l":
                previousPage();
                return false;
            case "r":
                nextPage();
                return false;
            case "name":
                System.out.print("Name : ");
                tmp = sc.nextLine();
                tmp = tmp.equals("") ? null : tmp;
                setName(tmp);
                return false;
            case "companyid":
                if (companyId(sc)) {
                    System.out.print("Company Id : ");
                    tmp = sc.nextLine();
                    try {
                        int id = tmp.equals("") ? 0 : Integer.parseInt(tmp);
                        setIdCompany(id);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Not an Id");
                    }
                    return false;
                }
                break;
            case "companyname":
                if (companyName(sc)) {
                    return false;
                }
                break;
            default:
                break;
            }
        }
    }

    public int getSizePage() {
        return SIZE_PAGE;
    }

    public void setSizePage(int sizePage) {
        SIZE_PAGE = sizePage < 1 ? 1 : sizePage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page < 0 ? 0 : page;
    }

    public int getIdComputer() {
        return idComputer;
    }

    public void setIdComputer(int idComputer) {
        this.idComputer = idComputer < 0 ? 0 : idComputer;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany < 0 ? 0 : idCompany;
    }

    public String getNameComputer() {
        return nameComputer;
    }

    public void setNameComputer(String nameComputer) {
        this.nameComputer = nameComputer;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public Date getIntroduced() {
        return introduced;
    }

    public void setIntroduced(Date introduced) {
        this.introduced = introduced;
    }

    public Date getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Date discontinued) {
        this.discontinued = discontinued;
    }
}
