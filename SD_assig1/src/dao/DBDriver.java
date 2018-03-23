package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBDriver {

	private static Connection connection = null;
	private static String user = "root";
	private static String pass = "asdf";

	private static void createConnection() {

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306?" + "user=" + user + "&password=" + pass + "&useSSL=false");

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static void populateDatabase() {

		StudentDAO sd = new StudentDAO();
		sd.createStudent("Radu", "Petrisel", "Azuga 3", "0747673818", "radupetrisel@gmail.com", "1960920125844",
				"20/09/1996", "asdf");
		sd.createStudent("Diana", "Danila", "Izlazului 2", "0752030023", "lucia_diana1995@yahoo.com", "2951221125635",
				"21/12/1995", "1234");
		sd.createStudent("Peter", "Zavaczki", "Intrelacuri 3", "0742737506", "bloopeti@gmail.com", "1970131134856",
				"31/01/1997", "parola");

		TeacherDAO td = new TeacherDAO();
		td.createTeacher("Tudor", "Vlad", "0712345678", "1012345678911", "teacher_address_1", "tudor.vlad@cs.utcluj.ro",
				"parola123");
		td.createTeacher("Emil", "Chifu", "0264401449", "1123456789123", "Baritiu 28", "emil.chifu@cs.utcluj.ro",
				"pw@1");

		CourseDAO cd = new CourseDAO();
		cd.createCourse("Sofware Design", 1);
		cd.createCourse("Format Languages and Translators", 2);

		EnrolDAO ed = new EnrolDAO();
		ed.createEnrol(1, 2, 0);

	}

	public static void initDatabase() {

		try {

			getConnection().prepareStatement("CREATE DATABASE asgn1").executeUpdate();
			getConnection()
					.prepareStatement("CREATE TABLE `asgn1`.`students` (" + "`idstudents` INT NOT NULL AUTO_INCREMENT,"
							+ " `firstName` VARCHAR(45) NOT NULL," + "  `lastName` VARCHAR(45) NOT NULL,"
							+ "  `cnp` VARCHAR(13) NOT NULL," + "  `phoneNumber` VARCHAR(10) NOT NULL,"
							+ "  `email` VARCHAR(45) NOT NULL," + "  `dob` DATE NOT NULL,"
							+ "  `address` VARCHAR(45) NOT NULL," + "  `password` VARCHAR(45) NOT NULL, "
							+ "  PRIMARY KEY (`idstudents`)," + "  UNIQUE INDEX `cnp_UNIQUE` (`cnp` ASC));")
					.executeUpdate(); // students table

			getConnection()
					.prepareStatement("CREATE TABLE `asgn1`.`teachers` ( "
							+ " `idteachers` INT NOT NULL AUTO_INCREMENT," + "  `firstName` VARCHAR(45) NOT NULL,"
							+ "  `lastName` VARCHAR(45) NOT NULL," + "  `phoneNumber` VARCHAR(10) NOT NULL,"
							+ "  `cnp` VARCHAR(13) NOT NULL," + "  `address` VARCHAR(45) NOT NULL,"
							+ "  `email` VARCHAR(45) NOT NULL," + "  `password` VARCHAR(45) NOT NULL,"
							+ "  PRIMARY KEY (`idteachers`)," + "  UNIQUE INDEX `cnp_UNIQUE` (`cnp` ASC));")
					.executeUpdate(); // teachers table

			getConnection().prepareStatement("CREATE TABLE `asgn1`.`courses` ("
					+ "  `idcourses` INT NOT NULL AUTO_INCREMENT," + "  `name` VARCHAR(45) NOT NULL,"
					+ "  `teacherID` INT NULL," + "  PRIMARY KEY (`idcourses`), "
					+ "  INDEX `teacher_idx` (`teacherID` ASC)," + "  CONSTRAINT `teacher`"
					+ "    FOREIGN KEY (`teacherID`)" + "    REFERENCES `asgn1`.`teachers` (`idteachers`)"
					+ "   ON DELETE NO ACTION" + "    ON UPDATE NO ACTION);").executeUpdate(); // courses

			getConnection()
					.prepareStatement("CREATE TABLE `asgn1`.`enrols` (" + "  `idenrols` INT NOT NULL AUTO_INCREMENT,"
							+ "  `studentId` INT NOT NULL," + "  `courseId` INT NOT NULL," + "  `grade` INT NULL,"
							+ "  PRIMARY KEY (`idenrols`)," + "  INDEX `course_idx` (`courseId` ASC),"
							+ "INDEX `student_idx` (`studentId` ASC)," + "  CONSTRAINT `student`"
							+ "    FOREIGN KEY (`studentId`)" + "    REFERENCES `asgn1`.`students` (`idstudents`)"
							+ "    ON DELETE NO ACTION" + "    ON UPDATE NO ACTION," + "  CONSTRAINT `course`"
							+ "    FOREIGN KEY (`courseId`)" + "    REFERENCES `asgn1`.`courses` (`idcourses`)"
							+ "    ON DELETE NO ACTION" + "    ON UPDATE NO ACTION);")
					.executeUpdate(); // student_to_course table
			
			getConnection().prepareStatement("ALTER TABLE `asgn1`.`courses` "
					+ "ADD UNIQUE INDEX `name_UNIQUE` (`name` ASC);"
					).executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection() {

		if (connection == null)
			createConnection();

		return connection;
	}

}