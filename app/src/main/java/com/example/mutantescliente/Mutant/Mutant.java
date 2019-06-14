package com.example.mutantescliente.Mutant;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Mutant implements Serializable {
    public int id;
    public String name;
    public String ability1;
    public String ability2;
    public String ability3;
    public Drawable photo;
    public String creator;

    public Mutant() {
        name = "";
        ability1 = "";
        ability2 = "";
        ability3 = "";
    }
}
