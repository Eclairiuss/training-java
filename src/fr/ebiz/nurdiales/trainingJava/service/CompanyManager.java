package fr.ebiz.nurdiales.trainingJava.service;

import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public class CompanyManager {
	// private static final Logger logger = LoggerFactory.getLogger(ComputerManager.class);
	
	private ComputerDAO computerDAO;
	
	public CompanyManager() {
		this.computerDAO = new ComputerDAO();
	}
}
