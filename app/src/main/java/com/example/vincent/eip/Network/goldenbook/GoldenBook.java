
package com.example.vincent.eip.Network.goldenbook;

import com.example.vincent.eip.Network.UserClientInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoldenBook {

    @SerializedName("user")
    @Expose
    private UserClientInfo user;
    @SerializedName("list")
    @Expose
    private java.util.List<UserMessages> list = null;

    public UserClientInfo getUser() {
        return user;
    }

    public void setUser(UserClientInfo user) {
        this.user = user;
    }

    public java.util.List<UserMessages> getList() {
        return list;
    }

    public void setList(java.util.List<UserMessages> list) {
        this.list = list;
    }

}
