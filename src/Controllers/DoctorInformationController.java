package Controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DBConnection.Main;
import Models.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DoctorInformationController implements Initializable {
	@FXML private TextField dname, dnumber, demail, clinic;

	public void getAndShow(String email) throws ClassNotFoundException, SQLException {
		String SQL = "select * from doctor d where d.demail = '" + email + "';";
		new Main();
		Connection connection = Main.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		ArrayList<Doctor> ar = new ArrayList<>();
		while (rs.next()) {
			ar.add(new Doctor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}

		dname.setText(ar.get(0).getDname());
		dnumber.setText(ar.get(0).getDnumber());
		demail.setText(ar.get(0).getDemail());
		clinic.setText(ar.get(0).getClinic());
	}

	@FXML private void exit(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}