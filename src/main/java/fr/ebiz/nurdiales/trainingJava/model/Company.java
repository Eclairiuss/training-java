package fr.ebiz.nurdiales.trainingJava.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Company {
	private static Logger logger;
	private int id;
	private String name;

	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}

	public static void initLogger() {
		logger = LoggerFactory.getLogger(Company.class);
	}
}
