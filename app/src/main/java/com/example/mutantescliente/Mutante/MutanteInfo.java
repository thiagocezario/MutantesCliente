package com.example.mutantescliente.Mutante;

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

import com.example.mutantescliente.Login.Usuario;
import com.example.mutantescliente.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MutanteInfo extends AppCompatActivity {
    private TextView viewStatus;
    private ImageView mutantPhoto;
    private EditText mutantFirstAbility;
    private EditText mutantSecondAbility;
    private EditText mutantThirdAbility;
    private Button saveNewMutant;

    private Bundle bundle;

    public static final int IMAGE_GALLERY_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("data");

        viewStatus = findViewById(R.id.viewStatus);
        mutantPhoto = findViewById(R.id.mutantPhoto);
        mutantFirstAbility = findViewById(R.id.mutantFirstAbility);
        mutantSecondAbility = findViewById(R.id.mutantSecondAbility);
        mutantThirdAbility = findViewById(R.id.mutantThirdAbility);
        saveNewMutant = findViewById(R.id.saveNewMutant);

        setupView();
    }

    private void setupView() {
        if (bundle != null) {
            viewStatus.setText("Editar Mutante");

            setupEditMutantListeners();
        } else {
            setupCreateMutantListeners();
        }
    }

    private void setupCreateMutantListeners() {
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

    private void setupEditMutantListeners() {

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
