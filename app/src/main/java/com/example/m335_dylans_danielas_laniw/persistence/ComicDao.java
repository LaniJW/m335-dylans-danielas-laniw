package com.example.m335_dylans_danielas_laniw.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ComicDao {
    // Gets all of the comics.
    @Query("SELECT * FROM comic ORDER BY num ASC")
    List<Comic> getAll();

    //Gets the comic with the given comic id.
    @Query("SELECT * FROM comic WHERE num = :num")
    Comic getByNum(int num);

    // Gets all of the favirsed comics.
    @Query("SELECT * FROM comic WHERE favorised = 1 ORDER BY num ASC")
    List<Comic> getFavorised();

    // Updates the value of favorised with the given comic id.
    @Query("UPDATE comic SET favorised = :favorised WHERE num = :num")
    void updateFavorised(boolean favorised, int num);

    // Deletes all comics.
    @Query("DELETE FROM comic")
    void deleteAll();

    // Inserts all comics passed in the list.
    @Insert
    void insertAll(List<Comic> Comic);

    // Inserts the comic passed.
    @Insert
    void insert(Comic comic);
}
