package com.example.m335_dylans_danielas_laniw.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * This class defines the Room DB entry for a Comic.
 *
 * @author Daniela Simões, Lani Wagner
 */
@Entity
public class Comic {
    // Database id of comic
    @PrimaryKey(autoGenerate = true)
    private int id;

    // Columns for comic info
    @ColumnInfo
    private int num;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String safe_title;
    @ColumnInfo
    private String img;
    @ColumnInfo
    private String day;
    @ColumnInfo
    private String month;
    @ColumnInfo
    private String year;
    @ColumnInfo
    private String transcript;
    @ColumnInfo
    private String alt;
    @ColumnInfo
    private boolean favorised;

    /**
     *
     * @param num - comic id
     * @param title - comic title
     * @param safe_title - comic safe title
     * @param img - comic img url
     * @param day - comic release day
     * @param month - comic release month
     * @param year - comic release year
     * @param transcript - comic transcript
     * @param alt - comic alt
     */
    public Comic(int num, String title, String safe_title, String img, String day, String month, String year, String transcript, String alt) {
        this.num = num;
        this.title = title;
        this.safe_title = safe_title;
        this.img = img;
        this.day = day;
        this.month = month;
        this.year = year;
        this.transcript = transcript;
        this.alt = alt;
        this.favorised = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSafe_title() {
        return safe_title;
    }

    public void setSafe_title(String safe_title) {
        this.safe_title = safe_title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public boolean isFavorised() {
        return favorised;
    }

    public void setFavorised(boolean favorised) {
        this.favorised = favorised;
    }
}
