package nanodegree.android.com.popularmoviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import nanodegree.android.com.popularmoviesapp.R;
import nanodegree.android.com.popularmoviesapp.model.Reviewer;
import nanodegree.android.com.popularmoviesapp.model.Trailer;

/**
 * Project Popularmoviesapp
 * Created by Larry Akah on 10/19/15 2:31 PM.
 */
public class ReviewerAdapter extends ArrayAdapter<Reviewer>{

    private Context ctx;
    private ArrayList<Reviewer> reviewers;

    public ReviewerAdapter(Context context, ArrayList<Reviewer> list) {
        super(context, 0);
        this.reviewers = list;
        this.ctx = context;
    }

    @Override
    public Reviewer getItem(int position) {
        return reviewers.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(this.ctx).inflate(R.layout.trailer_layout, parent, false);
        }
        return convertView;

    }

    @Override
    public int getCount() {
        return this.reviewers.size();
    }
}
