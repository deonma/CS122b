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
	public dbInterface(Connection conn){ 
		this.conn = conn;
		queries = new QueriesMaker(conn, ps);
		reader = new Scanner(System.in);
	
	}

	public static String selectOption(Scanner reader) {
		Set<String> options = new HashSet<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "s"));
		String menu = "Select Option:\n"
			+ "A: Find Movie Given a Star\n"
			+ "B: Insert Star into Database\n"
			+ "C: Insert Customer into Database\n"
			+ "D: Delete Customer from Database\n"
			+ "E: Provide Meta Data of Database\n"
			+ "F: Execute a SQL Query\n"
			+ "S: Stop";
		String choice = "";
		while(choice != "s") {
			System.out.println(menu);
			System.out.print("Enter::");
			choice = reader.nextLine().toLowerCase();	

			if (menu.contains(choice) ) {
				break;
			}
			else {
				System.out.println("Please choose valid option: a, b, c, d, e, f, s");
			}
		}
		return choice;
		
	}
	public void executeQuery() throws SQLException {

		String option = "";
		loop: while(true) {
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
				case "s":
					break loop;
			}
		}
	}
	
	//
	public void getMovieUI() throws SQLException {
		ResultSet rs = null;
		String fName = "", lName = "", id = "";
		
		while(true){
			System.out.print("Enter ID:");
			id = reader.nextLine();
			while(true) {
				if(id.isEmpty()) {
					System.out.print("Enter First Name:");
					fName = reader.nextLine();
					System.out.print("Enter Last Name:");
					lName = reader.nextLine();
					if(fName.isEmpty() && lName.isEmpty()) {
						System.out.println("Please input First Name and/or Last Name");
						continue;
					}

				}
				break;
			}
			
			break;
			
		}
		queries.getMovies(fName, lName, id);

		
	}

	public void insertStarUI() throws SQLException {
		String firstName, lastName, dob, photoURL;
		while (true) {
			System.out.print("Enter the First Name of the Star:");
			firstName = reader.nextLine();
			System.out.print("Enter the Last Name of the Star:");
			lastName = reader.nextLine();
			if (lastName.isEmpty())
			{
				System.out.println("ERROR");
				continue;
			}
			System.out.print("Enter the star Date of Birth:");
			dob = reader.nextLine();
			
			System.out.print("Enter the star Photo URL:");
			photoURL = reader.nextLine();
			queries.InsertStar(firstName, lastName, dob, photoURL);
			break;
		}
	}

	//
	public void insertCustomerUI() throws SQLException {
		String fName = "", lName = "", address = "", email = "", password = "";
		System.out.print("Enter First Name:");
		fName = reader.nextLine();
		while(true) {
			System.out.print("Enter Last Name:");
			lName = reader.nextLine();
			if (lName.isEmpty()) {
				System.out.println("Please Enter a Last Name");
				continue;
			}
			break;
		}
		ResultSet rs = sqlStatements.findCreditCard(ps, conn, fName, lName);
		ResultSet rs1 = sqlStatements.getCreditCard(ps, conn, fName, lName);
		
		if(sqlStatements.notEmpty(rs)) {
	    	  while(true) {
		    	  System.out.print("Enter Address:");
		    	  address = reader.nextLine();
		    	  if(address.isEmpty()) {
		    		  System.out.println("Please enter address");
		    		  continue;
		    	  }
		    	  break;
		      }
	    	  while(true) {
		    	  System.out.print("Enter Email:");
		    	  email = reader.nextLine();
		    	  if(email.isEmpty()) {
		    		  System.out.println("Please enter email");
		    		  continue;
		    	  }
		    	  break;
		      }	    	  
	    	  while(true) {
		    	  System.out.print("Enter Password:");
		    	  password = reader.nextLine();
		    	  if(password.isEmpty()) {
		    		  System.out.println("Please enter password");
		    		  continue;
		    	  }
		    	  break;
		      }
	    	  rs1.next();
	    	  queries.insertCustomer(fName, lName, rs1.getString("card"), address, email, password);

	    	  
		}
		else {
			System.out.println("Customer not found");
		}
	}
	
	public void deleteCustomerUI() throws SQLException {
		String ID;
		String firstName = "";
		String lastName = "";
		reader = new Scanner(System.in);
		while (true) {
			System.out.print("Enter the ID of the Customer: ");
			ID = reader.nextLine();
			if (ID.isEmpty()) {
				System.out.print("Enter the First Name of the Customer:");
				firstName = reader.nextLine();
				System.out.print("Enter the Last Name of the Customer:");
				lastName = reader.nextLine();
			}
			if (ID.isEmpty() && firstName.isEmpty() && lastName.isEmpty())
			{
				System.out.println("ERROR");
				continue;
			}
			queries.deleteCustomer(ID, firstName, lastName);
			break;
		}
	}
	
	public void provideMetaDataUI() throws SQLException {
		queries.provideMetaData();
	}
	
	public void createQueryUI() throws SQLException {
		String input;
		while (true) {
			System.out.print("Write a SQL Query:");
			input = reader.nextLine();
			queries.createQuery(input);
		}
	}
	
}
