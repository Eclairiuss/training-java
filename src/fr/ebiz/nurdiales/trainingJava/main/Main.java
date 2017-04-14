package fr.ebiz.nurdiales.trainingJava.main;

import fr.ebiz.nurdiales.trainingJava.cli.CLI;

public class Main {
	public static void main(String[] args) {
		if (args.length > 0) {
			if (args[0].equals("-debug"))
				System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
		}
		CLI.mainCLI();
	}
}