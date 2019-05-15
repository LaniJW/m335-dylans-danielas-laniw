package com.example.bottom_nav_test;

import android.support.v7.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchBar extends AppCompatActivity {

    public List<Comic> performSearch(ArrayList<Comic> list, EditText searchTextField){
        List<Comic> comicsForSearch = new ArrayList<>();
        String searchedID = searchTextField.getText().toString();

        if(searchedID.equals("") || searchedID.equals("*")){
            for(Comic comic : list){
                comicsForSearch.add(comic);
            }
        } else if(searchedID.matches("\\d")){
            for(Comic comic : list) {
                if (comic.getId() == Integer.valueOf(searchedID)) {
                    comicsForSearch.add(comic);
                }
            }
        } else {
        }

        return comicsForSearch;
    }

    public void openDropdown() {

    }

}
