package com.example.m335_dylans_danielas_laniw;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m335_dylans_danielas_laniw.persistence.Comic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComicAdapter extends ArrayAdapter<Comic> {

    private LayoutInflater layoutInflater;

    public ComicAdapter(Context context, List<Comic> comics){
        super(context, R.layout.comic_card);
        addAll(comics);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.comic_card, null);
            viewHolder = new ViewHolder();
            viewHolder.comicTitleTextView = convertView.findViewById(R.id.comicTitle);
            viewHolder.comicImageView = convertView.findViewById(R.id.imageView);
            if(getItem(position).isFavorised()){
                favoriteButton.setBackground("@drawable/ic_star_black_24dp");
            } else {
                favoriteButton.setBackground("@drawable/ic_star_border_black_24dp");
            }
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
