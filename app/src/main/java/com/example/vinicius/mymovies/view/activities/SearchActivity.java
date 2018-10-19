package com.example.vinicius.mymovies.view.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.example.vinicius.mymovies.R;
import com.example.vinicius.mymovies.databinding.ActivitySearchBinding;
import com.example.vinicius.mymovies.model.GenericResponse;
import com.example.vinicius.mymovies.model.Movie;
import com.example.vinicius.mymovies.model.SearchResponse;
import com.example.vinicius.mymovies.view.adapters.SearchMoviesAdapter;
import com.example.vinicius.mymovies.viewmodel.SearchViewModel;
import com.jaouan.viewsfrom.Views;

import java.util.List;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> implements SearchViewModel.SearchListener, SearchMoviesAdapter.OnClickSearchMovies {

    private int cx;
    private int cy;
    private int mRadius;
    private boolean mHidingAnimationStarted = false;

    private SearchMoviesAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected Class<SearchViewModel> getViewModelClass() {
        return SearchViewModel.class;
    }

    @Override
    protected void initBinding() {
        mViewDataBinding.setSearch(mViewModel);
        mViewModel.setNavigator(this);

        adapter = new SearchMoviesAdapter(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animateOpening();
        } else {
            mViewDataBinding.getRoot().setAlpha(1);
            endAnimation();
        }

        mViewDataBinding.svMovies.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewDataBinding.tvToolbarText.setVisibility(View.GONE);
            }
        });

        mViewDataBinding.svMovies.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mViewDataBinding.tvToolbarText.setVisibility(View.VISIBLE);
                return false;
            }
        });

        mViewDataBinding.svMovies.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMoviesByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void searchMoviesByName(String movieName) {
        mViewModel.searchMoviesByTitle(movieName).observe(this, new Observer<SearchResponse>() {
            @Override
            public void onChanged(@Nullable SearchResponse searchResponse) {
                setupMoviesList(searchResponse.getMovies());
            }
        });
    }

    private void setupMoviesList(List<Movie> response) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter.setMovies(response);
        mViewDataBinding.rvSearchMovies.setAdapter(adapter);
        mViewDataBinding.rvSearchMovies.setHasFixedSize(true);
        mViewDataBinding.rvSearchMovies.setLayoutManager(layoutManager);
    }

    @Override
    public void onClickOpenMovieDetail(Movie movie) {
        Toast.makeText(this, movie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mViewDataBinding.getRoot() != null && !mHidingAnimationStarted) {
            animateClosing();
        } else {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animateOpening() {

        // Set transition animation for shared elements
        TransitionSet transitionSet = new TransitionSet();
        Transition arc = new ChangeBounds();
        arc.setPathMotion(new ArcMotion());
        transitionSet.addTransition(arc);
        transitionSet.addTransition(new Fade());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.setDuration(400);

        getWindow().setSharedElementEnterTransition(transitionSet);
        getWindow().setSharedElementExitTransition(transitionSet);
        getWindow().setSharedElementReenterTransition(transitionSet);
        getWindow().setSharedElementReturnTransition(transitionSet);

        // Add a listener to prepare and animate root view with circle reveal
        mViewDataBinding.getRoot().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @SuppressLint("NewApi")
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                view.removeOnLayoutChangeListener(this);
                cx = right / 2;
                cy = bottom / 2;

                // get the hypotenuse so the radius is from one corner to the other
                mRadius = (int) Math.hypot(right, bottom);

                Animator reveal = ViewAnimationUtils.createCircularReveal(view, cx, cy, dpToPx(100), mRadius);
                reveal.setStartDelay(400);
                reveal.setInterpolator(new AccelerateInterpolator());
                reveal.setDuration(200);
                reveal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        // start with no alpha because we animate it
                        mViewDataBinding.body.setAlpha(0);

                        // revert full alpha. Its need to be 0 before Activity creating because shared elements transition
                        mViewDataBinding.getRoot().setAlpha(1);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        endAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                reveal.start();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animateClosing() {
        // Add reverse circle reveal for finish this
        Animator reveal = ViewAnimationUtils.createCircularReveal(mViewDataBinding.getRoot(), cx, cy, mRadius, dpToPx(50));
        reveal.setInterpolator(new DecelerateInterpolator());
        reveal.setDuration(400);
        reveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mHidingAnimationStarted = true;

                new Handler().postDelayed(() -> onBackPressed(), 200);

                AnimatorSet set = new AnimatorSet();
                set.setDuration(100);
                set.setInterpolator(new LinearInterpolator());

                ObjectAnimator alphaBody = ObjectAnimator.ofFloat(mViewDataBinding.body, "alpha", 1, 0);

                set.playTogether(alphaBody);
                set.start();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mViewDataBinding.getRoot().setBackgroundColor(getResources().getColor(android.R.color.white));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        reveal.start();
    }

    private void endAnimation() {
        // revert alpha to animate
        mViewDataBinding.body.setAlpha(1);

        // animate!!!
        Views.from((ViewGroup) mViewDataBinding.getRoot())
                .withId(R.id.body)
                .animateWith(SearchActivity.this, R.anim.enter_scale_fade)
                .withDelayBetweenEachChild(100)
//                .withEndAction(SearchActivity.this::setupCartAdapter)
                .start();
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
