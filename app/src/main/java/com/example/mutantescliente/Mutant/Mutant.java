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
    @SerializedName("photo1")
    public Drawable photo;
    @SerializedName("photo")
    public Object photo1;
    @SerializedName("user")
    public String creator;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("active")
    public int active;
    @SerializedName("id_user")
    public int id_user;

    public Mutant() {
        name = "";
        skill1 = "";
        skill2 = "";
        skill3 = "";
        creator = "";
    }
}
