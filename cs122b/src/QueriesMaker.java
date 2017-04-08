import java.sql.*;

public class QueriesMaker {
	
	private Connection conn;
	private Statement stmt;
	public QueriesMaker(Connection conn)
	{
		this.conn = conn;
	}
	// Insert a new star into the database. If the star has a single name, add it as his last_name 
	// and assign an empty string ("") to first_name.
	public void InsertStar(String star) throws SQLException
	{
		String sql;
		String[] name = star.split(" ");
		if (name.length == 1) // if user only gives last name
			sql = "INSERT INTO stars (firt_name, last_name) VALUES('', " + name[0] + ");";
		else 
			sql = "INSERT INTO stars (firt_name, last_name) VALUES("+ name[0] + ", " + name[1] + ");";
	}
	// Delete a customer from the database.
	public void deleteCustomer(String customer) throws SQLException
	{
		String sql;
		String[] parts = customer.split(" ");
		if (parts.length == 1 )
		{
			try {
				Integer.parseInt(customer);
				sql = "delete from customers where id = " + customer;
			} catch (NumberFormatException e) {
				sql = "delete from customers where first_name = " + customer + "or last_name = " + customer + ";";
			}
		} else {
			sql = "DELETE FROM customers WHERE first_name = " + parts[0] + "and last_name = "  +parts[1] + ";";
		}
		stmt = conn.createStatement();
		stmt.executeUpdate(sql);
	}
	
	// Provide the metadata of the database; in particular, print out the name of each table and, 
	// for each table, each attribute and its type.
	public void provideMetaData() throws SQLException
	{
		String sql ="select * from movies; select * from stars; select * from stars_in_movies; select * from genres; "
				+ "select * from genres_in_movies; select * from customers; select * from sales; select * from credit_cards";
		stmt = conn.createStatement();
		stmt.executeQuery(sql);
	}
	
	public void createQuery(String input) throws SQLException
	{
		String query = input.substring(0, input.indexOf(' '));
		switch(query.toUpperCase())
		{
		case "SELECT":
			stmt = conn.createStatement();
			stmt.executeQuery(input);
			break;
		case "UPDATE":
			break;
		case "INSERT":
			break;
		case "DELETE":
			stmt = conn.createStatement();
			stmt.executeUpdate(input);
			break;
		default:
			break;
		}
	}
}
