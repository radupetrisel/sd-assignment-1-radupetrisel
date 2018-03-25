package bll;

import java.util.Map;
import java.util.stream.Collectors;

import dao.EnrolDAO;
import dao.Student;
import dao.StudentDAO;

public class StudentBL extends UserBL{

	private Student findStudentByEmail(String email) {

		Student s = null;
		
		try {

			s = (Student)(new StudentDAO()).findUserByFieldValue("email", email).get(0);
					
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

	public void enrol(int studentID, int courseID) {
		(new EnrolDAO()).createEnrol(studentID, courseID, 0);
	}

	public Map<String, Integer> viewGrades(int studentID) {
				
		return (new EnrolDAO()).findEnrolByFieldValue("studentId", studentID).stream()
				.collect(Collectors.toMap(e -> e.getCourse().getName(), e -> e.getGrade()));

	}

}
