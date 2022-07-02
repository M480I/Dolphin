import java.sql.*;
import java.util.*;
import javax.sql.*;

public class Main {
	
	static public Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		

			
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
