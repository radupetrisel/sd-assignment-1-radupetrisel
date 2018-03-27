package pl;

import java.awt.Dimension;
import java.awt.Toolkit;

import bll.UserBL;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class UserWindow {

	private BorderPane layout;
	private Scene mainScene;
	protected int userId;
	private UserBL userBL;
	private VBox profileBox;

	public UserWindow(Stage window, int userId, UserBL userBL) {

		this.userId = userId;
		this.userBL = userBL;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.layout = new BorderPane();

		this.mainScene = new Scene(this.layout, screenSize.getWidth() / 2, screenSize.getHeight() / 2);
		window.setScene(this.mainScene);
		window.centerOnScreen();
		
		VBox left = new VBox();
		Button updatePassword = new Button("Update password");
		Button updateAddress = new Button("Update address");
		Button updateEmail = new Button("Update email");
		Button viewProfile = new Button("View profile");
		
		left.setSpacing(20);
		left.setAlignment(Pos.CENTER);
		
		left.getChildren().addAll(viewProfile, updatePassword, updateAddress, updateEmail);
		this.layout.setLeft(left);
		
		updatePassword.setOnAction(e -> updatePassword());
		
		Button logout = new Button("Logout");
		
		VBox bottom = new VBox();
		bottom.setAlignment(Pos.BOTTOM_RIGHT);
		
		bottom.getChildren().add(logout);
		this.setBottom(bottom);
		
		logout.setOnAction(e -> new Login(window, 250, 300));
		
		this.createProfileBox();
		this.setCenter(profileBox);
		this.displayHelloMessage();
	}
	
	private void createProfileBox() {
		
		
		
	}
	
	private void updatePassword() {
		
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(20);
		
		PasswordField oldPassword = new PasswordField();
		oldPassword.setPromptText("Old password");
		PasswordField newPassword = new PasswordField();
		newPassword.setPromptText("New password");
		PasswordField confirmPassword = new PasswordField();
		confirmPassword.setPromptText("Confirm new password");
		Button update = new Button("Update");
		
		box.setPrefWidth(120);
		
		oldPassword.setMaxWidth(Region.USE_PREF_SIZE);
		newPassword.setMaxWidth(Region.USE_PREF_SIZE);
		confirmPassword.setMaxWidth(Region.USE_PREF_SIZE);
		
		box.getChildren().addAll(oldPassword, newPassword, confirmPassword, update);
		
		oldPassword.focusedProperty().addListener(c -> {
			
			String password = userBL.findById(userId).getPassword();
			
			if (!oldPassword.isFocused()) {
				
				if (!oldPassword.getText().equals(password))
					oldPassword.setStyle("-fx-border-color: red ;" + 
							" -fx-border-width: 1px ; ");
				else oldPassword.setStyle(" -fx-border-color: lime ; -fx-border-width: 2px;");
				
			}
			
			else oldPassword.setStyle(null);
			
		});
		
		confirmPassword.setOnKeyReleased(e -> {
			
			if (confirmPassword.getText().equals(newPassword.getText())) {
				
				confirmPassword.setStyle("-fx-border-color: lime; -fx-border-width: 2px;");
				newPassword.setStyle("-fx-border-color: lime; -fx-border-width: 2px;");
			}
			else {
				
				confirmPassword.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
				newPassword.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
			}
			
		});
		
		update.setOnAction(e -> {
			
			String oldPass = userBL.findById(userId).getPassword();
			String newPass = newPassword.getText();
			
			if (oldPassword.getText().equals(oldPass) && newPass.equals(confirmPassword.getText())) {
				
				userBL.updatePassword(userId, newPass);
				
				Stage success = new Stage();
				success.centerOnScreen();
				
				
				VBox successBox = new VBox();
				successBox.setAlignment(Pos.CENTER);
				successBox.setSpacing(10);
				
				Scene successScene = new Scene(successBox, 200, 150);
				Text successText = new Text("Password updated successfully!");
				Button ok = new Button("Ok");
				
				successBox.getChildren().addAll(successText, ok);
				
				ok.setOnAction(eok -> {
					
					success.close();
					layout.setCenter(profileBox);
				});
				
				success.setScene(successScene);
				success.setTitle("Success");
				success.initModality(Modality.APPLICATION_MODAL);
				success.showAndWait();
			}
			
		});
		
		this.layout.setCenter(box);
		
	}
	
	private void displayHelloMessage() {
		
		Text hello = new Text("Hello " + userBL.findById(userId).getFirstName() + "!");
		hello.setFont(Font.font("Times New Roman", 25));
		hello.setSmooth(true);
		
		VBox helloBox = new VBox();
		helloBox.getChildren().add(hello);
		helloBox.setAlignment(Pos.CENTER);
		
		this.layout.setTop(helloBox);
	}

	public void setLeft(Node left) {
		this.layout.setLeft(left);
	}

	public void setRight(Node right) {
		this.layout.setRight(right);
	}

	public void setTop(Node top) {
		this.layout.setTop(top);
	}
	
	public void setBottom(Node bottom) {
		this.layout.setBottom(bottom);
	}
	
	public void setCenter(Node center) {
		this.layout.setCenter(center);
	}

}
