package com.asadeveloper.submissionempat.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.asadeveloper.submissionempat.BuildConfig;
import com.asadeveloper.submissionempat.model.TvItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class TvViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TvItems>> listTv = new MutableLiveData<>();
    private static final String API_KEY = BuildConfig.MovieAppId;
    public void setFilm(final String film) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvItems> listItems = new ArrayList<>();
        String Language = Locale.getDefault().getLanguage();
        String url;
        if(film ==""){
            if(Language=="en-US"){
                url = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language=en-US";
            }
            else {
                url = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language=id";
            }
        }
        //jika ada pencarian
        else {
            if(Language=="en-US"){
                url = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=en-US&query="+film+"";
            }
            else {
                url = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=id&query="+film+"";
            }
        }

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        TvItems tvItems = new TvItems(tv);
                        if(!tvItems.getOverview().equals("")){
                            listItems.add(tvItems);
                        }
                    }
                    listTv.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }

        });
    }

    public LiveData<ArrayList<TvItems>> getFilm() {
        return listTv;
    }
}
