package com.accademy.harvin.harvinacademy.model;

/**
 * Created by ishank on 6/7/17.
 */

public class User {
    private String name ;
    private String email;
    private String password;
    private String created_at;
    private String new_password;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_at() {
        return created_at;
    }

   public void setToken(){

       this.token=token;
   }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
