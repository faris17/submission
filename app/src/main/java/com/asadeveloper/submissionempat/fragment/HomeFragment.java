package com.asadeveloper.submissionempat.fragment;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asadeveloper.submissionempat.BuildConfig;
import com.asadeveloper.submissionempat.MainActivity;
import com.asadeveloper.submissionempat.R;
import com.asadeveloper.submissionempat.adapter.MovieAdapter;
import com.asadeveloper.submissionempat.model.MovieItems;
import com.asadeveloper.submissionempat.viewmodel.MovieViewModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment  {
    private RecyclerView rvMovie;
    private ProgressDialog dialog;

    private  MovieViewModel movieViewModel;

    MovieAdapter adapter;
    private Context context;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    public HomeFragment() {
        // Required empty public constructor
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rvMovie = root.findViewById(R.id.rv_movie);
        adapter = new MovieAdapter(context);
        adapter.notifyDataSetChanged();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvMovie.setLayoutManager(layoutManager);
        rvMovie.setAdapter(adapter);

        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);


        searchMovie("", null);

        setHasOptionsMenu(true);

        return root;
    }

    public void searchMovie(String type, String key){

        if(key == null){
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Sedang memuat data...");
            dialog.show();
            movieViewModel.setMovie(null);
            movieViewModel.getMovie().observe(this, new Observer<ArrayList<MovieItems>> () {
                @Override
                public void onChanged(ArrayList<MovieItems> movieItems) {
                    if (movieItems != null) {
                        adapter.setData(movieItems);

                    }
                    dialog.dismiss();
                }
            });

        }
        else {
            movieViewModel.setMovie(key);
            movieViewModel.getMovie().observe(this, new Observer<ArrayList<MovieItems>> () {
                @Override
                public void onChanged(ArrayList<MovieItems> movieItems) {
                    if (movieItems != null) {
                        adapter.setData(movieItems);
                    }

                }
            });

        }

    }

    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        menu.findItem(R.id.search).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_search));
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    searchMovie("", s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    searchMovie("", s);
                    return false;
                }
            });
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    searchMovie("", null);
                    return false;
                }
            });


    }

}
