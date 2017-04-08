import java.sql.*;
public class JDBCExample {
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/moviedb";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "123456789";
	   
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
	      System.out.println("Creating database...");
	      PreparedStatement ps = null;
	      //String sql = sqlStatements.getMovies("AshAnTi dOuglAs");
	      
	      String sql = sqlStatements.findCreditCard("xin", "wu");
	      
	      ps = conn.prepareStatement(sql);
	      ResultSet rs = ps.executeQuery();
	      ResultSetMetaData rsmd = rs.getMetaData();
	      int columnsNumber = rsmd.getColumnCount();
	      /*while (rs.next()) {
	    	//Print one row          
	    		for(int i = 1 ; i <= columnsNumber; i++){

	    	      System.out.print(rs.getString(i) + " "); //Print one element of a row

	    		}

	    	System.out.println();//Move to the next line to print the next row.           

			}*/
	      rs.next();
	      System.out.println(rs.getInt("total"));
	      if(rs.getInt("total") != 0) { 
	    	  sql = "insert into customers values(?, ?, ?, ?, ?, ?)";
	    	  ps = conn.prepareStatement(sql);
	    	  ps.setString(1, "xin");
	    	  ps.setString(2, "wu");
	    	  ps.setString(3, "06217777777777062177");
	    	  ps.setString(4, "x");
	    	  ps.setString(5, "xinwu@email.com");
	    	  ps.setString(6, "password");
	    	  ps.executeUpdate();
	      }

	      

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
	   System.out.println("Goodbye!");
	}//end main
	}//end JDBCExample