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
import static dao.DBDriver.getConnection;

public class TeacherDAO {

	/*
	 * firstName, lastName, address, phoneNumber, email, cnp, dob, password - not null
	 * phoneNumber - match 0[0-9]+
	 * email - contain '@'
	 * cnp - start with either 0, 1 or 2, only digis
	 */
	public void createTeacher(String firstName, String lastName, String phoneNumber, String cnp, String address, String email, String password){
		
		assert firstName != null: "First name null.\n";
		assert lastName != null: "Last name null.\n";
		assert address != null: "Address null.\n";
		assert phoneNumber.matches("0[0-9]{9}"): "Invalid phone number.\n";
		assert email.matches("[A-Za-z0-9_\\.-]+@.*"): "Invalid email.\n";
		assert cnp.matches("[012][0-9]{12}"): "Invalid CNP.\n";
		assert password != null: "Password null.\n";
		
		try {
			
			PreparedStatement statement = getConnection().prepareStatement("INSERT INTO asgn1.teachers "
							+ "(firstName,lastName,address,phoneNumber,email,cnp, password) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?);"); 
				
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, address);
			statement.setString(4, phoneNumber);
			statement.setString(5, email);
			statement.setString(6, cnp);
			statement.setString(7, password);
			
			statement.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private List<Teacher> createTeachersFromResultSet(ResultSet rs) throws SQLException{
		
		List<Teacher> teachers = new ArrayList<Teacher>();
		while(rs.next()){
			
			Teacher t = new Teacher();
			//id, firstName, lastName, cnp, phoneNumber, email, dob, address
			t.setId(rs.getInt("idteachers"));
			t.setFirstName(rs.getString("firstName"));
			t.setLastName(rs.getString("lastName"));
			t.setCnp(rs.getString("cnp"));
			t.setPhoneNumber(rs.getString("phoneNumber"));
			t.setEmail(rs.getString("email"));
			t.setAddress(rs.getString("address"));
			t.setPassword(rs.getString("password"));
			teachers.add(t);
		}
		
		return teachers;
	}
		
	public List<Teacher> findTeacherByFieldValue(String field, Object value){
		
		try {
			
			PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM asgn1.teachers WHERE " + field + "=?");
			statement.setObject(1, value);
			
			ResultSet rs = statement.executeQuery();
			
			return this.createTeachersFromResultSet(rs);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void updateTeacher(Teacher t){
		
		try {
			
			assert t.getFirstName() != null: "First name null.\n";
			assert t.getLastName() != null: "Last name null.\n";
			assert t.getAddress() != null: "Address null.\n";
			assert t.getPhoneNumber().matches("07[0-9]{8}"): "Invalid phone number.\n";
			assert t.getEmail().matches("[A-Za-z0-9_\\.-]+@.*"): "Invalid email.\n";
			assert t.getCnp().matches("[012][0-9]{12}"): "Invalid CNP.\n";
			assert t.getPassword() != null: "Password null.\n";
			
			
			//id, firstName, lastName, cnp, phoneNumber, email, dob, address
			PreparedStatement statement = getConnection().prepareStatement("UPDATE asgn1.teachers SET "
					+ "firstName=?, "
					+ "lastName=?, "
					+ "cnp=?, "
					+ "phoneNumber=?, "
					+ "email=?, "
					+ "address=?, "
					+ "password=? "
					+ " WHERE asgn1.teachers.idteachers=?;");
			
			statement.setString(1, t.getFirstName());
			statement.setString(2, t.getLastName());
			statement.setString(3, t.getCnp());
			statement.setString(4, t.getPhoneNumber());
			statement.setString(5, t.getEmail());
			statement.setString(6, t.getAddress());
			statement.setString(7, t.getPassword());
			statement.setInt(8, t.getId());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteTeacherByFieldValue(String field, Object value){
		
		
		try {
			PreparedStatement statement = getConnection().prepareStatement("DELETE FROM asgn1.teachers WHERE " + field + "=?;");
			statement.setObject(1, value);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

				
	}
	
	
}
