package com.example.mutantescliente.Search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mutantescliente.List.List;
import com.example.mutantescliente.Mutant.Mutant;
import com.example.mutantescliente.R;
import com.example.mutantescliente.Volley.VolleyRequestQueue;

import org.json.JSONArray;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements Response.Listener, Response.ErrorListener {
    public static String searchUrl = "http://192.168.100.16:3000/search/mutant?skill=";

    RequestQueue requestQueue;
    private ProgressDialog alert;

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
        alert = new ProgressDialog(Search.this);
        alert.setMessage("Aguarde...");
        alert.setCancelable(false);
        alert.show();

        String skill = mutantAbility.getText().toString();
        String url = searchUrl.concat(skill);

        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                alert.dismiss();
                displayResults();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert.dismiss();
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
        Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Object response) {

    }
}
