package com.example.mutantescliente;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mutantescliente.Login.Login;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoginScreen();
            }
        }, 3000);
    }

    private void showLoginScreen() {
        Intent intent = new Intent(
                SplashScreen.this, Login.class
        );
        startActivity(intent);
        finish();
    }
}
