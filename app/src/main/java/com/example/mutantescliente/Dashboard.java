package com.example.mutantescliente;

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
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApp();
            }
        });
    }

    private void exitApp() {
        finish();
        System.exit(0);
    }
}
/*
loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        doLogin();
        }
        });

        idField = findViewById(R.id.idField);
        passwordField = findViewById(R.id.passwordField);

        }

private void doLogin() {
        Intent intent = new Intent(
        MainActivity.this, Dashboard.class
        );
                startActivity(intent);
                finish();
                }