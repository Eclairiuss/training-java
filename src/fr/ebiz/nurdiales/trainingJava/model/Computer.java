package fr.ebiz.nurdiales.trainingJava.model;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Computer {
	private static final Date neverBefore = Date.valueOf("1970-01-01");
	private static Logger logger;
	private int id;
	private String name; // seul obligatoire
	private Date dateOfIntroduced, dateOfDiscontinued;
	private Company manufacturer;

	public Computer(int id, String name, Date dateOfIntroduced, Date dateOfDiscontinued, Company manufacturer) {
		this.id = id;
		this.name = name;
		this.dateOfIntroduced = dateOfIntroduced;
		this.dateOfDiscontinued = dateOfDiscontinued;
		this.manufacturer = manufacturer;
	}

	public Computer() {
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

	public Date getDateOfIntroduced() {
		return dateOfIntroduced;
	}

	public void setDateOfIntroduced(Date dateOfIntroduced) {
		if (dateOfIntroduced != null)
			this.dateOfIntroduced = dateOfIntroduced.before(neverBefore) ? this.dateOfIntroduced : dateOfIntroduced;
		else
			this.dateOfIntroduced = null;
	}

	public Date getDateOfDiscontinued() {
		return dateOfDiscontinued;
	}

	public void setDateOfDiscontinued(Date dateOfDiscontinued) {
		if (dateOfDiscontinued != null)
			this.dateOfDiscontinued = dateOfDiscontinued.before(neverBefore) ? this.dateOfDiscontinued
					: dateOfDiscontinued;
		else
			this.dateOfDiscontinued = null;
	}

	public Company getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", dateOfIntroduced=" + dateOfIntroduced
				+ ", dateOfDiscontinued=" + dateOfDiscontinued + ", manufacturer=" + manufacturer + "]";
	}

	public static void initLogger() {
		logger = LoggerFactory.getLogger(Computer.class);
	}

	public boolean checkDates() {
		if ((dateOfIntroduced != null) && (dateOfDiscontinued != null))
			if (dateOfIntroduced.before(dateOfDiscontinued))
				return true;
		return false;
	}
}
