import java.sql.*;  
  
public class AccessDB {  
	private Connection conn;
	private static Statement stmt;
	private static QueriesMaker queries;
	private static PreparedStatement ps;
	private static ResultSet rs;

	public AccessDB() {
		String DB_URL = "jdbc:mysql://localhost/moviedb";
		String username = "root";
		String password = "123456789";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, username, password);
			System.out.println("Login successful. Connecting to database...");
			queries = new QueriesMaker(conn, ps);
		} catch(SQLException se){
		  //Handle errors for JDBC
		} catch(Exception e){
		  //Handle errors for Class.forName
		}	   
	}
	public static boolean validate(String name,String pass){  
		try{  
			return queries.isCustomer(name, pass);
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}

	public static String getName(String email){
        try{
			rs = queries.selectQuery(String.format("select first_name from customers where email = '%s'", email)); 
			rs.next();
			return rs.getString("first_name");
		}catch(Exception e){
			System.out.println(e);
			return "";
		}
	}
    public static ResultSet getPageMovies(int offset) {
        try{
            return queries.getPageMovie(offset);
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }
    public static ResultSet searchForMovie(String toSearch, int offset) {
        try{
            return queries.searchMovie(toSearch, offset); 
        }catch(Exception e) {
            return rs;
        }
        
    } 

    public static ResultSet findMovie(String title){
        try{
            rs = queries.selectQuery(String.format("select * from movies where title = '%s'", title));
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    } 
}  
