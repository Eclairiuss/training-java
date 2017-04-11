package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.SQLException;
import java.util.Scanner;

import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public class PageCLI {
	private static int SIZE_PAGE = 10;
	private int page;

	public PageCLI() {
		page = 0;
	}

	private void nextPage() {
		page += 1;
	}

	private void previousPage() {
		page -= 1;
	}

	public void printCompagnies(Scanner sc) throws SQLException {
		boolean exitWanted = false;
		while (!exitWanted) {
			System.out.println("Page " + page + " : ");
			CompanyDAO.requestAllCompanies((SIZE_PAGE * page), (SIZE_PAGE * (page+1)) + 1);
			System.out.println("Next Page : r");
			System.out.println("Previous Page : l");
			System.out.println("Exit : exit");
			switch (sc.nextLine()) {
			case "l":
				previousPage();
				break;
			case "r":
				nextPage();
				break;
			case "exit":
				exitWanted = true;
				break;
			}
		}
	}

	public void printComputers(Scanner sc) throws SQLException {
		boolean exitWanted = false;
		while (!exitWanted) {
			System.out.println("Page " + page + " : ");
			ComputerDAO.requestAllComputers((SIZE_PAGE * page), (SIZE_PAGE * (page+1)) + 1);
			System.out.println("Next Page : r");
			System.out.println("Previous Page : l");
			System.out.println("Exit : exit");
			switch (sc.nextLine()) {
			case "l":
				previousPage();
				break;
			case "r":
				nextPage();
				break;
			case "exit":
				exitWanted = true;
				break;
			}
		}
	}
}
