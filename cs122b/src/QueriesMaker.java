import java.sql.*;

public class QueriesMaker {
	public QueriesMaker()
	{
	}
	// Insert a new star into the database. If the star has a single name, add it as his last_name 
	// and assign an empty string ("") to first_name.
	public String InsertStar(String star)
	{
		String sql;
		String[] name = star.split(" ");
		if (name.length == 1) // if user only gives last name
			sql = "INSERT INTO stars (firt_name, last_name) VALUES('', " + name[0] + ");";
		else 
			sql = "INSERT INTO stars (firt_name, last_name) VALUES("+ name[0] + ", " + name[1] + ");";
		return sql;
	}
	
	public String deleteCustomer(String customer)
	{
		String sql;
		String[] parts = customer.split(" ");
		if (parts.length == 1 )
		{
			try {
				Integer.parseInt(customer);
				return "DELETE FROM customers where id = " + customer;
			} catch (NumberFormatException e) {
				return "DELETE FROM customers where first_name = " + customer + "or last_name = " + customer ";";
			}
		} else {
			return "DELETE FROM customers WHERE first_name = " + parts[0] + "and last_name = "  +parts[1] + ";";
		}
	}
	
	public String provideMetaData()
	{
		return "Select * from  
	}
	
	
}
