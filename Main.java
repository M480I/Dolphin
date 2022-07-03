import java.sql.*;
import java.util.*;
import javax.sql.*;

public class Main {
	
	static public Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

	

	}

	//entry method
	private static void enter() {


		int mode = inputOption(2, "1 - Sign up\n2 - Sign in\nChoose:");

		if (mode == 1) {
			signUp();
		}
		else
			signIn();


	}

	private static void signIn() {
	
		int mode = inputOption(3, "1 - Student\n2 - Teacher\n3 - Principal");

	}

	private static void signUp() {

		int mode = inputOption(3, "1 - Student\n2 - Teacher\n3 - Principal");


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
  }
	
	
}
