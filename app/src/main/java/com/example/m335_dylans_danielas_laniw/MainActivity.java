package com.example.m335_dylans_danielas_laniw;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
    private static final String TAG = "Response";
    private ConstraintLayout constraintLayout;
    private SearchBar searchBarClass;
    private EditText searchTextField;
    private ListView mMainListView;
    private ProgressBar mSpinner;
    private Context context;
    private int highestId;
    private ComicDao mComicDao;
    private FloatingActionButton reloadButton;
    private ArrayList comicList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            reloadButton = findViewById(R.id.reload_button);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    reloadButton.show();
                    searchTextField.setText("");
                    loadHomeCards();
                    return true;
                case R.id.navigation_favorites:
                    reloadButton.hide();
                    searchTextField.setText("");
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
        searchTextField = findViewById(R.id.search_bar_text_field);

        loadAllComics();
        loadHomeCards();
    }

    void loadAllComics() {
        OkHttpClient client = new OkHttpClient();
        Request request = createRequest();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logOkHttpFail(e);
            }

            @Override
            public void onResponse(Call call, final Response response) {
                final Comic comic = parseComicFromResponse(response);

                highestId = comic.getNum();

                for (int i = 1; i <= (int) (highestId / 7); i += 1) {
                    if (mComicDao.getByNum(i) == null) {
                        Request request = createRequest(i);
                        OkHttpClient client = new OkHttpClient();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                logOkHttpFail(e);
                            }

                            @Override
                            public void onResponse(Call call, final Response response) {
                                final Comic comic = parseComicFromResponse(response);
                                mComicDao.insert(comic);
                            }
                        });
                    }
                }
            }
        });
    }

    void loadHomeCards() {
        showSpinner();
        removeComics();
        comicList = new ArrayList();

        OkHttpClient client = OkHttpClientFactory.getOkHttpClient();
        Request request = createRequest();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logOkHttpFail(e);
                hideSpinner();
            }

            @Override
            public void onResponse(Call call, final Response response) {
                final Comic comic = parseComicFromResponse(response);
                if (mComicDao.getByNum(comic.getNum()) == null) {
                    mComicDao.insert(comic);
                } else {
                    comic.setFavorised(mComicDao.getByNum(comic.getNum()).isFavorised());
                }
                highestId = comic.getNum();

                // Run view-related code back on the main thread
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            comicList.add(comic);
                        } catch (NullPointerException e) {
                            logOkHttpFail(e);
                            hideSpinner();
                        }
                    }
                });

                int rand = getRandomNum(highestId);
                Request request = createRequest(rand);

                OkHttpClient client = OkHttpClientFactory.getOkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        logOkHttpFail(e);
                        hideSpinner();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final Comic comic = parseComicFromResponse(response);
                        if (mComicDao.getByNum(comic.getNum()) == null) {
                            mComicDao.insert(comic);
                        } else {
                            comic.setFavorised(mComicDao.getByNum(comic.getNum()).isFavorised());
                        }

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
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

    int getRandomNum(int highestId) {
        int rand = 0;
        while (rand == 0 || rand == 404)
            rand = (int) ((Math.random() * highestId) + 1);
        return rand;
    }

    Request createRequest(int id) {
        return new Request.Builder()
                .url("https://www.xkcd.com/" + id + "/info.0.json")
                .build();
    }

    Request createRequest() {
        return new Request.Builder()
                .url("https://www.xkcd.com/info.0.json")
                .build();
    }

    void logOkHttpFail(Exception e) {
        Log.e("OkHttp Fail", e.getMessage());
    }

    void showSpinner() {
        mSpinner.setVisibility(View.VISIBLE);
    }

    void hideSpinner() {
        mSpinner.setVisibility(View.INVISIBLE);
    }

    Comic parseComicFromResponse(Response r) {
        try {
            return new Gson().fromJson(r.body().string(), Comic.class);
        } catch (IOException iOe) {
            logOkHttpFail(iOe);
            return null;
        }
    }

    void removeComics() {
        comicList = null;
    }

    void displayComics() {
        mMainListView.setAdapter(new ComicAdapter(context, comicList));
    }

    void loadFavCards() {
        // Display loading icon
        mSpinner.setVisibility(View.VISIBLE);
        // Get all comics
        List<Comic> comics = mComicDao.getFavorised();
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
            mMainListView = findViewById(R.id.main_listView);
            List<Comic> comics = mComicDao.getAll();
            TextView invalidText = findViewById(R.id.invalid_search_text);
            mMainListView.setAdapter(new ComicAdapter(context, searchBarClass.performSearch(
                    comics, searchTextField, invalidText)));
        }
    };

}
