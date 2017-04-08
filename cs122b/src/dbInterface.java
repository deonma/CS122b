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
		executeQuery();
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
	public static void executeQuery() {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement ps = null;
		try{
			String option = "";

			
			while(option != "s") {
				option = selectOption();
				switch(option) {
					case "a":
						getMovieUI(ps, conn);
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
	public static void getMovieUI(PreparedStatement ps, Connection conn) throws SQLException {
		ResultSet rs = null;
		while(true){
			Scanner reader = new Scanner(System.in);
			System.out.print("Enter Last Name and/or First Name or ID of Star::");
			String star = reader.nextLine();
			reader.close();
			String result = sqlStatements.getMovies(star);
			if (result == "f") {
				System.out.println("Please Enter Valid Star");
				continue;
			}
			ps = conn.prepareStatement(result);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
		    int columnsNumber = rsmd.getColumnCount();
		    while (rs.next()) {
		    	//Print one row          
		    	for(int i = 1 ; i <= columnsNumber; i++){
		    		System.out.print(rs.getString(i) + " "); //Print one element of a row
		    	}
		    	System.out.println();//Move to the next line to print the next row.           
			}
			
		}
		
	}
	
}
