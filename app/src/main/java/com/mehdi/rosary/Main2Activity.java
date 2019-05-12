package com.mehdi.rosary;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appolica.flubber.Flubber;
import com.mehdi.rosary.Azkar.AzkarActivity;
import com.mehdi.rosary.AzkarSong.PlaySong;
import com.mehdi.rosary.Qibla.CompassActivity;
import com.mehdi.rosary.SalawatTime.SalawetActivity;
import com.mehdi.rosary.Sebha.TasbihActivity;
import com.mehdi.rosary.databinding.MainFragBinding;


public class Main2Activity extends AppCompatActivity {

    MainFragBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_frag);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "arabe_font.otf");
        binding.textView.setTypeface(myTypeface);
        binding.textView2.setTypeface(myTypeface);
        binding.textView1.setTypeface(myTypeface);
        binding.textView3.setTypeface(myTypeface);

        binding.aaa.setTypeface(myTypeface);
        binding.a1.setTypeface(myTypeface);
        binding.a2.setTypeface(myTypeface);
        binding.qib.setTypeface(myTypeface);
        binding.textView4.setTypeface(myTypeface);

        binding.qibla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flubber.with()
                        .animation(Flubber.AnimationPreset.POP)
                        .repeatCount(0)
                        .duration(80)
                        .createFor(v)
                        .start();
                startActivity(new Intent(Main2Activity.this, CompassActivity.class));
            }
        });

    }

    public void T(View v) {
        Flubber.with()
                .animation(Flubber.AnimationPreset.POP)
                .repeatCount(0)
                .duration(80)
                .createFor(v)
                .start();
        Intent i = new Intent(Main2Activity.this, AzkarActivity.class);
        startActivity(i);

    }

    public void TT(View v) {
        Flubber.with()
                .animation(Flubber.AnimationPreset.POP)
                .repeatCount(0)
                .duration(80)
                .createFor(v)
                .start();
        Intent i = new Intent(Main2Activity.this, PlaySong.class);
        startActivity(i);
    }


    public void salawet(View v) {
        Flubber.with()
                .animation(Flubber.AnimationPreset.POP)
                .repeatCount(0)
                .duration(80)
                .createFor(v)
                .start();
        startActivity(new Intent(this, SalawetActivity.class));
    }

    public void mesbaha(View v) {
        Flubber.with()
                .animation(Flubber.AnimationPreset.POP)
                .repeatCount(0)
                .duration(80)
                .createFor(v)
                .start();
        startActivity(new Intent(Main2Activity.this, TasbihActivity.class));
    }

}
