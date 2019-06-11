package com.example.mutantescliente.Mutante;

import android.graphics.Bitmap;

public class Mutante {
    private String name;
    private String ability1;
    private String ability2;
    private String ability3;
    private Bitmap photo;

    public Mutante() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbility1() {
        return ability1;
    }

    public void setAbility1(String ability1) {
        this.ability1 = ability1;
    }

    public String getAbility2() {
        return ability2;
    }

    public void setAbility2(String ability2) {
        this.ability2 = ability2;
    }

    public String getAbility3() {
        return ability3;
    }

    public void setAbility3(String ability3) {
        this.ability3 = ability3;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
