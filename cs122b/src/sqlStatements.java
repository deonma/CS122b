import java.util.Scanner;
//select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id group by m.id;
public class sqlStatements
{
	public static String getMovies(String query) {
		String[] parts = query.split(" ");
		String fName, lName;
		if (parts.length == 1) {
		    try { 
		        Integer.parseInt(query); 
		        return String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and sm.star_id = %s and s.id = %s group by m.id;", query, query);
		    } catch(NumberFormatException e) { 
		        return String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id and (s.first_name = '%s' or s.last_name = '%s');", query, query);
		    }
		}
		else if (parts.length == 2) {
			fName = parts[0];
			lName = parts[1];
			return String.format("select m.id, m.title, m.myear, m.director, m.banner_url, m.trailer_url from movies m, stars s, stars_in_movies sm where m.id = sm.movie_id and s.id = sm.star_id and s.first_name = '%s' and s.last_name = '%s';", fName, lName);
		}
		else {
			return "f";
		}
		
	}
	
	public static String findCreditCard(String fName, String lName) {
		return String.format("select count(*) as total from creditcards cc where cc.first_name = '%s' and cc.last_name = '%s';", fName, lName);
	}
}
