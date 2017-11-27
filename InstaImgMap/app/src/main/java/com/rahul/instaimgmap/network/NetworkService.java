package com.rahul.instaimgmap.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rahul.instaimgmap.network.Constants.BASE_URL;

/**
 * Created by rahul on 25/11/17
 */

public class NetworkService {

    private static NetworkAPI mNetworkAPI;

    public NetworkService() {
        this(BASE_URL);
    }

    private NetworkService(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mNetworkAPI = retrofit.create(NetworkAPI.class);
    }

    NetworkAPI getAPI() {
        return mNetworkAPI;
    }

}
