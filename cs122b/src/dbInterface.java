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
		Scanner reader = new Scanner(System.in);
		executeQuery(reader);
		reader.close();
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
	public static void executeQuery(Scanner reader) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement ps = null;
		try{
		    //Register JDBC driverafad
		    Class.forName("com.mysql.jdbc.Driver");
	
		    //Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String option = "";
			loop: while(true) {
				option = selectOption(reader);
				switch(option) {
					case "a":
						getMovieUI(ps, conn, reader);
						break;
					case "b":
						break;
					case "c":
						insertCustomerUI(ps, conn, reader);
						break;
					case "d":
						break;
					case "e":
						break;
					case "f":
						break;
					case "s":
						break loop;
					
				} 
			}
		   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try
			   System.out.println("Goodbye!");
		
	}
	public static void getMovieUI(PreparedStatement ps, Connection conn, Scanner reader) throws SQLException {
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
		sqlStatements.getMovies(ps, conn, fName, lName, id);

		
	}
	public static void insertCustomerUI(PreparedStatement ps, Connection conn, Scanner reader) throws SQLException {
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
	    	  sqlStatements.insertCustomer(ps, conn, fName, lName, rs1.getString("card"), address, email, password);

	    	  
		}
		else {
			System.out.println("Customer not found");
		}
			
		
	}
	
}
