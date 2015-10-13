package nanodegree.android.com.popularmoviesapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nanodegree.android.com.popularmoviesapp.R;

/**
 * Created by root on 10/14/15.
 */
public class MoviesFragment extends Fragment{

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        return rootView;
    }
}
