package nanodegree.android.com.popularmoviesapp.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

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
    private final static String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private MoviedetailAdapter moviedetailAdapter;
    private ReviewerAdapter reviewerAdapter;
    private ListView trailerListview;
    private Movie movieToDisplay;
    private final String LOG_TAG = DetailsFragment.class.getName();

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
        }else{
            movieToDisplay = new Movie(0,"","","",0,"");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        trailerListview = (ListView) rootView.findViewById(R.id.trailerListView);
        trailerListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Trailer trailer = (Trailer) adapterView.getAdapter().getItem(i-1);
                    if(trailer != null)
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(YOUTUBE_URL+trailer.getTrailer_url())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //load the movie details alongside trailers and reviews
        if (movieToDisplay != null)
            this.loadMovieDetails(movieToDisplay.getMovie_id(), trailerListview, movieToDisplay);


        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater mit) {
        // Inflate the menu; this adds items to the action bar if it is present.
        try {
            mit.inflate(R.menu.menu_details, menu);
            MenuItem  mitem = menu.findItem(R.id.share);
            ShareActionProvider lShareActionProvider;
            lShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(mitem);
            if(lShareActionProvider !=null){

                lShareActionProvider.setShareIntent(getShareIntent("View this trailer "+YOUTUBE_URL+MoviedetailAdapter.FIRST_TRAILER_URL));
            }else{
                Toast.makeText(getActivity(), "Sharing misconfiguration", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("InlinedApi")
    private Intent getShareIntent(String text){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.setType("text/*");
        share.putExtra(Intent.EXTRA_TEXT, text);
        //share.putExtra(Intent.EXTRA_HTML_TEXT, Html.fromHtml(text));
        return share;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void loadMovieDetails(long movieid, final ListView lv, final Movie movie){
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
                            Toast.makeText(getActivity(), "No connection. Unable to retrieve trailers and reviews", Toast.LENGTH_LONG).show();
                            moviedetailAdapter = new MoviedetailAdapter(getActivity(), movie, new ArrayList<Trailer>(),new ArrayList<Reviewer>() );
                            lv.setAdapter(moviedetailAdapter);
                            moviedetailAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}
