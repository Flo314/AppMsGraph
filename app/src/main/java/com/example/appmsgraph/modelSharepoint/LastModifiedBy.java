
package com.example.appmsgraph.modelSharepoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastModifiedBy {

    @SerializedName("user")
    @Expose
    private User_ user;

    public User_ getUser() {
        return user;
    }

    public void setUser(User_ user) {
        this.user = user;
    }

}
