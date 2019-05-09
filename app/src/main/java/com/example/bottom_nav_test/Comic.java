package com.example.bottom_nav_test;

public class Comic {

    private String title;
    private String safe_title;
    private String url;
    private int day;
    private int month;
    private int year;
    private int transcript;
    private int alt;

    public Comic(String title, String url){
        this.title = title;
        this.url = url;
    }

    //(TODO) add constructor for detal view and getters/setters

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url = url;
    }

}
