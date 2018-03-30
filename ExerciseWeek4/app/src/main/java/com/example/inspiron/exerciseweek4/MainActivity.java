package com.example.inspiron.exerciseweek4;

import android.annotation.SuppressLint;
import android.app.UiAutomation;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private MyPagerAdapter myPagerAdapter;
    private ViewPager mViewPager;
    private OkHttpClient client;
    private static String nowPlayingJsonStr = "";
    private static String topRateJsonStr = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();
        getJSonStrNowPlayingMovies();
        getJSonStrNowTopRateMovies();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void getJSonStrNowTopRateMovies() {
        final Request request = new Request.Builder().url("https://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            topRateJsonStr = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void getJSonStrNowPlayingMovies() {
        final Request request = new Request.Builder().url("https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            nowPlayingJsonStr = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowPlayingFragment(), "Now Playing");
        adapter.addFragment(new TopRateFragment(), "Top Rate");
        viewPager.setAdapter(adapter);
    }

    public static List<Movie> getTopRateMovies(){
        //getWebservice();
        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(topRateJsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            movieList = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i<jsonArray.length();i++){
                JSONObject finalObject = jsonArray.getJSONObject(i);
                Movie movie = gson.fromJson(finalObject.toString(),Movie.class);
                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public static List<Movie> getNowPlayingMovies(){
        //getWebservice();
        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(nowPlayingJsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            movieList = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i<jsonArray.length();i++){
                JSONObject finalObject = jsonArray.getJSONObject(i);
                Movie movie = gson.fromJson(finalObject.toString(),Movie.class);
                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public void switchContent(int id, Fragment f){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(id,f,f.toString());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
