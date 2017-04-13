package fr.ebiz.nurdiales.trainingJava.logger;

import fr.ebiz.nurdiales.trainingJava.cli.CLI;
import fr.ebiz.nurdiales.trainingJava.cli.PageCLI;
import fr.ebiz.nurdiales.trainingJava.database.BasicConnector;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public class LoggerManager {
	public static void initLoggers() {
		CLI.initLogger();
		PageCLI.initLogger();
		BasicConnector.initLogger();
		CompanyDAO.initLogger();
		ComputerDAO.initLogger();
	}
}
