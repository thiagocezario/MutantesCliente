package com.example.mutantescliente.Mutant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class EditMutant extends AppCompatActivity implements Response.Listener, Response.ErrorListener {
    public static String updateUrl = "http://192.168.43.7:3000/update/mutant";
    public static String deleteUrl = "http://192.168.43.7:3000/delete/mutant";

    private TextView createdBy;
    private ImageView mutantPhoto;
    private EditText mutantName;
    private EditText mutantFirstAbility;
    private EditText mutantSecondAbility;
    private EditText mutantThirdAbility;
    private Button editMutant;
    private Button deleteMutant;

    private Mutant mutant;
    private RequestQueue requestQueue;

    private AlertDialog alertDialog;

    public static final int IMAGE_GALLERY_REQUEST = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mutant);

        Intent intent = getIntent();
        mutant = (Mutant)intent.getSerializableExtra("mutant");

        initialize();
    }

    private void initialize() {
        createdBy = findViewById(R.id.createdBy);
        mutantPhoto = findViewById(R.id.mutantPhoto1);
        mutantName = findViewById(R.id.mutantName1);
        mutantFirstAbility = findViewById(R.id.mutantAbility1);
        mutantSecondAbility = findViewById(R.id.mutantAbility2);
        mutantThirdAbility = findViewById(R.id.mutantAbility3);
        editMutant = findViewById(R.id.editMutant);
        deleteMutant = findViewById(R.id.deleteMutant);

        deleteMutant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMutant();
            }
        });

        editMutant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMutant();
            }
        });

        setupMutant();
        setPhotoOnClickListener();
    }

    private void setupMutant() {
        createdBy.setText(mutant.creator);

        mutantName.setText(mutant.name);
        mutantFirstAbility.setText(mutant.skill1);
        mutantSecondAbility.setText(mutant.skill2);
        mutantThirdAbility.setText(mutant.skill3);

        if (mutant.photo != null) {
            mutantPhoto.setImageDrawable(mutant.photo);
        }
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

    private void deleteMutant() {
        String url = deleteUrl.concat("?id=" + mutant.id);
        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        final ServiceHandler jsonRequest = new ServiceHandler(Request.Method.DELETE, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                finish();
            }
        }, this);

        requestQueue.add(jsonRequest);
    }

    private void editMutant(){

        HashMap<String, String> params = new HashMap<String, String> ();

        String name = mutantName.getText().toString();
        String ability1 = mutantFirstAbility.getText().toString();
        String ability2 = mutantSecondAbility.getText().toString();
        String ability3 = mutantThirdAbility.getText().toString();

        params.put("id", String.valueOf(mutant.id));
        params.put("name", name);
        params.put("skill1", ability1);
        params.put("skill2", ability2);
        params.put("skill3", ability3);
        params.put("id_user", "1");

        if (mutant.photo != null) {
            Bitmap bitmap = ((BitmapDrawable)mutant.photo).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 0, stream);
            byte[] bitmapdata = stream.toByteArray();
            String encodedImage = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
            params.put("photo", encodedImage);
        }
        JSONObject jsonBody = new JSONObject(params);

        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        final ServiceHandler jsonRequest = new ServiceHandler(Request.Method.POST, updateUrl, jsonBody, this, this);


        requestQueue.add(jsonRequest);
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
        showAlert(response.toString());
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
