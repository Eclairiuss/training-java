package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public abstract class PageCLI {
	protected int SIZE_PAGE = 10;
	// private static Logger logger;
	protected int page;
	protected int idComputer;
	protected int idCompany;
	protected String nameComputer;
	protected String nameCompany;
	protected Date introduced;
	protected Date discontinued;

	private void nextPage() {
		page += 1;
	}

	private void previousPage() {
		page = (page == 0) ? 0 : page - 1;
	}

	// affichage avec page pour les ordinateurs
	abstract public void printEntities(Scanner sc) throws SQLException;

	abstract protected boolean companyName(Scanner sc);

	abstract protected boolean companyId(Scanner sc);

	abstract protected String menuPage(Scanner sc);

	abstract protected void setName(String name);

	protected boolean printChoicesAndGet(Scanner sc, boolean isPageComputers) {
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
			case "name": {
				System.out.print("Name : ");
				String tmp = sc.nextLine();
				tmp = tmp.equals("") ? null : tmp;
				setName(tmp);
			}
				return false;
			case "companyid":
				if (companyId(sc)) {
					{
						System.out.print("Company Id : ");
						String tmp = sc.nextLine();
						try {
							int id = tmp.equals("") ? 0 : Integer.parseInt(tmp);
							setIdCompany(id);
						} catch (IllegalArgumentException e) {
							System.out.println("Not an Id");
						}
					}
					return false;
				}
				break;
			case "companyname":
				if (companyName(sc))
					return false;
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

	// public static void initLogger() {
	// logger = LoggerFactory.getLogger(PageCLI.class);
	// }
}
