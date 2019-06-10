package com.example.mutantescliente.Busca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mutantescliente.R;

public class Buscar extends AppCompatActivity {

    private Button searchMutants;
    private EditText mutantAbility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        mutantAbility = findViewById(R.id.mutantFirstAbility);
        searchMutants = findViewById(R.id.searchMutantsButton);

        searchMutants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayResults();
            }
        });
    }

    private void displayResults() {
        Intent intent = new Intent(Buscar.this, ResultadosBusca.class);

        startActivity(intent);
    }
}
