package com.example.m335_dylans_danielas_laniw;

import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.m335_dylans_danielas_laniw.persistence.Comic;

import java.util.ArrayList;
import java.util.List;

/**
 * Functions of search bar
 *
 * @author Daniela Simões
 */
public class SearchBar extends AppCompatActivity {

    /**
     *
     * @param list contains all comics
     * @param searchTextField text field with user input
     * @param invalidText
     * @return
     */
    public List<Comic> performSearch(List<Comic> list, EditText searchTextField, TextView invalidText) {
        invalidText.setVisibility(View.INVISIBLE);
        List<Comic> comicsForSearch = new ArrayList<>();
        //gets the user input
        String searchedValue = searchTextField.getText().toString();

        if (searchedValue.matches("\\*?")) {
            //prints out every comic
            for (Comic comic : list) {
                comicsForSearch.add(comic);
            }
        } else if (searchedValue.matches("[A-Za-z\\s]+")) {
            //prints out all comics with a matching title/alt/transcript
            for (Comic comic : list) {
                if (    comic.getTitle().toLowerCase().contains(searchedValue.toLowerCase()) ||
                        comic.getAlt().toLowerCase().contains(searchedValue.toLowerCase()) ||
                        comic.getTranscript().toLowerCase().contains(searchedValue.toLowerCase()) ) {
                    comicsForSearch.add(comic);
                }
            }
        } else if (searchedValue.matches("\\d+")) {
            //prints out every comic with a matching ID
            for (Comic comic : list) {
                if (comic.getNum() == Integer.valueOf(searchedValue)) {
                    comicsForSearch.add(comic);
                }
            }
        } else {
            //tells the user the input is invalid
            invalidText.setText("Invalid input.\nPlease enter an id or keyword to search for.");
            invalidText.setVisibility(View.VISIBLE);
        }

        if (comicsForSearch.size() == 0 && invalidText.getText().equals("")) {
            //tells the user no entries were found
            invalidText.setText("No entries found.");
            invalidText.setVisibility(View.VISIBLE);
        }

        //returns list with all comics found with the search
        return comicsForSearch;
    }

}
