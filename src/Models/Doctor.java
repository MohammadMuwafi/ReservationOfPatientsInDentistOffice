package Models;


public class Doctor {
	private String dname, dnumber, clinic, demail;

	public Doctor(String dname, String dnumber, String clinic, String demail) {
		super();
		this.dname = dname;
		this.dnumber = dnumber;
		this.clinic = clinic;
		this.demail = demail;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getDnumber() {
		return dnumber;
	}

	public void setDnumber(String dnumber) {
		this.dnumber = dnumber;
	}

	public String getClinic() {
		return clinic;
	}

	public void setClinic(String clinic) {
		this.clinic = clinic;
	}

	public String getDemail() {
		return demail;
	}

	public void setDemail(String demail) {
		this.demail = demail;
	}

	@Override
	public String toString() {
		return "Doctor: { [Name:" + dname + "], [Phone:" + dnumber + "], [Clinic's Name:" + clinic + "], [Email:" + demail + "] }";
	}
}
