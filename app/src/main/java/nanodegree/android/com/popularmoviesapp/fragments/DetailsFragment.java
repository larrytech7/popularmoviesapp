package nanodegree.android.com.popularmoviesapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.adapter.MovieAdapter;
import nanodegree.android.com.popularmoviesapp.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        //view items
        TextView titleTextView = (TextView) rootView.findViewById(R.id.movieTitleTextview);
        ImageView posterimg = (ImageView) rootView.findViewById(R.id.moviePosterImage);
        TextView releaseDate = (TextView) rootView.findViewById(R.id.movieReleaseDate);
        TextView rating = (TextView) rootView.findViewById(R.id.movieRating);
        TextView overview = (TextView) rootView.findViewById(R.id.movieOverview);
        //bind data to view
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

        return rootView;
    }
}
