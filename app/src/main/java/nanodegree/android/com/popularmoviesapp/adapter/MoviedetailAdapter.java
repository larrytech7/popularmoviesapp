package nanodegree.android.com.popularmoviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.model.Movie;
import nanodegree.android.com.popularmoviesapp.model.Reviewer;
import nanodegree.android.com.popularmoviesapp.model.Trailer;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/17/15 1:54 PM.
 */
public class MoviedetailAdapter extends ArrayAdapter<Trailer> {

    private List<Trailer> trailers;
    private List<Reviewer> reviewers;
    private Movie movie;
    private Context ctx;
    private static final String YOUTUBE_TRAILER_URL = "http://img.youtube.com/vi/";
    private static final String URL_IMG_SUFFIX ="/0.jpg";

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
}
