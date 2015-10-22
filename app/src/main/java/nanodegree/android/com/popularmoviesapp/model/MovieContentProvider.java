package nanodegree.android.com.popularmoviesapp.model;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/21/15 11:02 PM.
 */
@ContentProvider(authority = MovieContentProvider.AUTHORITY, database = MovieDatabase.class)
public class MovieContentProvider {
    public static final String AUTHORITY = "nanodegree.android.com.popularmoviesapp.model.MovieContentProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String MOVIES = "movies";
    }

    private static Uri buildUri(String ... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = MovieDatabase.MOVIE_TABLE) public static class Movies{
        @ContentUri(
                path = Path.MOVIES,
                type = "vnd.android.cursor.dir/favorite_movies",
                defaultSort = MovieColumns.MOVIE_ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.MOVIES);

        @InexactContentUri(
                name = "MOVIE_ID",
                path = Path.MOVIES + "/#",
                type = "vnd.android.cursor.item/movie",
                whereColumn = MovieColumns.MOVIE_ID,
                pathSegment = 1)
        public static Uri withId(long id){
            return buildUri(Path.MOVIES, String.valueOf(id));
        }
    }

}
