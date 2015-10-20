package nanodegree.android.com.popularmoviesapp.model;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/17/15 1:54 PM.
 */
public class Trailer {
    private String trailer_title; //name parameter
    private String trailer_synopsis; //type/size parameter
    private String trailer_url; //source parameter

    public Trailer(String ttitle, String tsynopsis, String turl) {

        this.trailer_title = ttitle;
        this.trailer_synopsis = tsynopsis;
        this.trailer_url = turl;
    }


    public String getTrailer_title() {
        return this.trailer_title;
    }

    public String getTrailer_synopsis() {
        return this.trailer_synopsis;
    }

    public String getTrailer_url() {
        return this.trailer_url;
    }

}
