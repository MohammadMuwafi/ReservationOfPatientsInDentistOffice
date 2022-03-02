package Controllers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBConnection.Main;
import Models.Patient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PatientPageController implements Initializable {
	private String path = "mouseClick.mp3";
	private Media media = new Media(new File(path).toURI().toString());
	private MediaPlayer mediaPlayer = new MediaPlayer(media); 

	private int idx = -1;
	private ObservableList<Patient> list;
	private double xOffset = 0, yOffset = 0;

	@FXML TextField searchFeild;
	@FXML private TableView<Patient> table;
	@FXML private TableColumn<Patient, Integer> pid;
	@FXML private TableColumn<Patient, String> pname, pgender, pcost, pbirthDate;

	@FXML private void showDoctorsPatient(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		if (idx != -1) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/FXMLs/DoctorInformation.fxml"));
			Parent root = loader.load();

			DoctorInformationController controller = loader.getController();
			controller.getAndShow(table.getItems().get(idx).getDemail());
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
	            if(!stage.isFocused())
	                Platform.runLater(() -> stage.close());
	        });	
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
			idx = -1;
		} else {
			Alert message = new Alert(Alert.AlertType.WARNING);
			message.setHeaderText("Note.");
			message.setContentText("Please mark a tuple from table");
			message.showAndWait();
		}
	}

	@FXML private void refreshTable(ActionEvent event) throws ClassNotFoundException, SQLException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		searchFeild.clear();
		list = FXCollections.observableArrayList(new Main().getTuplesOfPatient());
		table.getItems().clear();
		table.setItems(list);
		table.refresh();
	}

	@FXML private void addRowToTable(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/FXMLs/InsertPatient.fxml"));
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
            if(!stage.isFocused())
                Platform.runLater(() -> stage.close());
        });

		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void fillTable() throws ClassNotFoundException, SQLException {
		// add the data to the table.
		list = FXCollections.observableArrayList(new Main().getTuplesOfPatient());
		table.setItems(list);

		// in normal, a table will be read only mode.
		table.setEditable(true);

		// setCellValueFactory is the responsible of rendering the table.
		pid.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("pid"));
		pname.setCellValueFactory(new PropertyValueFactory<Patient, String>("pname"));
		pgender.setCellValueFactory(new PropertyValueFactory<Patient, String>("pgender"));
		pcost.setCellValueFactory(new PropertyValueFactory<Patient, String>("pcost"));
		pbirthDate.setCellValueFactory(new PropertyValueFactory<Patient, String>("pbirthDate"));

		pname.setCellFactory(TextFieldTableCell.forTableColumn());
		pgender.setCellFactory(TextFieldTableCell.forTableColumn());
		pcost.setCellFactory(TextFieldTableCell.forTableColumn());
//		pbirthDate.setCellFactory(TextFieldTableCell.forTableColumn());

		pname.setOnEditCommit(e -> {
			Patient patient = e.getRowValue();
			String temp = e.getNewValue().replaceAll("[0-9]", "");
			if (e.getNewValue().length() != temp.length()) {
				returnName(patient.getPname());
			} else {
				try { // update in database
					patient.setPname(e.getNewValue());
					new Main().updatePname(e.getRowValue().getPid(), e.getRowValue().getPname());
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}				
			}
		});

		pcost.setOnEditCommit(e -> {
			Patient patient = e.getRowValue();
			if (validCost(e.getNewValue())) {
				try { // update cost in database
					patient.setPcost(e.getNewValue());
					new Main().updatePcost(e.getRowValue().getPid(), e.getRowValue().getPcost());
				} catch (ClassNotFoundException | SQLException | NumberFormatException e1) {
					e1.printStackTrace();
				}
			} else {
				returnCost(patient.getPcost());
			}
		});

		pgender.setOnEditCommit(event -> {
			Patient patient = event.getRowValue();
			if (!event.getNewValue().toLowerCase().equals("female") && !event.getNewValue().toLowerCase().equals("male")) {
				returnGender(patient.getPgender());
			} else {
				try {
					patient.setPgender(event.getNewValue());
					new Main().updateGender(patient.getPid(), patient.getPgender());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	// this method reset the gender to its old value.
	public void returnGender(String gender) {
		if (idx > -1) {
			list.get(idx).setPgender(gender);
			table.refresh();
		}
	}
	
	public void returnCost(String cost) {
		if (idx > -1) {
			list.get(idx).setPcost(cost);
			table.refresh();
		}
	}
	
	public void returnName(String name) {
		if (idx > -1) {
			list.get(idx).setPname(name);
			table.refresh();
		}
	}
	
	public boolean validCost(String oldString) {
		return (oldString.replaceAll("[0-9]", "")).isEmpty();
	}

	@FXML private void showSelected() {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		idx = table.getSelectionModel().getSelectedIndex();
	}

	@FXML private void deleteTuple(ActionEvent event) throws ClassNotFoundException, SQLException {
		mediaPlayer.stop();
		mediaPlayer.play();
		
		if (idx > -1) {
			new Main().deletePatient(table.getItems().get(idx));
			table.getItems().remove(idx);
			table.refresh();
			idx = -1;
		}
	}
	
	public void search() {
		searchFeild.textProperty().addListener(e -> {
			String value = searchFeild.textProperty().get().toLowerCase();
			if (value.isEmpty()) {
				table.setItems(list);
				return;
			} 
			ObservableList<Patient> result = FXCollections.observableArrayList();
			for (int i = 0; i < table.getItems().size(); i++) {
				Patient p = table.getItems().get(i);
				for (int j = 0; j < 4; j++) {
					if (p.getPname().toLowerCase().indexOf(value) != -1) {
						result.add(p);
						break;
					} else if (p.getPbirthDate().toLowerCase().indexOf(value) != -1) {
						result.add(p);
						break;
					} else if (p.getPcost().toLowerCase().indexOf(value) != -1) {
						result.add(p);
						break;
					} else if (p.getPgender().toLowerCase().indexOf(value) != -1) {
						result.add(p);
						break;
					} else if ((p.getPid() + "").toLowerCase().indexOf(value) != -1) {
						result.add(p);
						break;
					} else if (p.getDemail().toLowerCase().indexOf(value) != -1) {
						result.add(p);
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
			fillTable();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}