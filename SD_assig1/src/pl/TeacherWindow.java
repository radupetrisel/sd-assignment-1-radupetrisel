package pl;

import bll.TeacherBL;
import javafx.stage.Stage;

public class TeacherWindow extends UserWindow {

	public TeacherWindow(Stage window, int userId) {
		
		super(window, userId, new TeacherBL());
		window.setTitle("Teacher");
	}	
	

}
