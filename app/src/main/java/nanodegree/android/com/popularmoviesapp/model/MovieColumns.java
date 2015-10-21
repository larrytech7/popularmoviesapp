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
    @DataType(INTEGER) @PrimaryKey
    @AutoIncrement
    String MOVIE_ID = "_id";

    @DataType(TEXT) @NotNull
    String TITLE = "title";
}
