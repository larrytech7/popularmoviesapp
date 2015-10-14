package nanodegree.android.com.popularmoviesapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

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
    private static final String MOVIES_API_LINK = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=";

    public MoviesFragment newInstance(String param1){
        Bundle dataBundle = new Bundle();
        dataBundle.putCharSequence("MOVIE_DATA", param1);
        this.setArguments(dataBundle);
        return this;
    }

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        moviesGridView = (GridView) rootView.findViewById(R.id.moviesGridView);
        Ion.with(getActivity())
                .load(MOVIES_API_LINK+API_KEY)
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

       /* ///nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
        List mymovies = new ArrayList<Movie>();
        mymovies.add(new Movie("1","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "xyz","xyz","2.5","2014-10-10"));
        mymovies.add(new Movie("2","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "xyz","xyz","2.5","2014-10-10"));
        mymovies.add(new Movie("3","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "xyz","xyz","2.5","2014-10-10"));
        mymovies.add(new Movie("4","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "xyz","xyz","2.5","2014-10-10"));
        mymovies.add(new Movie("1","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "xyz","xyz","2.5","2014-10-10"));
        mymovies.add(new Movie("2","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "xyz","xyz","2.5","2014-10-10"));
        mymovies.add(new Movie("3","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "xyz","xyz","2.5","2014-10-10"));
        mymovies.add(new Movie("4","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "xyz","xyz","2.5","2014-10-10"));

        movieAdapter = new MovieAdapter(getActivity(), (ArrayList<Movie>) mymovies);
        moviesGridView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();*/

        return rootView;
    }
}
