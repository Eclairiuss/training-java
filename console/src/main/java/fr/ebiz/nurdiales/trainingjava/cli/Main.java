package fr.ebiz.nurdiales.trainingjava.cli;

import fr.ebiz.nurdiales.trainingjava.dto.CompanyDTO;
import fr.ebiz.nurdiales.trainingjava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingjava.dto.PageDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static final String COMPANY_URI = "http://localhost:8080/api/companies";
    private static final String COMPUTER_URI = "http://localhost:8080/api/computers";
    private static final Client CLIENT = ClientBuilder.newClient();

    /**
     * Main menu.
     * @return the selection of the user
     */
    private static int mainMenu() {
        int selection;
        System.out.println("************************");
        System.out.println("*****Menu principale*****");
        System.out.println("*************************");
        /***************************************************/

        System.out.println("choisir une opperation à effectuer");
        System.out.println("-------------------------\n");
        System.out.println("1 - Lister tous les ordinateurs");
        System.out.println("2 - Lister toutes les compagnies");
        System.out.println("3 - Afficher les details d'un ordinateur");
        System.out.println("4 - Ajouter un ordinateur");
        System.out.println("5 - Modifier un ordinateur");
        System.out.println("6 - Supprimer un ordinateur");
        System.out.println("7-  Quitter");

        selection = sc.nextInt();
        return selection;
    }

    /**
     * menu to show the details of a computer selected by id.
     */
    private void detailsComputerMenu() {
        int choice = 0;
        System.out.println("inserez un identifiant d'un ordinateur");
        choice = sc.nextInt();
        ComputerDTO computerDTO = CLIENT.target(COMPUTER_URI)
                .path(String.valueOf(choice))
                .request(MediaType.APPLICATION_JSON)
                .get(ComputerDTO.class);
        System.out.println(computerDTO);
    }

    /**
     * menu to show all Company in database.
     */

    private void showListCompanyMenu() {
        List<CompanyDTO> companiesDTO = null;
        companiesDTO = CLIENT.target(COMPANY_URI)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<CompanyDTO>>() {
                });
        if (companiesDTO.isEmpty()) {
            System.out.println("aucune compagnie trouvée");
        } else {
            for (CompanyDTO companyDTO : companiesDTO) {
                System.out.println(companyDTO.getId() + "\t" + companyDTO.getName());
            }
        }
    }

    /**
     * menu to show all computer in database.
     */
    private void showListComputerMenu() {
        String resp = null;
        int page = 1;
        long size = 50;
        long total;
        List<ComputerDTO> computersDTO;

        do {
            resp = sc.nextLine();

            PageDTO pageDTO = CLIENT.target(COMPUTER_URI).queryParam("page", page).queryParam("size", size)
                    .request(MediaType.APPLICATION_JSON)
                    .get(PageDTO.class);

            computersDTO = pageDTO.getComputersDTO();
            total = pageDTO.getNbrComputers();
            page++;

            if (computersDTO.isEmpty()) {
                break;
            } else {
                for (ComputerDTO computer : computersDTO) {
                    System.out.println(computer.getCompanyId() + "\t" + computer.getCompanyName());
                }
                System.out.println("NEXT>>");
            }

        } while (!resp.equalsIgnoreCase("q") || page < total);

    }

    /**
     * menu for adding computer.
     */
    private void addComputerMenu() {
        int nbrAtt = 4;
        String[] inputText = new String[nbrAtt];
        String response;

        inputText[0] = sc.nextLine();

        for (int i = 0; i < nbrAtt; i++) {
            printText(i);
            if (i == 3) {
                response = sc.nextLine();
                if (response.equals("O") || response.equals("o")) {
                    showListCompanyMenu();
                } else {
                    System.out.println("tapez l'identifiant de la compagnie");
                }
            }
            inputText[i] = sc.nextLine();
        }
        try {
            ComputerDTO computerDTO = new ComputerDTO(null, inputText[0], inputText[1], inputText[2], Integer.parseInt(inputText[3]), null);
            CLIENT.target(COMPUTER_URI)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(computerDTO), String.class);

        } catch (NullPointerException | DateTimeParseException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * update a computer.
     */

    private void updateComputerMenu() {
        int choice = 0;
        //showListComputerMenu();
        System.out.println("tapez l'identifiant de l'ordinateur");
        choice = sc.nextInt();

        ComputerDTO computer = null;
        try {
            computer = detailsComputerMenuById(choice);
            String[] oldComputer = {computer.getName(), computer.getIntroduced(),
                    computer.getDiscontinued(), computer.getCompanyId() == null ? "" : computer.getCompanyId().toString()};
            String[] inputText = new String[oldComputer.length];
            String response = null;

            for (int i = 0; i < inputText.length - 1; i++) {
                inputText[i] = sc.nextLine();
                printText(i);
                System.out.print(oldComputer[i] + "->");
                if (i == 3) {
                    response = sc.nextLine();
                    if (response.equals("O") || response.equals("o")) {
                        showListCompanyMenu();
                    } else {
                        System.out.println("tapez l'identifiant de la compagnie");
                    }
                }

                if (!inputText[i].equals("")) {
                    System.err.print("modify");
                    oldComputer[i] = inputText[i];
                }
                System.out.println(i);
            }


            ComputerDTO computerDTO = new ComputerDTO(computer.getId(), computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getCompanyId(), computer.getCompanyName());
            System.out.println("resumé de votre modification");
            System.out.println(computerDTO);
            CLIENT.target(COMPUTER_URI)
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(computerDTO), String.class);
        } catch (NullPointerException | DateTimeParseException e) {
            System.err.println(e.getMessage());

        }
    }

    /**
     * delete a computer.
     */
    private void deleteComputerMenu() {
        System.out.println("inserez un identifiant à supprimer");
        int choice = sc.nextInt();
        CLIENT.target(COMPUTER_URI)
                .path(String.valueOf(choice))
                .request(MediaType.APPLICATION_JSON).delete();
        System.out.println("l'ordinateur " + choice + " a été bien supprimer");
    }


    /**
     * print text on the console.
     * @param i message output
     */
    private static void printText(int i) {

        switch (i) {
            case 0:
                System.out.print("nom de l'ordinateur: ");
                break;
            case 1:
                System.out.print("date d'entrée de l'ordinateur format[YYYY-MM-DD HH:mm:ss]: ");
                break;
            case 2:
                System.out.print("date d'arrêt de l'ordinateur format[YYYY-MM-DD HH:mm:ss]: ");
                break;
            case 3:
                System.out.print("voulez vous affichez toute les compagnies existantes? O/N");
                break;

        }

    }

    /**
     * @param id of computer.
     * @return the DTO corresponding.
     */
    private ComputerDTO detailsComputerMenuById(int id) {
        ComputerDTO computerDTO = null;
        computerDTO = CLIENT.target(COMPUTER_URI)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(ComputerDTO.class);
        return computerDTO;
    }

    /**
     * Main methode.
     * @param args null
     * @throws SQLException
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-cli.xml");
        Main vue = new Main();
        int choice = 0;
        do {
            switch (choice) {
                case 1:
                    vue.showListComputerMenu();
                    break;
                case 2:
                    vue.showListCompanyMenu();
                    break;
                case 3:
                    vue.detailsComputerMenu();
                    break;
                case 4:
                    vue.addComputerMenu();
                    break;
                case 5:
                    vue.updateComputerMenu();
                    break;
                case 6:
                    vue.deleteComputerMenu();
                    break;
                default:
                    System.out.println("choisissez un numéro entre 1 et 7");
            }
            choice = mainMenu();
        } while (choice != 7);

    }
}