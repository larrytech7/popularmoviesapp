package nanodegree.android.com.popularmoviesapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.adapter.MovieAdapter;
import nanodegree.android.com.popularmoviesapp.adapter.MovietrailerAdapter;
import nanodegree.android.com.popularmoviesapp.adapter.ReviewerAdapter;
import nanodegree.android.com.popularmoviesapp.model.Movie;
import nanodegree.android.com.popularmoviesapp.model.Trailer;

public class DetailsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private ImageButton favoriteButton;
    private String mParam1;
    private String mParam2;
    private final static String TRAILER_REVIEW_URL = "http://api.themoviedb.org/3/movie/";
    private MovietrailerAdapter movietrailerAdapter;
    private ReviewerAdapter reviewerAdapter;
    private ListView trailerListview;

    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        //view items
        favoriteButton = (ImageButton) rootView.findViewById(R.id.favoriteButton);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.movieTitleTextview);
        ImageView posterimg = (ImageView) rootView.findViewById(R.id.moviePosterImage);
        TextView releaseDate = (TextView) rootView.findViewById(R.id.movieReleaseDate);
        TextView rating = (TextView) rootView.findViewById(R.id.movieRating);
        TextView overview = (TextView) rootView.findViewById(R.id.movieOverview);
        trailerListview = (ListView) rootView.findViewById(R.id.trailerListView);
        //bind data to view
        favoriteButton.setOnClickListener(this);
        Movie movie = getActivity().getIntent().getParcelableExtra("nanodegree.android.com.popularmoviesapp.model.Movie");
        titleTextView.setText(movie.getMovie_title());
        releaseDate.setText("Released : "+movie.getMovie_release_date());
        rating.setText("Rating : "+movie.getMovie_rating());
        overview.setText(movie.getMovie_overview());
        Picasso.with(getActivity())
                .load(MovieAdapter.POSTER_URL + movie.getMovie_poster_url())
                .resize(280, 300)
                .placeholder(R.drawable.imageloading)
                .error(R.mipmap.err_image)
                .into(posterimg);
        //load the movie trailers
        this.loadTrailerInfo(movie.getMovie_id(), trailerListview);

        return rootView;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p/>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.favoriteButton:
                favoriteButton.setImageResource(android.R.drawable.star_on);
                Toast.makeText(getActivity(), "This movie has been marked as favorite", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void loadTrailerInfo(long movieid, final ListView lv){
        Ion.with(getActivity())
                .load(TRAILER_REVIEW_URL+movieid+"?api_key=API_KEY&append_to_response=trailers,reviews")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {

                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(result != null) {
                            JsonObject tobject = result.getAsJsonObject("trailers");
                            JsonArray movieTrailers = tobject.getAsJsonArray("youtube"); //now holds an array of json trailer objects

                            List trailers = new ArrayList<Trailer>();

                            for (JsonElement trailer : movieTrailers) {
                                trailers.add(new Trailer(
                                        trailer.getAsJsonObject().get("name").getAsString(),
                                        trailer.getAsJsonObject().get("size").getAsString()+" "+
                                                trailer.getAsJsonObject().get("type").getAsString(),
                                        trailer.getAsJsonObject().get("source").getAsString()));
                            }
                            movietrailerAdapter = new MovietrailerAdapter(getActivity(), (ArrayList<Trailer>) trailers);
                            lv.setAdapter(movietrailerAdapter);
                            movietrailerAdapter.notifyDataSetChanged();
                        }else{
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void loadReviewInfo(Context context, final ListView lv){

    }
}
