package com.rahul.instaimgmap.network;

import com.rahul.instaimgmap.models.ModelAccessToken;
import com.rahul.instaimgmap.models.postFeed.ModelPost;
import com.rahul.instaimgmap.models.userProfile.ModelUserProfile;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rahul on 25/11/17
 */

public interface NetworkAPI {

    @FormUrlEncoded
    @POST("/oauth/access_token")
    Observable<ModelAccessToken> getAccessToken(@Field("client_id") String clientId,
                                                @Field("client_secret") String clientSecret,
                                                @Field("redirect_uri") String redirectUri,
                                                @Field("grant_type") String grantType,
                                                @Field("code") String code);

    @GET("/v1/users/self/")
    Observable<ModelUserProfile> getUserProfile(
            @Query("access_token") String accessToken
    );

    @GET("/v1/users/self/media/recent/")
    Observable<ModelPost> getAllPost(
            @Query("access_token") String accessToken,
            @Query("max_id") String maxId,
            @Query("count") String count
    );

}
