package com.example.mutantescliente.Search;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mutantescliente.Mutant.Mutant;
import com.example.mutantescliente.R;
import com.example.mutantescliente.Volley.VolleyRequestQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class Search extends AppCompatActivity implements Response.Listener, Response.ErrorListener {
    public static String searchUrl = "http://192.168.100.16:3000/search/mutant?skill=";

    RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;

    private ArrayList<Mutant> mutants = new ArrayList<>();
    private Button searchMutants;
    private EditText mutantAbility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mutantAbility = findViewById(R.id.mutantAbility);
        searchMutants = findViewById(R.id.searchMutants);

        searchMutants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    private void search() {
        progressDialog = new ProgressDialog(Search.this);
        progressDialog.setMessage("Aguarde...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String skill = mutantAbility.getText().toString();
        String url = searchUrl.concat(skill);

        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                if (response.length() > 0) {
                    mutants = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), Mutant[].class)));
                }

                progressDialog.dismiss();
                displayResults();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                showAlert(error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void displayResults() {
        Intent intent = new Intent(Search.this, SearchResult.class);
        intent.putExtra("mutants", mutants);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        showAlert(error.toString());
    }

    @Override
    public void onResponse(Object response) {
        showAlert(response.toString());
    }

    private void showAlert(String message) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("ERRO");
        b.setMessage(message);
        b.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = b.create();
        alertDialog.show();
    }
}
