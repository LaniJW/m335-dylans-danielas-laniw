package com.example.m335_dylans_danielas_laniw;

import okhttp3.OkHttpClient;

/**
 * @author Lani Wagner
 */
public class OkHttpClientFactory {
    private static OkHttpClient okHttpClient;

    /**
     *
     * @return
     */
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
