package com.asadeveloper.favoriteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.asadeveloper.favoriteapp.adapter.FavoriteMovieAdapter;
import com.asadeveloper.favoriteapp.db.FavoriteHelper;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_movie;
    private FavoriteMovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_movie = findViewById(R.id.rv_movie);
        rv_movie.setLayoutManager(new LinearLayoutManager(this));
        rv_movie.setHasFixedSize(true);

        adapter = new FavoriteMovieAdapter(this);
        rv_movie.setAdapter(adapter);

        FavoriteHelper helper = new FavoriteHelper();
        // Ambil semua data mahasiswa di database
        ArrayList<MovieItems> favMovieadapter = helper.getAllMovie();


        adapter.setData(favMovieadapter);
    }
}
