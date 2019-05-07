package com.mehdi.rosary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frag);


    }

    public void A(View v) {
        Intent i = new Intent(Main2Activity.this, AzkarActivity.class);
        i.putExtra("ty", 1);
        startActivity(i);
    }

    public void D(View v) {
        Intent i = new Intent(Main2Activity.this, AzkarActivity.class);
        i.putExtra("ty", 2);
        startActivity(i);
    }

    public void T(View v) {
        Intent i = new Intent(Main2Activity.this, AzkarActivity.class);
        i.putExtra("ty", 3);
        startActivity(i);
    }

    public void X(SongsDetail detail) {
        Intent i = new Intent(Main2Activity.this, PlayerAzkar.class);
        PlayerAzkar.detail = detail;
        startActivity(i);
    }


    public void AA(View v) {
        Intent i = new Intent(Main2Activity.this, PlayerAzkar.class);
        PlayerAzkar.detail = null;
        startActivity(i);
    }

    public void DD(View v) {
        Intent i = new Intent(Main2Activity.this, PlayerAzkar.class);
        PlayerAzkar.detail = null;
        startActivity(i);
    }

    public void TT(View v) {
        Intent i = new Intent(Main2Activity.this, PlayerAzkar.class);
        PlayerAzkar.detail = null;
        startActivity(i);
    }


    public void salawet(View v) {
    }

    public void mesbaha(View v) {
        startActivity(new Intent(Main2Activity.this, TasbihActivity.class));
    }

}
