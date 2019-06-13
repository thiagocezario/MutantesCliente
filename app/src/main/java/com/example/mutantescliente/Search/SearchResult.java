package com.example.mutantescliente.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mutantescliente.Mutant.Mutant;
import com.example.mutantescliente.R;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
    private ArrayList<Mutant> mutants = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        mutants = (ArrayList<Mutant>)intent.getSerializableExtra("mutants");
    }
}
