package bll;

import java.util.ArrayList;
import java.util.List;

import dao.Enrol;
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

		} catch (IndexOutOfBoundsException ie) {

			return null;
		}

		return t;

	}

	public int login(String email, String password) {

		Teacher t = findTeacherByEmail(email);

		if (t == null) {

			return -1;
		}

		if (!t.getPassword().equals(password)) {

			return -2;
		}

		return t.getId();

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
		
		List<String> fields = new ArrayList<String>();
		fields.add("studentId");
		fields.add("courseId");
		
		List<Object> values = new ArrayList<Object>();
		values.add(studentID);
		values.add(courseID);
		
		EnrolDAO ed = new EnrolDAO();
		
		Enrol e = ed.findEnrolByFieldValue(fields, values).get(0);
		
		ed.updateEnrol(e.getId(), "grade", grade);
	}
	
	public void removeStudent(int studentID) {
		
		(new StudentDAO()).deleteStudentByFieldValue("idstudents", studentID);
		
	}
	

}
