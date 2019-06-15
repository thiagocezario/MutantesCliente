package com.example.mutantescliente.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mutantescliente.Mutant.EditMutant;
import com.example.mutantescliente.Mutant.Mutant;
import com.example.mutantescliente.R;
import com.example.mutantescliente.Volley.VolleyRequestQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class List extends AppCompatActivity implements Response.Listener, Response.ErrorListener {
    private static String listUrl = "http://192.168.43.7:3000/list";

    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private RequestQueue requestQueue;
    private ArrayList<Mutant> mutants;

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = findViewById(R.id.mutantList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog = new ProgressDialog(List.this);
        progressDialog.setMessage("Aguarde...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, listUrl, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                if (response.length() > 0) {
                    mutants = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), Mutant[].class)));
                }

                progressDialog.dismiss();

                CustomAdapter customAdapter = new CustomAdapter();
                list.setAdapter(customAdapter);
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

    @Override
    public void onErrorResponse(VolleyError error) {
        showAlert(error.toString());
    }

    @Override
    public void onResponse(Object response) {
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

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mutants.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.list_mutants, null);

            ImageView imageView = convertView.findViewById(R.id.mutantListPhoto);
            TextView textView = convertView.findViewById(R.id.mutantListName);
//
//            mutants.get(position).decodePhoto();

            imageView.setImageDrawable(mutants.get(position).photo);
            textView.setText(mutants.get(position).name);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(List.this, EditMutant.class);
                    intent.putExtra("mutant", mutants.get(position));
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }
}