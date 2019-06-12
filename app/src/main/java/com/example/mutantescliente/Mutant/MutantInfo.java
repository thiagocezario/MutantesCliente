package com.example.mutantescliente.Mutant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    private Button saveMutant;

    private Mutant mutant;

    public static final int IMAGE_GALLERY_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutant_info);

        Intent intent = getIntent();
        mutant = (Mutant)intent.getSerializableExtra("mutant");

        initialize();
        setupView();
    }

    private void initialize() {
        viewStatus = findViewById(R.id.viewStatus);
        mutantPhoto = findViewById(R.id.mutantPhoto);
        mutantName = findViewById(R.id.mutantName);
        mutantFirstAbility = findViewById(R.id.mutantFirstAbility);
        mutantSecondAbility = findViewById(R.id.mutantSecondAbility);
        mutantThirdAbility = findViewById(R.id.mutantThirdAbility);
        saveMutant = findViewById(R.id.saveMutant);
    }

    private void setupView() {
        if (mutant != null) {
            viewStatus.setText("Editar Mutant");

            mutantName.setText(mutant.name);
            mutantFirstAbility.setText(mutant.abilities[0]);
            mutantSecondAbility.setText(mutant.abilities[1]);
            mutantThirdAbility.setText(mutant.abilities[2]);

            if (mutant.photo != null) {
                mutantPhoto.setImageDrawable(mutant.photo);
            }

            setupEditMutantListeners();
        } else {
            setupCreateMutantListeners();
        }
    }

    private void setupCreateMutantListeners() {

        setPhotoOnClickListener();
        setSaveOnClickListener();
    }

    private void setupEditMutantListeners() {

        setEditOnClickListener();
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
        saveMutant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMutant();

                String json = writeToJson(mutant);
            }
        });
    }

    private void setEditOnClickListener() {
        saveMutant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMutant();

                String json = writeToJson(mutant);
            }
        });
    }

    private void setMutant() {
        String name = mutantName.getText().toString();
        String[] abilities = new String[3];
        abilities[0] = mutantFirstAbility.getText().toString();
        abilities[1] = mutantSecondAbility.getText().toString();
        abilities[2] = mutantThirdAbility.getText().toString();

        Drawable photo = mutantPhoto.getDrawable();

        if (mutant == null) {
            mutant = new Mutant();
        }

        mutant.name = name;
        mutant.abilities = abilities;
        mutant.photo = photo;
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
