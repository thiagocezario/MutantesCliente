package com.example.mutantescliente.Login;

public class Usuario {
    private static String username;
    private static Usuario user = new Usuario();

    private Usuario() {
        this.username = "";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    synchronized public static Usuario getUsuario() {
        if (user == null) {
            user = new Usuario();
        }

        return user;
    }
}
