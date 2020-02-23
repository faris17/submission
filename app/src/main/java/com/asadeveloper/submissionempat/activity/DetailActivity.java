package com.asadeveloper.submissionempat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.asadeveloper.submissionempat.FavoriteActivity;
import com.asadeveloper.submissionempat.R;
import com.asadeveloper.submissionempat.db.NoteHelper;
import com.asadeveloper.submissionempat.model.Favorite;
import com.asadeveloper.submissionempat.model.MovieItems;
import com.bumptech.glide.Glide;

import static android.provider.BaseColumns._ID;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.DATE;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.KATEGORI;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.OVERVIEW;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.POSTER;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.TITLE;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.VOTE;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_data";
    TextView thn, nam, desc,delete;
    ImageView photo,favorite;

    private ProgressDialog dialog;

    private NoteHelper noteHelper;
    MovieItems film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dialog = new ProgressDialog(this);
        noteHelper = NoteHelper.getInstance(getApplicationContext());
        noteHelper.open();

        thn = findViewById(R.id.tahun_film);
        nam = findViewById(R.id.judul_film);
        desc= findViewById(R.id.txt_description);
        photo = findViewById(R.id.img_photo);
        favorite = findViewById(R.id.imgfavorite);
        delete = findViewById(R.id.btn_delete);

        dialog.setMessage("Sedang memuat data...");
        dialog.show();

        film = getIntent().getParcelableExtra(EXTRA_FILM);
//        if(film.getKategori()=="favmovie"){
//            favorite.setVisibility(View.GONE);
//            delete.setVisibility(View.VISIBLE);
//        }
//        else  {
//            delete.setVisibility(View.GONE);
//            favorite.setVisibility(View.VISIBLE);
//        }

        final int idkey = film.getId();
        final String poster = film.getPoster_path();
        final String title = film.getOriginal_title();
        final String rilis = film.getRelease_date();
        final String description = film.getOverview();
        final String kategori ="movie";

        Glide.with(this).load(poster).into(photo);
        nam.setText(title);
        thn.setText(rilis);
        desc.setText(description);

        dialog.dismiss();

        favorite.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gunakan contentvalues untuk menampung data
                ContentValues values = new ContentValues();
                values.put(_ID, idkey);
                values.put(TITLE, title);
                values.put(OVERVIEW, description);
                values.put(DATE, rilis);
                values.put(POSTER, poster);
                values.put(KATEGORI, kategori);
                long cek = noteHelper.insert(values);

                if(cek >0){
                    Toast.makeText(DetailActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(DetailActivity.this, "Data Sudah Tersedia", Toast.LENGTH_SHORT).show();
                }


            }
        }));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteHelper.deleteById(String.valueOf(idkey));
                Intent favmovie = new Intent(DetailActivity.this, FavoriteActivity.class);
                startActivity(favmovie);
                Toast.makeText(DetailActivity.this, "Berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
