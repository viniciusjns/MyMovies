package com.example.vinicius.mymovies.view.activities;

import com.example.vinicius.mymovies.R;
import com.example.vinicius.mymovies.databinding.ActivityMainBinding;
import com.example.vinicius.mymovies.model.Movie;
import com.example.vinicius.mymovies.service.APIClient;
import com.example.vinicius.mymovies.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void initBinding() {
        mViewDataBinding.setMain(mViewModel);

        mViewModel.getMovieByCode("tt3896198").observe(this, genericResponse -> {
            Movie movie = genericResponse.getResponse();
            mViewModel.setMovie(movie);
            APIClient.getInstance().getPicasso().load(movie.getPoster()).into(mViewDataBinding.poster);
        });
    }
}
