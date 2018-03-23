package dao;

import static dao.DBDriver.getConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrolDAO {

	private List<Enrol> createEnrolsFromResultSet(ResultSet rs) throws SQLException {

		List<Enrol> enrols = new ArrayList<Enrol>();

		while (rs.next()) {

			Enrol e = new Enrol();
			e.setId(rs.getInt("idenrols"));
			e.setCourse((new CourseDAO()).findCourseByFieldValue("idcourses", rs.getInt("courseId")).get(0));
			e.setStudent((new StudentDAO()).findStudentByFieldValue("idstudents", rs.getInt("studentId")).get(0));
			e.setGrade(rs.getInt("grade"));
			enrols.add(e);
		}

		return enrols;

	}

	public void createEnrol(int studentId, int courseId, Integer grade) {

		try {

			PreparedStatement statement = getConnection()
					.prepareStatement("INSERT INTO asgn1.enrols (studentId, courseId, grade) VALUES(?, ?, ?);");
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			statement.setInt(3, grade);

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Enrol> findEnrolByFieldValue(String field, Object value) {

		try {

			PreparedStatement statement = getConnection()
					.prepareStatement("SELECT * FROM asgn1.enrols WHERE " + field + "=?");
			statement.setObject(1, value);

			return this.createEnrolsFromResultSet(statement.executeQuery());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void updateEnrol(Enrol e) {

		try {

			PreparedStatement statement = getConnection().prepareStatement("UPDATE asgn1.enrols SET " + "studentId=?, "
					+ "courseId=?, " + "grade=? " + "WHERE asgn1.enrols.idenrols=?;");

			statement.setInt(1, e.getStudent().getId());
			statement.setInt(2, e.getCourse().getId());
			statement.setInt(3, e.getGrade());
			statement.setInt(4, e.getId());

			statement.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	public void deleteEnrolsByFieldValue(String field, Object value) {
		
		try {
			
			PreparedStatement statement = getConnection().prepareStatement("DELETE FROM asgn1.enrols WHERE " + field + "=?;");
			statement.setObject(1, value);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
