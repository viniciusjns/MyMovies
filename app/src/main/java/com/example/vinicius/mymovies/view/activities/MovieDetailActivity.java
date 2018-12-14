package com.example.vinicius.mymovies.view.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;

import com.example.vinicius.mymovies.R;
import com.example.vinicius.mymovies.databinding.ActivityMovieDetailBinding;
import com.example.vinicius.mymovies.model.Movie;
import com.example.vinicius.mymovies.viewmodel.MovieDetailViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MovieDetailActivity extends BaseActivity<ActivityMovieDetailBinding, MovieDetailViewModel> implements MovieDetailViewModel.MovieDetailListener {

    public static final String MOVIE = "movie";
    private Movie movie;

    @Override
    public int getLayout() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected Class<MovieDetailViewModel> getViewModelClass() {
        return MovieDetailViewModel.class;
    }

    @Override
    protected void initBinding() {
        mViewDataBinding.setDetail(mViewModel);
        mViewModel.setNavigator(this);

        movie = getIntent().getParcelableExtra(MOVIE);
        Picasso.with(this).load(movie.getPoster()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mViewDataBinding.coverDetails.setImageBitmap(bitmap);
                mViewDataBinding.coverBgDetails.setImageBitmap(bitmap);
                Palette palette = Palette.from(bitmap).generate();
                int darkVibrantColor = palette.getDarkMutedColor(ContextCompat.getColor(MovieDetailActivity.this, R.color.colorWhite));
                int vibrantColor = palette.getMutedColor(ContextCompat.getColor(MovieDetailActivity.this, R.color.colorWhite));

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[] {darkVibrantColor,vibrantColor});
                gd.setCornerRadius(0f);

                mViewDataBinding.content.setBackground(gd);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


    }
}
