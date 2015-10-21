package nanodegree.android.com.popularmoviesapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.adapter.MoviedetailAdapter;
import nanodegree.android.com.popularmoviesapp.adapter.ReviewerAdapter;
import nanodegree.android.com.popularmoviesapp.model.Movie;
import nanodegree.android.com.popularmoviesapp.model.Reviewer;
import nanodegree.android.com.popularmoviesapp.model.Trailer;

public class DetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private ImageButton favoriteButton;
    private static String MOVIE = "nanodegree.android.com.popularmoviesapp.model.Movie";
    private final static String TRAILER_REVIEW_URL = "http://api.themoviedb.org/3/movie/";
    private MoviedetailAdapter moviedetailAdapter;
    private ReviewerAdapter reviewerAdapter;
    private ListView trailerListview;
    private Movie movieToDisplay;

    public static DetailsFragment newInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            movieToDisplay = getArguments().getParcelable(MOVIE);
        }else if(getActivity().getIntent() != null){
            movieToDisplay = getActivity().getIntent().getParcelableExtra(MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        trailerListview = (ListView) rootView.findViewById(R.id.trailerListView);

        //load the movie details alongside trailers and reviews
        this.loadTrailerInfo(movieToDisplay.getMovie_id(), trailerListview, movieToDisplay);

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

    private void loadTrailerInfo(long movieid, final ListView lv, final Movie movie){
        Ion.with(getActivity())
                .load(TRAILER_REVIEW_URL+movieid+"?api_key=76183055a219f7917ab7b2e71f9cada1&append_to_response=trailers,reviews")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {

                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(result != null) {
                            JsonObject tobject = result.getAsJsonObject("trailers");
                            JsonObject robject = result.getAsJsonObject("reviews");
                            JsonArray movieTrailers = tobject.getAsJsonArray("youtube"); //now holds an array of json trailer objects
                            JsonArray movieReviews = robject.getAsJsonArray("results");

                            List trailers = new ArrayList<Trailer>();
                            List reviews = new ArrayList<Reviewer>();

                            for (JsonElement trailer : movieTrailers) {
                                trailers.add(new Trailer(
                                        trailer.getAsJsonObject().get("name").getAsString(),
                                        trailer.getAsJsonObject().get("size").getAsString()+" "+
                                                trailer.getAsJsonObject().get("type").getAsString(),
                                        trailer.getAsJsonObject().get("source").getAsString()));
                            }
                            for(JsonElement review : movieReviews){
                                reviews.add( new Reviewer(review.getAsJsonObject().get("author").getAsString(),
                                        review.getAsJsonObject().get("content").getAsString(),
                                        review.getAsJsonObject().get("url").getAsString()));
                            }
                            moviedetailAdapter = new MoviedetailAdapter(getActivity(), movie, (ArrayList<Trailer>) trailers,(ArrayList<Reviewer>) reviews );
                            lv.setAdapter(moviedetailAdapter);
                            moviedetailAdapter.notifyDataSetChanged();
                        }else{
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
