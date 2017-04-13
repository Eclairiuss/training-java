package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public class CLI {
	private static Logger logger;
	private static final String EXIT = "exit", ALLCOMPUTERS = "computers", ALLCOMPANIES = "companies",
			DELETE = "delete", DETAILS = "details", UPDATE = "update", NEW = "new", ID = "id",
			ID_COMPANY = "company_id", NAME = "name", DATE_OF_DISCONTINUED = "date_of_discontinued",
			DATE_OF_INTRODUCED = "date_of_introduced", SEPARATOR = " ", DATE_FORMA = "AAAA-MM-JJ", SEPARATOR2 = "=";

	public static void mainCLI() {
		Scanner sc = new Scanner(System.in);
		boolean wantExit = false;
		try {
			logger.debug("Try connection to DB");
			BasicConnector.connectToDB("admincdb", "qwerty1234");
			logger.debug("Succes connection to DB");
			while (!wantExit) {
				printChoices();
				String line = sc.nextLine();
				String[] l = line.split(SEPARATOR);
				switch (l[0].toLowerCase()) {
				case EXIT:
					wantExit = true;
					break;
				case ALLCOMPUTERS: {
					PageCLI p = new PageCLI();
					p.printComputers(sc);
				}
					break;
				case ALLCOMPANIES: {
					PageCLI p = new PageCLI();
					p.printCompanies(sc);
				}
					break;
				case DETAILS:
					switch (l[1].split(SEPARATOR2)[0]) {
					case ID:
						try {
							Computer computer = ComputerDAO
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
						ComputerDAO.delete(Integer.parseInt(l2.split("=")[1]));
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}
			BasicConnector.disconnectToDB();
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
				c.setManufacturer(CompanyDAO.getCompanyById(Integer.parseInt(splited[1])));
			} catch (NumberFormatException | SQLException e) {
				c.setManufacturer(null);
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

	private static void newComputer(Scanner sc) throws SQLException {
		Computer computer = new Computer(0, "", null, null, null);
		while (computer.getName().equals("")) {
			System.out.print("Name : ");
			computer.setName(sc.nextLine());
		}
		System.out.print("Date of Introduced (" + DATE_FORMA + ") : ");
		try {
			computer.setDateOfIntroduced(stringToDate(sc.nextLine()));
		} catch (IllegalArgumentException e) {
			logger.debug("Date of Introduced is invalid");
		}
		System.out.print("Date of Discontinued (" + DATE_FORMA + ") : ");
		try {
			computer.setDateOfDiscontinued(stringToDate(sc.nextLine()));
		} catch (IllegalArgumentException e) {
			logger.debug("Date of Introduced is invalid");
		}
		System.out.print("Id of Company : ");
		try {
			computer.setManufacturer(CompanyDAO.getCompanyById(Integer.parseInt(sc.nextLine())));
		} catch (Exception e) {
			computer.setManufacturer(null);
		}
		logger.debug(computer.toString());
		ComputerDAO.Add(computer);
	}

	private static void updateComputer(Scanner sc) throws SQLException {
		Computer computer = new Computer(0, "", null, null, null);
		boolean isInteger = true;
		String s;
		while (computer.getId() == 0) {
			System.out.print("Id : ");
			s = sc.nextLine();
			try {
				computer = ComputerDAO.getComputerById(Integer.parseInt(s));
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
				logger.debug("Date of Introduced is invalid");
			}
			System.out.print("Date of Discontinued (" + DATE_FORMA + ") : ");
			try {
				computer.setDateOfDiscontinued(stringToDate(sc.nextLine()));
			} catch (IllegalArgumentException e) {
				logger.debug("Date of Introduced is invalid");
			}
			System.out.print("Id of Company : ");
			try {
				computer.setManufacturer(CompanyDAO.getCompanyById(Integer.parseInt(sc.nextLine())));
			} catch (Exception e) {
			}
			ComputerDAO.update(computer);
		}
	}

	public static void printChoices() {
		System.out.println("What do you want doing ?");
		System.out.println();
		System.out.println("List of computers : " + ALLCOMPUTERS);
		System.out.println("List of companies : " + ALLCOMPANIES);
		System.out.println("Show computer details : " + DETAILS + SEPARATOR + ID + SEPARATOR2 + ID);
		System.out.println("Create a computer : " + NEW);
		System.out.println("Update a computer : " + UPDATE);
		System.out.println("Delete a computer : " + DELETE + SEPARATOR + ID + SEPARATOR2 + ID);
		System.out.println("Exit and close connexion : " + EXIT);
	}

	public static void initLogger() {
		logger = LoggerFactory.getLogger(CLI.class);
	}
}