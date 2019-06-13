package com.example.mutantescliente.Login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mutantescliente.Dashboard.Dashboard;
import com.example.mutantescliente.R;
import com.example.mutantescliente.ServiceHandler.ServiceHandler;
import com.example.mutantescliente.Volley.VolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements Response.Listener, Response.ErrorListener {

    private static String authenticateUrl = "http://192.168.100.16:3000/authenticate?login=\"admin\"&password=\"admin\"";
    private ProgressDialog alert;
    private RequestQueue requestQueue;
    public static final String REQUEST_TAG = "Login";

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
                alert = new ProgressDialog(Login.this);
                alert.setMessage("Aguarde...");
                alert.setCancelable(false);
                alert.show();

                doLogin();
            }
        });

        idField = findViewById(R.id.idField);
        passwordField = findViewById(R.id.passwordField);

    }

    private void doLogin() {

        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        final ServiceHandler jsonRequest = new ServiceHandler(Request.Method.GET, authenticateUrl, new JSONObject(), this, this);
        jsonRequest.setTag(REQUEST_TAG);

        requestQueue.add(jsonRequest);

        String username = idField.getText().toString();
        String password = passwordField.getText().toString();

        User user = User.getUser();
        user.username = username;
        user.password = password;

        JSONObject userJson = new JSONObject();

        try {
            userJson.put("User", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.print(userJson);
    }

    private void callDashboard() {
        Intent intent = new Intent(
                Login.this, Dashboard.class
        );
        startActivity(intent);
        finish();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        alert.dismiss();
    }

    @Override
    public void onResponse(Object response) {
        alert.dismiss();
        callDashboard();
    }
}
