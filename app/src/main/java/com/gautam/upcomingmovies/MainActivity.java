package com.gautam.upcomingmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gautam.upcomingmovies.adapter.UpcomingMoviesAdapter;
import com.gautam.upcomingmovies.model.ApiClient;
import com.gautam.upcomingmovies.model.ApiInterface;
import com.gautam.upcomingmovies.model.UpcomingMovies;
import com.gautam.upcomingmovies.model.UpcomingMoviesResponse;
import com.gautam.upcomingmovies.utils.CommonCode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gautam on 03/12/2017.
 */
public class MainActivity extends AppCompatActivity implements UpcomingMoviesAdapter.RecyclerViewOnClick {

    private RecyclerView rvUpcomingMovies;
    private UpcomingMoviesAdapter adapter;
    private List<UpcomingMovies> upcomingMoviesList;
    private CommonCode ccd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ccd = new CommonCode(this);
        rvUpcomingMovies = (RecyclerView) findViewById(R.id.rv_upcoming_movies);
        rvUpcomingMovies.setLayoutManager(new LinearLayoutManager(this));
        setToolBar();
        upcomingMoviesList = new ArrayList<>();
        adapter = new UpcomingMoviesAdapter(upcomingMoviesList, this, this);
        rvUpcomingMovies.setAdapter(adapter);
        if (ccd.checkInternetConnectivity()) {
            ApiInterface apiService = ApiClient.getClient(MyApplication.upcoming_movie_url).create(ApiInterface.class);
            Call<UpcomingMoviesResponse> call = apiService.getUpcomingMovies(MyApplication.key);
            call.enqueue(new Callback<UpcomingMoviesResponse>() {
                @Override
                public void onResponse(Call<UpcomingMoviesResponse> call, Response<UpcomingMoviesResponse> response) {
                    if (!isDestroyed() && response.isSuccessful()) {
                        upcomingMoviesList.addAll(response.body().getResults());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<UpcomingMoviesResponse> call, Throwable t) {
                    t.printStackTrace();
                    ccd.ShowDialog(getResources().getString(R.string.some_thing_went_wrong));
                }
            });
        } else {
            ccd.ShowDialog(getResources().getString(R.string.internet_not_available));
        }
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.info) {
            startActivity(new Intent(MainActivity.this, InformationActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void recyclerViewOnClick(int position) {
        if (ccd.checkInternetConnectivity()) {
            Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
            intent.putExtra(MyApplication.movie_id, upcomingMoviesList.get(position).getId());
            intent.putExtra(MyApplication.movie_title, upcomingMoviesList.get(position).getTitle());
            startActivity(intent);
        } else {
            ccd.ShowDialog(getResources().getString(R.string.internet_not_available));
        }
    }
}