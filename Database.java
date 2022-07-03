import java.sql.*;
import java.util.*;

class Database {
		
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
	
	static void insertCourse(Course c) {
		
		try {
			stat.executeUpdate("INSERT INTO course VALUES ('" + c.getId() + "', '" + c.getName() + "', " + c.getCapacity() + ", '" + c.getTeacher().getName() + "', '" + c.getTeacher().getUsername() + "');");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	static void deletePerson(Person p) {
		
		
		try {
			stat.executeUpdate("DELETE FROM " + p.getClass().getSimpleName() + " WHERE (`ID` = '" + p.getUsername() + "');");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
	
	
	static void changePassword(Person p) {
		
		try {
			stat.executeUpdate("UPDATE " + p.getClass().getSimpleName() + " SET `Password` = '" + p.getPassword() + "' WHERE (`ID` = '" + p.getUsername() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	
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
	
}
