package com.example.mutantescliente.Search;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mutantescliente.Mutant.EditMutant;
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

            ImageView imageView = convertView.findViewById(R.id.mutantListPhoto);
            TextView textView = convertView.findViewById(R.id.mutantListName);

            imageView.setImageDrawable(mutants.get(position).photo);
            textView.setText(mutants.get(position).name);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchResult.this, EditMutant.class);
                    intent.putExtra("mutant", mutants.get(position));
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }
}
