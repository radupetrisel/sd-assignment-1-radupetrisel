package dao;

import static dao.DBDriver.getConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

	/*
	 * firstName, lastName, address, phoneNumber, email, cnp, dob, password - not
	 * null phoneNumber - match 07[0-9]+ email - contain '@' cnp - start with either
	 * 0 or 1, only digis
	 */
	public void createStudent(String firstName, String lastName, String address, String phoneNumber, String email,
			String cnp, String dob, String password) {

		assert firstName != null : "First name null.\n";
		assert lastName != null : "Last name null.\n";
		assert address != null : "Address null.\n";
		assert phoneNumber.matches("07[0-9]{8}") : "Invalid phone number.\n";
		assert email.matches("[A-Za-z0-9_\\.-]+@.*") : "Invalid email.\n";
		assert cnp.matches("[12][0-9]{12}") : "Invalid CNP.\n";
		assert dob != null : "Date of birth null.\n";
		assert password != null : "Password null.\n";

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		java.sql.Date dateOfBirth = null;

		try {
			dateOfBirth = new java.sql.Date(df.parse(dob).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		PreparedStatement statement;
		try {

			statement = getConnection().prepareStatement(
					"INSERT INTO asgn1.students " + "(firstName,lastName,address,phoneNumber,email,cnp,dob, password) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?);");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, address);
			statement.setString(4, phoneNumber);
			statement.setString(5, email);
			statement.setString(6, cnp);
			statement.setDate(7, dateOfBirth);
			statement.setString(8, password);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<Student> createStudentsFromResultSet(ResultSet rs) throws SQLException {

		List<Student> students = new ArrayList<Student>();
		while (rs.next()) {

			Student s = new Student();
			// id, firstName, lastName, cnp, phoneNumber, email, dob, address, password
			s.setId(rs.getInt("idstudents"));
			s.setFirstName(rs.getString("firstName"));
			s.setLastName(rs.getString("lastName"));
			s.setCnp(rs.getString("cnp"));
			s.setPhoneNumber(rs.getString("phoneNumber"));
			s.setEmail(rs.getString("email"));
			s.setDob(rs.getDate("dob"));
			s.setAddress(rs.getString("address"));
			s.setPassword(rs.getString("password"));
			students.add(s);
		}

		return students;
	}

	public List<Student> findStudentByFieldValue(String field, Object value) {

		PreparedStatement statement;

		try {
			statement = getConnection().prepareStatement("SELECT * FROM asgn1.students WHERE " + field + "=?");

			statement.setObject(1, value);

			ResultSet rs = statement.executeQuery();
			return this.createStudentsFromResultSet(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;

	}

	public List<Student> findStudentByFieldValue(List<String> fields, List<Object> values) {

		PreparedStatement statement;
		try {

			String statement_string = "SELECT * FROM asgn1.students WHERE ";

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

			ResultSet rs = statement.executeQuery();

			return this.createStudentsFromResultSet(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void updateStudentByFieldValue(int studentId, String field, Object value) {

		PreparedStatement statement;
		try {

			statement = getConnection()
					.prepareStatement("UPDATE asgn1.students SET " + field + "=? WHERE asgn1.students.idstudents=?;");
			statement.setObject(1, value);
			statement.setInt(2, studentId);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteStudentByFieldValue(String field, Object value) {

		PreparedStatement statement;
		try {

			statement = getConnection().prepareStatement("DELETE FROM asgn1.students WHERE " + field + "=?;");
			statement.setObject(1, value);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
