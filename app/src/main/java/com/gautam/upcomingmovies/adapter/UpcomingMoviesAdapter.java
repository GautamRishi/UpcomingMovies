package com.gautam.upcomingmovies.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gautam.upcomingmovies.MyApplication;
import com.gautam.upcomingmovies.R;
import com.gautam.upcomingmovies.model.UpcomingMovies;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gautam on 04/12/2017.
 */

public class UpcomingMoviesAdapter extends RecyclerView.Adapter<UpcomingMoviesAdapter.MovieViewHolder> {

    private Context mContext;
    private List<UpcomingMovies> upcomingMoviesList;
    private RecyclerViewOnClick onClick;

    public UpcomingMoviesAdapter(List<UpcomingMovies> upcomingMovies, Context context, RecyclerViewOnClick onClick) {
        this.upcomingMoviesList = upcomingMovies;
        this.mContext = context;
        this.onClick = onClick;
    }

    @Override
    public UpcomingMoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movies_row, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        String imgUrl = MyApplication.poster_url + upcomingMoviesList.get(position).getPosterPath();
        Picasso.with(mContext).load(imgUrl).resize(100, 100).centerCrop().into(holder.imgPoster);
        holder.txtName.setText(upcomingMoviesList.get(position).getTitle());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
            SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy/MM/DD", Locale.ENGLISH);
            Date releaseDate = sdf.parse(upcomingMoviesList.get(position).getReleaseDate());
            holder.txtReleaseDate.setText(sdfNew.format(releaseDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String adult;
        if (upcomingMoviesList.get(position).getAdult()) {
            adult = mContext.getResources().getString(R.string.adult);
        } else {
            adult = mContext.getResources().getString(R.string.not_adult);
        }
        holder.txtAdult.setText(adult);
    }

    @Override
    public int getItemCount() {
        return upcomingMoviesList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        AppCompatImageView imgPoster;
        TextView txtReleaseDate;
        TextView txtAdult;


        public MovieViewHolder(View view) {
            super(view);
            imgPoster = (AppCompatImageView) view.findViewById(R.id.img_poster);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtReleaseDate = (TextView) view.findViewById(R.id.txt_release_date);
            txtAdult = (TextView) view.findViewById(R.id.txt_adult);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.recyclerViewOnClick(getLayoutPosition());
                }
            });
        }
    }

    public interface RecyclerViewOnClick {
        void recyclerViewOnClick(int position);
    }
}
