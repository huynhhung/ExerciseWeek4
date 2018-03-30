package com.example.inspiron.exerciseweek4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by Inspiron on 30-Mar-18.
 */

public class TopRateFragment extends Fragment {
    public static List<Movie> movieList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_rate,container,false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rvTopRate);
        movieList = MainActivity.getTopRateMovies();
        MoviesAdapter moviesAdapter = new MoviesAdapter(this.getActivity(),movieList);
        recyclerView.setAdapter(moviesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        return  rootView;
    }
}
