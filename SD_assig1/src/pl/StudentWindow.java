package pl;

import bll.StudentBL;
import javafx.stage.Stage;

public class StudentWindow extends UserWindow {

	public StudentWindow(Stage window, int userId) {
		
		super(window, userId, new StudentBL());
		window.setTitle("Student");
	}

}
