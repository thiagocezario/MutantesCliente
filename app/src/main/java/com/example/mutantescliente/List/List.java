package com.example.mutantescliente.List;

import android.app.ProgressDialog;
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
    private static String listUrl = "http://192.168.100.16:3000/list";

    private ProgressDialog alert;
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

        alert = new ProgressDialog(List.this);
        alert.setMessage("Aguarde...");
        alert.setCancelable(false);
        alert.show();

        requestQueue = VolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, listUrl, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                if (response.length() > 0) {
                    mutants = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), Mutant[].class)));
                }

                alert.dismiss();

                CustomAdapter customAdapter = new CustomAdapter();
                list.setAdapter(customAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert.dismiss();
            }
        });

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