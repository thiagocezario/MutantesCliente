package com.example.mutantescliente.List;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mutantescliente.Mutant.Mutant;
import com.example.mutantescliente.R;
import com.example.mutantescliente.ServiceHandler.ServiceHandler;
import com.example.mutantescliente.Volley.VolleyRequestQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class List extends AppCompatActivity implements Response.Listener, Response.ErrorListener {
    private static String listUrl = "http://192.168.100.16:3000/list";
    private static String REQUEST_TAG = "List";

    private ProgressDialog alert;
    private RequestQueue requestQueue;
    private ArrayList<Mutant> mutants = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    protected void onStart() {
        super.onStart();

        alert = new ProgressDialog(List.this);
        alert.setMessage("Aguarde...");
        alert.setCancelable(false);
        alert.show();

        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, listUrl, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                alert.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert.dismiss();
            }
        });

        jsonArrayRequest.setTag(REQUEST_TAG);

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onResponse(Object response) {
        alert.dismiss();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        alert.dismiss();
    }
}
