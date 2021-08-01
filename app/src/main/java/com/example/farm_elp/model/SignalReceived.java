package com.example.farm_elp.model;

import java.io.Serializable;

public class SignalReceived implements Serializable {
    public String requestReceived;
    public String approveTheRequest;
    public String requestedUserUID;

    public String getRequestReceived() {
        return requestReceived;
    }

    public void setRequestReceived(String requestReceived) {
        this.requestReceived = requestReceived;
    }

    public String getApproveTheRequest() {
        return approveTheRequest;
    }

    public void setApproveTheRequest(String approveTheRequest) {
        this.approveTheRequest = approveTheRequest;
    }

    public String getRequestedUserUID() {
        return requestedUserUID;
    }

    public void setRequestedUserUID(String requestedUserUID) {
        this.requestedUserUID = requestedUserUID;
    }

    @Override
    public String toString() {
        return "SignalReceived{" +
                "requestReceived='" + requestReceived + '\'' +
                ", approveTheRequest='" + approveTheRequest + '\'' +
                ", requestedUserUID='" + requestedUserUID + '\'' +
                '}';
    }
}
