package com.example.m335_dylans_danielas_laniw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Bundle intentBundle = getIntent().getExtras();
        ArrayList<String> listing = intentBundle.getStringArrayList(ComicAdapter.INTENT_KEY_DETAIL);

        String titel = listing.get(0);
        TextView comicTitel = findViewById(R.id.comicTitle);
        comicTitel.setText(titel);

        String id = listing.get(1);
        TextView comicId = findViewById(R.id.idComic);
        comicId.setText(id);

        String url = listing.get(2);
        ImageView comicImg = findViewById(R.id.imgComic);
        Picasso.with(this).load(url).into(comicImg);

        String day = listing.get(3);
        String month = listing.get(4);
        String year = listing.get(5);
        String date = day + "." + month + "." + year;
        TextView comicDate = findViewById(R.id.dateComic);
        comicDate.setText(date);

        String alt = listing.get(6);
        TextView comicAlt = findViewById(R.id.altComic);
        comicAlt.setText(alt);
    }

}
