//import java.sql.*;
//import java.util.Scanner;
//
//public class DBUserInterface {
//
//	Connection conn;
//	Statement stmt;
//	QueriesMaker queries;
//	private Scanner reader;
//	public DBUserInterface(Connection conn) {
//		this.conn = conn;
//		queries = new QueriesMaker(conn);
//		reader = new Scanner(System.in);
//	}
//	
//	public void insertStarUI() throws SQLException {
//		String firstName;
//		String lastName;
//		while (true) {
//			System.out.print("Enter the First Name of the Star:");
//			firstName = reader.nextLine();
//			System.out.print("Enter the Last Name of the Star:");
//			lastName = reader.nextLine();
//			if (lastName.isEmpty())
//			{
//				System.out.println("ERROR");
//				continue;
//			}
//			queries.InsertStar(firstName, lastName);
//		}
//	}
//	
//	
//	public void insertCustomerUI() throws SQLException {
//		
//		
//	}
//	
//	public void deleteCustomerUI() throws SQLException {
//		String ID;
//		String firstName = "";
//		String lastName = "";
//		reader = new Scanner(System.in);
//		while (true) {
//			System.out.print("Enter the ID of the Customer: ");
//			ID = reader.nextLine();
//			if (ID.isEmpty()) {
//				System.out.print("Enter the First Name of the Customer:");
//				firstName = reader.nextLine();
//				System.out.print("Enter the Last Name of the Customer:");
//				lastName = reader.nextLine();
//			}
//			if (ID.isEmpty() && firstName.isEmpty() && lastName.isEmpty())
//			{
//				System.out.println("ERROR");
//				continue;
//			}
//			queries.deleteCustomer(ID, firstName, lastName);
//		}
//	}
//	
//	public void provideMetaDataUI() throws SQLException {
//		ResultSet results = queries.provideMetaData();
//		// Do w/e with it
//	}
//	
//	public void createQueryUI() throws SQLException {
//		String input;
//		while (true) {
//			System.out.print("Write a SQL Query:");
//			input = reader.nextLine();
//			queries.createQuery(input);
//		}
//	}
//}
