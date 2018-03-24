package pl;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class StudentView extends JFrame{
	
	public StudentView() {
		
		this.setSize(800, 600);
		this.setVisible(true);
		this.setLayout(null);
		
		this.add(new JLabel("logged in!"));
		
	}
	
}
