package com.asadeveloper.submissionempat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.asadeveloper.submissionempat.model.MovieItems;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);
        getSupportActionBar().setElevation(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

//        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//        if (searchManager != null) {
//            final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//            searchView.setQueryHint(getResources().getString(R.string.search));
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
////                    Toast.makeText(MainActivity.this, "tes"+query, Toast.LENGTH_SHORT).show();
//                    AsyncHttpClient client = new AsyncHttpClient();
//                    final ArrayList<MovieItems> listItems = new ArrayList<>();
//                    String API_KEY = BuildConfig.MovieAppId;
//                    String Language = Locale.getDefault().getLanguage();
//                    String url;
//                    if(Language=="en-US"){
//                        url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+query+"";
//                    }
//                    else {
//                        url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=id&query="+query+"";
//                    }
//                    client.get(url, new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
//                            try {
//                                String result = new String(responseBody);
//                                JSONObject responseObject = new JSONObject(result);
//                                JSONArray list = responseObject.getJSONArray("results");
//                                for (int i = 0; i < list.length(); i++) {
//                                    JSONObject movie = list.getJSONObject(i);
//                                    MovieItems movieItems = new MovieItems(movie);
//                                    if(!movieItems.getOverview().equals("")){
//                                        listItems.add(movieItems);
//                                    }
//                                }
//                            } catch (Exception e) {
//                                Toast.makeText(MainActivity.this, "gagal ", Toast.LENGTH_SHORT).show();
//                                Log.d("Exception", e.getMessage());
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
//                            Log.d("onFailure", error.getMessage());
//                        }
//
//                    });
//                    return true;
//                }
//                @Override
//                public boolean onQueryTextChange(String newText) {
//
//
//                    return true;
//                }
//            });
//        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        if (item.getItemId() == R.id.action_favorite) {
            Intent favorite = new Intent(this, FavoriteActivity.class);
            startActivity(favorite);
        }
        return super.onOptionsItemSelected(item);
    }
}
