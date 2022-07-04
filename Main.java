import java.util.*;

public class Main {
	
	static public Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

	

	}

	// this method brings the entry page 	
	static void enter() {
		
		int mode = inputOption(3, "1 - Sign up\n2 - Sign in\n3 - Exit \nChoose:");

		if (mode == 1) {
			signUp();
		}
		if (mode == 2)
			signIn();
		
		

	}


	// a method for signing in
	private static void signIn() {
		
		int mode = inputOption(3, "1 - Student\n2 - Teacher\n3 - Manager");
		System.out.println("Enter your username:");
		String username = scan.nextLine();
		if (Database.isNew(username, mode) == true) {
			System.out.println("There is not an account with this username in our database. Try again.");
			System.out.println("\n\n");
			enter();
			return;
			
		}
		
		System.out.println("Enter your password:");
		String password = scan.nextLine();
		
		
		if (Database.checkAccount(username, password, mode) == false) {
			System.out.println("Incorrect password. Try again.");
			System.out.println("\n\n");
			enter();
			return;
		}
		
		if (mode == 1) {
			Student.UI(username);
		}
		if (mode == 2) {
			Teacher.UI(username);
		}
		if (mode == 3) {
			Manager.UI(username);
		}

	}
	

	//a method for signing up
	private static void signUp() {
		
		int mode = inputOption(3, "1 - Student\n2 - Teacher\n3 - Manager");
		
		System.out.println("Enter a username:");
		String username = scan.nextLine();
		if (Database.isNew(username, mode) == false) {
			System.out.println("The same username already exists. Try again.");
			System.out.println("\n\n");
			enter();
			return;
			}
		System.out.println("Enter your password:");
		String password = scan.nextLine();
		
		
		if (Database.checkAccount(username, password, mode) == false) {
			System.out.println("Incorrect password. Try again.");
			System.out.println("\n\n");
			enter();
			return;
		}
		
		if (mode == 1) {
			Student.UI(username);
		}
		if (mode == 2) {
			Teacher.UI(username);
		}
		if (mode == 3) {
			Manager.UI(username);
		}
		}
	
		System.out.println("Enter a password:");
		String password = scan.nextLine();
		
		System.out.println("Enter your name:");
		String name = scan.nextLine();
		
		if (mode == 2) {
			int rankMode = inputOption(3, "1 - Assistant Professor\n2 - Associate Professor\n3 - Professor\nChoose:");
			Database.insertPerson(new Teacher(username, password, name, rModeToString(rankMode)));
		}
		if (mode == 1)
			Database.insertPerson(new Student(username, password, name));
		if (mode == 3)
			Database.insertPerson(new Manager(username, password, name));

		
		System.out.println("You are successfuly signed up.");
		System.out.println("\n\n");
		enter();
		
	}

	// a method that lets users choose between multiple options
	private static int inputOption(int cnt, String text) {

		boolean exit = false;
		int mode = 0;

		while (!exit) {

			System.out.println(text);

			boolean flag = false;

			try {
				mode = Integer.parseInt(scan.nextLine());
			}
			catch(Exception e) {
			}

			for (int i = 1; i <= cnt; i++) {
				if (mode == i)
					flag = true;
			}

			if (!flag) {
				System.out.println("Wrong input.\nTry again.");
				continue;		
			}

			exit = true;
			break;

		}

		return mode;

	}
	static String pModeToString(int mode) {
		if (mode == 1)
			return "Student";
		if (mode == 2)
			return "Teacher";
		return "Manager";
		
	}
	static String rModeToString(int mode) {
		if (mode == 1)
			return "Assistant Professor";
		if (mode == 2)
			return "Associate Professor";
		return "Professor";
	}


}


//class person is supercalss of Teacehr and Student and Principal
class Person{
	
	private String username;
	private String password;
	private String name;
	
	public Person(String username, String password, String name) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}

	
}

class Manager extends Person {
	
	static Scanner scan = new Scanner(System.in);
	
	public Manager(String username, String password, String name) {
		super(username, password, name);
	}
	
