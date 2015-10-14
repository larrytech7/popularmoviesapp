package nanodegree.android.com.popularmoviesapp.model;

/**
 * Created by Larry Akah on 10/14/15.
 * Represents the movie class for a movie on the api
 */
public class Movie {

    private String movie_id;
    private String movie_poster_url;
    private String movie_title;
    private String movie_overview;
    private String movie_rating;
    private String movie_release_date;

    public Movie(String id, String poster, String title, String overview, String rating,String releasedate){
        this.movie_id = id;
        this.movie_poster_url = poster;
        this.movie_title  = title;
        this.movie_overview = overview;
        this.movie_rating = rating;
        this.movie_release_date = releasedate;
    }

    public Movie(String id){
        this.movie_id = id;
    }

    public String getMovie_id() {
        return this.movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_poster_url() {
        return this.movie_poster_url;
    }

    public void setMovie_poster_url(String movie_poster_url) {
        this.movie_poster_url = movie_poster_url;
    }

    public String getMovie_title() {
        return this.movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_overview() {
        return this.movie_overview;
    }

    public void setMovie_overview(String movie_overview) {
        this.movie_overview = movie_overview;
    }

    public String getMovie_rating() {
        return this.movie_rating;
    }

    public void setMovie_rating(String movie_rating) {
        this.movie_rating = movie_rating;
    }

    public String getMovie_release_date() {
        return this.movie_release_date;
    }

    public void setMovie_release_date(String movie_release_date) {
        this.movie_release_date = movie_release_date;
    }
}
