package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public class ComputerCLI extends PageCLI {
	ComputerDAO computerDAO;

	public ComputerCLI() {
		super();
		computerDAO = new ComputerDAO();
	}

	public void printEntities(Scanner sc) throws SQLException {
		// logger.debug("start of printComputers");
		boolean exitWanted = false;
		while (!exitWanted) {
			System.out.println("Page " + page + " : ");
			{
				List<Computer> cl;
				if (nameCompany == null && idCompany == 0 && nameComputer == null)
					cl = computerDAO.requestAllComputers(page, SIZE_PAGE);
				else if (nameCompany == null && nameComputer == null)
					cl = computerDAO.requestAllComputersByCompanyID(idCompany, page, SIZE_PAGE);
				else if (idCompany == 0 && nameComputer == null)
					cl = computerDAO.requestAllComputersByCompanyName(nameCompany, page, SIZE_PAGE);
				else if (idCompany == 0 && nameCompany == null)
					cl = computerDAO.requestAllComputersByName(nameComputer, page, SIZE_PAGE);
				else
					cl = computerDAO.requestAllComputers(page, SIZE_PAGE); // TODO
				for (Computer c : cl) {
					System.out.println(c);
				}
				exitWanted = printChoicesAndGet(sc, true);
			}
		}
		// logger.debug("end of printComputers");
	}

	@Override
	protected void setName(String name) {
		this.nameComputer = name;
	}

	@Override
	protected String menuPage(Scanner sc) {
		System.out.println("Next Page : r");
		System.out.println("Previous Page : l");
		System.out.println("Search by name : name");
		System.out.println("Search by companyId : companyid");
		System.out.println("Search by companyName : companyname");
		System.out.println("Exit : exit");
		return sc.nextLine();
	}

	@Override
	protected boolean companyName(Scanner sc) {
		System.out.print("Company Name : ");
		String tmp = sc.nextLine();
		tmp = tmp.equals("") ? null : tmp;
		setNameCompany(tmp);
		return true;
	}

	@Override
	protected boolean companyId(Scanner sc) {
		System.out.print("Company Id : ");
		String tmp = sc.nextLine();
		try {
			int id = tmp.equals("") ? 0 : Integer.parseInt(tmp);
			setIdCompany(id);
		} catch (IllegalArgumentException e) {
			System.out.println("Not an Id");
		}
		return true;
	}
}
