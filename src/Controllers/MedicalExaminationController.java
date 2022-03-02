package Controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DBConnection.Main;
import Models.Patient;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MedicalExaminationController implements Initializable {
	private String date = "", pid, whichButton = "", getSelectedColor = "", getSelectedType = "", getSelectedDay = "";
	private boolean testButton = false;

	@FXML private DatePicker day;
	@FXML private TableView<Patient> table;
	@FXML private TableColumn<Patient, String> timeInterval, reservation;
	@FXML private ComboBox<String> materialColor, materialType;
	@FXML private CheckBox UR1, UR2, UR3, UR4, UR5, UR6, UR7, UR8, UL1, UL2, UL3, UL4, UL5, UL6, UL7, UL8;
	@FXML private CheckBox LR1, LR2, LR3, LR4, LR5, LR6, LR7, LR8, LL1, LL2, LL3, LL4, LL5, LL6, LL7, LL8;
	@FXML private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
	private Button[] copyButtons = new Button[10];
	
	
	
    @FXML void getColor(ActionEvent event) {
    	getSelectedColor = materialColor.getValue();	
    }

    @FXML void getType(ActionEvent event) {
    	getSelectedType = materialType.getValue();	
    }	
	
	public int test(CheckBox cb) {
		if (cb.isSelected()) {
			return 1;
		}
		return 0;
	}

	public int countTeeth() {
		int cnt = 0;
		cnt += test(UR1) + test(UR2) + test(UR3) + test(UR4) + test(UR5) + test(UR6) + test(UR7) + test(UR8);
		cnt += test(UL1) + test(UL2) + test(UL3) + test(UL4) + test(UL5) + test(UL6) + test(UL7) + test(UL8);
		cnt += test(LR1) + test(LR2) + test(LR3) + test(LR4) + test(LR5) + test(LR6) + test(LR7) + test(LR8);
		cnt += test(LL1) + test(LL2) + test(LL3) + test(LL4) + test(LL5) + test(LL6) + test(LL7) + test(LL8);
		return cnt;
	}
		
	@FXML
	public void clickButton(ActionEvent event) {
		testButton = true;
		for (int i = 0; i < 10; i++) {
			if (copyButtons[i].isFocused()) {
				copyButtons[i].setDisable(true);
				whichButton = i + 7 + "";
				for (int j = 0; j < 10; j++) {
					if (i != j && copyButtons[j].isDisabled() && !copyButtons[j].getText().equals("Reserved")) {
						copyButtons[j].setDisable(false);
					}
				}
				break;
			}
		}
	}
	
	@FXML public void reserve(ActionEvent event) throws NumberFormatException, ClassNotFoundException, SQLException {
		int cnt = countTeeth();
		boolean testDay = getSelectedDay.isEmpty();
		boolean testMaterialColor = getSelectedColor.isEmpty();
		boolean testMaterialType = getSelectedType.isEmpty();
		boolean testButtons = !testButton;
		
		if (cnt == 0 || testDay || testMaterialColor || testMaterialType || testButtons) {
			Alert message = new Alert(Alert.AlertType.ERROR);
			message.setHeaderText("Error.");
			message.setContentText("Please fill all required data.");
			message.showAndWait();
			
			resetAllLayout();
			editDays();
			
			return;
		}
				
		int price = 1;
		if (materialType.getItems().equals("Temporary - 5$ for 1 tooth")) {
			price = cnt * 5;
		} else if (materialType.getItems().equals("Metal - 10$ for 1 tooth")) {
			price = cnt * 10;						
		} else {
			price = cnt * 15;									
		}
		
		int idx = 0;
		for (int i = 0; i < pid.length(); i++) {
			if (Character.isDigit(pid.charAt(i))) {
				idx = i;
				break;
			}
		}
		String subPid = pid.substring(idx); // remove non numeric letters.
		
		new Main().updatePatient(Integer.parseInt(subPid), price + "", day.getValue().toString(), whichButton, cnt + "", materialColor.getValue(), materialType.getValue());

		day.setValue(LocalDate.now());
		resetAllLayout();
		editDays();
		Alert message = new Alert(Alert.AlertType.INFORMATION);
		message.setHeaderText("Done");
		message.setContentText("The medical examination has been booked successfully");
		message.showAndWait();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	public void getData(String id, String pdate) {
		date = pdate;
		pid = id;
		try {
			editDays();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		dump();
	}
	
	public void dump() {
		int idx = 0;
		for (int i = 0; i < date.length(); i++) {
			if (Character.isDigit(date.charAt(i))) {
				idx = i;
				break;
			}
		}
		String newDate = date.substring(idx);
		String[] result = newDate.split("-");

		Callback<DatePicker, DateCell> callB = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker param) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty); 
						LocalDate today = LocalDate.of(Integer.parseInt(result[0]),Integer.parseInt(result[1]), Integer.parseInt(result[2]));
						setDisable(empty || item.compareTo(today) < 0);
					}
				};
			}
		};
		day.setDayCellFactory(callB);
	}
	
	@FXML public void setValue(ActionEvent event) throws ClassNotFoundException, SQLException {
		dump();
		
		if (day.getValue() == (null)) {
			day.setValue(LocalDate.now());
		}
		resetAllLayout();
		editDays();
	}	
	
	
	public void editDays() throws ClassNotFoundException, SQLException {
		if (day.getValue() == (null)) {
			day.setValue(LocalDate.now());
		}
		getSelectedDay = day.getValue().toString();
		
		int idx2 = -1;
		for (int i = 0; i < pid.length(); i++) {
			if (Character.isDigit(pid.charAt(i))) {
				idx2 = i;
				break;
			}
		}
		String subPid = pid.substring(idx2);

		String temp = (new Main().getDemailForPatient(subPid));
		ArrayList<String>[] ar = new Main().getDateAndTimeOfExaminations(temp);

		for (int i = 0; i < ar[0].size(); i++) {
			String time = ar[1].get(i);
			if (!time.equals("0") && ar[0].get(i).equals(day.getValue().toString())) {
				setAndGet(time, true, "Reserved");
			} else {
				setAndGet(time, false, "");
			}
		}
	}

	@FXML private void exit(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		day.setValue(LocalDate.now());
		resetAllLayout();	
	}

	public void resetAllLayout() {
		materialType.setItems(FXCollections.observableArrayList(
			"Temporary - 5$ for 1 tooth",
			"Metal - 10$ for 1 tooth",
			"Zircon - 15$ for 1 tooth"
		));
		materialColor.setItems(FXCollections.observableArrayList(
			"a1", "a2", "a3",
			"b1", "b2", "b3",
			"c1", "c2", "c3"
		));
				
		copyButtons[0] = b1;
		copyButtons[1] = b2;
		copyButtons[2] = b3;
		copyButtons[3] = b4;
		copyButtons[4] = b5;
		copyButtons[5] = b6;
		copyButtons[6] = b7;
		copyButtons[7] = b8;
		copyButtons[8] = b9;
		copyButtons[9] = b10;
		
		for (int i = 0; i < 10; i++) {
			copyButtons[i].setDisable(false);
		}
	}
	
	public void setAndGet(String time, boolean state, String str) {
		for (int i = 0; i < copyButtons.length; i++) {
			if (time.equals("" + (i + 7))) {
				copyButtons[i].setDisable(state);
				if (!state && i + 7 < 12) {
					copyButtons[i].setText((i + 7) + " - "  + (i + 8) + " am");
				} else if (!state) {
					int s = (i + 7) % 12;
					copyButtons[i].setText(s + " - "  + (s + 1) + " pm");				
				} else {
					copyButtons[i].setText("Reserved");
				}			
			}
		}
	}
}