package fr.ebiz.nurdiales.trainingJava.model;

import java.sql.Date;

public class Computer {
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
		this.dateOfIntroduced = dateOfIntroduced;
	}

	public Date getDateOfDiscontinued() {
		return dateOfDiscontinued;
	}

	public void setDateOfDiscontinued(Date dateOfDiscontinued) {
		this.dateOfDiscontinued = dateOfDiscontinued;
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
}
