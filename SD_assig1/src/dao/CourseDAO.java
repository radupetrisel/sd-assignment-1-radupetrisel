package dao;

import static dao.DBDriver.getConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

	/*
	 * name - not null teacherId - < max(teacherIds)
	 */
	public void createCourse(String name, int teacherId) {

		assert name != null : "Name null.\n";

		try {
			assert getConnection()
					.prepareStatement(
							"SELECT * FROM asgn1.teachers " + "WHERE asgn1.teachers.idteachers = " + teacherId)
					.executeQuery() != null : "Invalid teacher";
			PreparedStatement statement = getConnection()
					.prepareStatement("INSERT INTO asgn1.courses (name, teacherId)" + "VALUES(?, ?);");

			statement.setString(1, name);
			statement.setInt(2, teacherId);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private List<Course> createCoursesFromResultSet(ResultSet rs) throws SQLException {

		List<Course> courses = new ArrayList<Course>();

		while (rs.next()) {

			Course c = new Course();
			c.setId(rs.getInt("idcourses"));
			c.setName(rs.getString("name"));
			c.setTeacher((new TeacherDAO()).findTeacherByFieldValue("idteachers", rs.getInt("teacherID")).get(0));
			courses.add(c);
		}

		return courses;
	}

	public List<Course> findCourseByFieldValue(String field, Object value) {

		PreparedStatement statement;
		try {
			statement = getConnection().prepareStatement("SELECT * FROM asgn1.courses WHERE " + field + "=?");
			statement.setObject(1, value);
			ResultSet rs = statement.executeQuery();

			return this.createCoursesFromResultSet(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	public void deleteCourseByFieldValue(String field, Object value) {

		PreparedStatement statement;

		try {
			
			statement = getConnection().prepareStatement("DELETE FROM asgn1.courses WHERE " + field + "=?;");
			statement.setObject(1, value);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateCourseByFieldValue(int courseId, String field, Object value) {

		PreparedStatement statement;
		try {
			
			statement = getConnection()
					.prepareStatement("UPDATE asgn1.courses SET " + field + "=? WHERE asgn1.courses.idcourses=?;");
			statement.setObject(1, value);
			statement.setInt(2, courseId);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
