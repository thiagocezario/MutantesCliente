package com.example.mutantescliente.Mutant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.Buffer;
import java.nio.ByteBuffer;

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
//        photo = Bitmap.createBitmap(239, 130, Bitmap.Config.ARGB_8888);
    }

    public void decodePhoto() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            Gson gson = new Gson();
//            String stringPhoto = gson.toJson(photo1);
//            jsonObject = new JSONObject(photo1.toString());
//            byte[] decodedByte = jsonObject.getString("data").getBytes();
//            Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
//
//            photo = decodedImage;
//
//        } catch (JSONException e) {
//
//        }
    }
}
