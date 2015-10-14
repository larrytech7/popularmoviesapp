package nanodegree.android.com.popularmoviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.model.Movie;

/**
 * Created by Larry Akah on 10/14/15.
 */
public class MovieAdapter extends ArrayAdapter<Movie>{

    private ArrayList<Movie> movieList;
    private Context datacontext;
    private final String POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    public MovieAdapter(Context context, ArrayList<Movie> resource) {
        super(context, 0, 0);
        this.movieList = resource;
        this.datacontext = context;
    }

    @Override
    public int getCount() {
        return this.movieList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(this.datacontext).inflate(R.layout.movie_layout, parent, false);
        //set data from movies array
        Movie movie = this.movieList.get(position);
        ImageView imageposter = (ImageView) convertView.findViewById(R.id.movie_poster);
        Picasso.with(this.datacontext)
                .load(POSTER_URL+movie.getMovie_poster_url())
                .resize(280, 300)
                .placeholder(R.mipmap.image_loading)
                .error(R.mipmap.err_image)
                .into(imageposter);

        return convertView;
    }
}
