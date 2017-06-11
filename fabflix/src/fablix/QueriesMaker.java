package fablix;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
public class QueriesMaker {
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement ps;
    private String genreString;
    private String allString;
	public QueriesMaker(Connection conn, PreparedStatement ps)
	{
		this.conn = conn;
		this.ps = ps;
        this.genreString = "set @row_number = 0; select * from (select (@row_number:=@row_number + 1) as num, m.title, m.id, m.myear, o.gname from movies as m inner join genres_in_movies as n on m.id=n.movie_id inner join genres as o on o.id=n.genre_id where o.gname='%s' order by '%s') as paged where paged.num < %d;";	
        this.allString = "set @row_number = 0; select * from (select (@row_number:=@row_number + 1) as num, m.title, m.id, m.myear from movies as m order by m.myear) as paged where paged.num < %d;";
    }
    

	// Insert a new star into the database. If the star has a single name, add it as his last_name 
	// and assign an empty string ("") to first_name.
	public void InsertStar(String firstName, String lastName, String dob, String photoURL) throws SQLException
	{
		  String addCustomer = "insert into stars values(default, ?, ?, ?, ?)";
	  	  ps = conn.prepareStatement(addCustomer);
	  	  ps.setString(1, firstName);
	  	  ps.setString(2, lastName);
	  	  if(dob.isEmpty())
	  		  ps.setString(3, null);
	  	  else
	  		  ps.setDate(3, java.sql.Date.valueOf(dob));
	  	  ps.setString(4, photoURL);
	  	  int updates = ps.executeUpdate();
	  	  //return String.format("%d rows changed", updates);

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
		int updates = stmt.executeUpdate(sql);
		System.out.format("%d rows changed\n\n", updates);;
	}
	
	// Provide the metadata of the database; in particular, print out the name of each table and, 
	// for each table, each attribute and its type.
	public ResultSetMetaData[] provideMetaData() throws SQLException
	{
		String[] arr= {"select * from movies;", "select * from stars;", "select * from stars_in_movies;", "select * from genres; ",
				 "select * from genres_in_movies;", "select * from customers;", "select * from sales;", "select * from creditcards;", "select * from employees;"};
		ResultSetMetaData[] results = new ResultSetMetaData[arr.length];
		for (int i = 0; i < arr.length; ++i){
			stmt = conn.createStatement();
			results[i] = printMetaData(stmt.executeQuery(arr[i]));
		}
		return results;
	}

