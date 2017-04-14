package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;

public class CompanyCLI extends PageCLI {
	public void printEntities(Scanner sc) throws SQLException {
		// logger.debug("start of printCompanies");
		boolean exitWanted = false;
		while (!exitWanted) {
			System.out.println("Page " + page + " : ");
			{
				List<Company> cl;
				if (nameCompany == null)
					cl = CompanyDAO.allCompanies(page, SIZE_PAGE);
				else
					cl = CompanyDAO.allCompaniesByName(nameCompany, page, SIZE_PAGE);
				for (Company c : cl) {
					System.out.println(c);
				}
				exitWanted = printChoicesAndGet(sc, false);
			}
		}
		// logger.debug("end of printCompanies");
	}

	@Override
	protected void setName(String name) {
		this.nameCompany = name;
	}

	@Override
	protected String menuPage(Scanner sc) {
		System.out.println("Next Page : r");
		System.out.println("Previous Page : l");
		System.out.println("Search by name : name");
		System.out.println("Exit : exit");
		return sc.nextLine();
	}

	@Override
	protected boolean companyName(Scanner sc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean companyId(Scanner sc) {
		// TODO Auto-generated method stub
		return false;
	}
}
