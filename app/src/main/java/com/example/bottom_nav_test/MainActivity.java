package com.example.bottom_nav_test;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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

}
