package com.example.inspiron.exerciseweek4;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Inspiron on 30-Mar-18.
 */

public class Test extends Fragment {
    private OkHttpClient client;
    private String JsonStr = "";
    TextView textView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.testapp,container,false);
        textView = (TextView) rootView.findViewById(R.id.textView);
        return rootView;
    }
}
