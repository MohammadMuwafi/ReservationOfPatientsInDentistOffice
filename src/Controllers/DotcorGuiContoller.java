package Controllers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBConnection.Main;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DotcorGuiContoller implements Initializable {
	private String path = "mouseClick.mp3";
	private Media media = new Media(new File(path).toURI().toString());
	private MediaPlayer mediaPlayer = new MediaPlayer(media); 
	
	@FXML private HBox putYourPageInsideMe;

	@FXML private void loadPage0(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/FXMLs/WelcomePage.fxml"));
		Parent root = loader.load();
		
		FadeTransition fade = new FadeTransition(Duration.seconds(0.5), putYourPageInsideMe);		
		
		fade.setFromValue(0.5);
		fade.setToValue(1);
		fade.play();
		
		putYourPageInsideMe.getChildren().clear();
		putYourPageInsideMe.getChildren().add(root);
	}
	
	@FXML private void loadPage1(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/FXMLs/DoctorPage.fxml"));
		Parent root = loader.load();
		
		FadeTransition fade = new FadeTransition(Duration.seconds(0.5), putYourPageInsideMe);
		fade.setFromValue(0.8);
		fade.setToValue(1);
		fade.play();
		
		putYourPageInsideMe.getChildren().clear();
		putYourPageInsideMe.getChildren().add(root);
	}
	
	@FXML private void loadPage2(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/FXMLs/PatientPage.fxml"));
		Parent root = loader.load();
		putYourPageInsideMe.getChildren().clear();
		FadeTransition fade = new FadeTransition(Duration.seconds(1), putYourPageInsideMe);
		fade.setFromValue(0.5);
		fade.setToValue(1);
		fade.play();		
		putYourPageInsideMe.getChildren().add(root);
		
	}

	@FXML private void loadPage4(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/FXMLs/PieChart.fxml"));
		Parent root = loader.load();
		putYourPageInsideMe.getChildren().clear();
		FadeTransition fade = new FadeTransition(Duration.seconds(1), putYourPageInsideMe);
		fade.setFromValue(0.5);
		fade.setToValue(1);
		fade.play();		
		putYourPageInsideMe.getChildren().add(root);
		
	}	
	
	@FXML private void exit(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();		
	}
	
	@FXML private void minimize(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}	
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("Y");
		try {
			boolean test = new Main().isDataBaseExist();
			if (!test) {
				Alert message = new Alert(Alert.AlertType.ERROR);
				message.setHeaderText("Error.");
				message.setContentText("Your database does not exist!");
				message.showAndWait();	
				System.exit(1);
			}
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/FXMLs/WelcomePage.fxml"));
			Parent root;
			root = loader.load();
			
			FadeTransition fade = new FadeTransition(Duration.seconds(0.5), putYourPageInsideMe);
			fade.setFromValue(0.5);
			fade.setToValue(1);
			fade.play();
			
			putYourPageInsideMe.getChildren().clear();
			putYourPageInsideMe.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}