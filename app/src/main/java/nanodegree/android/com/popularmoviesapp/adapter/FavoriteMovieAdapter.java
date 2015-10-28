package nanodegree.android.com.popularmoviesapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.model.Movie;
import nanodegree.android.com.popularmoviesapp.model.MovieColumns;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/22/15 1:41 AM.
 */
public class FavoriteMovieAdapter extends CursorAdapter {
    private Context mContext;
    private Cursor mCursor;

    public FavoriteMovieAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
        mCursor = c;
    }

    /**
     * Makes a new view to hold the data pointed to by cursor.
     *
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.movie_layout, parent, false);
    }

    /**
     * Bind an existing view to the data pointed to by cursor
     *
     * @param view    Existing view, returned earlier by newView
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView moviePoster = (ImageView) view.findViewById(R.id.movie_poster);
        moviePoster.setMaxWidth(280);
        moviePoster.setMaxHeight(300);
        byte[] posterData = null;
        if(cursor != null && cursor.moveToNext()){
            posterData = cursor.getBlob(cursor.getColumnIndex(MovieColumns.MOVIE_POSTER));
            Bitmap bmp = BitmapFactory.decodeByteArray(posterData, 0, posterData.length);
            moviePoster.setImageBitmap(bmp);
        }
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public Movie getItem(int position) {
        mCursor.moveToPosition(position+1);
        return new Movie(mCursor.getLong(mCursor.getColumnIndex(MovieColumns.MOVIE_ID)),
                mCursor.getBlob(mCursor.getColumnIndex(MovieColumns.MOVIE_POSTER)).toString(),
                mCursor.getString(mCursor.getColumnIndex(MovieColumns.MOVIE_TITLE)),
                mCursor.getString(mCursor.getColumnIndex(MovieColumns.MOVIE_SYNOPSIS)),
                mCursor.getFloat(mCursor.getColumnIndex(MovieColumns.MOVIE_RATING)),
                mCursor.getString(mCursor.getColumnIndex(MovieColumns.MOVIE_RELEASE_DATE)));
    }
}
