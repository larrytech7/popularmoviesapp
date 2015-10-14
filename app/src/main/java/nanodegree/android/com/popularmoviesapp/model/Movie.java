package nanodegree.android.com.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Larry Akah on 10/14/15.
 * Represents the movie class for a movie on the api
 */
public class Movie implements Parcelable{

    private long movie_id;
    private String movie_poster_url;
    private String movie_title;
    private String movie_overview;
    private float movie_rating;
    private String movie_release_date;

    public Movie(long id, String poster, String title, String overview, float rating,String releasedate){
        this.movie_id = id;
        this.movie_poster_url = poster;
        this.movie_title  = title;
        this.movie_overview = overview;
        this.movie_rating = rating;
        this.movie_release_date = releasedate;
    }

    public Movie(long id){
        this.movie_id = id;
    }
    //parcel constructor
    public Movie(Parcel parcel){
        this.movie_id = parcel.readLong();
        this.movie_poster_url = parcel.readString();
        this.movie_title  = parcel.readString();
        this.movie_overview = parcel.readString();
        this.movie_rating = parcel.readFloat();
        this.movie_release_date = parcel.readString();
    }

    public long getMovie_id() {
        return this.movie_id;
    }

    public void setMovie_id(long movie_id) {
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

    public float getMovie_rating() {
        return this.movie_rating;
    }

    public void setMovie_rating(float movie_rating) {
        this.movie_rating = movie_rating;
    }

    public String getMovie_release_date() {
        return this.movie_release_date;
    }

    public void setMovie_release_date(String movie_release_date) {
        this.movie_release_date = movie_release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
