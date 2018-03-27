package pl;

import dao.DBDriver;
import javafx.application.Application;
import javafx.stage.Stage;

public class Test extends Application {
	
	public static void main(String[] args) {
		
		launch(args);			
	}

	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.centerOnScreen();
		
		new Login(primaryStage, 250, 300);

		primaryStage.show();
	}

	
	
}	

