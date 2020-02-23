package com.asadeveloper.submissionempat.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asadeveloper.submissionempat.R;
import com.asadeveloper.submissionempat.adapter.FavoriteMovieAdapter;
import com.asadeveloper.submissionempat.db.NoteHelper;
import com.asadeveloper.submissionempat.model.MovieItems;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;

    private RecyclerView rvMovie;
    private Context context;

    private FavoriteMovieAdapter adapter;

    private ProgressDialog dialog;

    public FavoriteMovieFragment() {
        // Required empty public constructor
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        rvMovie = root.findViewById(R.id.rv_favoritemovie);
        adapter = new FavoriteMovieAdapter(context);
        adapter.notifyDataSetChanged();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvMovie.setLayoutManager(layoutManager);
        rvMovie.setAdapter(adapter);


        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Sedang memuat data...");
        dialog.show();

        NoteHelper helper = new NoteHelper(getContext());
        helper.open();
        // Ambil semua data mahasiswa di database
        ArrayList<MovieItems> favMovieadapter = helper.getAllMovie();
        helper.close();

        adapter.setData(favMovieadapter);
        dialog.dismiss();

        // Inflate the layout for this fragment
        return root;
    }


}
