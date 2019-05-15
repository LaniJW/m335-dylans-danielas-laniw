package com.example.bottom_nav_test.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Comic.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDb;
    public static AppDatabase getAppDb(Context context){
        if (appDb == null){
            appDb = Room.databaseBuilder(context, AppDatabase.class, "comic_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return appDb;
    }

    public abstract ComicDao getComicDao();
}
