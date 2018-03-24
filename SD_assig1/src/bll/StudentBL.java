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

			System.out.println("Cannot find student with email " + email);
		}

		return s;
	}

	public Student login(String email, String password) {

		Student s = findStudentByEmail(email);

		if (s == null) {
			System.out.println("invalid email");
			return null;
		}

		if (!s.getPassword().equals(password)) {
			System.out.println("invalid password");
			return null;
		}

		return s;
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
		
		List<String> fields = new ArrayList<String>();
		fields.add("studentId");
		
		List<Object> values = new ArrayList<Object>();
		values.add(studentID);
		
		return (new EnrolDAO()).findEnrolByFieldsValues(fields, values).stream()
				.collect(Collectors.toMap(e -> e.getCourse().getName(), e -> e.getGrade()));

	}
	
	public void delete(int studentID) {
		
		(new StudentDAO()).deleteStudentByFieldValue("idstudents", studentID);		
	}
	
	

}
