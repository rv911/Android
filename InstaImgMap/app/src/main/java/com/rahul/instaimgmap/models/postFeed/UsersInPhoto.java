
package com.rahul.instaimgmap.models.postFeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersInPhoto {

    @SerializedName("user")
    @Expose
    private User_ user;
    @SerializedName("position")
    @Expose
    private Position position;

    public User_ getUser() {
        return user;
    }

    public void setUser(User_ user) {
        this.user = user;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
