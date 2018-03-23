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
	 * firstName, lastName, address, phoneNumber, email, cnp, dob, password - not null
	 * phoneNumber - match 07[0-9]+ email - contain '@' cnp - start with either 0 or
	 * 1, only digis
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
		assert password != null: "Password null.\n";

		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			java.sql.Date dateOfBirth = new java.sql.Date(df.parse(dob).getTime());

			PreparedStatement statement = getConnection().prepareStatement("INSERT INTO asgn1.students "
					+ "(firstName,lastName,address,phoneNumber,email,cnp,dob, password) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);");

			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, address);
			statement.setString(4, phoneNumber);
			statement.setString(5, email);
			statement.setString(6, cnp);
			statement.setDate(7, dateOfBirth);
			statement.setString(8, password);

			statement.executeUpdate();

		} catch (SQLException | ParseException e) {
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

		try {

			PreparedStatement statement = getConnection()
					.prepareStatement("SELECT * FROM asgn1.students WHERE " + field + "=?");
			statement.setObject(1, value);

			ResultSet rs = statement.executeQuery();

			return this.createStudentsFromResultSet(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void updateStudent(Student s) {

		try {

			assert s.getFirstName() != null : "First name null.\n";
			assert s.getLastName() != null : "Last name null.\n";
			assert s.getAddress() != null : "Address null.\n";
			assert s.getPhoneNumber().matches("07[0-9]{8}") : "Invalid phone number.\n";
			assert s.getEmail().matches("[A-Za-z0-9_\\.-]+@.*") : "Invalid email.\n";
			assert s.getCnp().matches("[12][0-9]{12}") : "Invalid CNP.\n";
			assert s.getDob() != null : "Date of birth null.\n";
			assert s.getPassword() != null: "Password null.\n";

			// id, firstName, lastName, cnp, phoneNumber, email, dob, address, password
			PreparedStatement statement = getConnection().prepareStatement(
					"UPDATE asgn1.students SET " + "firstName=?, " + "lastName=?, " + "cnp=?, " + "phoneNumber=?, "
							+ "email=?, " + "dob=?, " + "address=?, " + "password=? " + " WHERE asgn1.students.idstudents=?;");

			statement.setString(1, s.getFirstName());
			statement.setString(2, s.getLastName());
			statement.setString(3, s.getCnp());
			statement.setString(4, s.getPhoneNumber());
			statement.setString(5, s.getEmail());
			statement.setDate(6, new java.sql.Date(s.getDob().getTime()));
			statement.setString(7, s.getAddress());
			statement.setString(8, s.getPassword());
			statement.setInt(9, s.getId());

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteStudentByFieldValue(String field, Object value) {

		try {
			PreparedStatement statement = getConnection()
					.prepareStatement("DELETE FROM asgn1.students WHERE " + field + "=?;");
			statement.setObject(1, value);

			statement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
