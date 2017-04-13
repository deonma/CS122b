import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
public class dbInterface
{
	private Connection conn;
	private Statement stmt;
	private QueriesMaker queries;
	private PreparedStatement ps;
	private Scanner reader;
	public dbInterface(){ 
		reader = new Scanner(System.in);
	
	}

	// Login and Logout methods 
	public void  loginMenu() {
		while (true) {
			System.out.print("Select Option: \n"
					+ "A: Login \nB: Exit \n"
					+ "Enter::");
			String choice = reader.nextLine().toLowerCase();
			if (choice.equals("a")){
				employeeLogin();
			} else if (choice.equals("b")){
				System.out.println("Have a nice day!");
				break;
			} else
				System.out.println("Invalid command. Please choose a valid option: a, b.");
		}
	}
	
	public void employeeLogin() {
		String username, password, DB_URL = "jdbc:mysql://localhost/";
		System.out.print("Enter a database: ");
		DB_URL += reader.nextLine();
		System.out.print("Enter username: ");
		username = reader.nextLine();
		System.out.print("Enter password: ");
		password = reader.nextLine();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, username, password);
			System.out.println("Login successful. Connecting to database...");
			queries = new QueriesMaker(conn, ps);
			executeQuery();
		} catch(SQLException se){
		  //Handle errors for JDBC
			System.out.format("Error: %s\n\n" ,se.getMessage());
		} catch(Exception e){
		  //Handle errors for Class.forName
			System.out.format("Error: %s\n\n" ,e.getMessage());
		}	   
	}

	// Menu method
	public String selectOption(Scanner reader) {
		Set<String> options = new HashSet<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g"));
		String menu = "Select Option:\n"
			+ "A: Find Movie Given a Star\n"
			+ "B: Insert Star into Database\n"
			+ "C: Insert Customer into Database\n"
			+ "D: Delete Customer from Database\n"
			+ "E: Provide Meta Data of Database\n"
			+ "F: Execute a SQL Query\n"
			+ "G: Logout";
		String choice = "";
		while(choice != "g") {
			System.out.println(menu);
			System.out.print("Enter::");
			choice = reader.nextLine().toLowerCase();	

			if (menu.contains(choice) ) {
				break;
			}
			else {
				System.out.println("Please choose valid option: a, b, c, d, e, f, g");
			}
		}
		return choice;
		
	}
	public void executeQuery() {

		String option = "";
		loop: while(true) {
			try {
				option = selectOption(reader);
				switch(option) {
					case "a":
						getMovieUI();
						break;
					case "b":
						insertStarUI();
						break;
					case "c":
						insertCustomerUI();
						break;
					case "d":
						deleteCustomerUI();
						break;
					case "e":
						provideMetaDataUI();
						break;
					case "f":
						createQueryUI();
						break;
					case "g":
						break loop;
				}
			} catch (SQLException e) {
				System.out.println("test");
			}
		}
	}
	
	// Queries
	public void getMovieUI() throws SQLException {
		ResultSet rs = null;
		String fName = "", lName = "", id = "";
		
		System.out.print("Enter ID number:");
		id = reader.nextLine();
		if(!id.isEmpty()){
			try{
				Integer.parseInt(id);
			} catch(NumberFormatException ne){
				System.out.println("ID must be type Integer.\n");
			}
		}
		if(id.isEmpty()) {
			System.out.print("Enter First Name:");
			fName = reader.nextLine();
			System.out.print("Enter Last Name:");
			lName = reader.nextLine();
			if(fName.isEmpty() && lName.isEmpty()) {
				System.out.println("Please input an ID or a First Name and/or Last Name.\n");
			}
		}
		try{
			queries.getMovies(fName, lName, id);
		} catch (SQLException se) {
			System.out.format("Error: %s\n\n" ,se.getMessage());
			return;
		}
		
	}

	public void insertStarUI() throws SQLException {
		String firstName, lastName, dob, photoURL;
		System.out.print("Enter the First Name of the Star:");
		firstName = reader.nextLine();
		System.out.print("Enter the Last Name of the Star:");
		lastName = reader.nextLine();
		if (lastName.isEmpty())
		{
			System.out.println("Error: Please enter a valid name.\n");
		} else {
			System.out.print("Enter the star Date of Birth (in format YYYY-MM-DD):");
			dob = reader.nextLine();
			
			System.out.print("Enter the star Photo URL:");
			photoURL = reader.nextLine();
			try{
				queries.InsertStar(firstName, lastName, dob, photoURL);
			} catch (SQLException se) {
				System.out.format("Error: %s\n\n" ,se.getMessage());
				return;
			}
		}
	}

	//
	public void insertCustomerUI() throws SQLException {
		String fName = "", lName = "", address = "", email = "", password = "";
		System.out.print("Enter First Name:");
		fName = reader.nextLine();
		
		System.out.print("Enter Last Name:");
		lName = reader.nextLine();
		if (lName.isEmpty()) {
			System.out.println("Please Enter a Last Name");
			return;
		}
		ResultSet rs = queries.findCreditCard(fName, lName);
		ResultSet rs1 = queries.getCreditCard( fName, lName);
		
		if(queries.notEmpty(rs)) {
			System.out.print("Enter Address:");
			address = reader.nextLine();
		    if(address.isEmpty()) {
		    	System.out.println("Please enter an address.\n");
		    	return;
		    }
		    System.out.print("Enter Email:");
			email = reader.nextLine();
			if(email.isEmpty()) {
				System.out.println("Please enter an email.\n");
				return;
			}    	  
		    System.out.print("Enter Password:");
			password = reader.nextLine();
			if(password.isEmpty()) {
				System.out.println("Please enter a password.\n");
				return;
			}
			rs1.next();
			try{
				queries.insertCustomer(fName, lName, rs1.getString("card"), address, email, password);
			} catch (SQLException se) {
				System.out.format("Error: %s\n\n" ,se.getMessage());
				return;
			}
		}
		else {
			System.out.println("Customer not found\n");
		}
	}
	
	public void deleteCustomerUI() throws SQLException {
		String ID;
		String firstName = "";
		String lastName = "";
		reader = new Scanner(System.in);
		System.out.print("Enter the ID number of the Customer: ");
		ID = reader.nextLine();
		if(!ID.isEmpty()){
			try{
				Integer.parseInt(ID);
			} catch(NumberFormatException ne){
				System.out.println("ID must be type Integer.\n");
				return;
			}
		}
		if (ID.isEmpty()) {
			System.out.print("Enter the First Name of the Customer:");
			firstName = reader.nextLine();
			System.out.print("Enter the Last Name of the Customer:");
			lastName = reader.nextLine();
		}
		if (ID.isEmpty() && firstName.isEmpty() && lastName.isEmpty())
			System.out.println("Error: Please enter a valid ID or a valid first name and/or last name.\n");
		 else{
			try{
			 	queries.deleteCustomer(ID, firstName, lastName);
		 	} catch (SQLException se) {
				System.out.format("Error: %s\n\n" ,se.getMessage());
				return;
			}
		 }
	}
	
	public void provideMetaDataUI() throws SQLException {
		try{
			queries.provideMetaData();
		} catch (SQLException se) {
			System.out.format("Error: %s\n\n" ,se.getMessage());
			return;
		}
	}
	
	public void createQueryUI() throws SQLException {
		String input;
		System.out.print("Write a SQL Query:");
		input = reader.nextLine();
		if(!input.isEmpty()){
			try{
				queries.createQuery(input);
			} catch (SQLException se){
				System.out.println("Enter a proper SQL Query.\n");
			} catch (StringIndexOutOfBoundsException io){
				System.out.println("Enter a proper SQL Query.\n");
			}
		}
		else
			System.out.println("Please enter a query.\n");
	}
}
