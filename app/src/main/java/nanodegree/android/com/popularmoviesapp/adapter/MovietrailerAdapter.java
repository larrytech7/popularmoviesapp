package nanodegree.android.com.popularmoviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.model.Trailer;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/17/15 1:54 PM.
 */
public class MovietrailerAdapter extends ArrayAdapter<Trailer> {

    private List<Trailer> trailers;
    private Context ctx;

    public MovietrailerAdapter(Context context, ArrayList<Trailer> list) {
        super(context, 0);
        this.trailers = list;
        this.ctx = context;
    }

    @Override
    public Trailer getItem(int position) {
        return this.trailers.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(this.ctx).inflate(R.layout.trailer_layout, parent, false);
        }
        Trailer trailer = this.trailers.get(position);
        TextView title = (TextView) convertView.findViewById(R.id.trailer_title);
        TextView synopsis = (TextView) convertView.findViewById(R.id.trailer_synopsis);
        title.setText(trailer.getTrailer_title());
        synopsis.setText(trailer.getTrailer_synopsis());

        return convertView;

    }

    @Override
    public int getCount() {
        return this.trailers.size();
    }
}
