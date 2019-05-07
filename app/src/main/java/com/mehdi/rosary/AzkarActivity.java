package com.mehdi.rosary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AzkarActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_azkar);

        RecyclerView recyclerView = findViewById(R.id.rec_azkar);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecycleAzkar recycleAzkar = new RecycleAzkar(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recycleAzkar);

        ImageView imageView = findViewById(R.id.vect);
        TextView textView = findViewById(R.id.tex);


        int i = getIntent().getIntExtra("ty", 1);
        switch (i){
            case 1:
                imageView.setImageResource(R.drawable.ic_sunrise);
                textView.setText("أذكار الصباح");
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_sunset);
                textView.setText("أذكار المساء");
                break;
            case 3:
                imageView.setImageResource(R.drawable.ic_sun);
                textView.setText("أذكار الاستيقاظ من النوم");
                break;
        }
        ArrayList<ReadAzkarJson> data = ReadAzkarJson.readJson(this, i);
        recycleAzkar.swapAdapter(data);


}
}


