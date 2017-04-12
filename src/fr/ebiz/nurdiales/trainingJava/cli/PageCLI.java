package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.Date;
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
	private int idComputer;
	private int idCompany;
	private String nameComputer;
	private String nameCompany;
	private Date introduced;
	private Date discontinued;

	public PageCLI() {
		page = 0;
	}

	private void nextPage() {
		page += 1;
	}

	private void previousPage() {
		page = (page == 0) ? 0 : page - 1;
	}

	public void printCompagnies(Scanner sc) throws SQLException {
		boolean exitWanted = false;
		while (!exitWanted) {
			System.out.println("Page " + page + " : ");
			{
				List<Company> cl = CompanyDAO.requestAllCompanies(page, SIZE_PAGE);
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
				List<Computer> cl = ComputerDAO.requestAllComputers(page, SIZE_PAGE);
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

	public static int getSizePage() {
		return SIZE_PAGE;
	}

	public static void setSizePage(int sizePage) {
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
