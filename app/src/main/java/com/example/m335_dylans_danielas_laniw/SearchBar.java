package com.example.m335_dylans_danielas_laniw;

import android.annotation.SuppressLint;
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
@SuppressLint("Registered")
public class SearchBar extends AppCompatActivity {
    List<Comic> comicsForSearch = new ArrayList<>();

    private List<Comic> checkSearch(String searchedValue, List<Comic> list, TextView invalidText){

        if (searchedValue.matches("\\*?")) {
            // Gets all of the comics if the search term is either "" or "*".
            for (Comic comic : list) {
                comicsForSearch.add(comic);
            }
        } else if (searchedValue.matches("[A-Za-z\\s]+")) {
            // Gets all of the comics, which contain the search term in either the title, alt or transcript if the entry consists of letters and spaces.
            for (Comic comic : list) {
                if (    comic.getTitle().toLowerCase().contains(searchedValue.toLowerCase()) ||
                        comic.getAlt().toLowerCase().contains(searchedValue.toLowerCase()) ||
                        comic.getTranscript().toLowerCase().contains(searchedValue.toLowerCase()) ) {
                    comicsForSearch.add(comic);
                }
            }
        } else if (searchedValue.matches("\\d+")) {
            // Gets the comic with the given comic id.
            for (Comic comic : list) {
                if (comic.getNum() == Integer.valueOf(searchedValue)) {
                    comicsForSearch.add(comic);
                }
            }
        } else {
            // Informs the user that their search term is invalid.
            invalidText.setText(R.string.invalid_search);
            invalidText.setVisibility(View.VISIBLE);
        }

        if (comicsForSearch.size() == 0 && invalidText.getText().equals("")) {
            // Informs the user that their search didn't match any entries.
            invalidText.setText(R.string.no_entries_search);
            invalidText.setVisibility(View.VISIBLE);
        }

        return comicsForSearch;
    }

    /**
     * Performs the search with the given search value.
     * @param list - list of comics
     * @param searchTextField - search field
     * @param invalidText - invalid text textview incase there's an issue with the search
     * @return - found comics
     */
    public List<Comic> performSearch(List<Comic> list, EditText searchTextField, TextView invalidText) {
        invalidText.setVisibility(View.INVISIBLE);

        //gets the user input
        String searchedValue = searchTextField.getText().toString();

        comicsForSearch = checkSearch(searchedValue, list, invalidText);

        //returns list with all comics found with the search
        return comicsForSearch;
    }

}
