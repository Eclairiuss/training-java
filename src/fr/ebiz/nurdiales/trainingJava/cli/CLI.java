package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import fr.ebiz.nurdiales.trainingJava.database.JDBCSingleton;
import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;
import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;

public class CLI {
	// private static Logger logger = LoggerFactory.getLogger(CLI.class);
	private static final String EXIT = "exit", ALLCOMPUTERS = "computers", ALLCOMPANIES = "companies",
			DELETE = "delete", DETAILS = "details", UPDATE = "update", NEW = "new", ID = "id",
			ID_COMPANY = "company_id", NAME = "name", DATE_OF_DISCONTINUED = "date_of_discontinued",
			DATE_OF_INTRODUCED = "date_of_introduced", SEPARATOR = " ", DATE_FORMA = "AAAA-MM-JJ", SEPARATOR2 = "=";

	private static Scanner sc;
	private static PageCLI p;
	private static ComputerManager computerManager;
	private static CompanyManager companyManager;

	public void mainCLI() throws ComputerDAOException, CompanyDAOException {

		JDBCSingleton connection = JDBCSingleton.getInstance();

		sc = new Scanner(System.in);
		computerManager = new ComputerManager();
		companyManager = new CompanyManager();

		boolean wantContinue = true;
		try {
			while (wantContinue) {
				String[] l = mainMenu().split(SEPARATOR);
				switch (l[0].toLowerCase()) {
				case EXIT:
					wantContinue = false;
					break;
				case ALLCOMPUTERS: {
					p = new ComputerCLI();
					p.printEntities(sc);
				}
					break;
				case ALLCOMPANIES: {
					p = new CompanyCLI();
					p.printEntities(sc);
				}
					break;
				case DETAILS:
					switch (l[1].split(SEPARATOR2)[0]) {
					case ID:
						try {
							Computer computer = computerManager
									.getComputerById(Integer.parseInt(l[1].split(SEPARATOR2)[1]));
							System.out.println(computer);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					default:
						break;
					}
					break;
				case NEW: {
					newComputer(sc);
				}
					break;
				case UPDATE: {
					updateComputer(sc);
				}
					break;
				case DELETE:
					String l2 = l[1].toLowerCase();
					switch (l2.split(SEPARATOR2)[0]) {
					case ID:
						computerManager.delete(Integer.parseInt(l2.split("=")[1]));
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}
			connection.disconnectToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sc.close();
	}

	public static boolean parseForComputer(String s, Computer c) {
		String[] splited = s.split(SEPARATOR2);
		switch (splited[0]) {
		case NAME:
			c.setName(splited[1]);
			return true;
		case DATE_OF_INTRODUCED:
			c.setDateOfIntroduced(stringToDate(splited[1]));
			break;
		case DATE_OF_DISCONTINUED:
			c.setDateOfDiscontinued(stringToDate(splited[1]));
			break;
		case ID_COMPANY:
			try {
				c.setCompany(companyManager.companyById(Integer.parseInt(splited[1])));
			} catch (NumberFormatException | CompanyDAOException e) {
				c.setCompany(null);
				e.printStackTrace();
			}
			break;
		case ID:
			try {
				c.setId(Integer.parseInt(splited[1]));
			} catch (NumberFormatException e) {
				c.setId(0);
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		return false;
	}

	public static Date stringToDate(String s) {
		return Date.valueOf(s);
	}

	private void newComputer(Scanner sc) throws SQLException, ComputerDAOException {
		Computer computer = new Computer(0, "", null, null, null);
		while (computer.getName().equals("")) {
			System.out.print("Name : ");
			computer.setName(sc.nextLine());
		}
		System.out.print("Date of Introduced (" + DATE_FORMA + ") : ");
		try {
			computer.setDateOfIntroduced(stringToDate(sc.nextLine()));
		} catch (IllegalArgumentException e) {
			// logger.debug("Date of Introduced is invalid");
		}
		System.out.print("Date of Discontinued (" + DATE_FORMA + ") : ");
		try {
			computer.setDateOfDiscontinued(stringToDate(sc.nextLine()));
		} catch (IllegalArgumentException e) {
			// logger.debug("Date of Introduced=
			// LoggerFactory.getLogger(CLI.class); is invalid");
		}
		System.out.print("Id of Company : ");
		try {
			computer.setCompany(companyManager.companyById(Integer.parseInt(sc.nextLine())));
		} catch (Exception e) {
			computer.setCompany(null);
		}
		// logger.debug(computer.toString());
		computerManager.add(computer);
	}

	private void updateComputer(Scanner sc) throws SQLException, ComputerDAOException {
		Computer computer = new Computer(0, "", null, null, null);
		boolean isInteger = true;
		String s;
		while (computer.getId() == 0) {
			System.out.print("Id : ");
			s = sc.nextLine();
			try {
				computer = computerManager.getComputerById(Integer.parseInt(s));
			} catch (IllegalArgumentException e) {
				isInteger = false;
			}
		}
		if (isInteger) {
			System.out.print("Name : ");
			s = sc.nextLine();
			if (s.equals(""))
				;
			else
				computer.setName(s);
			System.out.print("Date of Introduced (" + DATE_FORMA + ") : ");
			try {
				computer.setDateOfIntroduced(stringToDate(sc.nextLine()));
			} catch (IllegalArgumentException e) {
				// logger.debug("Date of Introduced is invalid");
			}
			System.out.print("Date of Discontinued (" + DATE_FORMA + ") : ");
			try {
				computer.setDateOfDiscontinued(stringToDate(sc.nextLine()));
			} catch (IllegalArgumentException e) {
				// logger.debug("Date of Introduced is invalid");
			}
			System.out.print("Id of Company : ");
			try {
				computer.setCompany(companyManager.companyById(Integer.parseInt(sc.nextLine())));
			} catch (Exception e) {
			}
			computerManager.update(computer);
		}
	}

	public static String mainMenu() {
		System.out.println("What do you want doing ?");
		System.out.println();
		System.out.println("List of computers : " + ALLCOMPUTERS);
		System.out.println("List of companies : " + ALLCOMPANIES);
		System.out.println("Show computer details : " + DETAILS + SEPARATOR + ID + SEPARATOR2 + ID);
		System.out.println("Create a computer : " + NEW);
		System.out.println("Update a computer : " + UPDATE);
		System.out.println("Delete a computer : " + DELETE + SEPARATOR + ID + SEPARATOR2 + ID);
		System.out.println("Exit and close connexion : " + EXIT);

		return sc.nextLine();
	}
}