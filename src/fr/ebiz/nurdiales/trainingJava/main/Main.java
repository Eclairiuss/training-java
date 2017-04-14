package fr.ebiz.nurdiales.trainingJava.main;

import fr.ebiz.nurdiales.trainingJava.cli.CLI;
import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;

public class Main {
	public static void main(String[] args) {
		if (args.length > 0) {
			if (args[0].equals("-debug"))
				System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
		}
		try {
			(new CLI()).mainCLI();
		} catch (ComputerDAOException | CompanyDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}