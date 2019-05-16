package com.example.m335_dylans_danielas_laniw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.example.m335_dylans_danielas_laniw.persistence.AppDatabase;
import com.example.m335_dylans_danielas_laniw.persistence.Comic;
import com.example.m335_dylans_danielas_laniw.persistence.ComicDao;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Response";
    private ConstraintLayout constraintLayout;
    private SearchBar searchBarClass;
    private EditText searchTextField;
    ArrayList<Comic> comicID = new ArrayList<>();
    private ListView mMainListView;
    private ProgressBar mSpinner;
    private Context context;
    private int highestId;
    private ArrayList homeComicList;
    private ComicDao mComicDao;
    private FloatingActionButton reloadButton;
    private CardView cardView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            reloadButton = findViewById(R.id.reload_button);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    reloadButton.show();
                    // Add home comics
                    loadHomeCards();
                    return true;
                case R.id.navigation_favorites:
                    reloadButton.hide();
                    loadFavCards();
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
        mSpinner = findViewById(R.id.progressBar);
        context = this.getApplicationContext();
        mComicDao = AppDatabase.getAppDb(getApplicationContext()).getComicDao();

        reloadButton = findViewById(R.id.reload_button);
        reloadButton.setOnClickListener(reload);

        final Toolbar searchBar = findViewById(R.id.search_bar);
        setSupportActionBar(searchBar);
        Button searchBarSearchButton = findViewById(R.id.search_bar_search_button);
        searchBarSearchButton.setOnClickListener(performSearch);
        Button searchBarDropDown = findViewById(R.id.search_bar_dropdown_button);
        searchBarDropDown.setOnClickListener(openDropdown);

        loadAllComics();
        loadHomeCards();
    }

    void loadAllComics() {
        // Build request data for API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.xkcd.com/info.0.json")
                .build();

        // Request data from API and work with it asynchronically
        Callback responseCallback = new Callback() {
            // Display toast if comics are unavailable.
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast toast = Toast.makeText(getApplicationContext(), "Failed to get information for a random comic.", Toast.LENGTH_LONG);
                //toast.show();

                Log.e("OkHttp Fail", e.getMessage());
            }

            // Work with response
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // Get data from API and save data in Comic object.
                final Comic comic = new Gson().fromJson(response.body().string(), Comic.class);

                // Save id of last comic
                highestId = comic.getNum();

                // Run view-related code back on the main thread
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Create and add one item to comic list
                            homeComicList = new ArrayList<>();
                            homeComicList.add(new Comic(comic.getNum(), "Newest: \"" + comic.getTitle() + "\"", comic.getSafe_title(), comic.getImg(), comic.getDay(), comic.getMonth(), comic.getYear(), comic.getTranscript(), comic.getAlt()));

                            // Display comic
                            mMainListView.setAdapter(new ComicAdapter(context, homeComicList));
                        } catch (NullPointerException e) {
                            // Display toast if NullPointerException occurs
                            e.printStackTrace();
                            Toast toast = Toast.makeText(getApplicationContext(), "Error occured while displaying newest comic information.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

                for (int i = 1; i <= (int) (highestId / 7); i += 1) {
                    if (mComicDao.getByNum(i) == null){
                        // Build request data for API
                        Request request = new Request.Builder()
                                .url("https://www.xkcd.com/" + i + "/info.0.json")
                                .build();

                        // Request data from API and work with it asynchronically
                        OkHttpClient client = new OkHttpClient();
                        client.newCall(request).enqueue(new Callback() {
                            // Display toast if comics are unavailable.
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e("OkHttp Fail", e.getMessage());

                                // Make the Spinner invisible
                                mSpinner.setVisibility(View.INVISIBLE);
                            }

                            // Work with response
                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                // Get data from API and save data in Comic object.
                                final Comic comic = new Gson().fromJson(response.body().string(), Comic.class);

                                mComicDao.insert(new Comic(comic.getNum(), comic.getTitle(), comic.getSafe_title(), comic.getImg(), comic.getDay(), comic.getMonth(), comic.getYear(), comic.getTranscript(), comic.getAlt()));
                            }
                        });
                    }
                }
            }
        };
        client.newCall(request).enqueue(responseCallback);
    }

    void loadHomeCards() {
        // Display loading icon
        mSpinner.setVisibility(View.VISIBLE);
        // Empty ListView
        mMainListView.setAdapter(new ComicAdapter(context, new ArrayList<Comic>()));
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("mMainListView.setOnItemClickListener", "testing item clicking");

            }
        });
        // Build request data for API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.xkcd.com/info.0.json")
                .build();

        // Request data from API and work with it asynchronically
        client.newCall(request).enqueue(new Callback() {
            // Display toast if comics are unavailable.
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("OkHttp Fail", e.getMessage());
            }

            // Work with response
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // Get data from API and save data in Comic object.
                final Comic comic = new Gson().fromJson(response.body().string(), Comic.class);

                // Save id of last comic
                highestId = comic.getNum();

                // Run view-related code back on the main thread
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Create and add one item to comic list
                            homeComicList = new ArrayList<>();
                            homeComicList.add(new Comic(comic.getNum(), "Newest: \"" + comic.getTitle() + "\"", comic.getSafe_title(), comic.getImg(), comic.getDay(), comic.getMonth(), comic.getYear(), comic.getTranscript(), comic.getAlt()));

                            // Display comic
                            mMainListView.setAdapter(new ComicAdapter(context, homeComicList));
                        } catch (NullPointerException e) {
                            Log.e("OkHttp Fail", e.getMessage());
                        }
                    }
                });

                // Get random number of existing comics (yes, the comic with the id 404 doesn't exist)
                int rand = 0;
                while (rand == 0 || rand == 404)
                    rand = (int) ((Math.random() * highestId) + 1);

                // Build request data for API
                Request request = new Request.Builder()
                        .url("https://www.xkcd.com/" + (int) ((Math.random() * highestId) + 1) + "/info.0.json")
                        .build();

                // Request data from API and work with it asynchronically
                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    // Display toast if comics are unavailable.
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("OkHttp Fail", e.getMessage());

                        // Make the Spinner invisible
                        mSpinner.setVisibility(View.INVISIBLE);
                    }

                    // Work with response
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        // Get data from API and save data in Comic object.
                        final Comic comic = new Gson().fromJson(response.body().string(), Comic.class);

                        // Run view-related code back on the main thread
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // Add one item to comic list
                                    homeComicList.add(new Comic(comic.getNum(), "Random: \"" + comic.getTitle() + "\"", comic.getSafe_title(), comic.getImg(), comic.getDay(), comic.getMonth(), comic.getYear(), comic.getTranscript(), comic.getAlt()));

                                    // Display comic
                                    mMainListView.setAdapter(new ComicAdapter(context, homeComicList));

                                    // Make the Spinner invisible
                                    mSpinner.setVisibility(View.INVISIBLE);
                                } catch (NullPointerException e) {
                                    Log.e("OkHttp Fail", e.getMessage());

                                    // Make the Spinner invisible
                                    mSpinner.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    void loadFavCards() {
        // Display loading icon
        mSpinner.setVisibility(View.VISIBLE);
        // Get all comics
        List<Comic> comics = mComicDao.getAll();
        // Empty ListView
        mMainListView.setAdapter(new ComicAdapter(context, comics));
        // Remove loading icon
        mSpinner.setVisibility(View.INVISIBLE);
    }

    private View.OnClickListener reload = new View.OnClickListener() {
        @Override
        public void onClick(View button1) {
            loadHomeCards();
        }
    };

    private View.OnClickListener performSearch = new View.OnClickListener() {
        @Override
        public void onClick(View button1) {
            searchBarClass = new SearchBar();
            searchTextField = findViewById(R.id.search_bar_text_field);
            mMainListView = findViewById(R.id.main_listView);
            List<Comic> comics = mComicDao.getAll();
            mMainListView.setAdapter(new ComicAdapter(context, searchBarClass.performSearch(
                    comics, searchTextField)));
        }
    };

    private View.OnClickListener openDropdown = new View.OnClickListener() {
        @Override
        public void onClick(View button1) {
            searchBarClass = new SearchBar();
            searchBarClass.openDropdown();
        }
    };

}
