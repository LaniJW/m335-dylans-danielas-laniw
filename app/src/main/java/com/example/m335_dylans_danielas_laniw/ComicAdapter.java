package com.example.m335_dylans_danielas_laniw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m335_dylans_danielas_laniw.persistence.AppDatabase;
import com.example.m335_dylans_danielas_laniw.persistence.Comic;
import com.example.m335_dylans_danielas_laniw.persistence.ComicDao;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ComicAdapter extends ArrayAdapter<Comic> {

    private final ComicDao comicDao;
    private LayoutInflater layoutInflater;
    private ComicDao mComicDao;
    private Context context;
    public static final String INTENT_KEY_DETAIL = "TEXT_TO_TEST_FUNCTIONALITY";

    public ComicAdapter(Context context, List<Comic> comics) {
        super(context, R.layout.comic_card);
        this.context = context;
        addAll(comics);
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        comicDao = AppDatabase.getAppDb(context.getApplicationContext()).getComicDao();

        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.comic_card, null);

            viewHolder.favoriteButton = convertView.findViewById(R.id.favorite_button);
            viewHolder.comicTitleTextView = convertView.findViewById(R.id.comicTitle);
            viewHolder.comicImageView = convertView.findViewById(R.id.imageView);
            if (getItem(position).isFavorised()) {
                viewHolder.favoriteButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
            } else {
                viewHolder.favoriteButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String title = getItem(position).getTitle();
        final String img = getItem(position).getImg();
        final String id = Integer.toString(getItem(position).getNum());
        final String day = getItem(position).getDay();
        final String month = getItem(position).getMonth();
        final String year = getItem(position).getYear();
        final String transcript = getItem(position).getTranscript();
        final String isFavorite = Boolean.toString(getItem(position).isFavorised());
        final ArrayList<String> fullComic = new ArrayList<>();
        fullComic.add(title);
        fullComic.add(id);
        fullComic.add(img);
        fullComic.add(day);
        fullComic.add(month);
        fullComic.add(year);
        fullComic.add(transcript);
        fullComic.add(isFavorite);

        Picasso.with(getContext()).load(getItem(position).getImg()).into(viewHolder.comicImageView);


        convertView.setOnClickListener(new View.OnClickListener() {
        viewHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Item Clicked", title);
                Intent intent = new Intent(getContext(), Description.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(INTENT_KEY_DETAIL, fullComic);
                intent.putExtras(bundle);
                context.startActivity(intent);

                Comic comic = comicDao.getByNum(getItem(position).getNum());
                if (comic.isFavorised()) {
                    comicDao.updateFavorised(false, getItem(position).getNum());
                } else {
                    comicDao.updateFavorised(true, getItem(position).getNum());
                }
            }
        });


        return convertView;
    }

    public void openDetailView(String position) {

    }

    public static class ViewHolder {
        TextView comicTitleTextView;
        ImageView comicImageView;
        Button favoriteButton;
    }

}
