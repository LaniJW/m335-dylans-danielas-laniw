package com.example.m335_dylans_danielas_laniw;

import okhttp3.OkHttpClient;

/**
 * @author Lani Wagner
 */
class OkHttpClientFactory {
    private static OkHttpClient okHttpClient;

    // Return the same OkHttpClient object to avoid using more than one, which would be redundant.
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
