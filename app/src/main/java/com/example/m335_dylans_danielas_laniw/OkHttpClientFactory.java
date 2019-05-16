package com.example.m335_dylans_danielas_laniw;

import okhttp3.OkHttpClient;

public class OkHttpClientFactory {
    private static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClient(){
        if (okHttpClient == null){
            okHttpClient = new OkHttpClient();
            return okHttpClient;
        }
        else{
            return okHttpClient;
        }
    }
}
