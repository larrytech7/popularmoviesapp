package nanodegree.android.com.popularmoviesapp.model;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/21/15 11:00 PM.
 */
public interface MovieColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    String MOVIE_ID = "_id";

    @DataType(DataType.Type.TEXT) @NotNull
    String MOVIE_TITLE = "title";

    @DataType(DataType.Type.BLOB) @NotNull
    String MOVIE_POSTER = "posterurl";

    @DataType(DataType.Type.TEXT) @NotNull
    String MOVIE_SYNOPSIS = "synopsis";

    @DataType(DataType.Type.REAL) @NotNull
    String MOVIE_RATING = "rating";


    @DataType(DataType.Type.TEXT) @NotNull
    String MOVIE_RELEASE_DATE = "release_date";
}
