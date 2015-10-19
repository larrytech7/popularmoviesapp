package nanodegree.android.com.popularmoviesapp.model;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/19/15 2:27 PM.
 */
public class Reviewer {

    private String author, content, url;

    public Reviewer(String author, String content, String url) {
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    public String getUrl() {
        return this.url;
    }
}
