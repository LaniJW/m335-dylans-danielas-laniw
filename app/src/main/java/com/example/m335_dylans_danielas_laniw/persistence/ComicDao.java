package com.example.m335_dylans_danielas_laniw.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ComicDao {
    @Query("SELECT * FROM comic ORDER BY num ASC")
    List<Comic> getAll();

    @Query("SELECT * FROM comic WHERE num = :num")
    Comic getByNum(int num);

    @Query("SELECT * FROM comic WHERE favorised = 1 ORDER BY num ASC")
    List<Comic> getFavorised();

    @Query("DELETE FROM comic")
    void deleteAll();

    @Insert
    void insertAll(List<Comic> Comic);

    @Insert
    void insert(Comic comic);
}
