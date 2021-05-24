package com.example.farm_elp.model;

import com.google.protobuf.Empty;

import java.io.Serializable;

public class UserData implements Serializable {
    public static String fullName, email, address;
    public static long phoneNo, adhaarID;
    public static String password;



    //---------------------------------Constructors-----------------------------------
   public UserData(){}
    public UserData(String fullName, String email, String address, long phoneNo, long adhaarID, String create_password) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNo = phoneNo;
        this.adhaarID = adhaarID;
        this.password = create_password;
    }

    //---------------------------------Getters and Setters-----------------------------------

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public long getAdhaarID() {
        return adhaarID;
    }

    public void setAdhaarID(long adhaarID) {
        this.adhaarID = adhaarID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
