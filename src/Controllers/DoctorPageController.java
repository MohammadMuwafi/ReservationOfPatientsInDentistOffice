package Controllers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DBConnection.Main;
import Models.Doctor;
import Models.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DoctorPageController implements Initializable {
	private String path = "mouseClick.mp3";
	private Media media = new Media(new File(path).toURI().toString());
	private MediaPlayer mediaPlayer = new MediaPlayer(media); 

	private int idx = -1;
	private ObservableList<Doctor> list;
	private double xOffset = 0, yOffset = 0;

	@FXML TextField searchFeild;
	@FXML public Button addButton;
	@FXML private TableView<Doctor> table;
	@FXML private TableColumn<Doctor, String> dname, dnumber, clinic, demail;

	@FXML private void refreshTable(ActionEvent event) throws ClassNotFoundException, SQLException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		searchFeild.clear();
		list = FXCollections.observableArrayList(new Main().getTuplesOfDoctors());
		table.getItems().clear();
		table.setItems(list);
		table.refresh();
	}

	@FXML private void addRowToTable(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/FXMLs/InsertDoctor.fxml"));
		Parent root = loader.load();
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
		stage.show();
	}

	@FXML private void showPatientsDoctor(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		if (idx != -1) {
			ArrayList<Patient> ar = new Main().getPatientWithSpeceficDemail(table.getItems().get(idx).getDemail());

			if (ar.size() == 0) {
				Alert message = new Alert(Alert.AlertType.WARNING);
				message.setHeaderText("Note.");
				message.setContentText("Empty List");
				message.showAndWait();

			} else {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/FXMLs/PatientsOfDoctor.fxml"));
				Parent root = loader.load();

				PatientsOfDoctorController controller = loader.getController();
				controller.showInformation(table.getItems().get(idx).getDemail());
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
//		                Platform.runLater(() -> stage.close());
		        });

				stage.setScene(new Scene(root));
				stage.showAndWait();
			}
			idx = -1;

		} else {
			Alert message = new Alert(Alert.AlertType.WARNING);
			message.setHeaderText("Note.");
			message.setContentText("Please mark a tuple from table");
			message.showAndWait();
		}
	}

	@FXML private void deleteRowFromTable(ActionEvent event) throws ClassNotFoundException, SQLException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		if (idx > -1) {
			int size = new Main().isDoctorHasPatients(table.getItems().get(idx).getDemail());

			if (size != 0) {
				Alert message = new Alert(Alert.AlertType.ERROR);
				message.setHeaderText("Error.");
				message.setContentText("Cannot delete this Doctor, since There is/are " + size + " patient/s enrolled with him/her");
				message.showAndWait();
				idx = -1;
			} else {
				new Main().deleteDoctor(table.getItems().get(idx));
				table.getItems().remove(idx);
				table.refresh();
				idx = -1;
			}
		}
	}

	@FXML private void showSelected() {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		idx = table.getSelectionModel().getSelectedIndex();
	}

	public void fillTable() throws ClassNotFoundException, SQLException {
		// add the data to the table.
		table.setItems(list);

		// in normal, a table will be read only mode.
		table.setEditable(true);

		// setCellValueFactory is the responsible of rendering the table.
		dname.setCellValueFactory(new PropertyValueFactory<Doctor, String>("dname"));
		dnumber.setCellValueFactory(new PropertyValueFactory<Doctor, String>("dnumber"));
		clinic.setCellValueFactory(new PropertyValueFactory<Doctor, String>("clinic"));
		demail.setCellValueFactory(new PropertyValueFactory<Doctor, String>("demail"));

		dname.setCellFactory(TextFieldTableCell.forTableColumn());
		dnumber.setCellFactory(TextFieldTableCell.forTableColumn());
		clinic.setCellFactory(TextFieldTableCell.forTableColumn());

		dname.setOnEditCommit(e -> {
			Doctor doctor = e.getRowValue();
			String temp = e.getNewValue().replaceAll("[0-9]", "");
			if (e.getNewValue().length() != temp.length()) {
				returnName(doctor.getDname());
			} else {
				try { // update in database
					doctor.setDname(e.getNewValue());
					new Main().updateDname(e.getRowValue().getDemail(), e.getRowValue().getDname());
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		dnumber.setOnEditCommit(e -> {
			Doctor doctor = e.getRowValue();
			String temp = e.getNewValue().replaceAll("[0-9]", "");
			if (!temp.isEmpty() || (temp.isEmpty() && e.getNewValue().length() != 10)) {
				returnNumber(doctor.getDnumber());
			} else {
				try { // update in database
					doctor.setDnumber(e.getNewValue());
					new Main().updateDnumber(e.getRowValue().getDemail(), e.getRowValue().getDnumber());
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}	
			}
		});
		clinic.setOnEditCommit(e -> {
			Doctor doctor = e.getRowValue();
			String temp = e.getNewValue().replaceAll("[0-9]", "");
			if (temp.isEmpty()) {
				returnClinic(doctor.getClinic());
			} else {
				try { // update in database
					doctor.setClinic(e.getNewValue());
					new Main().updateClinic(e.getRowValue().getDemail(), e.getRowValue().getClinic());
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}	
			}
		});
	}
	
	public void returnName(String name) {
		if (idx > -1) {
			list.get(idx).setDname(name);
			table.refresh();
		}
	}

	public void returnNumber(String number) {
		if (idx > -1) {
			list.get(idx).setDnumber(number);
			table.refresh();
		}
	}
	
	public void returnClinic(String clinic) {
		if (idx > -1) {
			list.get(idx).setClinic(clinic);
			table.refresh();
		}
	}
	
	
	public void search() {
		searchFeild.textProperty().addListener(e -> {
			String value = searchFeild.textProperty().get().toLowerCase();
			if (value.isEmpty()) {
				table.setItems(list);
				return;
			} 
			ObservableList<Doctor> result = FXCollections.observableArrayList();
			for (int i = 0; i < table.getItems().size(); i++) {
				Doctor d = table.getItems().get(i);
				for (int j = 0; j < 4; j++) {
					if (d.getDnumber().toLowerCase().indexOf(value) != -1) {
						result.add(d);
						break;
					} else if (d.getDname().toLowerCase().indexOf(value) != -1) {
						result.add(d);
						break;						
					} else if (d.getDemail().toLowerCase().indexOf(value) != -1) {
						result.add(d);
						break;					
					} else if (d.getClinic().toLowerCase().indexOf(value) != -1) {
						result.add(d);
						break;					
					}
				}
			}
			table.setItems(result);
		});		
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			list = FXCollections.observableArrayList(new Main().getTuplesOfDoctors());
			fillTable();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}
}