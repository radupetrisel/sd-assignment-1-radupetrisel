package pl;

import bll.StudentBL;
import bll.TeacherBL;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login {

	private Stage window;
	private Scene loginScene;
	private TextField username;
	private PasswordField password;
	private CheckBox isTeacher;
	private Button login;
	
	public Login(Stage window, double width, double height) {
		
		this.window = window;
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		
		loginScene = new Scene(box, width, height);
		
		username = new TextField();
		username.setPromptText("username");
		username.setMaxSize(width / 2, 30);
		
		password = new PasswordField();
		password.setPromptText("password");
		password.setMaxSize(width / 2, 30);
		
		isTeacher = new CheckBox();
		isTeacher.setText("I am a teacher");
		
		login = new Button("Login");
		login.setOnAction(e -> handleLogin());
			
		box.getChildren().addAll(username, password, isTeacher, login);
		
		loginScene.setOnKeyPressed(e -> {
			
			if (e.getCode().equals(KeyCode.ENTER)) handleLogin();
			
		});
		
		this.window.setScene(loginScene);
	}
	
	private void handleLogin() {
		
		int id = 0;
		
		if (isTeacher.isSelected()) {
			
			id = (new TeacherBL()).login(username.getText(), password.getText());
			
			if (id > 0) {
				
				BorderPane p = new BorderPane();
				Scene teacherScene = new Scene(p, 500, 500);
				this.window.setTitle("Teacher");
				this.window.setScene(teacherScene);
			}
			
		}
		else {
			
			id = (new StudentBL()).login(username.getText(), password.getText());
			
			if (id > 0) {
				
				BorderPane p = new BorderPane();
				Scene studentScene = new Scene(p, 200, 200);
				this.window.setTitle("Student");
				this.window.setScene(studentScene);
			}
			
		}
		
	}
	
}
