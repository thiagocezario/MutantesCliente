package com.example.mutantescliente.Login;

public class User {
    public String username;
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
