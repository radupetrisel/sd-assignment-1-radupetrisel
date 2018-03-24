package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.EnrolDAO;
import dao.Student;
import dao.StudentDAO;

public class StudentBL {

	private Student findStudentByEmail(String email) {

		Student s = null;
		try {

			s = (new StudentDAO()).findStudentByFieldValue("email", email).get(0);

		} catch (IndexOutOfBoundsException ie) {

			return null;
		}

		return s;
	}

	public int login(String email, String password) {

		Student s = findStudentByEmail(email);

		if (s == null) {
			return -1;
		}

		if (!s.getPassword().equals(password)) {
			return -2;
		}

		return s.getId();
	}

	public void updateEmail(int studentID, String newEmail) {

		(new StudentDAO()).updateStudentByFieldValue(studentID, "email", newEmail);

	}

	public void updateAddress(int studentID, String newAddress) {

		(new StudentDAO()).updateStudentByFieldValue(studentID, "address", newAddress);

	}

	public void updatePassword(int studentID, String newPassword) {

		(new StudentDAO()).updateStudentByFieldValue(studentID, "password", newPassword);

	}

	public void enrol(int studentID, int courseID) {
		(new EnrolDAO()).createEnrol(studentID, courseID, 0);
	}

	public Map<String, Integer> viewGrades(int studentID) {
				
		return (new EnrolDAO()).findEnrolByFieldValue("studentId", studentID).stream()
				.collect(Collectors.toMap(e -> e.getCourse().getName(), e -> e.getGrade()));

	}
	
	public void delete(int studentID) {
		
		(new StudentDAO()).deleteStudentByFieldValue("idstudents", studentID);		
	}
	
	

}
