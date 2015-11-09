package nanodegree.android.com.popularmoviesapp.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.adapter.FavoriteMovieAdapter;
import nanodegree.android.com.popularmoviesapp.adapter.MovieAdapter;
import nanodegree.android.com.popularmoviesapp.model.Movie;
import nanodegree.android.com.popularmoviesapp.model.MovieContentProvider;

/**
 * Created by Larry akah on 10/14/15.
 */
public class MoviesFragment extends Fragment implements View.OnClickListener{

    private GridView moviesGridView;
    private MovieAdapter movieAdapter;
    private View rootView;
    private static final String API_KEY = "76183055a219f7917ab7b2e71f9cada1";
    private int mPosition;
    private String SELECTED_MOVIE_POSITION;
    private enum API_REQUEST {POPULARITY, RATING};
    private static final String MOVIES_API_LINK = "http://api.themoviedb.org/3/discover/movie?sort_by=";
    private FloatingActionButton fabFavorite, fabPopular, fabRated;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabPopular:
                fabPopular.setIndeterminate(true);
                fabFavorite.setIndeterminate(false);
                fabRated.setIndeterminate(false);
                if(isNetworkAvailable())
                    Ion.with(getActivity())
                            .load(this.buildApiRequest(API_REQUEST.POPULARITY))
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {

                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if( result != null) {
                                        JsonArray movies = result.getAsJsonArray("results");
                                        List mymovies = new ArrayList<Movie>();

                                        for (JsonElement movie : movies) {
                                            mymovies.add(new Movie(movie.getAsJsonObject().get("id").getAsLong(),
                                                    movie.getAsJsonObject().get("poster_path").getAsString(),
                                                    movie.getAsJsonObject().get("original_title").getAsString(),
                                                    movie.getAsJsonObject().get("overview").getAsString(),
                                                    movie.getAsJsonObject().get("vote_average").getAsFloat(),
                                                    movie.getAsJsonObject().get("release_date").getAsString()));
                                        }
                                        movieAdapter = new MovieAdapter(getActivity(), (ArrayList<Movie>) mymovies);
                                        moviesGridView.setAdapter(movieAdapter);
                                        movieAdapter.notifyDataSetChanged();
                                    }else{
                                        e.printStackTrace();
                                        //connection error probably, so show favorited movies
                                        Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                else{
                    Toast.makeText(getActivity(), "No internet Connection. Connectivity is required for this operation", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.fabHrated:
                fabPopular.setIndeterminate(false);
                fabFavorite.setIndeterminate(false);
                fabRated.setIndeterminate(true);
                if(isNetworkAvailable())
                    Ion.with(getActivity())
                            .load(this.buildApiRequest(API_REQUEST.RATING))
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {

                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if( result != null) {
                                        JsonArray movies = result.getAsJsonArray("results");
                                        List mymovies = new ArrayList<Movie>();

                                        for (JsonElement movie : movies) {
                                            mymovies.add(new Movie(movie.getAsJsonObject().get("id").getAsLong(),
                                                    movie.getAsJsonObject().get("poster_path").getAsString(),
                                                    movie.getAsJsonObject().get("original_title").getAsString(),
                                                    movie.getAsJsonObject().get("overview").getAsString(),
                                                    movie.getAsJsonObject().get("vote_average").getAsFloat(),
                                                    movie.getAsJsonObject().get("release_date").getAsString()));
                                        }
                                        movieAdapter = new MovieAdapter(getActivity(), (ArrayList<Movie>) mymovies);
                                        moviesGridView.setAdapter(movieAdapter);
                                        movieAdapter.notifyDataSetChanged();
                                    }else{
                                        e.printStackTrace();
                                        //connection error probably, so show favorited movies
                                        Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                else{
                    Toast.makeText(getActivity(), "No internet Connection. Connectivity is required for this operation", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.fabFavorites:
                fabFavorite.setIndeterminate(true);
                fabPopular.setIndeterminate(false);
                fabRated.setIndeterminate(false);
                moviesGridView.setAdapter(new FavoriteMovieAdapter(getActivity(),
                        getActivity().getContentResolver().query(MovieContentProvider.Movies.CONTENT_URI,
                                null,
                                null,
                                null,
                                null), 0));
                break;
        }
    }

    public MoviesFragment newInstance(String param1){
        Bundle dataBundle = new Bundle();
        dataBundle.putCharSequence("MOVIE_DATA", param1);
        this.setArguments(dataBundle);
        return this;
    }

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        moviesGridView = (GridView) rootView.findViewById(R.id.moviesGridView);
        fabFavorite = (FloatingActionButton) rootView.findViewById(R.id.fabFavorites); fabFavorite.setOnClickListener(this);
        fabRated = (FloatingActionButton) rootView.findViewById(R.id.fabHrated); fabRated.setOnClickListener(this);
        fabPopular = (FloatingActionButton) rootView.findViewById(R.id.fabPopular); fabPopular.setOnClickListener(this);

        if(isNetworkAvailable())
        Ion.with(getActivity())
                .load(this.buildApiRequest(API_REQUEST.POPULARITY))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {

                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if( result != null) {
                            JsonArray movies = result.getAsJsonArray("results");
                            List mymovies = new ArrayList<Movie>();

                            for (JsonElement movie : movies) {
                                mymovies.add(new Movie(movie.getAsJsonObject().get("id").getAsLong(),
                                        movie.getAsJsonObject().get("poster_path").getAsString(),
                                        movie.getAsJsonObject().get("original_title").getAsString(),
                                        movie.getAsJsonObject().get("overview").getAsString(),
                                        movie.getAsJsonObject().get("vote_average").getAsFloat(),
                                        movie.getAsJsonObject().get("release_date").getAsString()));
                            }
                            movieAdapter = new MovieAdapter(getActivity(), (ArrayList<Movie>) mymovies);
                            moviesGridView.setAdapter(movieAdapter);
                            movieAdapter.notifyDataSetChanged();
                        }else{
                            e.printStackTrace();
                            //connection error probably, so show favorited movies
                            Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        else{
            moviesGridView.setAdapter(new FavoriteMovieAdapter(getActivity(),
                    getActivity().getContentResolver().query(MovieContentProvider.Movies.CONTENT_URI,
                            null,
                            null,
                            null,
                            null), 0));
        }

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie mv = (Movie) adapterView.getItemAtPosition(position);
                mv.writeToParcel(Parcel.obtain(), 0);
                ((MovieClickListener) getActivity()).onMovieItemClick(mv);
                mPosition = position;
            }
        });

        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_MOVIE_POSITION)){
            mPosition = savedInstanceState.getInt(SELECTED_MOVIE_POSITION);
        }

        return rootView;
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to Activity of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        moviesGridView.setSelection(mPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private String buildApiRequest(API_REQUEST request){
        if(API_REQUEST.POPULARITY == request){
            return MOVIES_API_LINK+"popularity.desc&api_key="+API_KEY;
        }else if (API_REQUEST.RATING == request){
            return MOVIES_API_LINK+"vote_average.desc&certification_country=US&certification=R&api_key="+API_KEY;
        }

        return "";
    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(android.os.Bundle)},
     * {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}, and
     * {@link #onActivityCreated(android.os.Bundle)}.
     * <p/>
     * <p>This corresponds to {@link android.app.Activity#onSaveInstanceState(android.os.Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mPosition != GridView.INVALID_POSITION){
            outState.putInt(SELECTED_MOVIE_POSITION, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Called when all saved state has been restored into the view hierarchy
     * of the fragment.  This can be used to do initialization based on saved
     * state that you are letting the view hierarchy track itself, such as
     * whether check box widgets are currently checked.  This is called
     * after {@link #onActivityCreated(android.os.Bundle)} and before
     * {@link #onStart()}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public interface MovieClickListener{

        public void onMovieItemClick(Movie movie);
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI | ConnectivityManager.TYPE_MOBILE);

        return networkInfo.isConnected();
    }
}
