package com.mehdi.rosary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TasbihActivity extends AppCompatActivity {


    private Integer Counter = 0;
    private int Statis = 0;

    TextView sta;
    TextView con;
    SharedPreferences preference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rosary_frag);



        preference = PreferenceManager.getDefaultSharedPreferences(this);

        Counter = preference.getInt("counter", 0);
        Statis = preference.getInt("statis", 0);

        sta = findViewById(R.id.staa);
        sta.setText(String.valueOf(Statis));

        con = findViewById(R.id.conn);
        con.setText(String.valueOf(Counter));

        /*
        Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "arabe_font.otf");
        TextView az = view.findViewById(R.id.azkar);
        az.setTypeface(myTypeface);
        Button bu = view.findViewById(R.id.azkar_butt);
        bu.setTypeface(myTypeface);
*/

        findViewById(R.id.main_bead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(TasbihActivity.this).edit();
                if (Counter >= 108){
                    preferences.putInt("counter", 0);
                    preferences.putInt("statis", Statis + 1);
                }else {
                    preferences.putInt("counter", Counter + 1);
                }
                preferences.apply();

                Counter = preference.getInt("counter", 0);
                Statis = preference.getInt("statis", 0);

                sta.setText(String.valueOf(Statis));
                con.setText(String.valueOf(Counter));

            }
        });


    }
}
