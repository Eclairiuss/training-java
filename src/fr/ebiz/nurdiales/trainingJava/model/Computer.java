package fr.ebiz.nurdiales.trainingJava.model;

import java.sql.Date;

public class Computer {
	private static final Date neverBefore = Date.valueOf("1970-01-01");

	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;

	public Computer(int id, String name, Date introduced, Date discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
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
		return introduced;
	}

	public void setDateOfIntroduced(Date introduced) {
		this.introduced = (introduced != null) ? (introduced.before(neverBefore) ? this.introduced : introduced) : null;
	}

	public Date getDateOfDiscontinued() {
		return discontinued;
	}

	public void setDateOfDiscontinued(Date discontinued) {
		this.discontinued = (discontinued != null)
				? (discontinued.before(neverBefore) ? this.discontinued : discontinued) : null;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
	}

	public boolean checkDates() {
		if (introduced != null && discontinued != null && introduced.after(discontinued))
			return false;
		return true;
	}
}
