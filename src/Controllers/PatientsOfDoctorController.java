package Controllers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DBConnection.Main;
import Models.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class PatientsOfDoctorController implements Initializable {
	private String email = "";
	
	private String path = "mouseClick.mp3";
	private Media media = new Media(new File(path).toURI().toString());
	private MediaPlayer mediaPlayer = new MediaPlayer(media); 

	private ArrayList<Patient> patientsList;

	@FXML private GridPane gridPane;
	@FXML private ScrollPane scrollPane;

	@FXML private void exit(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();

	}
	
	@FXML public void reload(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		patientsList = new Main().getPatientWithSpeceficDemail(email);

		int rows = 1, cols = 1;
		if (patientsList.isEmpty() != true) {
			for (Patient p : patientsList) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../Views/FXMLs/PatientInformation.fxml"));
				AnchorPane pane = loader.load();
				PatientInformationController contoller = loader.getController();

				contoller.getPatient(p);
				if (cols == 3) {
					cols = 1;
					rows++;
				}
				gridPane.add(pane, cols++, rows);
				GridPane.setMargin(pane, new Insets(10));
			}
		}

	}

	public void showInformation(String demail) throws IOException, ClassNotFoundException, SQLException {
		email = demail;
		reload(null);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		patientsList = new ArrayList<>();
	}
}