	// brings the UI after signing in with manager role
	public static void UI(String username) {
		
		int mode = Main.inputOption(10,
				  "\n\n"
				+ "1 - List students\n"
				+ "2 - Add student\n"
				+ "3 - Remove student\n"
				+ "4 - List teachers\n"
				+ "5 - Add teacher\n"
				+ "6 - Remove teacher\n"
				+ "7 - List courses\n"
				+ "8 - Add courses\n"
				+ "9 - Change password\n"
				+ "10 - Log out\n"
				+ "Choose:");
		
		switch(mode) {
		case 1:
			getAllStudents(username);
			break;
	
		case 2:
			addPerson(1, username);
			break;
			
		case 3:
			removePerson(1, username);
			break;		
			
		case 4:
			getAllTeachers(username);
			break;			

		case 5:
			addPerson(2, username);
			break;
			
		case 6:
			removePerson(2, username);
			break;
			
		case 7:
			getAllCourses(username);
			break;			
			
		case 8:
			addCourse(username);
			break;			
			
		case 9:
			changePass(username);
			break;
			
		case 10:
			System.out.println("loging out...");
			System.out.println("\n\n");
			Main.enter();
			break;		
			
		}
		
	}

	private static void changePass(String username) {
		
		System.out.println("Enter a password:");
		String password = scan.nextLine();
		
		Database.changePassword(new Manager(username, password, ""));
		System.out.println("Your password is updated.");
		UI(username);
	}

	private static void addCourse(String mUsername) {
		
		System.out.println("Enter an ID:");
		String ID = scan.nextLine();
		
		if (Database.isNewCourse(ID) == false) {
			System.out.println("The same ID already exists. Try again.");
			System.out.println();
			UI(mUsername);
			return;
		}
		
		System.out.println("Enter a name:");
		String name = scan.nextLine();
		System.out.println("Enter the capacity:");
		int capacity = Integer.parseInt(scan.nextLine());
		
		System.out.println("Enter the teacher's name:");
		String teacherName = scan.nextLine();
		System.out.println("Enter the teacher's username:");
		String teacherUsername = scan.nextLine();
		
		Teacher t = new Teacher(teacherUsername, "", teacherName, "");
		
		
		Course c = new Course(ID, name, capacity, t);
		
		Database.insertCourse(c);
		
		System.out.println("Adding was successful.");
		UI(mUsername);
	}

	private static void removePerson(int mode, String mUsername) {
		
		System.out.println("Enter a username:");
		String username = scan.nextLine();
		
		if(mode == 1) {
			Database.deletePerson(new Student(username, "", ""));
		}
		else {
			Database.deletePerson(new Teacher(username, "", "", ""));
		}
		
		System.out.println("This username is no longer in database.");
		UI(mUsername);
	}

	private static void addPerson(int mode, String mUsername) {
		
		System.out.println("Enter a username:");
		String username = scan.nextLine();
		if (Database.isNew(username, mode) == false) {
			System.out.println("The same username already exists. Try again.");
			System.out.println();
			UI(mUsername);
			return;
		}
		
		System.out.println("Enter a password:");
		String password = scan.nextLine();
		System.out.println("Enter the name:");
		String name = scan.nextLine();
		
		if (mode == 2) {
			int rankMode = Main.inputOption(3, "1 - Assistant Professor\n2 - Associate Professor\n3 - Professor\nChoose:");
			Database.insertPerson(new Teacher(username, password, name, Main.rModeToString(rankMode)));
		}
		else {
			Database.insertPerson(new Student(username, password, name));
		}
		
		System.out.println("Adding was successful.");
		UI(mUsername);
		
	}

	private static void getAllStudents(String username) {
		Database.getAll(0);
		UI(username);
	}
	private static void getAllTeachers(String username) {
		Database.getAll(1);
		UI(username);
	}
	private static void getAllCourses(String username) {
		Database.getAll(2);
		UI(username);
	}
	
}

class Student extends Person {
  public Student(String username, String password, String name) {
    super(username, password, name);
    
  }
	
	// brings the UI after signing in with student role
	public static void UI(String username) {
		
		int mode = Main.inputOption(5,
				  "\n\n"
				+ "1 - List courses\n"
				+ "2 - take course\n"
				+ "3 - check grades\n"
				+ "4 - Change password\n"
				+ "5 - Log out\n"
				+ "Choose:");
		
		switch (mode) {
			
		case 1:
			getAllCourses(username);
			break;
	
		case 2:
			takeCourse(username);
			break;
			
		case 3:
			checkGrades(username);
			break;		
			
		case 4:
			changePass(username);
			break;			

		case 5:
			System.out.println("loging out...");
			System.out.println("\n\n");
			Main.enter();
			break;			
		
		}
		
		
	}
	
