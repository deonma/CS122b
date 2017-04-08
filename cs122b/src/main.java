import java.sql.*;

public class main {
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/moviedb";

	   //  Database credentials
	   static final String USER = "phi";
	   static final String PASS = "bigfoot";
	   
	public static void main(String[] args) {
		 Connection conn = null;
		 Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);

		      //STEP 4: Execute a query
		      System.out.println("Running statements");
		      
		      String sql = "select m.title from stars s, movies m, stars_in_movies sm WHERE s.first_name = 'Christopher' and s.id = sm.star_id and m.id = sm.movie_id;";
		      stmt.executeQuery(sql);
		      System.out.println("Finished query");
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
	}

}
