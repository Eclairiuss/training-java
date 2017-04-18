package main.java.fr.ebiz.nurdiales.trainingJava.main;

import main.java.fr.ebiz.nurdiales.trainingJava.cli.CLI;
import main.java.fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import main.java.fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;

public class Main {
    /**
     * main function for run the CLI.
     * @param args Arguments of the program launch.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-debug")) {
                System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
            }
        }
        try {
            (new CLI()).mainCLI();
        } catch (ComputerDAOException | CompanyDAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}