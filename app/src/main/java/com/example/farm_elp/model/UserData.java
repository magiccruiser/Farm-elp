package com.example.farm_elp.model;


import java.io.Serializable;

public class UserData implements Serializable {
    public static String fullName, email, address;
    public static long phoneNo, adhaarID;
    public static String password;
    public static String profession;


    //---------------------------------Constructors-----------------------------------
    public UserData() {
    }

    public UserData(String fullName, String email, String address, long phoneNo, long adhaarID, String create_password, String prof) {
        UserData.fullName = fullName;
        UserData.email = email;
        UserData.address = address;
        UserData.phoneNo = phoneNo;
        UserData.adhaarID = adhaarID;
        password = create_password;
        profession = prof;
    }

    //---------------------------------Getters and Setters-----------------------------------


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        UserData.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        UserData.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        UserData.address = address;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        UserData.phoneNo = phoneNo;
    }

    public long getAdhaarID() {
        return adhaarID;
    }

    public void setAdhaarID(long adhaarID) {
        UserData.adhaarID = adhaarID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        UserData.password = password;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        UserData.profession = profession;
    }

    public String dataToString() {
        return "UserData{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNo=" + phoneNo +
                ", adhaarID=" + adhaarID +
                ", password='" + password + '\'' +
                ", profession='" + profession + '\'' +
                '}';
    }

    public void clear() {
        fullName = "";
        email = "";
        address = "";
        phoneNo = 0;
        adhaarID = 0;
        password = "";
        profession = "";

    }

}
