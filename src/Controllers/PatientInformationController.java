package Controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Models.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PatientInformationController implements Initializable {
	private double xOffset = 0, yOffset = 0;

	@FXML private TextField pname, pid, pgender, pdateOfBirth, pcost, pnumberOfTeeth, pdateOfExamination;
    @FXML private Button makeOrder;
	
	@FXML public void loadMedicalExamination(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/FXMLs/MedicalExamination.fxml"));
		Parent root = loader.load();

		MedicalExaminationController controller = loader.getController();
		controller.getData(pid.getText(), pdateOfBirth.getText());
		
		Stage stage = new Stage();

		stage.initStyle(StageStyle.TRANSPARENT);
		root.setOnMousePressed(e -> {
			xOffset = e.getSceneX();
			yOffset = e.getSceneY();
		});

		root.setOnMouseDragged(e -> {
			stage.setX(e.getScreenX() - xOffset);
			stage.setY(e.getScreenY() - yOffset);
		});

        stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
            if(!stage.isFocused());
//                Platform.runLater(() -> stage.close());
        });
		
		stage.setScene(new Scene(root));
		stage.showAndWait();		
	}
	
	
	public void getPatient(Patient patient) {
		pname.setText(patient.getPname());
		pid.setText(patient.getPid() + "");
		pgender.setText(patient.getPgender());
		pdateOfBirth.setText(patient.getPbirthDate());
		pcost.setText(patient.getPcost());
		pnumberOfTeeth.setText(patient.getPnumberOfteeth());
		
		int start = 0;
		if ((patient.getPtimeOfdayOfExamination().charAt(0)) - '0' > 0) {
			start = Integer.parseInt(patient.getPtimeOfdayOfExamination());
		}
		if (start > 0 && start < 12) {
			pdateOfExamination.setText(patient.getPdateOfExamination() + " - " + start + " to" + (start + 1) + " am");				
			makeOrder.setStyle("-fx-background-color: Red");
			makeOrder.setText("The examination is made");
			makeOrder.setDisable(true);
		} else if (start > 0 && start > 11) {
			pdateOfExamination.setText(patient.getPdateOfExamination() + " - " + start + " to" + (start + 1));				
			makeOrder.setStyle("-fx-background-color: Red");
			makeOrder.setText("The examination is made");
			makeOrder.setDisable(true);
		} else {
			pcost.setText("Unknown");		
			pnumberOfTeeth.setText("Unknown");
			pdateOfExamination.setText("Unknown");			
			makeOrder.setDisable(false);
		}
		
		pname.setEditable(false);
		pid.setEditable(false);
		pgender.setEditable(false);
		pdateOfBirth.setEditable(false);
		pcost.setEditable(false);
		pnumberOfTeeth.setEditable(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
}