	private static void takeCourse(String username) {
		
		System.out.println("Enter an ID:");
		String id = scan.nextLine();
		
		if (Database.isInCourse(username, id) == true) {
			System.out.println("You are already in this course.");
			System.out.println();
			UI(username);
			return;
		}
		if (Database.isNewCourse(id) == true) {
			System.out.println("There is no cousre with the given ID.");
			System.out.println();
			UI(username);
			return;
		}
		if (Database.isCourseFull(id) == true) {
			System.out.println("The selected course is full.");
			System.out.println();
			UI(username);
			return;
		}
		
		
		Course c = new Course(id, "", 0, new Teacher(Database.getTeacherID(id), "", "", ""));
		
		Database.takeCourse(username, c);
		
		System.out.println("You are added to this course.");
		
		UI(username);
		
	}
	
	private static void checkGrades(String username) {
		Database.checkGrades(username);
		UI(username);
	}
	private static void changePass(String username) {
		System.out.println("Enter a password:");
		String password = scan.nextLine();
		
		Database.changePassword(new Student(username, password, ""));
		System.out.println("Your password is updated.");
		UI(username);		
	}


	
}

class Teacher extends Person {

	private String rank;

	public Teacher(String username, String password, String name, String rank) {
		super(username, password, name);
		this.rank = rank;
		
	}

	public String getRank() {
		return rank;
	}
	// brings the UI after signing in with teacher role
	public static void UI(String username) {
		
		int mode = Main.inputOption(5,
				  "\n\n"
				+ "1 - List my courses\n"
				+ "2 - List my students\n"
				+ "3 - grade student\n"
				+ "4 - Change password\n"
				+ "5 - Log out\n"
				+ "Choose:");
		
		switch (mode) {
			
		case 1:
			getAllCourses(username);
			break;
	
		case 2:
			getAllStudents(username);
			break;
			
		case 3:
			gradeStudent(username);
			break;		
			
		case 4:
			changePass(username);
			break;			

		case 5:
			System.out.println("loging out...");
			System.out.println("\n\n");
			Main.enter();
			break;			
		
		}
		
	}
	private static void getAllCourses(String username) {	
		Database.checkCourses(username);
		UI(username);
	}

	private static void getAllStudents(String username) {
		Database.viewStudents(username);
		UI(username);
	}
	private static void gradeStudent(String tUsername) {
		
		System.out.println("Enter the course's ID:");
		String id = scan.nextLine();
		if (Database.isNewCourse(id) == true) {
			System.out.println("There is no cousre with the given ID.");
			System.out.println();
			UI(tUsername);
			return;
		}
		if (Database.doesTeachCourse(tUsername, id) == false) {
			System.out.println("You are not the Teacher of this course.");
			System.out.println();
			UI(tUsername);
			return;
		}
		
		System.out.println("Enter the student's username:");
		String username = scan.nextLine();
		if (Database.isInCourse(username, id) == false) {
			System.out.println("This student is not in this course.");
			System.out.println();
			UI(tUsername);
			return;
		}
		
		System.out.println("Enter the student's grade:");
		float grade = Float.parseFloat(scan.nextLine());
		if (grade > 20 || grade < 0) {
			System.out.println("Grade should be in range 0-20.");
			System.out.println();
			UI(tUsername);
			return;
		}
		
		Database.insertGrade(username, id, grade);
		System.out.println("This student's grade is updated successfully.");
		UI(tUsername);
		
	}
		private static void changePass(String username) {
		System.out.println("Enter a password:");
		String password = scan.nextLine();
		
		Database.changePassword(new Teacher(username, password, "", ""));
		System.out.println("Your password is updated.");
		UI(username);			
	}

	
}


class Course {
	

	private String name;
	private int capacity;
	private Teacher teacher;
	private String id;
		
	public Course(String id, String name, int capacity, Teacher teacher) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.teacher = teacher;
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	public Teacher getTeacher() {
		return teacher;
	}


	public String getId() {
		return id;
	}	
	
	
	
}
