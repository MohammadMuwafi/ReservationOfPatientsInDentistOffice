package Controllers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DBConnection.Main;
import Models.Doctor;
import Models.Patient;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class InsertPatientController implements Initializable {
	private String path = "mouseClick.mp3";
	private Media media = new Media(new File(path).toURI().toString());
	private MediaPlayer mediaPlayer = new MediaPlayer(media);

	private ArrayList<Integer> pids;

	@FXML DatePicker pdateOfBirth;
	@FXML private TextField pname, pid;
	@FXML private ComboBox<String> pgender;
	@FXML private ComboBox<Doctor> demail;

	@FXML private void exit(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	@FXML public void getDate(ActionEvent event) {
		if (pdateOfBirth.getValue() == null) {
			pdateOfBirth.setValue(LocalDate.now());
		}
	}

	@FXML public void getData(ActionEvent event) throws ClassNotFoundException, SQLException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		Alert message = new Alert(Alert.AlertType.INFORMATION);
		message.setHeaderText("Error.");
		if (pname.getText().isEmpty() || pid.getText().isEmpty() || pdateOfBirth.getValue() == null || pgender.getValue() == null ||  demail.getValue() == null) {
			message.setContentText("Please fill all textfields.");
			message.showAndWait();
		} else if (checkString(pid.getText())) {
			message.setContentText("Please make sure to enter only numbers in {ID number} field.");
			message.showAndWait();
			pid.clear();
		} else {
			// DD-MM-YYYY
//			if (!isValidDate(pdateOfBirth.getValue().toString())) {
//				pdateOfBirth.setValue(LocalDate.now());
//			}
			
			pids = new Main().getPids();
			
			boolean isValidPid = true;
			for (Integer id : pids) {
				if (Integer.parseInt(pid.getText()) == id) {
					isValidPid = false;
					break;
				}
			}
			if (!isValidPid) {
				message.setContentText("This ID is already exist, please choose another one.");
				message.showAndWait();
				pid.clear();
			} else {								
				Patient p = new Patient(Integer.parseInt(pid.getText()), pname.getText(), pgender.getValue(), demail.getValue().getDemail(), "100", pdateOfBirth.getValue().toString());
				
				new Main().addPatientsToDB(p);
				message.setContentText("the new doctor is added to DB successfully.");
				message.showAndWait();
								
				pid.clear();
				pname.clear();
				pgender.setValue("Choose Gender");
				pdateOfBirth.setValue(LocalDate.of(1995, 1, 1));
				demail.setItems(FXCollections.observableArrayList(getDoctors()));
			}
		}
	}

	private ArrayList<Doctor> getDoctors() {
		try {
			return new Main().getTuplesOfDoctors();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean checkString(String oldString) {
		return !(oldString.replaceAll("[0-9]", "")).isEmpty();
	}

	public boolean isValidDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD-MM-YYYY");
		try {
			formatter.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pgender.setItems(FXCollections.observableArrayList("Female", "Male"));
		demail.setItems(FXCollections.observableArrayList(getDoctors()));
		pdateOfBirth.setValue(LocalDate.of(1995, 1, 1));
		pids = new ArrayList<Integer>();
	}
}