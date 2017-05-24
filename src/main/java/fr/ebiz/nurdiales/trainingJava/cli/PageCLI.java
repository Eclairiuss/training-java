package fr.ebiz.nurdiales.trainingJava.cli;

import fr.ebiz.nurdiales.trainingJava.exceptions.DAOCompanyException;
import fr.ebiz.nurdiales.trainingJava.exceptions.DAOComputerException;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.Scanner;

public abstract class PageCLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageCLI.class);
    protected int idComputer;
    protected Date introduced;
    protected Date discontinued;
    protected Parameters params;

    /**
     * modify page cursor to the next page.
     */
    private void nextPage() {
        params.setPage(params.getPage() + 1);
    }

    /**
     * modify page cursor to the previous page if the cursor isn't to start.
     */
    private void previousPage() {
        int tmp = params.getPage() - 1;
        tmp = (tmp < 0) ? 0 : tmp;
        params.setPage(tmp);
    }

    /**
     * Default function for print entities that the page contains.
     * @param sc Scanner for the CLI output.
     * @throws DAOComputerException ComputerDAO fails to execute a request.
     * @throws DAOCompanyException CompanyDAO fails to execute a request.
     */
    public abstract void printEntities(Scanner sc) throws DAOComputerException, DAOCompanyException;

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
                case "d":
                    System.out.print("Id of " + (isPageComputers ? "Computer" : "Company") + " : ");
                    tmp = sc.nextLine();
                    tmp = tmp.equals("") ? null : tmp;
                    delete(tmp);
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
                            params.setIdCompany(id);
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

    /**
     * delete the entity wanted.
     * @param tmp id of entity to delete.
     */
    protected abstract void delete(String tmp);

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
