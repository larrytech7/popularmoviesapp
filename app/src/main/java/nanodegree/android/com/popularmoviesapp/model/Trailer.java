package nanodegree.android.com.popularmoviesapp.model;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/17/15 1:54 PM.
 */
public class Trailer {
    private long trailer_id;
    private String trailer_title;
    private String trailer_synopsis;
    private String trailer_url;

    public Trailer() {
    }

    public long getTrailer_id() {
        return trailer_id;
    }

    public void setTrailer_id(long trailer_id) {
        this.trailer_id = trailer_id;
    }

    public String getTrailer_title() {
        return trailer_title;
    }

    public void setTrailer_title(String trailer_title) {
        this.trailer_title = trailer_title;
    }

    public String getTrailer_synopsis() {
        return trailer_synopsis;
    }

    public void setTrailer_synopsis(String trailer_synopsis) {
        this.trailer_synopsis = trailer_synopsis;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }
}
