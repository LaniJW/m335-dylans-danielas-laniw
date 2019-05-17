package com.example.m335_dylans_danielas_laniw;

import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.m335_dylans_danielas_laniw.persistence.Comic;

import java.util.ArrayList;
import java.util.List;

public class SearchBar extends AppCompatActivity {

    public List<Comic> performSearch(List<Comic> list, EditText searchTextField, TextView invalidText) {
        invalidText.setVisibility(View.INVISIBLE);
        List<Comic> comicsForSearch = new ArrayList<>();
        String searchedValue = searchTextField.getText().toString();

        if (searchedValue.matches("\\*?")) {
            for (Comic comic : list) {
                comicsForSearch.add(comic);
            }
        } else if (searchedValue.matches("[A-Za-z\\s]+")) {
            for (Comic comic : list) {
                if (comic.getTitle().contains(searchedValue) || comic.getAlt().contains(searchedValue) || comic.getTranscript().contains(searchedValue)) {
                    comicsForSearch.add(comic);
                }
            }
        } else if (searchedValue.matches("\\d+")) {
            for (Comic comic : list) {
                if (comic.getNum() == Integer.valueOf(searchedValue)) {
                    comicsForSearch.add(comic);
                }
            }
        } else {
            invalidText.setText("Invalid input.\nPlease enter an id or keyword to search for.");
            invalidText.setVisibility(View.VISIBLE);
        }

        if (comicsForSearch.size() == 0 && invalidText.getText().equals("")) {
            invalidText.setText("No entries found.");
            invalidText.setVisibility(View.VISIBLE);
        }

        return comicsForSearch;
    }

}
