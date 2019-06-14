package com.example.mutantescliente.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mutantescliente.Mutant.Mutant;
import com.example.mutantescliente.R;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
    private ArrayList<Mutant> mutants = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        mutants = (ArrayList)intent.getSerializableExtra("mutants");

        listView = findViewById(R.id.resultList);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

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
            convertView = getLayoutInflater().inflate(R.layout.search_list, null);

            TextView textView = convertView.findViewById(R.id.mutantNameCustomList);
            textView.setText(mutants.get(position).name);

            return convertView;
        }
    }
}
