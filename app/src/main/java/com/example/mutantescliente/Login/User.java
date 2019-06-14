package com.example.mutantescliente.Login;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("login")
    public String username;

    @SerializedName("password")
    public String password;

    private static User user;

    private User() {}

    synchronized public static User getUser() {
        if (user == null) {
            user = new User();
        }

        return user;
    }
}
