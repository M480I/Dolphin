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

class Principal extends Person {

  public Principal(String username, String password, String name) {
    super(username, password, name);
    
  }
}

class Student extends Person {
  public Student(String username, String password, String name) {
    super(username, password, name);
    
  }
}

class Teacher extends Person {

	private String rank;

	public Teacher(String username, String password, String name, String rank) {
		super(username, password, name);
		this.rank = rank;
		// TODO Auto-generated constructor stub
		
	}

	public String getRank() {
		return rank;
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
