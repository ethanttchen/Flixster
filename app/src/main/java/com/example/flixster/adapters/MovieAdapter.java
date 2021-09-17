package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.MainActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;
    int POPULAR = 1;
    int BORING = 0;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // tells the adapter which type of view to inflate
    @Override
    public int getItemViewType(int position) {
        System.out.println(movies.get(position).toString());
        if(movies.get(position).getVote_average() > 10)
            return POPULAR;
        else
            return BORING;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        MovieAdapter.ViewHolder viewHolder;

        // if the movie is popular we inflate the FiveStarViewHolder to show just the poster in large backdrop format
        if(viewType == POPULAR){
            View fiveStar = LayoutInflater.from(context).inflate(R.layout.full_backdrop, parent, false);
            return new FiveStarViewHolder(fiveStar);
        } else { // else we show normal poster with title and overview
            View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(movieView);
        }
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder + " + position);

        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // main view holder which displays the movie poster, title, and overview
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // If phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                // then imageUrl = back drop image
                imageUrl = movie.getBackdropPath();
            } else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }
            Glide.with(context).load(imageUrl).placeholder(R.drawable.loading).into(ivPoster);

            // Registers a click listener on the whole row
            // Navigates to a new activity on tap

            // sets a click listener so when the title is clicked a toast message pops up
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, (View)tvTitle, "profile");
                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }

    // this view holder is used if a movie is 5 or more stars that just displays the backdrop poster
    public class FiveStarViewHolder extends MovieAdapter.ViewHolder {

        ImageView ivBackDrop;

        public FiveStarViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackDrop = itemView.findViewById(R.id.ivBackDrop);
        }

        public void bind(Movie movie) {
            Glide.with(context).load(movie.getBackdropPath()).placeholder(R.drawable.loading).into(ivBackDrop);
        }
    }

}
