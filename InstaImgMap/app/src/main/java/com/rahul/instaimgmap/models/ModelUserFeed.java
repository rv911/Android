package com.rahul.instaimgmap.models;

/**
 * Created by rahul on 25/11/17
 */

public class ModelUserFeed {

    public static final String FEED_TABLE = "feedTable";
    public static final String FEED_ID = "feedId";
    public static final String FEED_USERNAME = "username";
    public static final String FEED_PROFILE_IMAGE = "profilePic";
    public static final String FEED_IMAGE = "image";
    public static final String FEED_CAPTION = "caption";
    public static final String FEED_LOCATION = "location";
    public static final String FEED_CREATION_TIME = "creationTime";

    private String feedId;
    private String username, profileImg;
    private String postImage;
    private String captionText;
    private String location;
    private String creationTime;

    public ModelUserFeed() {
        this("", "", "", "", "", "", "");
    }

    public ModelUserFeed(String feedId, String username, String profileImg, String postImage, String captionText, String location, String creationTime) {
        this.feedId = feedId;
        this.username = username;
        this.profileImg = profileImg;
        this.postImage = postImage;
        this.captionText = captionText;
        this.location = location;
        this.creationTime = creationTime;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getCaptionText() {
        return captionText;
    }

    public void setCaptionText(String captionText) {
        this.captionText = captionText;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
