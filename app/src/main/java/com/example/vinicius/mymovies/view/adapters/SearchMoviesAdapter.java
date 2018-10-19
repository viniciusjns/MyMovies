package com.example.vinicius.mymovies.view.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vinicius.mymovies.R;
import com.example.vinicius.mymovies.databinding.ItemSearchMoviesBinding;
import com.example.vinicius.mymovies.model.Movie;
import com.example.vinicius.mymovies.service.APIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchMoviesAdapter extends RecyclerView.Adapter<SearchMoviesAdapter.ViewHolder> {

    private List<Movie> movies;
    private OnClickSearchMovies onClickSearchMovies;

    public SearchMoviesAdapter(List<Movie> movies, OnClickSearchMovies onClickSearchMovies) {
        this.movies = movies;
        this.onClickSearchMovies = onClickSearchMovies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchMoviesBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_search_movies,
                parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemSearchMoviesBinding binding;
        private Picasso picasso;

        public ViewHolder(ItemSearchMoviesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.picasso = APIClient.getInstance().getPicasso();
        }

        public void bind(Movie movie) {
            this.picasso.load(movie.getPoster()).into(binding.ivPoster);
            binding.tvTitle.setText(movie.getTitle());
            binding.tvYear.setText(movie.getYear());
            binding.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSearchMovies.onClickOpenMovieDetail(movie);
                }
            });
        }
    }

    public interface OnClickSearchMovies {
        void onClickOpenMovieDetail(Movie movie);
    }
}
