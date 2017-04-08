import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
//select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id group by m.id;
public class sqlStatements
{
	public static String getMovies(String fName, String lName, String id) {
		
		if (!id.isEmpty()) { 
			return String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and sm.star_id = %s and s.id = %s group by m.id;", id, id);
		}
		if (fName.isEmpty() && !lName.isEmpty()) {
		        return String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id and s.last_name = '%s';", lName);
		    }
		if (!fName.isEmpty() && lName.isEmpty()) {
	        return String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id and s.first_name = '%s';", fName);
	    }
		if (!fName.isEmpty() && !lName.isEmpty()) {
			return String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id and s.first_name = '%s' and s.last_name = '%s';", fName, lName);
		}
		return "f";
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
	  String addCustomer = "insert into customers values(?, ?, ?, ?, ?, ?)";
  	  ps = conn.prepareStatement(addCustomer);
  	  ps.setString(2, fName);
  	  ps.setString(3, lName);
  	  ps.setString(4, ccid);
  	  ps.setString(5, address);
  	  ps.setString(6, email);
  	  ps.setString(7, password);
  	  ps.executeUpdate();
	}
	public static boolean isEmpty(ResultSet rs) throws SQLException {
		rs.next();
		return rs.getInt("total") != 0;
	}
}
