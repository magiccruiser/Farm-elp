package com.example.farm_elp.model;

public class Loged_in_User {

    public static String UID="";
    public static String logged_UserName="";
    public static long logged_Phone=0;

    public String getLogged_UserName() {
        return logged_UserName;
    }

    public void setLogged_UserName(String logged_UserName) {
        Loged_in_User.logged_UserName = logged_UserName;
    }

    public long getLogged_Phone() {
        return logged_Phone;
    }

    public void setLogged_Phone(long logged_Phone) {
        Loged_in_User.logged_Phone = logged_Phone;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }


    public void removeLoginUser(){
        UID="";
    }
}
