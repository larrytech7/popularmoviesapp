package nanodegree.android.com.popularmoviesapp.model;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/21/15 11:01 PM.
 */
@Database(version = MovieDatabase.DB_VERSION)
public final class MovieDatabase {
    public static final int DB_VERSION = 1;

    @Table(MovieColumns.class) public static final String MOVIE_TABLE = "favorite_movies"; //default: LIST
}
