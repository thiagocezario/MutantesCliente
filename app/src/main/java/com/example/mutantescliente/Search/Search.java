package com.example.mutantescliente.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mutantescliente.R;

public class Search extends AppCompatActivity {

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
                displayResults();
            }
        });
    }

    private void displayResults() {
        Intent intent = new Intent(Search.this, ResultadosBusca.class);

        startActivity(intent);
    }
}
