package com.example.mutantescliente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    private Button exitButton;
    private Button searchButton;
    private Button newMutantButton;
    private Button listMutantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        exitButton = findViewById(R.id.exitButton);
        searchButton = findViewById(R.id.searchMutantsButton);
        newMutantButton = findViewById(R.id.newMutantButton);
        listMutantsButton = findViewById(R.id.listMutantsButton);

        setupOnClickListeners();
    }

    private void setupOnClickListeners() {
        newMutantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMutant();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMutant();
            }
        });

        listMutantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMutants();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApp();
            }
        });
    }

    private void newMutant() {
        Intent intent = new Intent(
                Dashboard.this, Cadastro.class
        );
        startActivity(intent);
    }

    private void listMutants() {
        Intent intent = new Intent(
                Dashboard.this, Cadastro.class
        );
        startActivity(intent);
    }

    private void searchMutant() {
        Intent intent = new Intent(
                Dashboard.this, Cadastro.class
        );
        startActivity(intent);0
    }

    private void exitApp() {
        finish();
        System.exit(0);
    }
}