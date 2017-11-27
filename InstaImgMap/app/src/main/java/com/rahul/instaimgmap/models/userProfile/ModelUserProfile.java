
package com.rahul.instaimgmap.models.userProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUserProfile {

    @SerializedName("data")
    @Expose
    private User data;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
