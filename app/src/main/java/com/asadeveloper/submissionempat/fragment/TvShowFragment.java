package com.asadeveloper.submissionempat.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asadeveloper.submissionempat.R;
import com.asadeveloper.submissionempat.adapter.TvAdapter;
import com.asadeveloper.submissionempat.model.MovieItems;
import com.asadeveloper.submissionempat.model.TvItems;
import com.asadeveloper.submissionempat.viewmodel.TvViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private RecyclerView rvTv;
    private ProgressDialog dialog;

    private TvViewModel tvViewModel;

    TvAdapter adapter;
    private Context context;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    public TvShowFragment() {
        // Required empty public constructor
        this.context = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tv_show, container, false);
        rvTv = root.findViewById(R.id.rv_tvshow);
        adapter = new TvAdapter(context);
        adapter.notifyDataSetChanged();


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvTv.setLayoutManager(layoutManager);
        rvTv.setAdapter(adapter);

        tvViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvViewModel.class);


        searchMovie( "");

        setHasOptionsMenu(true);

        return root;
    }

    public void searchMovie(String key){

        if(key == ""){

            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Sedang memuat data...");
            dialog.show();
            tvViewModel.setFilm("");
            tvViewModel.getFilm().observe(this, new Observer<ArrayList<TvItems>> () {
                @Override
                public void onChanged(ArrayList<TvItems> tvItems) {
                    if (tvItems != null) {
                        adapter.setData(tvItems);
                        dialog.dismiss();
                    }

                }
            });

        }
        else {
            tvViewModel.setFilm(key);
            tvViewModel.getFilm().observe(this, new Observer<ArrayList<TvItems>> () {
                @Override
                public void onChanged(ArrayList<TvItems> tvItems) {
                    if (tvItems != null) {
                        adapter.setData(tvItems);
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
                searchMovie(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchMovie(s);
                Toast.makeText(getContext(),"Coba "+s,Toast.LENGTH_LONG).show();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchMovie("");
                return false;
            }
        });


    }
}
