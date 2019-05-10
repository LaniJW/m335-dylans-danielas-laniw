package com.example.bottom_nav_test;

import android.support.v7.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchBar extends AppCompatActivity {

    public void performSearch(ArrayList<Integer> list, EditText searchTextField, TextView text){

        String searchedID = searchTextField.getText().toString();
        text.setText("No comic with ID \"" + searchedID + "\" found.");

        if(searchedID.equals("") || searchedID.equals("*")){
            for(int ID : list){
                text.setText("I would print everything out!");
            }
        } else if(searchedID.matches("\\d")){
            for(int ID : list) {
                if (ID == Integer.valueOf(searchedID)) {
                    text.setText(ID + " found");
                }
            }
        } else {
            text.setText("Invalid input type.");
        }


    }

    public void openDropdown() {

    }

}
