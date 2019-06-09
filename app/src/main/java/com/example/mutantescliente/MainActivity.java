package com.example.mutantescliente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText idField;
    private EditText passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
