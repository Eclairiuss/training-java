package fr.ebiz.nurdiales.trainingJava.main;

import fr.ebiz.nurdiales.trainingJava.cli.CLI;
import fr.ebiz.nurdiales.trainingJava.exceptions.DAOCompanyException;
import fr.ebiz.nurdiales.trainingJava.exceptions.DAOComputerException;

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
        } catch (DAOComputerException | DAOCompanyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}