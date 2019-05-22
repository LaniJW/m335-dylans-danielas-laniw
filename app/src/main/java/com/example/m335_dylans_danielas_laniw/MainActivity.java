package com.example.m335_dylans_danielas_laniw;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.example.m335_dylans_danielas_laniw.persistence.AppDatabase;
import com.example.m335_dylans_danielas_laniw.persistence.Comic;
import com.example.m335_dylans_danielas_laniw.persistence.ComicDao;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private SearchBar searchBarClass;
    private EditText searchTextField;
    private ListView mMainListView;
    private ProgressBar mSpinner;
    private Context context;
    private int highestId;
    private ComicDao mComicDao;
    private FloatingActionButton reloadButton;
    private ArrayList comicList;

    /**
     * Handles the switching between the home and favorites page.
     */
    final private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         *
         * @param item - item of bottom nav
         * @return -
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            reloadButton = findViewById(R.id.reload_button);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // Display the reload button for newest and random.
                    reloadButton.show();
                    // Clear the search field
                    searchTextField.setText("");
                    loadHomeCards();
                    return true;
                case R.id.navigation_favorites:
                    // Hide the reload button for newest and random.
                    reloadButton.hide();
                    // Clear the search field
                    searchTextField.setText("");
                    loadFavCards();
                    return true;
            }
            return false;
        }
    };

    /**
     *
     * @param savedInstanceState -
     */
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

        // Set reloadButton variable and add the OnClickListener
        reloadButton = findViewById(R.id.reload_button);
        reloadButton.setOnClickListener(reload);

        //searchbar
        final Toolbar searchBar = findViewById(R.id.search_bar);
        setSupportActionBar(searchBar);
        Button searchBarSearchButton = findViewById(R.id.search_bar_search_button);
        searchBarSearchButton.setOnClickListener(performSearch);
        searchTextField = findViewById(R.id.search_bar_text_field);

        // Load all of the comics to from the API
        loadAllComics();
        // Load and display the newest and a random comic.
        loadHomeCards();
    }

    /**
     * Loads all comics from the XKCD API with the use of OkHttp and persists the data in the database for further queries.
     */
    private void loadAllComics() {
        OkHttpClient client = OkHttpClientFactory.getOkHttpClient();
        Request request = createRequest();

        // Get API response from request.
        client.newCall(request).enqueue(new Callback() {
            /**
             *
             * @param call -
             * @param e - The exception thrown
             */
            @Override
            public void onFailure(Call call, IOException e) {
                logOkHttpFail(e);
            }

            /**
             *
             * @param call -
             * @param response - Response from the API request
             */
            @Override
            public void onResponse(Call call, final Response response) {
                final Comic comic = parseComicFromResponse(response);

                // Save the id of the latest comic.
                highestId = comic.getNum();

                // Load every single (had to be cut down to a seventh of the total since the database wouldn't allow that many threads to access it.
                for (int i = 1; i <= (int) (highestId / 7); i += 1) {
                    if (mComicDao.getByNum(i) == null) {
                        // Load the comic into the database if it's not saved yet.
                        Request request = createRequest(i);
                        OkHttpClient client = OkHttpClientFactory.getOkHttpClient();
                        // Get API response from request.
                        client.newCall(request).enqueue(new Callback() {
                            /**
                             *
                             * @param call -
                             * @param e - The exception thrown
                             */
                            @Override
                            public void onFailure(Call call, IOException e) {
                                logOkHttpFail(e);
                            }

                            /**
                             *
                             * @param call -
                             * @param response - The response from the API call
                             */
                            @Override
                            public void onResponse(Call call, final Response response) {
                                // Save the comic into the database.
                                final Comic comic = parseComicFromResponse(response);
                                mComicDao.insert(comic);
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * Displays the newest and random comic in the home tab, which are pulled from the API.
     * If they're not already saved in the API they're persisted to avoid crashing the app when they're favorised or the detailview is opened.
     */
    private void loadHomeCards() {
        showSpinner();
        removeComics();
        comicList = new ArrayList();

        OkHttpClient client = OkHttpClientFactory.getOkHttpClient();
        Request request = createRequest();

        // Get API response from request.
        client.newCall(request).enqueue(new Callback() {
            /**
             *
             * @param call -
             * @param e - The exception thrown
             */
            @Override
            public void onFailure(Call call, IOException e) {
                logOkHttpFail(e);
                hideSpinner();
            }

            /**
             *
             * @param call -
             * @param response - The response form the API call
             */
            @Override
            public void onResponse(Call call, final Response response) {
                final Comic comic = parseComicFromResponse(response);
                if (mComicDao.getByNum(comic.getNum()) == null) {
                    // Insert newest comic into database if it's not there yet.
                    mComicDao.insert(comic);
                } else {
                    // Load the correct icon if the newest comic is already in the database and favorised.
                    comic.setFavorised(mComicDao.getByNum(comic.getNum()).isFavorised());
                }
                highestId = comic.getNum();

                // Run view-related code back on the main thread
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Add newest comic to list to be displayed later.
                            comic.setTitle("Newest: \"" + comic.getTitle() + "\"");
                            comicList.add(comic);
                        } catch (NullPointerException e) {
                            logOkHttpFail(e);
                            hideSpinner();
                        }
                    }
                });

                int rand = getRandomNum(highestId);
                Request request = createRequest(rand);

                // Get API response from request.
                OkHttpClient client = OkHttpClientFactory.getOkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    /**
                     *
                     * @param call -
                     * @param e - The exception thrown
                     */
                    @Override
                    public void onFailure(Call call, IOException e) {
                        logOkHttpFail(e);
                        hideSpinner();
                    }

                    /**
                     *
                     * @param call -
                     * @param response - The response from the API call
                     * @throws IOException -
                     */
                    @Override
                    public void onResponse(Call call, final Response response) {
                        final Comic comic = parseComicFromResponse(response);
                        if (mComicDao.getByNum(comic.getNum()) == null) {
                            // Insert random comic into database if it's not there yet.
                            mComicDao.insert(comic);
                        } else {
                            // Load the correct icon if the newest comic is already in the database and favorised.
                            comic.setFavorised(mComicDao.getByNum(comic.getNum()).isFavorised());
                        }

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // Add the random comic to the comiclist, display the comic list and hide the spinner.
                                    comic.setTitle("Random" +
                                            ": \"" + comic.getTitle() + "\"");
                                    comicList.add(comic);
                                    displayComics();
                                    hideSpinner();
                                } catch (NullPointerException e) {
                                    logOkHttpFail(e);
                                    hideSpinner();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Returns a random number between 1 and the highestId parameter.
     * Additionally the random function doesn't return a random value that is equal to 404 since the call of the comic with the id 404 returns, well a 404 Not Found error.
     * @param highestId - highest comic id
     * @return - random number
     */
    int getRandomNum(int highestId) {
        int rand = 0;
        while (rand == 0 || rand == 404)
            rand = (int) ((Math.random() * highestId) + 1);
        return rand;
    }

    /**
     * Returns the request with the id parameter.
     * @param id - id for the request
     * @return - the request with the given id
     */
    private Request createRequest(int id) {
        return new Request.Builder()
                .url("https://www.xkcd.com/" + id + "/info.0.json")
                .build();
    }

    /**Returns the request for the newest comic.
     *
     * @return the request for the highest id
     */
    private Request createRequest() {
        return new Request.Builder()
                .url("https://www.xkcd.com/info.0.json")
                .build();
    }

    /**
     * Logs a fail while requesting something over the API using OkHttp
     * @param e - the exception that is to be logged
     */
    private void logOkHttpFail(Exception e) {
        Log.e("OkHttp Fail", e.getMessage());
    }

    /**
     * Makes the Spinner visible.
     */
    private void showSpinner() {
        mSpinner.setVisibility(View.VISIBLE);
    }

    /**
     * Makes the spinner disappear.
     */
    private void hideSpinner() {
        mSpinner.setVisibility(View.INVISIBLE);
    }

    /**
     * Parses the json string that got returned from the api call into a comic object and returns that.
     * @param r - The response from the API call
     * @return - The comic object parsed from the result with GSON
     */
    private Comic parseComicFromResponse(Response r) {
        try {
            return new Gson().fromJson(r.body().string(), Comic.class);
        } catch (IOException iOe) {
            logOkHttpFail(iOe);
            return null;
        }
    }

    /**
     * Removes all of the comics from the list that is used when displaying them.
     */
    private void removeComics() {
        comicList = null;
    }

    /**
     * Displays the comics on the page with the use of the parameter comicList.
     */
    private void displayComics() {
        mMainListView.setAdapter(new ComicAdapter(context, comicList));
    }

    /**
     * Displays all of the comics which are favorised from the database.
     */
    private void loadFavCards() {
        // Display loading icon
        mSpinner.setVisibility(View.VISIBLE);
        // Get all comics
        List<Comic> comics = mComicDao.getFavorised();
        // Empty ListView
        mMainListView.setAdapter(new ComicAdapter(context, comics));
        // Remove loading icon
        mSpinner.setVisibility(View.INVISIBLE);
    }

    /**
     * OnClickListener for the home cards reload.
     */
    final private View.OnClickListener reload = new View.OnClickListener() {
        /**
         *
         * @param button1 - button clicked
         */
        @Override
        public void onClick(View button1) {
            loadHomeCards();
        }
    };

    /**
     * OnClickListener for the search icon to launch a search.
     */
    final private View.OnClickListener performSearch = new View.OnClickListener() {
        /**
         *
         * @param button1 - button clicked
         */
        @Override
        public void onClick(View button1) {
            searchBarClass = new SearchBar();
            mMainListView = findViewById(R.id.main_listView);
            List<Comic> comics = mComicDao.getAll();
            TextView invalidText = findViewById(R.id.invalid_search_text);
            mMainListView.setAdapter(new ComicAdapter(context, searchBarClass.performSearch(
                    comics, searchTextField, invalidText)));
        }
    };

}
