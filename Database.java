import java.sql.*;
import java.util.*;

class Database {
	//configuring and connecting to our database	
	static String url = "jdbc:mysql://localhost:3306/universitydb";
	static String username = "root";
	static String password = "1234";
	static Connection con;
	static Statement stat, stat1;
	
	static {
		
		System.out.println("Using Database...");
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("You have the needed drivers...");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection(url, username, password);
			System.out.println("You are connected to the data base...");
			System.out.println();
			stat = con.createStatement();
			stat1 = con.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	
	//inserts a person to database
	static void insertPerson(Person p) {	
		
		try {
			if (p.getClass().getSimpleName() != "Teacher")
				stat.executeUpdate("INSERT INTO " + p.getClass().getSimpleName() + " VALUES ('" + p.getUsername() + "', '" + p.getPassword() + "', '" + p.getName() + "');");
			else {
				stat.executeUpdate("INSERT INTO teacher VALUES ('" + p.getUsername() + "', '" + p.getPassword() + "', '" + p.getName() + "', '" + ((Teacher) p).getRank() + "');");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
				
	}
	
	//inserts a course to database
	static void insertCourse(Course c) {
		
		try {
			stat.executeUpdate("INSERT INTO course VALUES ('" + c.getId() + "', '" + c.getName() + "', " + c.getCapacity() + ", '" + c.getTeacher().getName() + "', '" + c.getTeacher().getUsername() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	
	//removes a person from database
	static void deletePerson(Person p) {
		
		
		try {
			stat.executeUpdate("DELETE FROM " + p.getClass().getSimpleName() + " WHERE (`ID` = '" + p.getUsername() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//outputs all persons in the related table of database
	static void getAll(int mode) {
		
		String modeStr = "";
		
		if (mode == 0) {
			modeStr = "student";
		}
		if (mode == 1) {
			modeStr = "teacher";
		}
		
		if (mode == 2) {
			modeStr = "Course";
		}
		
		ResultSet res;
		try {
			res = stat.executeQuery("select * from " + modeStr);
			while (res.next()) {
				
				System.out.print("ID: " + res.getString("ID") + "   Name: " + res.getString("Name"));
				if (mode == 1)
					System.out.print("   Rank: " + res.getString("Rank"));
				if (mode == 2) {
					System.out.print("   Capacity: " + res.getString("Capacity") + "   Teacher's Name: " + res.getString("Teacher_Name"));
				}
				System.out.println();
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		

		
	}
	
	//changes an accounts password
	static void changePassword(Person p) {
		
		try {
			stat.executeUpdate("UPDATE " + p.getClass().getSimpleName() + " SET `Password` = '" + p.getPassword() + "' WHERE (`ID` = '" + p.getUsername() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	
	//lists a student's grades with given username
	static void checkGrades(String username) {
		
		ResultSet res;
		
		try {
			res = stat.executeQuery("select * FROM grade WHERE (`Student_ID` = '" + username + "')");
			while (res.next()) {
				System.out.println(res.getString("Course_ID") + " : " + res.getFloat("Grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//adds a student to a course
	static void takeCourse(String username, Course c) {
		
		
		try {
			
			ResultSet res = stat.executeQuery("select max(id) from grade");
			res.next();
			int gradeID = res.getInt("max(id)");
			gradeID++;

			ResultSet res2 = stat1.executeQuery("select * FROM course WHERE (`ID` = '" + c.getId() + "')");
			res2.next();
			int capacityNum = res2.getInt("Capacity");
			
			stat.executeUpdate("INSERT INTO grade VALUES ('" + c.getId() + "', '" + username + "', '" + c.getTeacher().getUsername() + "', " + 0f + ", " + gradeID + ");");
			stat.executeUpdate("UPDATE `course` SET `Capacity` = '" + (capacityNum - 1) + "' WHERE (`ID` = '" + c.getId() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//getting Teacher_ID of course 'id'
	static String getTeacherID(String id) {
		ResultSet res;
		 try {
			res = stat.executeQuery("select * FROM course WHERE (`ID` = '" + id + "')");
			while(res.next()) {
				return res.getString("Teacher_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 return "";
	}
	
	static void checkCourses(String username) {
		
		ResultSet res;
		
		 try {
			res = stat.executeQuery("select * FROM course WHERE (`Teacher_ID` = '" + username + "')");
			while(res.next()) {
				System.out.println(res.getString("ID") + " : " + res.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//lists all students of a teacher
	static void viewStudents(String username) {
		
		ArrayList<String> output = new ArrayList<>();
		
		ResultSet res;
		
		try {
			res = stat.executeQuery("select * FROM grade WHERE (`Teacher_ID` = '" + username + "')");
			
			while(res.next()) {
				
				String courseId = res.getString("Course_ID");
				String studentId = res.getString("Student_ID");
				
				ResultSet res2 = stat1.executeQuery("select * FROM student WHERE (`ID` = '" + studentId + "')");
				res2.next();
				String studentName = res2.getString("Name");
				res2 = stat1.executeQuery("select * FROM course WHERE (`ID` = '" + courseId + "')");
				res2.next();
				String courseName = res2.getString("Name");
				
				output.add(courseName + " | " + courseId + " : " + studentName + " | " + studentId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Collections.sort(output);
		
		for(String x: output) {
			System.out.println(x);
		}
		
		
	}
	 
	//inset a grade in the related table
	static void insertGrade(String username, String id, float grade) {
		
		try {
			stat.executeUpdate("UPDATE grade SET `Grade` = " + grade + " WHERE Student_ID = '" + username + "' AND Course_ID = '" + id + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
//methods for validating inputs
	
	//method for checking if an account with username 'username' exists
	static boolean isNew(String username, int mode) {
		
		ResultSet res;
		int cnt = 0;
		
	
		
		try {
			res = stat.executeQuery("select * FROM " + Main.pModeToString(mode) + " WHERE (`ID` = '" + username + "')");
			
			while (res.next()) {
				cnt++;
			}
			
			if (cnt == 0)
				return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
			
	}
	
	//method for checking if a course with ID 'id' exists
	static boolean isNewCourse(String id) {
		
		ResultSet res;
		int cnt = 0;
			
		try {
			res = stat.executeQuery("select * FROM course WHERE (`ID` = '" + id + "')");
			
			while (res.next()) {
				cnt++;
			}
			
			if (cnt == 0)
				return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
					
	}
	
	//method for validating password of an account
	static boolean checkAccount(String usr, String pass, int mode) {
		
		ResultSet res;
		try {
			res = stat.executeQuery("select * FROM " + Main.pModeToString(mode) + " WHERE (`ID` = '" + usr + "')");
			
			while (res.next()) {
				if (res.getString("Password").equals(pass))
					return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	static boolean isInCourse(String username, String id) {
		ResultSet res;
		int cnt = 0;
			
		try {
			res = stat.executeQuery("select * FROM grade WHERE (`Course_ID` = '" + id + "')");
			
			while (res.next()) {
				if (res.getString("Student_ID").equals(username)) {
					cnt++;
				}
			}
			
			if (cnt == 0)
				return false;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	static boolean isCourseFull(String id) {
		ResultSet res;
			
		try {
			res = stat.executeQuery("select * FROM course WHERE (`ID` = '" + id + "')");
			
			while (res.next()) {
				if (res.getInt("Capacity") == 0) {
					return true;
				}
				else
					return false;
			}
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	static boolean doesTeachCourse(String username, String id) {
		ResultSet res;
		int cnt = 0;
			
		try {
			res = stat.executeQuery("select * FROM course WHERE (`ID` = '" + id + "')");
			
			while (res.next()) {
				if (res.getString("Teacher_ID").equals(username)) {
					cnt++;
				}
			}
			
			if (cnt == 0)
				return false;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
