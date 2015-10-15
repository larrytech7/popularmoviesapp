package nanodegree.android.com.popularmoviesapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import nanodegree.android.com.popularmoviesapp.DetailsActivity;
import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.adapter.MovieAdapter;
import nanodegree.android.com.popularmoviesapp.model.Movie;

/**
 * Created by Larry akah on 10/14/15.
 */
public class MoviesFragment extends Fragment{

    private GridView moviesGridView;
    private MovieAdapter movieAdapter;
    private View rootView;
    private static final String API_KEY = "76183055a219f7917ab7b2e71f9cada1";
    private enum API_REQUEST {POPULARITY, RATING};
    private static final String MOVIES_API_LINK = "http://api.themoviedb.org/3/discover/movie?sort_by=";

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        moviesGridView = (GridView) rootView.findViewById(R.id.moviesGridView);
        Ion.with(getActivity())
                .load(this.buildApiRequest(API_REQUEST.POPULARITY))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {

                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonArray movies = result.getAsJsonArray("results");
                        List mymovies = new ArrayList<Movie>();

                        for(JsonElement movie: movies){
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
                    }
                });
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
        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie mv = (Movie) adapterView.getItemAtPosition(position);
                mv.writeToParcel(Parcel.obtain(), 0);
                Intent detailIntent = new Intent(getActivity(), DetailsActivity.class);
                detailIntent.putExtra("nanodegree.android.com.popularmoviesapp.model.Movie", mv);
                startActivity(detailIntent);
                //Toast.makeText(getActivity(), ""+mv.getMovie_title(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mrated:
                Ion.with(getActivity())
                        .load(this.buildApiRequest(API_REQUEST.RATING))
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {

                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                JsonArray movies = result.getAsJsonArray("results");
                                List mymovies = new ArrayList<Movie>();

                                for(JsonElement movie: movies){
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
                            }
                        });
                return true;
            case R.id.mpopular:
                Ion.with(getActivity())
                        .load(this.buildApiRequest(API_REQUEST.POPULARITY))
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {

                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                JsonArray movies = result.getAsJsonArray("results");
                                List mymovies = new ArrayList<Movie>();

                                for(JsonElement movie: movies){
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
                            }
                        });
                return true;
        }
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
}
