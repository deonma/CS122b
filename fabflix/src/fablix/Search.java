package fablix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


import java.util.ArrayList;
import java.util.HashMap;
public class Search extends HttpServlet {
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	

    	long TSstartTime = System.nanoTime();

        AccessDB access = new AccessDB(0);
        ResultSet rs;
        response.setContentType("text/html");
        String searchString = request.getParameter("searchstring");
        String scrollString = request.getParameter("scrollString");
        String sortString = request.getParameter("sortString"); 
        String sortBy = request.getParameter("sortBy");
        String genre = request.getParameter("genre");
        String genreString = request.getParameter("genreString");
        String toSearch = null;
        String pageLimit= request.getParameter("paginate");
        String changePage = request.getParameter("changePage");  
        Cookie offset = ProcessCookies.get(request, "offset");
        Cookie pageCookie = ProcessCookies.get(request, "pageCookie");
        boolean last = false; 
        if(offset == null) {
            offset = new Cookie("offset", "0");
        } 
        if(pageCookie == null) {
            pageCookie = new Cookie("pageCookie", "5");
        }
        if(pageLimit != null) {
            pageCookie.setValue(pageLimit);
            offset.setValue("0");
        }
        request.setAttribute("pageSize", pageCookie.getValue());
        if(scrollString != null) {
            request.setAttribute("searchString", scrollString);
            toSearch = scrollString;
        }
        if(searchString != null && searchString != "") {
            request.setAttribute("searchString", searchString);
            offset.setValue("0");
            toSearch = searchString;
        }
        if((request.getParameter("enter") != null && searchString != null && searchString == "") || request.getParameter("title") != null) {
            request.setAttribute("searchString", searchString);
            offset.setValue("0");
            toSearch = searchString;
        }
        if(toSearch != null){
            toSearch = toSearch.trim();
        }
        if (genre != null) {
            offset = new Cookie("offset", "0");
            request.setAttribute("genreString", genre);
            genreString = genre;
        }
        if (genreString != null) {
            request.setAttribute("genreString", genreString);
        }
    
        if(changePage != null) { 
            if(changePage.equals("<") && !offset.getValue().equals("0")) {
                offset.setValue(String.valueOf(Integer.parseInt(offset.getValue())-1));
                
            }
            else if(changePage.equals(">")) {
                offset.setValue(String.valueOf(Integer.parseInt(offset.getValue())+1));
            }
            else if(changePage.equals("<<")) {
                offset.setValue("0");
            }   
            else if(changePage.equals(">>")) {
                last = true;
            }
        }
        if(sortString == null) {
            sortString = "m.title";
        }
        if(sortBy != null){
            sortString = sortBy;
            offset.setValue("0");
        }
        request.setAttribute("sortString", sortString);
        

        ArrayList<String> movies= new ArrayList<String>();
        ArrayList<String> idList = new ArrayList<String>();
        ArrayList<String> directors = new ArrayList<String>();
        ArrayList<String> years = new ArrayList<String>();
        ArrayList<String> banners = new ArrayList<String>();
        ArrayList<HashMap<String,String>> stars = new ArrayList<HashMap<String,String>>();
        ArrayList<ArrayList<String>> genres = new ArrayList<ArrayList<String>>();
        try{
        	
        	long TJstartTime = System.nanoTime();
            if (toSearch != null && toSearch != "") 
                rs = access.searchForMovie(toSearch, toSearch, sortString, Integer.parseInt(offset.getValue()), last, Integer.parseInt(pageCookie.getValue()));
            else 
                rs = access.getPageMovies(Integer.parseInt(offset.getValue()), last, sortString, Integer.parseInt(pageCookie.getValue()), genreString);
            long TJendTime = System.nanoTime();
            long TSendTime = System.nanoTime();
            long TSelapsedTime = TSendTime - TSstartTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
            long TJelapsedTime = TJendTime - TJstartTime;
            String string = "TS=" + TSelapsedTime + ",TJ=" + TJelapsedTime + "\n";
            try(FileWriter fw = new FileWriter(MyConstants.LOG_FILE, true);
            	    BufferedWriter bw = new BufferedWriter(fw);
            	    PrintWriter out = new PrintWriter(bw))
            	{
            	    out.println(string);
            	    out.close();
            	} catch (IOException e) {
            	}
            int i = 0, z = 0;    
            if (!rs.next() && offset.getValue().equals("0")) 
            	 request.setAttribute("none", "hello");
            else {
                do {
                if(last){
                    int off = findOffset(rs.getInt(1), Integer.parseInt(pageCookie.getValue()));
                    z = rs.getInt(1) - (off*Integer.parseInt(pageCookie.getValue()));
                    offset.setValue(String.valueOf(off));
                    last = false;
                }
                if(z > 0) {
                    if(i++ < z){
                        movies.add(0, rs.getString(2));
                        idList.add(0, Integer.toString(rs.getInt(3)));
                        directors.add(0, rs.getString(5));
                        years.add(0, rs.getString(4));
                        banners.add(0, rs.getString(6));
                    }
                
                }
                else{
                    movies.add(rs.getString(2));
                    idList.add(Integer.toString(rs.getInt(3)));
                    directors.add(rs.getString(5));
                    years.add(rs.getString(4));
                    banners.add(rs.getString(6));
                }    
            }while(rs.next()); 
            request.setAttribute("Movies", movies);
            request.setAttribute("Ids", idList);
            request.setAttribute("Directors", directors);
            request.setAttribute("Years", years);
            request.setAttribute("Banners", banners);
            } 
        }
        catch(Exception e){
        }
        for(String id : idList){
            stars.add(access.getStars(id));
            genres.add(access.getGenre(id));
                
        }
        request.setAttribute("Stars", stars);
        request.setAttribute("Genres", genres); 
        offset.setMaxAge(60*60*24);
        pageCookie.setMaxAge(60*60*24);
        response.addCookie(offset);
        response.addCookie(pageCookie);
        
        RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
        rd.forward(request,response);
      }
    
    int findOffset(int max, int pageLimit) {
        int i = 0;
        while (pageLimit*i < max) {
            i++;
            
        }
        if( pageLimit*i == max){
            return i;
        }
        return --i;

    }
}


