package nanodegree.android.com.popularmoviesapp.adapter;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.BitmapDrawableFactory;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.model.Movie;
import nanodegree.android.com.popularmoviesapp.model.MovieColumns;
import nanodegree.android.com.popularmoviesapp.model.MovieContentProvider;
import nanodegree.android.com.popularmoviesapp.model.Reviewer;
import nanodegree.android.com.popularmoviesapp.model.Trailer;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/17/15 1:54 PM.
 */
public class MoviedetailAdapter extends ArrayAdapter<Trailer> {

    private static final String LOG_TAG = MoviedetailAdapter.class.getName();
    private List<Trailer> trailers;
    private List<Reviewer> reviewers;
    private Movie movie;
    private Context ctx;
    private static final String YOUTUBE_TRAILER_URL = "http://img.youtube.com/vi/";
    private static final String URL_IMG_SUFFIX ="/0.jpg";
    private int reviewCount = 0;

    public MoviedetailAdapter(Context context, Movie movie, ArrayList<Trailer> list, ArrayList<Reviewer> reviews) {
        super(context, 0);
        this.trailers = list;
        this.reviewers = reviews;
        this.movie = movie;
        this.ctx = context;
    }

    @Override
    public Trailer getItem(int position) {
        return this.trailers.get(position+1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(ctx).inflate(R.layout.trailer_layout, parent, false);
        }
        if(position == 0){
            convertView = LayoutInflater.from(ctx).inflate(R.layout.movie_detail, parent,false);
            //view items
            final ImageButton favoriteButton = (ImageButton) convertView.findViewById(R.id.favoriteButton);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.movieTitleTextview);
            final ImageView posterimg = (ImageView) convertView.findViewById(R.id.moviePosterImage);
            TextView releaseDate = (TextView) convertView.findViewById(R.id.movieReleaseDate);
            TextView rating = (TextView) convertView.findViewById(R.id.movieRating);
            TextView overview = (TextView) convertView.findViewById(R.id.movieOverview);
            ListView trailerListview = (ListView) convertView.findViewById(R.id.trailerListView);
            //bind data to view
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(insertMovie(movie, getByteArrayFromDrawable(posterimg.getDrawable())))
                    favoriteButton.setImageResource(android.R.drawable.star_on);
                    Toast.makeText(ctx, "This movie has been marked as favorite", Toast.LENGTH_LONG).show();
                }
            });
            titleTextView.setText(movie.getMovie_title());
            releaseDate.setText("Released : "+movie.getMovie_release_date());
            rating.setText("Rating : "+movie.getMovie_rating());
            overview.setText(movie.getMovie_overview());
            Picasso.with(ctx)
                    .load(MovieAdapter.POSTER_URL + movie.getMovie_poster_url())
                    .resize(280, 300)
                    .placeholder(R.drawable.imageloading)
                    .error(R.mipmap.err_image)
                    .into(posterimg);
            return convertView;
        }
        if(position > trailers.size()){
            convertView = LayoutInflater.from(ctx).inflate(R.layout.reviewlayout, parent, false);
            try {
                Reviewer review = this.reviewers.get(reviewCount++);
                //load views
                //CircularImageView circularAuthorImageView = (CircularImageView) convertView.findViewById(R.id.authorimage);
                TextView revAuthorName = (TextView) convertView.findViewById(R.id.authorTextview);
                TextView content = (TextView) convertView.findViewById(R.id.reviewContent);
                revAuthorName.setText(review.getAuthor());
                content.setText(review.getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }
        try {
            Trailer trailer = this.trailers.get(position-1);
            TextView title = (TextView) convertView.findViewById(R.id.trailer_title);
            TextView synopsis = (TextView) convertView.findViewById(R.id.trailer_synopsis);
            title.setText(trailer.getTrailer_title());
            synopsis.setText(trailer.getTrailer_synopsis());
            Picasso.with(ctx)
                    .load(YOUTUBE_TRAILER_URL + trailer.getTrailer_url()+URL_IMG_SUFFIX)
                    .resize(280, 300)
                    .placeholder(R.drawable.imageloading)
                    .error(R.mipmap.err_image)
                    .into((ImageView) convertView.findViewById(R.id.trailer_preview));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;

    }

    /**
     * get  all items to be shown or displayed in the listview
     * @return the sum of all items including a header item that represents the movie & details
     */
    @Override
    public int getCount() {
        return this.trailers.size() + this.reviewers.size() +1;
    }

    private boolean insertMovie(Movie movie, byte[] moviephoto){
        Log.d(LOG_TAG, "insert");
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(1);

        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                MovieContentProvider.Movies.CONTENT_URI);
        builder.withValue(MovieColumns.MOVIE_ID, movie.getMovie_id());
        builder.withValue(MovieColumns.MOVIE_TITLE, movie.getMovie_title());
        builder.withValue(MovieColumns.MOVIE_POSTER,moviephoto);
        builder.withValue(MovieColumns.MOVIE_SYNOPSIS, movie.getMovie_overview());
        builder.withValue(MovieColumns.MOVIE_RATING, movie.getMovie_rating());
        builder.withValue(MovieColumns.MOVIE_RELEASE_DATE, movie.getMovie_release_date());
        batchOperations.add(builder.build());

        try{
            ctx.getContentResolver().applyBatch(MovieContentProvider.AUTHORITY, batchOperations);
            return true;
        } catch(RemoteException | OperationApplicationException e){
            Log.e(LOG_TAG, "Error applying  insert", e);
            return false;
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        Log.v("Bitmap width - Height :", width + " : " + height);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    private byte[] getByteArrayFromDrawable( Drawable drawable){
        Bitmap bitmap = drawableToBitmap(drawable);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
