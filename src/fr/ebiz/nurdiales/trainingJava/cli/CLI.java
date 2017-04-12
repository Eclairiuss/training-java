package fr.ebiz.nurdiales.trainingJava.cli;

//import java.io.Console;
//import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import javax.swing.JOptionPane;
//import javax.swing.JPasswordField;

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
		// String username = null;
		// String password = null;
		Scanner sc = new Scanner(System.in);
		boolean wantExit = false;

		// System.out.print("Username : ");
		// username = sc.nextLine();
		// try {
		// password = readPwd();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		try {
			logger.debug("Try connection to DB");
			// BasicConnector.connectToDB(username, password);
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
					// List<Computer> computers = bc.requestAllComputers();
					// for(Computer c : computers)
					// {
					// System.out.println(c);
					// }
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
					// case NAME:
					// // nameOfComputer:
					// break;
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
					// case NAME:
					// // nameOfComputer:
					// // TODO
					// break;
					default:
						break;
					}
					break;

				default:
					break;
				}
			}
			sc.close();
			BasicConnector.disconnectToDB();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	// private static String readPwd() throws IOException {
	// String message = "Enter password";
	// String passwd = null;
	// Console c = System.console();
	// if (c == null) { // IN ECLIPSE IDE
	// JPasswordField pf = new JPasswordField();
	// passwd = JOptionPane.showConfirmDialog(null, pf, message,
	// JOptionPane.OK_CANCEL_OPTION,
	// JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION ? new
	// String(pf.getPassword()) : null;
	// return passwd;
	// } else {
	// return new String(c.readPassword("Password: "));
	// }
	// }

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
			computer.setDateOfIntroduced(null);
		}
		System.out.print("Date of Discontinued (" + DATE_FORMA + ") : ");
		try {
			computer.setDateOfDiscontinued(stringToDate(sc.nextLine()));
		} catch (IllegalArgumentException e) {
			computer.setDateOfDiscontinued(null);
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
			}
			System.out.print("Date of Discontinued (" + DATE_FORMA + ") : ");
			try {
				computer.setDateOfDiscontinued(stringToDate(sc.nextLine()));
			} catch (IllegalArgumentException e) {
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
		// System.out.println("Show computer details : " + DETAILS + SEPARATOR +
		// NAME + SEPARATOR2 + "nameOfComputer");
		System.out.println("Create a computer : " + NEW);
		System.out.println("Update a computer : " + UPDATE);
		System.out.println("Delete a computer : " + DELETE + SEPARATOR + ID + SEPARATOR2 + ID);
		System.out.println("Exit and close connexion : " + EXIT);

	}

	public static void initLogger() {
		logger = LoggerFactory.getLogger(CLI.class);
	}
}