package com.ass3.i190500_i190594;

public class MyContact {
    String username,pass,phone;
    String recivername;
    String dp;

    public MyContact(String username, String pass, String phone) {
        this.username = username;
        this.pass = pass;
        this.phone = phone;
    }

    public MyContact(String username,String dp){
        this.username=username;
        this.dp=dp;
    }


    public void setRecivername(String rec){
        this.recivername=rec;

    }
    public String getDp(){
        return dp;
    }

    public String getRecivername(){
        return recivername;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
