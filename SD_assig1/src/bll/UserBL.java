package bll;

import dao.StudentDAO;

public abstract class UserBL {
	
	public void updateEmail(int userId, String newEmail) {

		(new StudentDAO()).updateUserFieldValue(userId, "email", newEmail);

	}

	public void updateAddress(int userId, String newAddress) {

		(new StudentDAO()).updateUserFieldValue(userId, "address", newAddress);

	}

	public void updatePassword(int userId, String newPassword) {

		(new StudentDAO()).updateUserFieldValue(userId, "password", newPassword);

	}
	
	public void delete(int userId) {
		
		(new StudentDAO()).deleteUserByFieldValue("idstudents", userId);		
	}
	
	public abstract int login(String username, String password);
	

}
