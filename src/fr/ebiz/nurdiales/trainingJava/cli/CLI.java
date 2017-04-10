package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public class CLI {
	private static final String EXIT = "exit", ALLCOMPUTERS = "computers", ALLCOMPANIES = "companies",
			DELETE = "delete", ID = "id", NAME = "name", DATE = "date", SEPARATOR = " ", AAAA = "AAAA", MM = "MM",
			JJ = "JJ", SEPARATOR2 = "=", SEPARATORDATE = "-";

	public static void main(String[] args) {
		BasicConnector bc = BasicConnector.getInstance();
		ComputerDAO computerDAO = new ComputerDAO();
		Scanner sc = new Scanner(System.in);
		boolean wantExit = false;
		try {
			BasicConnector.connectToDB();
			while (!wantExit) {
				printChoices();
				String l = sc.nextLine();
				String ll = l.toLowerCase();

				switch (ll.split(SEPARATOR)[0]) {
				case EXIT:
					wantExit = true;
					break;
				case ALLCOMPUTERS:
					computerDAO.requestAllComputers();
					// List<Computer> computers = bc.requestAllComputers();
					// for(Computer c : computers)
					// {
					// System.out.println(c);
					// }
					break;
				case ALLCOMPANIES:
					break;
				case "details":
					switch (ll.split(SEPARATOR)[1]) {
					case ID:
						// idOfComputer
						break;
					case "name":
						// nameOfComputer:
						break;
					default:
						break;
					}
					break;
				case "new":
					break;
				case "update":
					break;
				case DELETE:
					String l2 = ll.split(SEPARATOR)[1];
					switch (l2.split(SEPARATOR2)[0]) {
					case ID:
						computerDAO.delete(Integer.parseInt(l2.split("=")[1]));
						break;
					case "name":
						// nameOfComputer:
						break;
					default:
						break;
					}
					break;

				default:
					// List<Computer> computers = bc.requestAllComputers();
					// for(Computer c : computers)
					// {
					// System.out.println(c);
					// }
					break;
				}
			}
			bc.disconnectToDB();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
	}

	public static void printChoices() {
		System.out.println("What do you want doing ?");
		System.out.println();
		System.out.println("List of computers : " + ALLCOMPUTERS);
		System.out.println("List of companies : " + ALLCOMPANIES);
		System.out.println("Show computer details : details" + SEPARATOR + ID + SEPARATOR2 + ID);
		System.out.println("Show computer details : details" + SEPARATOR + NAME + SEPARATOR2 + "nameOfComputer");
		System.out.println("Create a computer : new");
		System.out.println("Update a computer : update" + SEPARATOR + NAME + SEPARATOR2 + "nameOfComputer" + SEPARATOR
				+ DATE + "OfIntroduced" + SEPARATOR2 + AAAA + SEPARATORDATE + MM + SEPARATORDATE + JJ + SEPARATOR + DATE
				+ "OfDiscontinued" + SEPARATOR2 + AAAA + SEPARATORDATE + MM + SEPARATORDATE + JJ + SEPARATOR + ID
				+ SEPARATOR2 + ID);
		System.out.println("Delete a computer : delete" + SEPARATOR + ID + SEPARATOR2 + ID);
		System.out.println("Exit and close connexion : " + EXIT);

	}
}