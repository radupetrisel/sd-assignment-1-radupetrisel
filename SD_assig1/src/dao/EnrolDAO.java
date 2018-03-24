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

		PreparedStatement statement;
		try {

			statement = getConnection()
					.prepareStatement("INSERT INTO asgn1.enrols (studentId, courseId, grade) VALUES(?, ?, ?);");
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			statement.setInt(3, grade);

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public List<Enrol> findEnrolByFieldValue(String field, Object value){
		
		try {
			
			PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM asgn1.enrols WHERE " + field + "=?");
			statement.setObject(1, value);
			ResultSet rs = statement.executeQuery();
			
			return this.createEnrolsFromResultSet(rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

	public List<Enrol> findEnrolByFieldValue(List<String> fields, List<Object> values) {

		PreparedStatement statement;
		try {

			String statement_string = "SELECT * FROM asgn1.enrols WHERE ";

			int i = 0;
			for (i = 0; i < fields.size() - 1; i++) {

				statement_string += fields.get(0) + "=? and ";

			}

			statement_string += fields.get(i) + "=?";

			statement = getConnection().prepareStatement(statement_string);

			i = 1;

			for (Object o : values) {

				statement.setObject(i++, o);
			}

			return this.createEnrolsFromResultSet(statement.executeQuery());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void updateEnrol(int enrolId, String field, Object value) {

		PreparedStatement statement;
		try {

			statement = getConnection()
					.prepareStatement("UPDATE asgn1.enrols SET " + field + "=? WHERE asgn1.enrols.idenrols=?;");
			statement.setObject(1, value);
			statement.setInt(2, enrolId);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteEnrolsByFieldValue(String field, Object value) {

		PreparedStatement statement;
		try {

			statement = getConnection().prepareStatement("DELETE FROM asgn1.enrols WHERE " + field + "=?;");
			statement.setObject(1, value);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
