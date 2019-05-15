package com.example.bottom_nav_test;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bottom_nav_test.persistence.Comic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComicAdapter extends ArrayAdapter<Comic> {

    private LayoutInflater layoutInflater;

    public ComicAdapter(Context context, List<Comic> comics){
        super(context, R.layout.comic_card);
        addAll(comics);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.comic_card, null);
            viewHolder = new ViewHolder();
            viewHolder.comicTitleTextView = convertView.findViewById(R.id.comicTitle);
            viewHolder.comicImageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String name = getItem(position).getTitle();
        viewHolder.comicTitleTextView.setText(name);
        Picasso.with(getContext()).load(getItem(position).getImg()).into(viewHolder.comicImageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ItemClickListener", name);
            }
        });

        return convertView;
    }

    public void openDetailView(String position) {

    }

    public static class ViewHolder {
        TextView comicTitleTextView;
        ImageView comicImageView;
    }

}
