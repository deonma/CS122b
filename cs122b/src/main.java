import java.util.Scanner;
import java.sql.*;

public class main {

	static final String DB_URL = "jdbc:mysql://localhost/moviedb";
	static final String USER = "phi";
	static final String PASS = "bigfoot";
	public static void main(String[] args) {
		Connection conn;
		Scanner reader = new Scanner(System.in);
	    //Register JDBC driverafad
	    try {
			Class.forName("com.mysql.jdbc.Driver");
	    //Open a connection
	    System.out.println("Connecting to database...");
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    dbInterface dbInterface = new dbInterface(conn);
		dbInterface.executeQuery();

	    } catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	    } catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	    }	   
	    System.out.println("Goodbye!");
	}
}

