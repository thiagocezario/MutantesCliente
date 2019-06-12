package com.example.mutantescliente.Login;

public class Usuario {
    public String username;
    public String password;
    private static Usuario user;

    private Usuario() {}

    synchronized public static Usuario getUsuario() {
        if (user == null) {
            user = new Usuario();
        }

        return user;
    }
}
