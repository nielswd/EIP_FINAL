package com.example.vincent.eip.Network.domotique;

import java.io.Serializable;

/**
 * Created by iNfecteD on 15/06/2017.
 */

public class DevicesSendAction implements Serializable {

    private String time;
    private String room;
    private String token;

    public String getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getSw() {
        return sw;
    }

    public void setSw(String sw) {
        this.sw = sw;
    }

    private String deviceNum;
    private String sw;


    public String getTime() {
        return time;
    }

    public  void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
