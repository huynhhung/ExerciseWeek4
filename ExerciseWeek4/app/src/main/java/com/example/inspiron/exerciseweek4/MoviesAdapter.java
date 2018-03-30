package com.example.inspiron.exerciseweek4;

/**
 * Created by Inspiron on 15-Mar-18.
 */

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{
    // Store a member variable for the movies
    private List<Movie> mMovies;
    // Store the context for easy access
    private Context mContext;
    // Pass in the movie array into the constructor
    public MoviesAdapter(Context context, List<Movie> movies) {
        mMovies = movies;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Movie movie = mMovies.get(position);
        // Set item views based on my views and data model
        viewHolder.txtvTitle.setText(movie.getTitle());
        viewHolder.txtvDescription.setText(movie.getOverview());
        Picasso.get().load("http://image.tmdb.org/t/p/w500/" + movie.getPoster_path()).into(viewHolder.imgPoster);
        viewHolder.btnVideo.setBackgroundResource(movie.isVideo(movie.getVideo()) ? R.drawable.button_3_512 : R.drawable.prohibited_155486_640);
        /*viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailActivity= new Intent(mContext,MovieDetailActivity.class);
                detailActivity.putExtra("movie", Parcels.wrap(movie));    // use Parcel to send an object to another activity
                mContext.startActivity(detailActivity);
            }
        });*/
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView txtvTitle;
        public TextView txtvDescription;
        public ImageView imgPoster;
        public ImageView btnVideo;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);
            txtvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            txtvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            imgPoster = (ImageView) itemView.findViewById(R.id.imgPoster);
            btnVideo = (ImageButton) itemView.findViewById(R.id.imgPlay);
        }
        /*public void jumpToFragment(){
            Test t = new Test();
            switchContent(R.id.test1,t);
        }

        public void switchContent(int id, Fragment f){
            if (mContext==null){
                return;
            }
            if (mContext instanceof  MainActivity){
                MainActivity mainActivity = (MainActivity)mContext;
                Fragment fragment = f;
                mainActivity.switchContent(id,fragment);
            }
        }*/

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Movie movie = mMovies.get(position);
            openDetailActivity(movie.getTitle(),movie.getPoster_path(),movie.getOverview(),movie.getRelease_date(),movie.getVote_average());
        }
    }


    private void openDetailActivity(String title, String path_poster, String overview, String releaseDate, String vote_average)
    {
        Intent i=new Intent(mContext, MovieDetailActivity.class);
        //detailActivity.putExtra("result", Parcels.wrap(result));
        //PACK DATA TO SEND
        i.putExtra("title",title);
        i.putExtra("path_poster",path_poster);
        i.putExtra("overview",overview);
        i.putExtra("vote_average",vote_average);
        i.putExtra("releaseDate","Release Date: " + releaseDate);
        //open activity
        mContext.startActivity(i);

    }

    /*Context c;
    List<Movie> tvShows;
    LayoutInflater inflater;
    public MoviesAdapter(Context c, List<Movie> tvShows) {
        this.c = c;
        this.tvShows = tvShows;
    }

    @Override
    public int getCount() {
        return tvShows.size();
    }

    @Override
    public Object getItem(int position) {
        return tvShows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(itemView==null)
        {
            itemView=inflater.inflate(R.layout.item_movie,parent,false);
        }

        //TextView nameTxt= (TextView) convertView.findViewById(R.id.nameTxt);
        //ImageView img= (ImageView) convertView.findViewById(R.id.movieImage);
        TextView txtvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        TextView txtvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        ImageView imgPoster = (ImageView) itemView.findViewById(R.id.imgPoster);
        ImageButton btnVideo = (ImageButton) itemView.findViewById(R.id.imgPlay);
        //final String name=tvShows.get(position).getName();
        //int image=tvShows.get(position).getImage();
        txtvTitle.setText(tvShows.get(position).getTitle());
        txtvDescription.setText(tvShows.get(position).getOverview());
        Picasso.get().load("http://image.tmdb.org/t/p/w500/" + tvShows.get(position).getPoster_path()).into(imgPoster);
        btnVideo.setBackgroundResource(tvShows.get(position).isVideo(tvShows.get(position).getVideo()) ? R.drawable.button_3_512 : R.drawable.prohibited_155486_640);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "position: " + position, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(c, MovieDetailActivity.class);
                //Toast.makeText(c, "position: " + title + " " + path_poster +" "+ overview +" "+ releaseDate +" "+ vote_average, Toast.LENGTH_SHORT).show();
                //PACK DATA TO SEND
        i.putExtra("title",tvShows.get(position).getTitle());
        i.putExtra("path_poster",tvShows.get(position).getPoster_path());
        i.putExtra("overview",tvShows.get(position).getOverview());
        i.putExtra("vote_average",tvShows.get(position).getVote_average());
        i.putExtra("releaseDate","Release Date: " + tvShows.get(position).getRelease_date());
        //open activity
        //Toast.makeText(c, "position: " + title + " " + path_poster +" "+ overview +" "+ releaseDate +" "+ vote_average, Toast.LENGTH_SHORT).show();
        c.startActivity(i);
                //openDetailActivity(tvShows.get(position).getTitle(),tvShows.get(position).getPoster_path(),tvShows.get(position).getOverview(),tvShows.get(position).getRelease_date(),tvShows.get(position).getVote_average());
            }
        });
        return itemView;
    }


    private void openDetailActivity(String title, String path_poster, String overview, String releaseDate, String vote_average)
    {

        Intent i=new Intent(c, MovieDetailActivity.class);
        Toast.makeText(c, "position: " + title + " " + path_poster +" "+ overview +" "+ releaseDate +" "+ vote_average, Toast.LENGTH_SHORT).show();
        //PACK DATA TO SEND
        /*i.putExtra("title",title);
        //i.putExtra("path_poster",path_poster);
        i.putExtra("overview",overview);
        i.putExtra("vote_average",vote_average);
        i.putExtra("releaseDate","Release Date: " + releaseDate);
        //open activity
        Toast.makeText(c, "position: " + title + " " + path_poster +" "+ overview +" "+ releaseDate +" "+ vote_average, Toast.LENGTH_SHORT).show();
        c.startActivity(i);*/

        //}
}
