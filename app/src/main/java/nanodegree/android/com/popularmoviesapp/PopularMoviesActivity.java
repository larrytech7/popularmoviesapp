package nanodegree.android.com.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import nanodegree.android.com.popularmoviesapp.fragments.DetailsFragment;
import nanodegree.android.com.popularmoviesapp.fragments.MoviesFragment;
import nanodegree.android.com.popularmoviesapp.model.Movie;

public class PopularMoviesActivity extends ActionBarActivity implements MoviesFragment.MovieClickListener{

    private boolean isTwoPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        if(findViewById(R.id.detail_container) != null){
            isTwoPaneLayout = true;
            if (savedInstanceState == null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.detail_container, new DetailsFragment())
                        .commit();
            }
        }else{
            isTwoPaneLayout = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClick(Movie movie) {
        if(isTwoPaneLayout){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_container, new DetailsFragment().newInstance(movie))
                    .commit();
        }else{
            Intent detailIntent = new Intent(this, DetailsActivity.class);
            detailIntent.putExtra("nanodegree.android.com.popularmoviesapp.model.Movie", movie);
            startActivity(detailIntent);
        }
    }
}
