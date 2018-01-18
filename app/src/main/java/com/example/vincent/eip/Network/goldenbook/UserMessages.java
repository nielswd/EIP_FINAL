
package com.example.vincent.eip.Network.goldenbook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserMessages {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("numRoom")
    @Expose
    private String numRoom;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumRoom() {
        return numRoom;
    }

    public void setNumRoom(String numRoom) {
        this.numRoom = numRoom;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
