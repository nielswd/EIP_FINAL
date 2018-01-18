package com.example.vincent.eip.Network.goldenbook;

import com.example.vincent.eip.Network.UserClientInfo;
import com.example.vincent.eip.Network.messages.ListMessages;

import java.io.Serializable;
import java.util.List;

/**
 * Created by iNfecteD on 15/06/2017.
 */

public class GoldenParams implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<UserMessages> list;
    private UserClientInfo user;


    public GoldenParams() {
    }

    public List<UserMessages> getList() {
        return list;
    }

    public void setList(List<UserMessages> list) {
        this.list = list;
    }

    public UserClientInfo getUser() {
        return user;
    }

    public void setUser(UserClientInfo info) {
        this.user = info;
    }


}
