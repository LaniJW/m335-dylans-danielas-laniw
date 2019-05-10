package com.example.bottom_nav_test;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ListView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private SearchBar searchBarClass;
    private EditText searchTextField;
    ArrayList<Integer> comicID = new ArrayList<>();
    private ListView mMainListView;
    private Context context;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // test data for card list view in home
                    List<Comic> homeComicList = new ArrayList<>();
                    for(int i = 1; i <= 12; i++){
                        Comic comic1 = new Comic("test_home" + i, "url");
                        homeComicList.add(comic1);
                    }
                    mMainListView.setAdapter(new ComicAdapter(context, new ArrayList<Comic>()));
                    mMainListView.setAdapter(new ComicAdapter(context, homeComicList));
                    return true;
                case R.id.navigation_favorites:
                    // test data for card list view in favorites
                    List<Comic> favComicList = new ArrayList<>();
                    for(int i = 1; i <= 12; i++){
                        Comic comic1 = new Comic("test_fav" + i, "url");
                        favComicList.add(comic1);
                    }
                    mMainListView.setAdapter(new ComicAdapter(context, new ArrayList<Comic>()));
                    mMainListView.setAdapter(new ComicAdapter(context, favComicList));
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
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mMainListView = findViewById(R.id.main_listView);
        context = this.getApplicationContext();

        // test data for card list view in home
        List<Comic> homeComicList = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            Comic comic1 = new Comic("test_home" + i, "url");
            homeComicList.add(comic1);
        }

        // test data for card list view in favorites
        List<Comic> favComicList = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            Comic comic1 = new Comic("test_fav" + i, "url");
            favComicList.add(comic1);
        }

        mMainListView.setAdapter(new ComicAdapter(this, homeComicList));
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
