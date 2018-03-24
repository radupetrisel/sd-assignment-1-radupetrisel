package pl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bll.StudentBL;
import bll.TeacherBL;

public class LoginController {

	private LoginPage login;

	public LoginController(LoginPage login) {

		this.login = login;

		this.login.addLoginListener(new LoginListener());
	}

	private class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent a) {

			String username = login.getUsername();
			String password = login.getPassword();

			int id = 0;

			if (login.isTeacher()) {
				id = (new TeacherBL()).login(username, password);
			} else {
				id = (new StudentBL()).login(username, password);
			}
			
			if (id < 0)
				login.displayErrorMessage(id);
			else {
				login.setVisible(false);
				
				if (login.isTeacher()) {
					new TeacherView();
				}
				else {
					new StudentView();
				}
			}

		}

	}

}
