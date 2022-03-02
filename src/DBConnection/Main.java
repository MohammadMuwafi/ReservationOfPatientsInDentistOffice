package DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import Models.Doctor;
import Models.Patient;

public class Main {
	private static Connection connection;
	private static String dataBaseUsername = "root";
	private static String dataBasePassword = "1234";
	private static String dataBaseName = "finalProject";
	private static String URL = "127.0.0.1";
	private static String port = "3306";

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		System.out.println(connection.hashCode());
	}

	// return list of table of doctors.
	public ArrayList<Doctor> getTuplesOfDoctors() throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();

		ArrayList<Doctor> ar = new ArrayList<>();
		String SQL = "select * from doctor";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		while (rs.next()) {
			ar.add(new Doctor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}

		rs.close();
		statement.close();
		connection.close();

		return ar;
	}

	// return list of table of patients.
	public ArrayList<Patient> getTuplesOfPatient() throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();

		ArrayList<Patient> ar = new ArrayList<>();
		String SQL = "select * from patient";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		while (rs.next()) {
			Patient p = new Patient();
			p.setPid(Integer.parseInt(rs.getString(1)));
			p.setPname(rs.getString(2));
			p.setPgender(rs.getString(3));
			p.setDemail(rs.getString(4));
			p.setPcost(rs.getString(5));
			p.setPbirthDate(rs.getString(6));
			p.setPdateOfExamination(rs.getString(7));
			p.setPtimeOfdayOfExamination(rs.getString(8));
			p.setPnumberOfteeth(rs.getString(9));
			p.setPcolorOfMaterial(rs.getString(10));
			p.setpTypeOfMaterial(rs.getString(11));
			ar.add(p);
		}

		rs.close();
		statement.close();
		connection.close();

		return ar;
	}

	// update the name of patient
	public void updatePatient(int pid, String cost, String date, String time, String cnt, String color, String type) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		StringBuilder SQL = new StringBuilder("");
		SQL.append("update patient set pcost = '" + cost + "'"); 
		SQL.append(", pdateOfExamination = '" + date + "'"); 
		SQL.append(", ptimeOfdayOfExamination = '" + time + "'"); 
		SQL.append(", pnumberOfteeth = '" + cnt + "'"); 
		SQL.append(", pcolorOfMaterial = '" + color + "'"); 
		SQL.append(", pTypeOfMaterial = '" + type + "'"); 
		SQL.append("where pid = " + pid + ";");
		Statement statement = connection.createStatement();
		statement.execute(SQL.toString());
		statement.close();
		connection.close();
	}
	
	public void updatePass(String email, String pass) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		StringBuilder SQL = new StringBuilder("");
		SQL.append("update newUser set upassword = '" + pass + "' where uemail = '" + email + "'"); 
		Statement statement = connection.createStatement();
		statement.execute(SQL.toString());
		statement.close();
		connection.close();		
	}
	
	public String getDemailForPatient(String pid) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();

		String SQL = "select p.demail from patient p, doctor d where d.demail = p.demail and p.pid = " + Integer.parseInt(pid) + ";";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);
		
		StringBuilder demail = new StringBuilder("");
		while (rs.next()) {
			demail.append(rs.getString(1));
		}
			
		rs.close();
		statement.close();
		connection.close();

		return demail.toString();
	}
	
	public ArrayList<String>[] getDateAndTimeOfExaminations(String demail) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();

		String SQL = "select p.pdateOfExamination,  p.ptimeOfdayOfExamination from doctor d, patient p where p.demail = d.demail  and d.demail = " + "\"" + demail + "\";";

		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);
		
		@SuppressWarnings("unchecked")
		ArrayList<String>[] ar = new ArrayList[2];
		ar[0] = new ArrayList<>();
		ar[1] = new ArrayList<>();

		while (rs.next()) {
			ar[0].add(rs.getString(1));
			ar[1].add(rs.getString(2));
		}
	
		rs.close();
		statement.close();
		connection.close();

		return ar;
	}

	
	// method to get all patient of a doctor
	public ArrayList<Patient> getPatientWithSpeceficDemail(String demail) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();

		ArrayList<Patient> ar = new ArrayList<>();
		String SQL = "select * from patient p where p.demail = '" + demail + "';";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		while (rs.next()) {
			Patient p = new Patient();
			p.setPid(Integer.parseInt(rs.getString(1)));
			p.setPname(rs.getString(2));
			p.setPgender(rs.getString(3));
			p.setDemail(rs.getString(4));
			p.setPcost(rs.getString(5));
			p.setPbirthDate(rs.getString(6));
			p.setPdateOfExamination(rs.getString(7));
			p.setPtimeOfdayOfExamination(rs.getString(8));
			p.setPnumberOfteeth(rs.getString(9));
			p.setPcolorOfMaterial(rs.getString(10));
			p.setpTypeOfMaterial(rs.getString(11));
			ar.add(p);
		}

		rs.close();
		statement.close();
		connection.close();

		return ar;
	}

	// delete a tuple from patientTable.
	public void deletePatient(Patient patient) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();

		String SQL = "delete from patient where pid = " + patient.getPid() + ";";
		Statement statement = connection.createStatement();
		statement.execute(SQL);
		statement.close();
		connection.close();
	}

	// update the name of patient
	public void updatePname(int pid, String pname) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		String SQL = "update patient set pname = '" + pname + "' where pid = " + pid + ";";
		Statement statement = connection.createStatement();
		statement.execute(SQL);
		statement.close();
		connection.close();
	}
	
	// update the name of patient
	public void updatePcost(int pid, String pcost) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		String SQL = "update patient set pcost = '" + pcost + "' where pid = " + pid + ";";
		Statement statement = connection.createStatement();
		statement.execute(SQL);
		statement.close();
		connection.close();
	}
	
	// update the gender of patient
	public void updateGender(int pid, String gender) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();

		StringBuilder st;
		if (gender.toLowerCase().charAt(0) == 'f') {
			st = new StringBuilder("Female");
		} else {
			st = new StringBuilder("Male");
		}
		String SQL = "update patient set pgender =  '" + st.toString() + "'  where pid = " + pid + ";";
		Statement statement = connection.createStatement();
		statement.execute(SQL);
		statement.close();
		connection.close();
	}

	// update the name of Doctor
	public void updateDname(String demail, String dname) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		String SQL = "update doctor set dname = '" + dname + "' where demail = '" + demail + "';";
		Statement statement = connection.createStatement();
		statement.execute(SQL);
		statement.close();
		connection.close();
	}

	// update the phone number of Doctor
	public void updateDnumber(String demail, String dnumber) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		String SQL = "update doctor set dnumber = '" + dnumber + "' where demail = '" + demail + "';";
		Statement statement = connection.createStatement();
		statement.execute(SQL);
		statement.close();
		connection.close();
	}

	// update the name of clinic of Doctor
	public void updateClinic(String demail, String clinic) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		String SQL = "update doctor set clinic = '" + clinic + "' where demail = '" + demail + "';";
		Statement statement = connection.createStatement();
		statement.execute(SQL);
		statement.close();
		connection.close();
	}

	// delete doctor from Doctor Table
	public void deleteDoctor(Doctor doctor) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		String SQL = "delete from doctor where demail = '" + doctor.getDemail() + "';";
		Statement statement = connection.createStatement();
		statement.execute(SQL);
		statement.close();
		connection.close();
	}

	// check if the doctor has patients.
	public int isDoctorHasPatients(String demail) throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();

		String SQL = "select distinct p.pid from doctor d, patient p where p.demail = '" + demail + "';";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		int size = 0;
		while (rs.next()) {
			size++;
		}

		rs.close();
		statement.close();
		connection.close();

		return size;
	}

	// return a list of pids of all patients in DB
	public ArrayList<Integer> getPids() throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		String SQL = "select pid from patient;";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		ArrayList<Integer> ar = new ArrayList<>();
		while (rs.next()) {
			ar.add(Integer.parseInt(rs.getString(1)));
		}

		rs.close();
		statement.close();
		connection.close();

		return ar;
	}

	// return a list of emalis of all Doctors in DB
	public ArrayList<String> getDemails() throws ClassNotFoundException, SQLException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		String SQL = "select demail from doctor";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		ArrayList<String> ar = new ArrayList<>();
		while (rs.next()) {
			ar.add(rs.getString(1));
		}

		rs.close();
		statement.close();
		connection.close();

		return ar;
	}

	// method to add a new Patient in database.
	public void addPatientsToDB(Patient patient) throws SQLException, ClassNotFoundException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		StringBuilder SQL = new StringBuilder("");
		SQL.append("insert into patient(pid, pname, pgender, demail, pcost, pbirthdate) values(");
		SQL.append(patient.getPid() + ", ");
		SQL.append("\"" + patient.getPname() + "\"" + ", ");
		SQL.append("\"" + patient.getPgender() + "\"" + ", ");
		SQL.append("\"" + patient.getDemail() + "\"" + ", ");
		SQL.append("\"" + patient.getPcost() + "\"" + ", ");
		SQL.append("\"" + patient.getPbirthDate() + "\"" + ");");

		Statement statement = connection.createStatement();
		statement.execute(SQL.toString());
		statement.close();
		connection.close();
	}

	// method to add a new Doctor in database.
	public void addDoctorsToDB(Doctor doctor) throws SQLException, ClassNotFoundException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();

		StringBuilder SQL = new StringBuilder("");
		SQL.append("insert into doctor values(");
		SQL.append("\"" + doctor.getDname() + "\"" + ", ");
		SQL.append("\"" + doctor.getDnumber() + "\"" + ", ");
		SQL.append("\"" + doctor.getClinic() + "\"" + ", ");
		SQL.append("\"" + doctor.getDemail() + "\"" + ");");

		Statement statement = connection.createStatement();
		statement.execute(SQL.toString());
		statement.close();
		connection.close();
	}

	public String getNumberOfPatietns(String from, String to) throws SQLException, ClassNotFoundException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		String SQL = "select count(*) from patient p where p.pdateOfExamination >= " + "\"" + LocalDate.parse(from) + "\" and p.pdateOfExamination <= " + "\"" + LocalDate.parse(to) + "\";";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		StringBuilder cnt = new StringBuilder("");
		while (rs.next()) {
			cnt.append(rs.getString(1));
		}

		rs.close();
		statement.close();
		connection.close();

		return cnt.toString();
	}

	public ArrayList<String>[] getPatientsForEachDoctors(String from, String to) throws SQLException, ClassNotFoundException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		StringBuilder SQL = new StringBuilder("");
		SQL.append("select count(*), d.dname from doctor d, patient p where p.demail = d.demail");
		SQL.append(" and p.pdateOfExamination >= \"" + LocalDate.parse(from) + "\"");
		SQL.append(" and p.pdateOfExamination <= \"" + LocalDate.parse(to) + "\" group by (d.demail);");

		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL.toString());

		@SuppressWarnings("unchecked")
		ArrayList<String>[] ar = new ArrayList[2];
		ar[0] = new ArrayList<>();
		ar[1] = new ArrayList<>();

		while (rs.next()) {
			ar[0].add(rs.getString(1));
			ar[1].add(rs.getString(2));
		}

		rs.close();
		statement.close();
		connection.close();

		return ar;
	}

	public boolean isDataBaseExist() throws SQLException, ClassNotFoundException {
		connection = CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
		String SQL = "show databases;";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		boolean test = false;
		while (rs.next()) {
			if (rs.getString(1).toLowerCase().equals(dataBaseName.toLowerCase())) {
				test = true;
				break;
			}
		}

		rs.close();
		statement.close();
		connection.close();

		return test;
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		return 	CreateConnectionWithDatabase.getInstance(URL, port, dataBaseName, dataBaseUsername, dataBasePassword).makeConnections();
	}

	public static void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}
}