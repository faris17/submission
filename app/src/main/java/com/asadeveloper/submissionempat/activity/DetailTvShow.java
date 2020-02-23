package com.asadeveloper.submissionempat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asadeveloper.submissionempat.R;
import com.asadeveloper.submissionempat.db.NoteHelper;
import com.asadeveloper.submissionempat.fragment.FavoriteTvFragment;
import com.asadeveloper.submissionempat.model.Favorite;
import com.asadeveloper.submissionempat.model.TvItems;
import com.bumptech.glide.Glide;

import static android.provider.BaseColumns._ID;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.DATE;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.KATEGORI;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.OVERVIEW;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.POSTER;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.TITLE;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.VOTE;

public class DetailTvShow extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_film";
    ImageView poster,favorite;
    TextView title, tahun, vote, overview, delete;
    private NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        noteHelper = NoteHelper.getInstance(getApplicationContext());
        noteHelper.open();

        poster = findViewById(R.id.img_photo);
        title  = findViewById(R.id.title);
        tahun  = findViewById(R.id.tahun_film);
        vote   = findViewById(R.id.vote);
        overview = findViewById(R.id.overview);
        favorite = findViewById(R.id.imgfavorite);
        delete = findViewById(R.id.btn_delete);

        TvItems tv = getIntent().getParcelableExtra(EXTRA_FILM);
        final int idkey = tv.getId() ;
        final String nama = tv.getOriginal_name() ;
        final String release = tv.getFirst_air_date();
        final String description = tv.getOverview();
        final String count = tv.getVote_average();
        final String datafoto = tv.getPoster_path();
        final String kategori ="tvshow";


        Glide.with(this).load(datafoto).into(poster);
        title.setText(nama);
        tahun.setText(release);
        vote.setText(count);
        overview.setText(description);

        favorite.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gunakan contentvalues untuk menampung data
                ContentValues values = new ContentValues();
                values.put(_ID, idkey);
                values.put(TITLE, nama);
                values.put(OVERVIEW, description);
                values.put(DATE, release);
                values.put(POSTER, datafoto);
                values.put(KATEGORI, kategori);
                values.put(VOTE, count);
                long newRow = noteHelper.insert(values);

                if(newRow >0){
                    Toast.makeText(DetailTvShow.this, "Data Berhasil Disimpan.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DetailTvShow.this, "Data sudah tersedia sebelumnya.", Toast.LENGTH_SHORT).show();
                }

            }
        }));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteHelper.deleteById(String.valueOf(idkey));
                Toast.makeText(DetailTvShow.this, "Berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
