package fablix;

import java.sql.*; 
import java.util.*; 
  
public class AccessDB {  
	private Connection conn;
	private Statement stmt;
	private QueriesMaker queries;
	private PreparedStatement ps;
	private ResultSet rs;

	public AccessDB() {
		String DB_URL = "jdbc:mysql://localhost/moviedb";
		String username = "root";
		String password = "123456789";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, username, password);
			queries = new QueriesMaker(conn, ps);
		} catch(SQLException se){
		  //Handle errors for JDBC
		} catch(Exception e){
		  //Handle errors for Class.forName
		}	   
	}
	public boolean validate(String name,String pass){  
		try{  
			return queries.isCustomer(name, pass);
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
	
	public boolean  validateEmployee(String name, String pass) {
		try{  
			return queries.isEmployee(name, pass);
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
    public boolean validateCC(String fname, String lname, String ccid, String expiration){
        try{
            return queries.isCreditCard(fname, lname, ccid, expiration);
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

	public String getName(String email){
        try{
			rs = queries.selectQuery(String.format("select first_name from customers where email = '%s'", email)); 
			rs.next();
			return rs.getString("first_name");
		}catch(Exception e){
			System.out.println(e);
			return "";
		}
	}
    public ResultSet getPageMovies(int offset, boolean last, String order, int pageLimit, String genre) {
        try{
            if(last){
                return queries.getLast(queries.getPageMovie(genre, order), pageLimit);
            }    
            return queries.paginate(queries.getPageMovie(genre, order), offset, pageLimit);
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }
    public ResultSet getDropList(String name) {
        try{
        		String query = queries.movieDropList(name);
                return queries.selectQuery(query);
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }
    public ResultSet searchForMovie(String toSearch, String genre, String order, int offset, boolean last, int pageLimit)  {
        try{
            if(last){
                return queries.getLast(queries.getMoviesActor(toSearch, genre, order), pageLimit);
            }
            return queries.paginate(queries.getMoviesActor(toSearch, genre, order), offset, pageLimit);
        }catch(Exception e) {
            return rs;
        }
    }
    public ResultSet searchMovies(String toSearch, int offset, boolean last, String order, int pageLimit) {
        try{
            if(last){
                return queries.getLast(queries.searchMovie(toSearch, order), pageLimit);
            }
            return queries.paginate(queries.searchMovie(toSearch, order), offset, pageLimit);
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }
    //Look for movie content
    public ResultSet findMovie(String id){
        try{
            rs = queries.selectQuery(String.format("select * from movies where id = '%s'", id));
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }

    //Find stars that are in the movie
    public ResultSet findStars(String movieID){
        try{
            rs = queries.selectQuery(String.format("select id as s_id, concat(s.first_name, ' ', s.last_name) as name from stars s, stars_in_movies sm where s.id = sm.star_id and sm.movie_id = '%s'", movieID));
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }
    
    //Find the genres of a movie
    public ResultSet findGenre(String movieID){
        try{
            rs = queries.selectQuery(String.format("select gname from genres g, genres_in_movies gm where g.id = gm.genre_id and gm.movie_id = '%s'", movieID));
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }
    
    //Look for contents of a star given ID
    public ResultSet getStar(String starID){
        try{
            rs = queries.selectQuery(String.format("select * from stars where id = '%s'", starID));
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }
 
    public ResultSet findMovies(String starID){
        try{
            rs = queries.selectQuery(String.format("select m.id as m_id, title from movies m, stars_in_movies sm where m.id = sm.movie_id and sm.star_id = '%s'", starID));
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }

    

    public ArrayList<String> getMovie(String id){
        ArrayList<String> movie = new ArrayList<String>();
        try{
            rs = findMovie(id);
            while(rs.next()){
                for(int i = 1; i <= 6; ++i){
                    movie.add(rs.getString(i));
                }
            }
        }catch(Exception e){
        }

        return movie;
    }

    public HashMap<String, String> getStars(String movieID){
        HashMap<String, String> stars = new HashMap<String, String>();
        try{
            rs = findStars(movieID);
            while(rs.next()){
                stars.put(rs.getString("s_id"), rs.getString("name"));
            }
        }catch(Exception e){
        }
        return stars;
    }

    public ArrayList<String> getGenre(String movieID){
        ArrayList<String> genre = new ArrayList<String>();
        try{
            rs = findGenre(movieID);
            while(rs.next()){
                genre.add(rs.getString(1));
            }
        }catch(Exception e){
        }
        return genre;
    }

    public ArrayList<String> getStarInfo(String id) {
        ArrayList<String> star = new ArrayList<String>();
        try{
            rs = getStar(id);
            while(rs.next()){
                for(int i = 1; i <= 5; ++i){
                    star.add(rs.getString(i));
                }
            }
        }catch(Exception e){
        }
        return star;
    }

    public HashMap<String, String> getMovies(String id){
        HashMap<String, String> movies = new HashMap<String, String>();
        try{
            rs = findMovies(id);
            while(rs.next()){
                movies.put(rs.getString("m_id"), rs.getString("title"));
            }
        }catch(Exception e){
        }
        return movies;
    }

    // Get user id from email cookie
    public String getCustomerId(String email)
    {
    	try {
			rs = queries.selectQuery(String.format("select id from customers where email = '%s';", email));
			while(rs.next()){
				return Integer.toString(rs.getInt(1));
			}
			return null;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    public void insertSales(String c_id, String m_id, String date){
        try{
            String query = String.format("insert into sales(customer_id, movie_id, sale_date) values('%s', '%s', '%s')", c_id, m_id, date);
            queries.createQuery(query);
        }catch(Exception e){
        }
    }
    
    public String insertStar(String fname, String lname, String dob, String photoURL){
    	
    	try{
    		String check = String.format("select count(*) as total from stars where first_name='%s' and last_name='%s'", fname, lname);
            rs = queries.selectQuery(check);
            if(queries.notEmpty(rs)){
            	return "Could not add star, already exists in database";
            }
            else{
            	queries.InsertStar(fname, lname, dob, photoURL);
            	return "Star has been added";
            }
        }catch(Exception e){
        	return e.getMessage();
        }
    }
    
    public String bigUpdate(String title, int year, String director, String fname,
    		String lname, String genre){
    	ResultSet mrs, srs, grs;
    	String message = "", query = "";
    	String m_id = String.format("(select id from movies where title='%s' and myear=%d and director='%s')", title, year, director);
    	String s_id = String.format("(select id from stars where first_name='%s' and last_name='%s')", fname, lname);
    	String g_id = String.format("(select id from genres where gname='%s')", genre);
    	
    	try{//Check movie
    		query = String.format("select count(*) as mtotal from movies where title='%s' and myear=%d and director='%s'", title, year, director);
			rs = queries.selectQuery(query);
			rs.next();
			if(rs.getInt("mtotal") == 0)
				message += "Movie has been added<br>";
			else
				message += "Could not add movie, already exists in database<br>";
			
			//Check star
			query = String.format("select count(*) as stotal from stars where first_name='%s' and last_name='%s'", fname, lname);
			rs = queries.selectQuery(query);
			rs.next();
			if(rs.getInt("stotal") == 0)
				message += "Star has been added<br>";
			else
				message += "Could not add star, already exists in database<br>";
			
			//Check stars_in_movies
			query = String.format("select count(*) as sitotal from stars_in_movies where star_id=%s and movie_id=%s", s_id, m_id);
			rs = queries.selectQuery(query);
			rs.next();
			if(rs.getInt("sitotal") == 0)
				message += "Star has been linked to movie<br>";
			else
				message += "Star is already linked to movie<br>";
			
			//Check genre
			query = String.format("select count(*) as qtotal from genres where gname='%s'", genre);
			rs = queries.selectQuery(query);
			rs.next();
			if(rs.getInt("qtotal") == 0)
				message += "Genre has been added<br>";
			else
				message += "Could not add genre, already exists in database<br>";
			
			//Check genre_in_movie
			query = String.format("select count(*) as gitotal from genres_in_movies where genre_id=%s and movie_id=%s", g_id, m_id);
			rs = queries.selectQuery(query);
			rs.next();
			if(rs.getInt("gitotal") == 0)
				message += "Genre has been linked to movie<br>";
			else
				message += "Genre is already linked to movie<br>";
			
    		return message;
    	}catch(Exception e){
    		return e.getMessage();
    	}
    }
    
    public String addMovie(String title, int year, String director, String fname,
    		String lname, String genre){
    	String message = bigUpdate(title, year, director, fname, lname, genre);
    	try{
    		//Check if exists : movie, star, genre
    		String query = String.format("call add_movie('%s', %d, '%s', '%s', '%s', '%s')", title, year, director, fname, lname, genre);
    		queries.callProcedure(query);
    		return message;
    	}catch(Exception e){
    		return e.getMessage();
        }
    }
    
    public ResultSetMetaData[] provideMetadata() {
    	try {
			return queries.provideMetaData();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    } 

}  
