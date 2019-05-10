package com.example.bottom_nav_test;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

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
    private ListView mMainListView;
    private Context context;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // Add home info
                    getAPIResponse();
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

        getAPIResponse();
    }

    void getAPIResponse(){
        OkHttpClient client = new OkHttpClient();
        Picasso picasso = new Picasso.Builder(context).downloader(new OkHttp3Downloader(client)).build();
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println('f');
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // ... check for failure using `isSuccessful` before proceeding

                // Read data on the worker thread
                final String responseData = response.body().string();

                // Run view-related code back on the main thread
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //TextView myTextView = (TextView) findViewById(R.id.myTextView);
                            //myTextView.setText(responseData);

                            // test data for card list view in home
                            List<Comic> homeComicList = new ArrayList<>();
                            homeComicList.add(new Comic("Newest - ", "img URL"));
                            homeComicList.add(new Comic("Random - ", "img URL"));

                            mMainListView.setAdapter(new ComicAdapter(context, new ArrayList<Comic>()));
                            mMainListView.setAdapter(new ComicAdapter(context, homeComicList));
                        } catch (Exception /*IOException*/ e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

}
