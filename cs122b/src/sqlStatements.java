import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
//select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id group by m.id;
public class sqlStatements
{
	public static void getMovies(PreparedStatement ps, Connection conn, String fName, String lName, String id) throws SQLException {
		String query = "";
		ResultSet rs = null;
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
		rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();
	    if (!rs.next()) {
	    	System.out.println("No results found.");
	    }
	    do{
	    	//Print one row          
	    	for(int i = 1 ; i <= columnsNumber; i++){
	    		System.out.print(rs.getString(i) + " "); //Print one element of a row
	    	}
	    	System.out.println();//Move to the next line to print the next row.           
		} while(rs.next());
				
	}
	
	public static ResultSet findCreditCard(PreparedStatement ps, Connection conn, String fName, String lName) throws SQLException {
		String check = String.format("select count(*) as total from creditcards cc where cc.first_name = '%s' and cc.last_name = '%s';", fName, lName);
		ps = conn.prepareStatement(check);
		ResultSet rs = ps.executeQuery();
		return rs;
		
	}
	public static ResultSet getCreditCard(PreparedStatement ps, Connection conn, String fName, String lName) throws SQLException {
		String check = String.format("select cc.id as card from creditcards cc where cc.first_name = '%s' and cc.last_name = '%s';", fName, lName);
		ps = conn.prepareStatement(check);
		ResultSet rs = ps.executeQuery();
		return rs;
		
	}
	public static void insertCustomer(PreparedStatement ps, Connection conn, String fName, String lName, String ccid, String address, String email, String password) throws SQLException{
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
	public static boolean notEmpty(ResultSet rs) throws SQLException {
		rs.next();
		return rs.getInt("total") != 0;
	}
}
