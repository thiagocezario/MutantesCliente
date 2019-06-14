package com.example.mutantescliente.Mutant;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Mutant implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("skill1")
    public String skill1;
    @SerializedName("skill2")
    public String skill2;
    @SerializedName("skill3")
    public String skill3;
    @SerializedName("photo")
    public Drawable photo;
    @SerializedName("user")
    public String creator;

    public Mutant() {
        name = "";
        skill1 = "";
        skill2 = "";
        skill3 = "";
        creator = "";
    }
}
