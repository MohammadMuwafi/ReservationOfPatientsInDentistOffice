package Controllers;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DBConnection.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.DatePicker;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PieChartController implements Initializable {
	private String path = "mouseClick.mp3";
	private Media media = new Media(new File(path).toURI().toString());
	private MediaPlayer mediaPlayer = new MediaPlayer(media); 

	@FXML private DatePicker from, to;
	@FXML private PieChart chart;

	@FXML public void getDate() {
		if (from.getValue() == null) {
			from.setValue(LocalDate.of(2000, 01, 01));
		}
		if (to.getValue() == null || to.getValue().compareTo(from.getValue()) < 1) {
			to.setValue(LocalDate.now());
		}
	}
	
	public void callChart() {
		try {
			int numberOfPatients = Integer.parseInt(new Main().getNumberOfPatietns(from.getValue().toString(), to.getValue().toString()));
			if (numberOfPatients > 0) {
				ArrayList<String>[] ar = new Main().getPatientsForEachDoctors(from.getValue().toString(), to.getValue().toString());
				ObservableList<Data> list = FXCollections.observableArrayList();

				for (int i = 0; i < ar[0].size(); i++) {
					Data data = new Data(ar[1].get(i).toString(), Integer.parseInt(ar[0].get(i)));
					list.add(data);
				}

				chart.setData(list);
				
			}
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void showChart(ActionEvent event) {
		mediaPlayer.stop();
		mediaPlayer.play();
		ObservableList<Data> list = FXCollections.observableArrayList();
		chart.setData(list);
		callChart();
		for (Data data : chart.getData()) {
			data.nameProperty().set("[" + data.getName() + " has " + (int) data.getPieValue() + " Patient/s]");
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		from.setValue(LocalDate.of(2000, 01, 01));
		to.setValue(LocalDate.now());
	}

}