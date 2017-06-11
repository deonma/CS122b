package fablix;

import java.util.ArrayList;
public class MovieInfo {
    private String id, title, year,director; 
    private String[] stars;
    ArrayList<String> genres;
    public MovieInfo(String id, String title, String year, String director) {
        AccessDB access = new AccessDB(0);
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        genres = access.getGenre(id);
        stars = (String[]) access.getStars(id).values().toArray();
    }
    public String getID () {return id;}
    public String getTitle () {return title;}
    public String getYear () {return year;}
    public String getDirector () {return director;}
    public ArrayList<String> getGenres () {return genres;}
    public String[] getStars () {return stars;}



}
