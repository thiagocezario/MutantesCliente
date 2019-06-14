package com.example.mutantescliente.Mutant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditMutant extends AppCompatActivity implements Response.Listener, Response.ErrorListener {
    public static String updateUrl = "http://192.168.100.16:3000/update/mutant";
    
    private TextView createdBy;
    private ImageView mutantPhoto;
    private EditText mutantName;
    private EditText mutantFirstAbility;
    private EditText mutantSecondAbility;
    private EditText mutantThirdAbility;
    private Button editMutant;

    private Mutant mutant;
    private RequestQueue requestQueue;

    public static final int IMAGE_GALLERY_REQUEST = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mutant);

        Intent intent = getIntent();
        mutant = (Mutant)intent.getSerializableExtra("mutant");

        initialize();
        setupMutant();
    }

    private void initialize() {
        setPhotoOnClickListener();
        createdBy = findViewById(R.id.createdBy);
        mutantPhoto = findViewById(R.id.mutantPhoto1);
        mutantName = findViewById(R.id.mutantName1);
        mutantFirstAbility = findViewById(R.id.mutantAbility1);
        mutantSecondAbility = findViewById(R.id.mutantAbility2);
        mutantThirdAbility = findViewById(R.id.mutantAbility3);
        editMutant = findViewById(R.id.editMutant);

        editMutant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setupMutant() {
        createdBy.setText(mutant.creator);

        mutantName.setText(mutant.name);
        mutantFirstAbility.setText(mutant.ability1);
        mutantSecondAbility.setText(mutant.ability2);
        mutantThirdAbility.setText(mutant.ability3);

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

    private void saveMutantWithUrl(String url){
        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        final ServiceHandler jsonRequest = new ServiceHandler(Request.Method.POST, url, new JSONObject(), this, this);

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
        Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Object response) {
        Toast.makeText(this, "Mutante salvo com sucesso", Toast.LENGTH_LONG).show();
    }
}
