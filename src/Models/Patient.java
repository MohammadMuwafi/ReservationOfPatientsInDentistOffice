package Models;

public class Patient {
	private int pid;
	private String pname, pgender, demail, pcost, pbirthDate;

	// new attribute.
	private String ptimeOfdayOfExamination;
	private String pdateOfExamination;
	private String pcolorOfMaterial;
	private String pTypeOfMaterial;
	private String pnumberOfteeth;

	
	public Patient() {
		this(1, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
	}
	
	public Patient(int pid, String pname, String pgender, String demail, String pcost, String pbirthDate) {
		this(pid, pname, pgender, demail, pcost, pbirthDate, "0", "0", "0", "0", "0");
	}
	
	public Patient(int pid, String pname, String pgender, String demail, String pcost, String pbirthDate, String ptimeOfdayOfExamination, String pdateOfExamination, String pcolorOfMaterial,
			String pTypeOfMaterial, String pnumberOfteeth) {
		super();
		this.pid = pid;
		this.pname = pname;
		this.pgender = pgender;
		this.demail = demail;
		this.pcost = pcost;
		this.pbirthDate = pbirthDate;
		this.ptimeOfdayOfExamination = ptimeOfdayOfExamination;
		this.pdateOfExamination = pdateOfExamination;
		this.pcolorOfMaterial = pcolorOfMaterial;
		this.pTypeOfMaterial = pTypeOfMaterial;
		this.pnumberOfteeth = pnumberOfteeth;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPgender() {
		return pgender;
	}

	public void setPgender(String pgender) {
		this.pgender = pgender;
	}

	public String getDemail() {
		return demail;
	}

	public void setDemail(String demail) {
		this.demail = demail;
	}

	public String getPcost() {
		return pcost;
	}

	public void setPcost(String pcost) {
		this.pcost = pcost;
	}

	public String getPbirthDate() {
		return pbirthDate;
	}

	public void setPbirthDate(String pbirthDate) {
		this.pbirthDate = pbirthDate;
	}

	public String getPtimeOfdayOfExamination() {
		return ptimeOfdayOfExamination;
	}

	public void setPtimeOfdayOfExamination(String ptimeOfdayOfExamination) {
		this.ptimeOfdayOfExamination = ptimeOfdayOfExamination;
	}

	public String getPdateOfExamination() {
		return pdateOfExamination;
	}

	public void setPdateOfExamination(String pdateOfExamination) {
		this.pdateOfExamination = pdateOfExamination;
	}

	public String getPcolorOfMaterial() {
		return pcolorOfMaterial;
	}

	public void setPcolorOfMaterial(String pcolorOfMaterial) {
		this.pcolorOfMaterial = pcolorOfMaterial;
	}

	public String getpTypeOfMaterial() {
		return pTypeOfMaterial;
	}

	public void setpTypeOfMaterial(String pTypeOfMaterial) {
		this.pTypeOfMaterial = pTypeOfMaterial;
	}

	public String getPnumberOfteeth() {
		return pnumberOfteeth;
	}

	public void setPnumberOfteeth(String pnumberOfteeth) {
		this.pnumberOfteeth = pnumberOfteeth;
	}
}