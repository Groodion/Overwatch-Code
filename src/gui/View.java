package gui;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class View extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(FXMLLoader.load(new File("ovc_parser_view.fxml").toURI().toURL())));
		
		stage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}