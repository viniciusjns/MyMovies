package com.example.vinicius.mymovies.view.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;

import com.example.vinicius.mymovies.R;
import com.example.vinicius.mymovies.databinding.ActivityMainBinding;
import com.example.vinicius.mymovies.model.Movie;
import com.example.vinicius.mymovies.service.APIClient;
import com.example.vinicius.mymovies.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainViewModel.MainListener {

    /*
    See this link to create a new layout

    http://yasirameen.com/2016/05/android-activity-transition/
     */

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
        mViewModel.setNavigator(this);

//        mViewModel.getMovieByCode("tt3896198").observe(this, genericResponse -> {
//            Movie movie = genericResponse.getResponse();
//            mViewModel.setMovie(movie);
//            APIClient.getInstance().getPicasso().load(movie.getPoster()).into(mViewDataBinding.poster);
//        });
    }

    @Override
    public void onOpenSearchActivity() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet transitionSet = new TransitionSet();
            Transition arc = new ChangeBounds();
            arc.setPathMotion(new ArcMotion());
            transitionSet.addTransition(arc);
            transitionSet.addTransition(new Fade());
            transitionSet.addTransition(new ChangeBounds());
            transitionSet.setDuration(400);

            getWindow().setSharedElementEnterTransition(transitionSet);
            getWindow().setSharedElementExitTransition(transitionSet);
        }

        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, mViewDataBinding.fab, "transition");
        startActivity(intent, options.toBundle());
    }
}
