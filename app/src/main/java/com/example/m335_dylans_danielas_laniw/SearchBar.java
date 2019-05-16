package com.example.m335_dylans_danielas_laniw;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;

import android.widget.EditText;

import com.example.m335_dylans_danielas_laniw.persistence.Comic;

import java.util.ArrayList;
import java.util.List;

public class SearchBar extends AppCompatActivity {

    public List<Comic> performSearch(List<Comic> list, EditText searchTextField) {
        List<Comic> comicsForSearch = new ArrayList<>();
        String searchedID = searchTextField.getText().toString();

        if (searchedID.equals("") || searchedID.equals("*")) {
            for (Comic comic : list) {
                comicsForSearch.add(comic);
            }
        } else if(searchedID.matches("[A-Za-z\\s]+")){
            for(Comic comic : list) {
                if(comic.getTitle().contains(searchedID)){
                    comicsForSearch.add(comic);
                }
            }
        } else if(searchedID.matches("\\d+")){
            for(Comic comic : list) {
                if (comic.getNum() == Integer.valueOf(searchedID)) {
                    comicsForSearch.add(comic);
                }
            }
        } else {
        }

        return comicsForSearch;
    }

    public void openDropdown(Dialog dialog) {
        dialog.show();
    }

}
