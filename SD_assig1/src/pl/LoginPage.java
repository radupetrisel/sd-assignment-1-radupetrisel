package pl;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage extends JFrame{
	
	private JLabel usernameLabel;
	private JTextField usernameTextField;
	private JLabel passwordLabel;
	private JPasswordField passwordTextField;
	private JButton loginButton;
	private JCheckBox isTeacher;
	
	public LoginPage() {
		
		this.setSize(250, 300);
		this.setVisible(true);
		this.setLayout(null);
		
		this.usernameLabel = new JLabel("Username:");
		this.usernameLabel.setBounds(20, 50, 70, 20);
		this.add(this.usernameLabel);
		
		this.usernameTextField = new JTextField();
		this.usernameTextField.setBounds(90, 52, 120, 20);
		this.add(this.usernameTextField);
		
		this.passwordLabel = new JLabel("Password:");
		this.passwordLabel.setBounds(20, 80, 70, 20);
		this.add(this.passwordLabel);
		
		this.passwordTextField = new JPasswordField();
		this.passwordTextField.setBounds(90, 82, 120, 20);
		this.add(this.passwordTextField);
		
		this.loginButton = new JButton("Login");
		this.loginButton.setBounds(150, 230, 70, 20);
		this.add(this.loginButton);
		
		this.isTeacher = new JCheckBox("I am a teacher");
		this.isTeacher.setBounds(90, 150, 120, 20);
		this.add(this.isTeacher);
		
	}
	
	public String getUsername() {
		return this.usernameTextField.getText();
	}
	
	public String getPassword() {
		return new String(this.passwordTextField.getPassword());
	}
	
	public void addLoginListener(ActionListener a) {
		this.loginButton.addActionListener(a);
	}
	
	public boolean isTeacher() {
		return isTeacher.isSelected();
	}
	
	public void displayErrorMessage(int error) {
		
		JLabel msg = null;
		if (error == -1) {
			
			msg = new JLabel("Invalid username!");
			
		}
		else if (error == -2) {
			
			msg = new JLabel("Invalid password!");
		
		}
		
		msg.setBackground(this.getBackground());
		msg.setBorder(null);
		msg.setForeground(Color.red);
		msg.setBounds(70, 105, 100, 20);
		this.add(msg);
		this.repaint();
	}
	
	
}
