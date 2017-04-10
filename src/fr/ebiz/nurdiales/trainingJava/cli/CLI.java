package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.SQLException;
import java.util.Scanner;

import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public class CLI {
	private static final String EXIT = "exit", ALLCOMPUTERS = "listallcomputers", ALLCOMPANIES = "listallcompanies";
	public static void main(String[] args) {
		BasicConnector bc = BasicConnector.getInstance();
		ComputerDAO computerDAO = new ComputerDAO();
		Scanner sc = new Scanner(System.in);
		boolean wantExit = false;
		try {
			BasicConnector.connectToDB();
			while(!wantExit){
				printChoices();
				String l = sc.nextLine();
				String ll=l.toLowerCase();
				switch (ll.split(" ")[0]) {
				case EXIT:
					wantExit = true;
					break;
				case ALLCOMPUTERS :
					computerDAO.requestAllComputers();
//					List<Computer> computers = bc.requestAllComputers();
//					for(Computer c : computers)
//					{
//						System.out.println(c);
//					}
					break;
				case ALLCOMPANIES:
					break;
				case "details":
					switch(ll.split(" ")[1]){
						case "id":
//							idOfComputer 
							break;
						case "name":
//							nameOfComputer:
							break;
						default :
							break;
					}
					break;
				case "new":
					break;
				case "update":
					break;
				case "delete":
					break;

				default:
//					List<Computer> computers = bc.requestAllComputers();
//					for(Computer c : computers)
//					{
//						System.out.println(c);
//					}
					break;
				}
			}
			bc.disconnectToDB();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
	}
	
	public static void printChoices(){
		System.out.println("What do you want doing ?");
		System.out.println();
		System.out.println("List of computers : listallcomputers");
		System.out.println("List of companies : listallcompanies");
		System.out.println("Show computer details : details + id=idOfComputer");
		System.out.println("Show computer details : details + name=nameOfComputer");
		System.out.println("Create a computer : new");
		System.out.println("Update a computer : update");
		System.out.println("Delete a computer : delete");
	}
}