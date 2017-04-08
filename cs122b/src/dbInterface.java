package cs122b;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
public class dbInterface
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/moviedb";
	static final String USER = "root";
	static final String PASS = "123456789";
	public static void main(String[] args) {
		
	}
	public static String selectOption() {
		Set<String> options = new HashSet<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "s"));
		String menu = "Select Option:\n"
			+ "A: Find Movie Given a Star\n"
			+ "B: Insert Star into Database\n"
			+ "C: Insert Customer into Database\n"
			+ "D: Delete Customer from Database\n"
			+ "E: Provide Meta Data of Database\n"
			+ "F: Execute a SQL Query\n"
			+ "S: Stop";
		while(true) {
			System.out.println(menu);
			Scanner reader = new Scanner(System.in);
			System.out.print("Enter::");
			String choice = reader.nextLine().toLowerCase();
			reader.close();
			if (menu.contains(choice) ) {
				return choice;
			}
			else {
				System.out.println("Please choose valid option: a, b, c, d, e, f, s");
			}
		}
		
	}
	public void executeQuery() {
		String option = "";
		Connection conn = null;
		Statement stmt = null;
		while(option != "s") {
			option = selectOption();
			switch(option) {
				case "a":
					break;
				case "b":
					break;
				case "c":
					break;
				case "d":
					break;
				case "e":
					break;
				
			}
			
		}

		
	}
	public void getMovieUI() {
		while(true){
			Scanner reader = new Scanner(System.in);
			System.out.print("Enter Last Name and/or First Name or ID of Star::");
			String star = reader.nextLine();
			reader.close();
			String result = sqlStatements.getMovies();
		}
		
	}
	
}
