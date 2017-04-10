import java.sql.*;

public class QueriesMaker {
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement ps;
	public QueriesMaker(Connection conn, PreparedStatement ps)
	{
		this.conn = conn;
		this.ps = ps;
	}

	// Insert a new star into the database. If the star has a single name, add it as his last_name 
	// and assign an empty string ("") to first_name.
	public void InsertStar(String firstName, String lastName, String dob, String photoURL) throws SQLException
	{
		  String addCustomer = "insert into stars values(default, ?, ?, ?, ?)";
	  	  ps = conn.prepareStatement(addCustomer);
	  	  ps.setString(1, firstName);
	  	  ps.setString(2, lastName);
	  	  if (dob.isEmpty())
	  		  ps.setString(3, null);
	  	  else
	  		  ps.setDate(3, java.sql.Date.valueOf(dob));
	  	  ps.setString(4, photoURL);
	  	  ps.executeUpdate();
	}

	// Delete a customer from the database.
	public void deleteCustomer(String ID, String firstName, String lastName) throws SQLException
	{
		String sql;
		
		if (!ID.isEmpty())
		{
				sql = "delete from customers where id = " + ID + ";";
		}
		else {
			if (firstName.isEmpty())
				sql = "delete from customers where last_name = '" + lastName + "';";
			else if (lastName.isEmpty())
				sql = "delete from customers where first_name = '" + firstName + "';";
			else 
				sql = "delete from customers where first_name = '" + firstName + "' and last_name = '"  + lastName + "';";
		}
		stmt = conn.createStatement();
		stmt.executeUpdate(sql);
	}
	
	// Provide the metadata of the database; in particular, print out the name of each table and, 
	// for each table, each attribute and its type.
	public void provideMetaData() throws SQLException
	{
		String[] arr= {"select * from movies;", "select * from stars;", "select * from stars_in_movies;", "select * from genres; ",
				 "select * from genres_in_movies;", "select * from customers;", "select * from sales;", "select * from creditcards;"};
		for (String a: arr){
			stmt = conn.createStatement();
			printResultSet(stmt.executeQuery(a));
		}
	}
	
	// Enter a valid SELECT/UPDATE/INSERT/DELETE SQL command. 
	// The system should take the corresponding action, and return and display the valid results.
	public void createQuery(String input) throws SQLException
	{
		String query = input.substring(0, input.indexOf(' '));
		switch(query.toUpperCase())
		{
		case "SELECT":
			stmt = conn.createStatement();
			stmt.executeQuery(input);
			break;
		default:
			stmt = conn.createStatement();
			stmt.executeUpdate(input);
		}
	}
	
	// Get Movies
	public void getMovies(String fName, String lName, String id) throws SQLException {
		String query = "";
		if (!id.isEmpty()) { 
			query = String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and sm.star_id = %s and s.id = %s group by m.id;", id, id);
		}
		if (fName.isEmpty() && !lName.isEmpty()) {
		        query = String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id and s.last_name = '%s';", lName);
		    }
		if (!fName.isEmpty() && lName.isEmpty()) {
	        query = String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id and s.first_name = '%s';", fName);
	    }
		if (!fName.isEmpty() && !lName.isEmpty()) {
			query = String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id and s.first_name = '%s' and s.last_name = '%s';", fName, lName);
		}
		ps = conn.prepareStatement(query);
		printResultSet(ps.executeQuery(query));
	}
	
	public ResultSet findCreditCard(String fName, String lName) throws SQLException {
		String check = String.format("select count(*) as total from creditcards cc where cc.first_name = '%s' and cc.last_name = '%s';", fName, lName);
		ps = conn.prepareStatement(check);
		ResultSet rs = ps.executeQuery();
		return rs;
		
	}
	public ResultSet getCreditCard(String fName, String lName) throws SQLException {
		String check = String.format("select cc.id as card from creditcards cc where cc.first_name = '%s' and cc.last_name = '%s';", fName, lName);
		ps = conn.prepareStatement(check);
		ResultSet rs = ps.executeQuery();
		return rs;
		
	}
	public void insertCustomer(String fName, String lName, String ccid, String address, String email, String password) throws SQLException{
	  String addCustomer = "insert into customers values(default, ?, ?, ?, ?, ?, ?)";
  	  ps = conn.prepareStatement(addCustomer);
  	  ps.setString(1, fName);
  	  ps.setString(2, lName);
  	  ps.setString(3, ccid);
  	  ps.setString(4, address);
  	  ps.setString(5, email);
  	  ps.setString(6, password);
  	  ps.executeUpdate();
	}
	
	public boolean notEmpty(ResultSet rs) throws SQLException {
		rs.next();
		return rs.getInt("total") != 0;
	}
	
	public void printResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();
	    if (!rs.next()) {
	    	System.out.println("No results found.\n");
	    }
	    else{
		    do{
		    	//Print one row          
		    	for(int i = 1 ; i <= columnsNumber; i++){
		    		System.out.print(rs.getString(i) + " "); //Print one element of a row
		    	}
		    	System.out.println();//Move to the next line to print the next row.           
			} while(rs.next());	
	    }
	}
}
