package Controllers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DBConnection.Main;
import Models.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class InsertDoctorController implements Initializable {
	private String path = "mouseClick.mp3";
	private Media media = new Media(new File(path).toURI().toString());
	private MediaPlayer mediaPlayer = new MediaPlayer(media);

	private ArrayList<String> emaliList;

	@FXML
	private TextField dname, dnumber, demail, clinic;

	@FXML
	private void exit(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();

	}

	@FXML
	public void getData(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		mediaPlayer.stop();
		mediaPlayer.play();

		Alert message = new Alert(Alert.AlertType.INFORMATION);
		message.setHeaderText("Error.");
		if (dname.getText().isEmpty() || dnumber.getText().isEmpty() || demail.getText().isEmpty() || clinic.getText().isEmpty()) {
			message.setContentText("Please fill all textfields.");
			message.showAndWait();
		} else if (checkString(dnumber.getText()) || dnumber.getText().length() != 10) {
			message.setContentText("Please enter 10 numbers in phone field.");
			message.showAndWait();
			dnumber.clear();
		} else {

			emaliList = new Main().getDemails();
			boolean isValidEmail = true;
			for (String email : emaliList) {
				if (demail.getText().equals(email)) {
					isValidEmail = false;
					break;
				}
			}
			if (!isValidEmail) {
				message.setContentText("This email is already exist, please choose another one.");
				message.showAndWait();
				demail.clear();
			} else {
				String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
				boolean result = demail.getText().matches(regex);
				if (result) {
					Doctor d = new Doctor(dname.getText(), dnumber.getText(), clinic.getText(), demail.getText());
					new Main().addDoctorsToDB(d);
					message.setContentText("the new doctor is added to DB successfully.");
					message.showAndWait();
					dname.clear();
					dnumber.clear();
					clinic.clear();
					demail.clear();
				} else {
					demail.clear();
				}
			}
		}
	}

	public boolean checkString(String oldString) {
		return !(oldString.replaceAll("[0-9]", "")).isEmpty();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		emaliList = new ArrayList<>();
	}
}