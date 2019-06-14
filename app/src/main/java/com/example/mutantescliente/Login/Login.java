package com.example.mutantescliente.Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONObject;

public class Login extends AppCompatActivity implements Response.Listener, Response.ErrorListener {

    private static String authenticateUrl = "http://192.168.100.16:3000/authenticate";
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private AlertDialog alertDialog;
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
                progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Aguarde...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                doLogin();
            }
        });

        idField = findViewById(R.id.idField);
        passwordField = findViewById(R.id.passwordField);

    }

    private void doLogin() {
        String username = idField.getText().toString();
        String password = passwordField.getText().toString();

        User user = User.getUser();
        user.username = username;
        user.password = password;

        String url = authenticateUrl.concat("?login="+username+"&password="+password);

        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        final ServiceHandler jsonRequest = new ServiceHandler(Request.Method.GET, url, new JSONObject(), this, this);

        requestQueue.add(jsonRequest);
        //callDashboard();

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
//        Toast.makeText(this, "Login Inválido", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("ERRO");
        b.setMessage(error.toString());
        b.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = b.create();
        alertDialog.show();
    }

    @Override
    public void onResponse(Object response) {
        progressDialog.dismiss();
        callDashboard();
    }
}
