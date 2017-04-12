package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public class PageCLI {
	private static int SIZE_PAGE = 10;
	private static Logger logger;
	private int page;
	private int idComputer;
	private int idCompany;
	private String nameComputer;
	private String nameCompany;
	private Date introduced;
	private Date discontinued;

	private void nextPage() {
		page += 1;
	}

	private void previousPage() {
		page = (page == 0) ? 0 : page - 1;
	}

	// affichage avec page pour les compagnies
	public void printCompanies(Scanner sc) throws SQLException {
		logger.debug("start of printCompanies");
		boolean exitWanted = false;
		while (!exitWanted) {
			System.out.println("Page " + page + " : ");
			{
				List<Company> cl;
				if (nameCompany == null)
					cl = CompanyDAO.requestAllCompanies(page, SIZE_PAGE);
				else
					cl = CompanyDAO.requestAllCompaniesByName(nameCompany, page, SIZE_PAGE);
				for (Company c : cl) {
					System.out.println(c);
				}
				exitWanted = printChoicesAndGet(sc, false);
			}
		}
		logger.debug("end of printCompanies");
	}

	// affichage avec page pour les ordinateurs
	public void printComputers(Scanner sc) throws SQLException {
		logger.debug("start of printComputers");
		boolean exitWanted = false;
		while (!exitWanted) {
			System.out.println("Page " + page + " : ");
			{
				List<Computer> cl;
				if (nameCompany == null && idCompany == 0 && nameComputer == null)
					cl = ComputerDAO.requestAllComputers(page, SIZE_PAGE);
				else if (nameCompany == null && nameComputer == null)
					cl = ComputerDAO.requestAllComputersByCompanyID(idCompany, page, SIZE_PAGE);
				else if (idCompany == 0 && nameComputer == null)
					cl = ComputerDAO.requestAllComputersByCompanyName(nameCompany, page, SIZE_PAGE);
				else if (idCompany == 0 && nameCompany == null)
					cl = ComputerDAO.requestAllComputersByName(nameComputer, page, SIZE_PAGE);
				else
					cl = ComputerDAO.requestAllComputers(page, SIZE_PAGE); // TODO
				for (Computer c : cl) {
					System.out.println(c);
				}

				exitWanted = printChoicesAndGet(sc, true);
			}
		}
		logger.debug("end of printComputers");
	}

	private boolean printChoicesAndGet(Scanner sc, boolean isPageComputers) {
		while (true) {
			System.out.println("Next Page : r");
			System.out.println("Previous Page : l");
			System.out.println("Search by name : name");
			// System.out.println("Search by id : id");
			if (isPageComputers) {
				System.out.println("Search by companyId : companyid");
				System.out.println("Search by companyName : companyname");
			}
			System.out.println("Exit : exit");
			switch (sc.nextLine()) {
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
				if (isPageComputers)
					setNameComputer(tmp);
				else
					setNameCompany(tmp);
			}
				return false;
			// case "id": {
			// System.out.print("Id : ");
			// String tmp = sc.nextLine();
			// try {
			// int id = tmp.equals("") ? 0 : Integer.parseInt(tmp);
			// if (isPageComputers)
			// setIdComputer(id);
			// else
			// setIdCompany(id);
			// } catch (IllegalArgumentException e) {
			// System.out.println("Not an Id");
			// }
			// }
			// return false;
			case "companyid":
				if (isPageComputers) {
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
				if (isPageComputers) {
					{
						System.out.print("Company Name : ");
						String tmp = sc.nextLine();
						tmp = tmp.equals("") ? null : tmp;
						setNameCompany(tmp);
					}
					return false;
				}
				break;
			default:
				break;
			}
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

	public static void initLogger() {
		logger = LoggerFactory.getLogger(PageCLI.class);
	}
}
