package com.example.mutantescliente.Mutant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mutantescliente.R;
import com.example.mutantescliente.ServiceHandler.ServiceHandler;
import com.example.mutantescliente.Volley.VolleyRequestQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class NewMutant extends AppCompatActivity implements Response.Listener, Response.ErrorListener {
    public static String createUrl = "http://192.168.100.16:3000/register/mutant";

    private ImageView mutantPhoto;
    private EditText mutantName;
    private EditText mutantFirstAbility;
    private EditText mutantSecondAbility;
    private EditText mutantThirdAbility;
    private Button saveMutant;
    private JSONObject jsonBody = new JSONObject();
    private Mutant mutant;
    private RequestQueue requestQueue;

    private AlertDialog alertDialog;

    public static final int IMAGE_GALLERY_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutant_info);

        initialize();
        setupView();
    }

    private void initialize() {
        mutantPhoto = findViewById(R.id.mutantPhoto);
        mutantName = findViewById(R.id.mutantName);
        mutantFirstAbility = findViewById(R.id.mutantFirstAbility);
        mutantSecondAbility = findViewById(R.id.mutantSecondAbility);
        mutantThirdAbility = findViewById(R.id.mutantThirdAbility);
        saveMutant = findViewById(R.id.saveMutant);
    }

    private void setupView() {
        setPhotoOnClickListener();
        setSaveOnClickListener();
    }

    private void setPhotoOnClickListener() {
        mutantPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();

                Uri data = Uri.parse(pictureDirectoryPath);

                photoPickerIntent.setDataAndType(data, "image/*");

                startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
            }
        });
    }

    private void setSaveOnClickListener() {
        saveMutant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMutant();
                JSONObject params = setURLParameters(createUrl);
                saveMutantWithUrl(params);
            }
        });
    }

    private void saveMutantWithUrl(JSONObject params){
        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        final ServiceHandler jsonRequest = new ServiceHandler(Request.Method.POST, createUrl, params, this, this);

        requestQueue.add(jsonRequest);
    }

    private JSONObject setURLParameters(String url) {

        HashMap<String, String>  params = new HashMap<String, String> ();

        String name = mutant.name;
        String ability1 = mutant.skill1;
        String ability2 = mutant.skill2;
        String ability3 = mutant.skill3;

        params.put("name", name);
        params.put("skill1", ability1);
        params.put("skill2", ability2);
        params.put("skill3", ability3);
        params.put("id_user", "1");
        JSONObject jsonBody = new JSONObject(params);


         if (mutant.photo != null) {
            Bitmap bitmap = mutant.photo;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 0, stream);
            byte[] bitmapdata = stream.toByteArray();
            String encodedImage = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
            params.put("photo", encodedImage);
            return new JSONObject(params) ;
        }

        return new JSONObject(params);
    };

    private void setMutant() {
        String name = mutantName.getText().toString();
        String ability1 = mutantFirstAbility.getText().toString();
        String ability2 = mutantSecondAbility.getText().toString();
        String ability3 = mutantThirdAbility.getText().toString();

        Bitmap photo = mutantPhoto.getDrawingCache();

        if (mutant == null) {
            mutant = new Mutant();
        }

        mutant.name = name;
        mutant.skill1 = ability1;
        mutant.skill2 = ability2;
        mutant.skill3 = ability3;
        mutant.photo = photo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                Uri imageUri = data.getData();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    mutantPhoto.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Não foi possível abrir a galeria", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        showAlert(error.toString());
    }

    @Override
    public void onResponse(Object response) {

        try{
            JSONObject jsonObject = new JSONObject(response.toString());
            String message = jsonObject.getString("message");
            showAlert(message);
        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(this, "Não foi possível abrir o alert", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAlert(String message) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(message);
        b.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = b.create();
        alertDialog.show();
    }
}
