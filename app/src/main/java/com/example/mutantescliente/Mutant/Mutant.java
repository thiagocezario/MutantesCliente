package com.example.mutantescliente.Mutant;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Mutant implements Serializable {
    public int id;
    public String name;
    public String[] abilities;
    public Drawable photo;

    public Mutant() {
        name = "";
        abilities = new String[3];
    }
}
