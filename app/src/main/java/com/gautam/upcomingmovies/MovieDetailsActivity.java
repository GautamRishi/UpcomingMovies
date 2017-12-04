package com.gautam.upcomingmovies;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gautam.upcomingmovies.model.ApiClient;
import com.gautam.upcomingmovies.model.ApiInterface;
import com.gautam.upcomingmovies.model.MovieDetailsResponse;
import com.gautam.upcomingmovies.model.Posters;
import com.gautam.upcomingmovies.model.PostersResponse;
import com.gautam.upcomingmovies.utils.CommonCode;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gautam on 04/12/2017.
 */
public class MovieDetailsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private String title;
    private int movieId;
    private CommonCode ccd;
    private ViewPager posterPager;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private String[] posters;
    private ViewPagerAdapter mAdapter;
    private RatingBar rbPopularity;
    private TextView txtTitle, txtDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (getIntent().getExtras() != null) {
            title = getIntent().getStringExtra(MyApplication.movie_title);
            movieId = Integer.parseInt(getIntent().getStringExtra(MyApplication.movie_id));
        }
        ccd = new CommonCode(this);
        setToolBar(title);
        initialise();

        if (ccd.checkInternetConnectivity()) {
            ApiInterface apiService = ApiClient.getClient(MyApplication.upcoming_movie_url).create(ApiInterface.class);
            Call<MovieDetailsResponse> call = apiService.getMovieDetails(movieId, MyApplication.key);
            call.enqueue(new Callback<MovieDetailsResponse>() {
                @Override
                public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                    if (!isDestroyed() && response.isSuccessful()) {
                        MovieDetailsResponse data = response.body();
                        txtTitle.setText(data.getTitle());
                        txtDes.setText(data.getOverview());
                        float popularity = Float.parseFloat(data.getPopularity());
                        popularity /= 100;
                        rbPopularity.setRating(popularity);
                    }
                }

                @Override
                public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                    t.printStackTrace();
                    ccd.ShowDialog(getResources().getString(R.string.some_thing_went_wrong));
                }
            });

            Call<PostersResponse> posterCall = apiService.getMoviePosters(movieId, MyApplication.key);
            posterCall.enqueue(new Callback<PostersResponse>() {
                @Override
                public void onResponse(Call<PostersResponse> call, Response<PostersResponse> response) {
                    if (!isDestroyed() && response.isSuccessful()) {
                        List<Posters> posterList = response.body().getPosters();
                        posters = new String[posterList.size() > 5 ? 5 : posterList.size()];
                        for (int i = 0; i < posters.length; i++) {
                            posters[i] = posterList.get(i).getFilePath();
                        }
                        setReference();
                    }
                }

                @Override
                public void onFailure(Call<PostersResponse> call, Throwable t) {
                    t.printStackTrace();
                    ccd.ShowDialog(getResources().getString(R.string.some_thing_went_wrong));
                }
            });
        } else {
            ccd.ShowDialog(getResources().getString(R.string.internet_not_available));
        }
    }

    private void initialise() {
        posterPager = (ViewPager) findViewById(R.id.pager_introduction);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        rbPopularity = (RatingBar) findViewById(R.id.rb_popularity);

        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtDes = (TextView) findViewById(R.id.txt_des);

        rbPopularity.setMax(10);
        rbPopularity.setStepSize(.5f);
    }

    private void setToolBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setReference() {


        mAdapter = new ViewPagerAdapter(MovieDetailsActivity.this, posters);
        posterPager.setAdapter(mAdapter);
        posterPager.setCurrentItem(0);
        posterPager.addOnPageChangeListener(this);
        setUiPageViewController();
    }

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selected_item));
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private Context mContext;
        private String[] posterUrl;

        public ViewPagerAdapter(Context mContext, String[] posterUrl) {
            this.mContext = mContext;
            this.posterUrl = posterUrl;
        }

        @Override
        public int getCount() {
            return posterUrl.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
            String imgUrl = MyApplication.full_poster_url + posterUrl[position];
            Picasso.with(mContext).load(imgUrl).into(imageView);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selected_item));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
