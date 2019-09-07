package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SMSModel {

    public List<IndividualMessage> getSmsList() {
        return smsList;
    }

    public void setSmsList(List<IndividualMessage> smsList) {
        this.smsList = smsList;
    }

    @SerializedName("sms_list")
    List<IndividualMessage> smsList;
}
