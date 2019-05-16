package com.example.m335_dylans_danielas_laniw;

import android.app.Dialog;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.m335_dylans_danielas_laniw.persistence.Comic;

import java.util.ArrayList;
import java.util.List;

public class SearchBar extends AppCompatActivity {

    public List<Comic> performSearch(List<Comic> list, EditText searchTextField, String filter, TextView invalidText) {
        invalidText.setVisibility(View.INVISIBLE);
        List<Comic> comicsForSearch = new ArrayList<>();
        String searchedValue = searchTextField.getText().toString();

//        if (searchedValue.equals("") || searchedValue.equals("*")) {
//            for (Comic comic : list) {
//                comicsForSearch.add(comic);
//            }
//        } else {
//            switch (filter) {
//                case "radio_button_id":
//                    if (searchedValue.matches("\\d+")) {
//                        for (Comic comic : list) {
//                            if (comic.getNum() == Integer.valueOf(searchedValue)) {
//                                comicsForSearch.add(comic);
//                            }
//                        }
//                    } else {
//                        invalidText.setVisibility(View.VISIBLE);
//                    }
//                    break;
//                case "radio_button_title":
//                    for (Comic comic : list) {
//                        if (comic.getTitle().contains(searchedValue)) {
//                            comicsForSearch.add(comic);
//                        }
//                    }
//                    break;
//                case "radio_button_transcript":
//                    for (Comic comic : list) {
//                        if (comic.getTranscript().contains(searchedValue)) {
//                            comicsForSearch.add(comic);
//                        }
//                    }
//                    break;
//            }
//        }


        if (searchedValue.equals("") || searchedValue.equals("*")) {
            for (Comic comic : list) {
                comicsForSearch.add(comic);
            }
        } else if(searchedValue.matches("[A-Za-z\\s]+")){
            for(Comic comic : list) {
                if(comic.getTitle().contains(searchedValue)){
                    comicsForSearch.add(comic);
                }
            }
        } else if(searchedValue.matches("\\d+")){
            for(Comic comic : list) {
                if (comic.getNum() == Integer.valueOf(searchedValue)) {
                    comicsForSearch.add(comic);
                }
            }
        } else {
            invalidText.setVisibility(View.VISIBLE);
        }

        return comicsForSearch;
    }

    public void openDropdown(Dialog dialog) {
        dialog.show();
    }

}
