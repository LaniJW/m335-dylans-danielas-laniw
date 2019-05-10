package com.example.bottom_nav_test;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private SearchBar searchBarClass;
    private EditText searchTextField;
    ArrayList<Integer> comicID = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_favorites);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        comicID.add(6);
        comicID.add(5);
        comicID.add(4);
        comicID.add(3);
        comicID.add(2);
        comicID.add(1);

        Toolbar searchBar = findViewById(R.id.search_bar);
        setSupportActionBar(searchBar);
        Button searchBarSearchButton = findViewById(R.id.search_bar_search_button);
        searchBarSearchButton.setOnClickListener(performSearch);
        Button searchBarDropDown = findViewById(R.id.search_bar_dropdown_button);
        searchBarDropDown.setOnClickListener(openDropdown);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private View.OnClickListener performSearch = new View.OnClickListener() {
        @Override
        public void onClick(View button1){
            searchBarClass = new SearchBar();
            searchTextField = findViewById(R.id.search_bar_text_field);
            mTextMessage = findViewById(R.id.textView4);
            searchBarClass.performSearch(comicID, searchTextField, mTextMessage);
        }
    };

    private View.OnClickListener openDropdown = new View.OnClickListener() {
        @Override
        public void onClick(View button1){
            searchBarClass = new SearchBar();
            searchBarClass.openDropdown();
        }
    };

}
