package com.example.mutantescliente.Mutant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mutantescliente.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MutantInfo extends AppCompatActivity {
    private TextView viewStatus;
    private ImageView mutantPhoto;
    private EditText mutantName;
    private EditText mutantFirstAbility;
    private EditText mutantSecondAbility;
    private EditText mutantThirdAbility;
    private Button saveNewMutant;

    public static final int IMAGE_GALLERY_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutant_info);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");

        viewStatus = findViewById(R.id.viewStatus);
        mutantPhoto = findViewById(R.id.mutantPhoto);
        mutantName = findViewById(R.id.mutantName);
        mutantFirstAbility = findViewById(R.id.mutantFirstAbility);
        mutantSecondAbility = findViewById(R.id.mutantSecondAbility);
        mutantThirdAbility = findViewById(R.id.mutantThirdAbility);
        saveNewMutant = findViewById(R.id.saveNewMutant);

        setupView(bundle);
    }

    private void setupView(Bundle bundle) {
        if (bundle != null) {
            viewStatus.setText("Editar Mutant");

            setupEditMutantListeners(bundle);
        } else {
            setupCreateMutantListeners();
        }
    }

    private void setupCreateMutantListeners() {

        setPhotoOnClickListener();
        setSaveOnClickListener();
    }

    private void setupEditMutantListeners(Bundle bundle) {


        setPhotoOnClickListener();
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
        saveNewMutant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mutant mutant = getMutant();

                String json = writeToJson(mutant);
            }
        });
    }

    private void setEditOnClickListener() {
        saveNewMutant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mutant mutant = getMutant();

                String json = writeToJson(mutant);
            }
        });
    }

    private Mutant getMutant() {
        Mutant mutant = new Mutant();

        String name = mutantName.getText().toString();
        String[] abilities = new String[3];
        abilities[0] = mutantFirstAbility.getText().toString();
        abilities[1] = mutantSecondAbility.getText().toString();
        abilities[2] = mutantThirdAbility.getText().toString();

        Drawable photo = mutantPhoto.getDrawable();

        mutant.name = name;
        mutant.abilities = abilities;
        mutant.photo = photo;

        return mutant;
    }

    private String writeToJson(Mutant mutant) {
        JSONObject json = new JSONObject();

        try {
            json.put("Mutante", mutant);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
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
}
