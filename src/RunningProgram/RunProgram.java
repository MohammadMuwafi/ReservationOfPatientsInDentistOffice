package RunningProgram;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RunProgram extends Application {

	private double xOffset = 0, yOffset = 0;

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../Views/FXMLs/DoctorGui.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../Views/Styles/style.css").toExternalForm());
		scene.setFill(Color.TRANSPARENT);

		root.setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		root.setOnMouseDragged(event -> {
			primaryStage.setX(event.getScreenX() - xOffset);
			primaryStage.setY(event.getScreenY() - yOffset);
		});

		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setTitle("Qudent App");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) throws InterruptedException {
		launch(args);
	}
}