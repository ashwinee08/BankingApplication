package models;

import com.google.gson.annotations.SerializedName;

public class IndividualMessage {
    @SerializedName("address")
    String address;

    @SerializedName("body")
    String body;

    @SerializedName("date_get")
    String dateGet;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDateGet() {
        return dateGet;
    }

    public void setDateGet(String dateGet) {
        this.dateGet = dateGet;
    }
}
