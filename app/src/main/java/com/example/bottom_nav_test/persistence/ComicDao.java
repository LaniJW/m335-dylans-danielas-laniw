package com.example.bottom_nav_test.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ComicDao {
    @Query("SELECT * FROM comic ORDER BY num ASC")
    List<Comic> getAll();

    @Query("DELETE FROM comic")
    void deleteAll();

    @Insert
    void insertAll(List<Comic> Comic);

    @Insert
    void insert(Comic comic);
}
