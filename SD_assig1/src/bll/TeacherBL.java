package bll;

import dao.EnrolDAO;
import dao.Student;
import dao.StudentDAO;
import dao.Teacher;
import dao.TeacherDAO;

public class TeacherBL {

	private Teacher findTeacherByEmail(String email) {

		Teacher t = null;

		try {

			t = (new TeacherDAO()).findTeacherByFieldValue("email", email).get(0);

		} catch (ArrayIndexOutOfBoundsException ie) {

			return null;
		}

		return t;

	}

	public Teacher login(String email, String password) {

		Teacher t = findTeacherByEmail(email);

		if (t == null) {

			System.out.println("Invalid email.");
			return null;
		}

		if (!t.getEmail().equals(password)) {

			System.out.println("Incorrect password.");
			return null;
		}

		return t;

	}

	public void updateEmail(int teacherID, String newEmail) {

		(new TeacherDAO()).updateTeacherByFieldValue(teacherID, "email", newEmail);

	}

	public void updateAddress(int teacherID, String newAddress) {

		(new TeacherDAO()).updateTeacherByFieldValue(teacherID, "address", newAddress);

	}

	public void updatePassword(int teacherID, String newPassword) {

		(new TeacherDAO()).updateTeacherByFieldValue(teacherID, "password", newPassword);

	}
	
	public void createStudent(String firstName, String lastName, String address, String phoneNumber, String email,
			String cnp, String dob) {
		
		(new StudentDAO()).createStudent(firstName, lastName, address, phoneNumber, email, cnp, dob, "1234");
		
	}
	
	public Student viewStudent(int studentID) {
						
		return (new StudentDAO()).findStudentByFieldValue("idstudents", studentID).get(0);
	}
	
	public void giveMarkToStudent (int courseID, int studentID, int grade) {
		
		
		
	}
	

}