	// Returns a result set of a query
	public ResultSet selectQuery(String query) throws SQLException
	{
		ps = conn.prepareStatement(query);
		return ps.executeQuery();
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
			break;
		default:
			stmt = conn.createStatement();
			int updates = stmt.executeUpdate(input);
			System.out.format("%d rows changed\n\n", updates);
		}
	}
    	
	// Get Movies
	public  String getMoviesActor(String searchString, String genre, String order) {
        String fName = searchString; 
        String lName = "blahblahblah";
        String[] toSplit = searchString.split(" ");
        if(toSplit.length == 2) {
            fName = toSplit[0];
            lName = toSplit[1];
        }
        String query = String.format("select (@row_number:=@row_number + 1) as num, m.title, m.id, m.myear, m.director, m.banner_url from (select distinct m.myear, m.id, m.title, m.director, m.banner_url from movies m, stars s, stars_in_movies sm, genres g, genres_in_movies gm where (m.id = sm.movie_id and s.id = sm.star_id and m.id = gm.movie_id and g.id = gm.genre_id) and ((s.first_name like '%%%s%%' and s.last_name like '%%%s%%') or (s.first_name like '%%%s%%') or (s.last_name like '%%%s%%' ) or (s.id = '%s') or (m.title like '%%%s%%') or (g.gname like '%%%s%%') or (m.myear = '%s') or (m.director like '%%%s%%')) order by %s) as m", fName, lName, searchString, searchString, searchString, searchString, genre, searchString, searchString, order);
		
		return query;
	}
	
	public ResultSet findCreditCard(String email) throws SQLException {
		String check = String.format("select count(*) as total from creditcards cc where cc.email = '%s';", email);
		ps = conn.prepareStatement(check);
		ResultSet rs = ps.executeQuery();
		return rs;
		
	}
	public ResultSet getCreditCard(String email) throws SQLException {
		String check = String.format("select cc.id as card from creditcards cc where cc.email = '%s';", email);
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
  	  	int updates = ps.executeUpdate();
		System.out.format("%d rows changed\n\n", updates);
	}
	
	// Checks if the user is in the customer table
    public boolean isCustomer(String user, String pass) throws SQLException {
		String findCustomer = String.format("select count(*) as total from customers c where c.email = '%s' and c.cpassword = '%s';", user, pass);
                ps = conn.prepareStatement(findCustomer);
		ResultSet rs = ps.executeQuery();
                return notEmpty(rs);
	}

    // Checks if user is in the employees table
    public boolean isEmployee(String user, String pass) throws SQLException {
		String findEmployee = String.format("select count(*) as total from employees where email = '%s' and password = '%s';", user, pass);
                ps = conn.prepareStatement(findEmployee);
		ResultSet rs = ps.executeQuery();
                return notEmpty(rs);
	}
    public boolean isCreditCard(String fname, String lname, String ccid, String expiration) throws SQLException{
        String findCreditCard = String.format("select count(*) as total from creditcards cc where cc.first_name = '%s' and cc.last_name = '%s' and cc.id = '%s' and cc.expiration = '%s';", fname, lname, ccid, expiration);
        ps = conn.prepareStatement(findCreditCard);
        ResultSet rs = ps.executeQuery();
        return notEmpty(rs);
    }    
	
	public boolean notEmpty(ResultSet rs) throws SQLException {
		rs.next();
		return rs.getInt("total") != 0;
	}
	

    public String getPageMovie(String genre, String order)  {
        String query = "";
        if(genre == null){
            query = String.format("select (@row_number:=@row_number + 1) as num, m.title, m.id, m.myear, m.director, m.banner_url from movies m order by %s", order);
        }
        else {
            query = String.format("select (@row_number:=@row_number + 1) as num, m.title, m.id, m.myear, m.director, m.banner_url from (select distinct m.title, m.id, m.myear, m.director, m.banner_url from movies m, genres g, genres_in_movies gm where (m.id = gm.movie_id and g.id = gm.genre_id) and g.gname = '%s' order by %s) as m", genre, order);
        }
        return query;
    }
    public String searchMovie(String toSearch, String order)  {
        String query = String.format("select (@row_number:=@row_number + 1) as num, m.title, m.id, m.myear from movies as m where m.title like '%%%s%%' or m.id='%s' order by %s", toSearch, toSearch, order);
        return query;
    }
    public ResultSet getLast(String toSwitch, int pageLimit) throws SQLException {
        String query = String.format("select * from (%s) as switch order by switch.num desc limit %d;", toSwitch, pageLimit);
        ps = conn.prepareStatement(query);
        ps.execute("set @row_number = 0;");
        return ps.executeQuery();
    }
    public ResultSet paginate(String toPage, int offset, int pageLimit) throws SQLException {
        String query = String.format("select * from (%s) as paged where paged.num > %d and paged.num <= %d;", toPage, offset*pageLimit, (offset+1)*pageLimit);
        ps = conn.prepareStatement(query);
        ps.execute("set @row_number = 0;");
        return ps.executeQuery(); 
    }

    public boolean checkUser(String username) throws SQLException {
        String query = String.format("select count(*) as total from userlog where email='%s' and ison=1;", username); 
        ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        return notEmpty(rs);
    }
    public void logOff(String username) throws SQLException {
        String query = String.format("update userlog set ison=0 where email='%s' and ison=1;", username);
        ps = conn.prepareStatement(query);
        ps.executeUpdate();
    }
    public void addUser(String username) throws SQLException {
        String addCustomer = "insert into customers values(?,default,default,default)";
        ps = conn.prepareStatement(addCustomer);  
        ps.setString(1, username);
        int updates = ps.executeUpdate();  
    }
	
	public ResultSetMetaData printMetaData(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
	    if (!rs.next()) {
	    	return null;
	    }
	    else{
	    	return rsmd;
	    }
	}
	
	public void callProcedure(String query) throws SQLException{
		ps = conn.prepareStatement(query);
		int updates = ps.executeUpdate();
		//return String.format("%d rows changed", updates);
	}
	
	//Drop down search : title
	public String movieDropList(String searchString){
		String[] tokenize = searchString.split(" ");
		String concat = "";
		for(int i = 0; i < tokenize.length-1; ++i){
			concat += String.format("+%s ", tokenize[i]);
		}
		concat += String.format("+%s*", tokenize[tokenize.length-1]);
		String query = String.format("select title from movies where match (title) against ('%s' in boolean mode) limit 10;", concat); 
		return query;
	}
	
}

