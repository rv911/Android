package com.rahul.instaimgmap.network;

/**
 * Created by rahul on 25/11/17
 */

public class Constants {

    public static final String CLIENT_ID = "9ce1fa80b7c24551bb4528bce9c3d8c0";
    public static final String CLIENT_SECRET = "ff3d2b2dd317499489fcd2a971ac5d5e";
    public static final String REDIRECT_URL = "https://www.linkedin.com/in/rahul-verma-157112aa/";
    public static final String AUTHORIZATION_CODE = "authorization_code";

    public static final String BASE_URL = "https://api.instagram.com";
    public static final String AUTHORIZATION_API = BASE_URL + "/oauth/authorize/?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL + "&response_type=code&display=touch&scope=public_content";

}
