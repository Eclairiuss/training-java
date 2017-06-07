package view;

/**
 * Created by eclairiuss on 07/06/17.
 */
public class MainView {
    private static final String EXIT = "exit",
            ALLCOMPUTERS = "computers",
            ALLCOMPANIES = "companies",
            DETAILS = "details",
            UPDATE = "update",
            NEW = "new",
            ID = "id",
            SEPARATOR = " ",
            DATE_FORMAT = "AAAA-MM-JJ",
            SEPARATOR2 = "=";

    /**
     * Print choices for the main menu.
     */
    public void mainMenu() {
        System.out.println("What do you want doing ?");
        System.out.println();
        System.out.println("List of computers : " + ALLCOMPUTERS);
        System.out.println("List of companies : " + ALLCOMPANIES);
        System.out.println("Show listComputers details : " + DETAILS + SEPARATOR + ID + SEPARATOR2 + ID);
        System.out.println("Create a listComputers : " + NEW);
        System.out.println("Update a listComputers : " + UPDATE);
        System.out.println("Exit and close connexion : " + EXIT);
    }

    /**
     * Called create and update.
     */
    public void name() {
        System.out.print("Name : ");
    }

    /**
     * Called create and update.
     */
    public void introduced() {
        System.out.print("Date of Introduced (" + DATE_FORMAT + ") : ");
    }

    /**
     * Called create and update.
     */
    public void discontinued() {
        System.out.print("Date of Discontinued (" + DATE_FORMAT + ") : ");
    }

    /**
     * Called create and update.
     */
    public void companyId() {
        System.out.print("Id of Company : ");
    }

    /**
     * Called create and update.
     */
    public void id() {
        System.out.print("Id : ");
    }
}
