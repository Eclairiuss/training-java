package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
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
		page = (page < 0) ? 0 : page - 1;
	}

	public void printCompagnies(Scanner sc) throws SQLException {
		boolean exitWanted = false;
		while (!exitWanted) {
			System.out.println("Page " + page + " : ");
			{
				List<Company> cl = CompanyDAO.requestAllCompanies((SIZE_PAGE * page), (SIZE_PAGE * (page + 1)) + 1);
				for (Company c : cl) {
					System.out.println(c);
				}
				exitWanted = printChoicesAndGet(sc);
			}
		}
	}

	public void printComputers(Scanner sc) throws SQLException {
		boolean exitWanted = false;
		while (!exitWanted) {
			System.out.println("Page " + page + " : ");

			{
				List<Computer> cl = ComputerDAO.requestAllComputers((SIZE_PAGE * page), (SIZE_PAGE * (page + 1)) + 1);
				for (Computer c : cl) {
					System.out.println(c);
				}

				exitWanted = printChoicesAndGet(sc);
			}
		}
	}

	private boolean printChoicesAndGet(Scanner sc) {
		System.out.println("Next Page : r");
		System.out.println("Previous Page : l");
		System.out.println("Exit : exit");
		switch (sc.nextLine()) {
		case "l":
			previousPage();
			return false;
		case "r":
			nextPage();
			return false;
		case "exit":
			return true;
		default:
			return printChoicesAndGet(sc);
		}
	}
}
