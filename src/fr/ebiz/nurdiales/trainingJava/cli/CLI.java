package fr.ebiz.nurdiales.trainingJava.cli;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

public class CLI {
	public static void main(String[] args) {
		BasicConnector bc = new BasicConnector ();
		Scanner sc = new Scanner(System.in);
		boolean wantExit = false;
		try {
			bc.connectToDB();
			while(!wantExit){
				printChoices();
				String l = sc.nextLine();
				l=l.toLowerCase();
				switch (l.split(" ")[0]) {
				case "exit":
					wantExit = true;
					break;
				default:
					bc.requestAllComputers();
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
	}
	
	public static void printChoices(){
		System.out.println("What do you want doing ?");
	}
}