package com.mehdi.rosary.SalawatTime;


import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mehdi.rosary.NetworkUtil;
import com.mehdi.rosary.R;
import com.mehdi.rosary.databinding.PrayerLayoutBinding;

import java.util.Calendar;
import java.util.Date;


public class SalawetActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<SalawetDetail> {


    String[] cor = new String[] {"Tunis,36.806112,10.171078", "Sfax,34.740556,10.760278", "Hammam Sousse,35.860904,10.603131"
                                , "Gabès,33.881457,10.098196", "Zarzis,33.503981,11.112155", "Kairouan,35.678102,10.096333"
                                , "Bizerte,37.274423,9.87391", "Gafsa,34.425,8.784167", "Nabeul,36.456058,10.73763"
                                , "Ariana,36.860117,10.193371", "Kasserine,35.167578,8.836506", "Mnara,35.697731,10.781591",
                                 "Tataouine,32.929674,10.451767", "Béja,36.725638,9.181692", "Jendouba,36.501136,8.780239", "El Kef,36.174239,8.704863",
                                 "Mahdia,35.504722,11.062222", "Sidi Bouzid,35.038234,9.484935", "Tozeur,33.919683,8.13352", "Siliana,36.084966,9.370818",
                                 "Kebili,33.704387,8.969034", "Ben Gardane,33.13783,11.219649", "Zaghouan,36.402907,10.142925",
                                 "Dehiba,32.007992,10.701353", "Sousse,35.825388,10.636991", "Ben Arous,36.753056,10.218889",
                                 "Medenine,33.354947,10.505478", "Manouba,36.808029,10.097205", "Monastir,35.783333,10.833333"

    };

    private String month;
    private String year;
    private String day;

    PrayerLayoutBinding binding;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.prayer_layout);


        Spinner spinner = binding.place;

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        spinner.setSelection(preferences.getInt("pos", 0));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf((calendar.get(Calendar.MONTH)) + 1);
        day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH)) - 1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor preferen = preferences.edit();
                preferen.putInt("pos", position);
                preferen.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.timeEd.setVisibility(View.VISIBLE);
                binding.day.setText(day);
                binding.month.setText(month);
                binding.year.setText(year);
                binding.aplly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int d = Integer.valueOf(binding.day.getText().toString());
                        int m = Integer.valueOf(binding.month.getText().toString());
                        int y = Integer.valueOf(binding.year.getText().toString());

                        if ((d <= 31 && d > 0) && (m <= 12 && m > 0) && (y <= 2019 && y > 0)){
                            day = String.valueOf(d - 1);
                            month = String.valueOf(m);
                            year = String.valueOf(y);
                            binding.timeEd.setVisibility(View.GONE);
                            getLoaderManager().restartLoader(0, null, SalawetActivity.this);
                        }else {
                            Toast.makeText(SalawetActivity.this, "التاريخ ليس صحيح ",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        if (!(NetworkUtil.isNetworkOnline(this))){
            binding.back.setVisibility(View.VISIBLE);
            binding.connection.setVisibility(View.VISIBLE);
            binding.restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLoaderManager().restartLoader(0, null, SalawetActivity.this);
                }
            });
        }else {
            getLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    public void UpdatUI(SalawetDetail salat){
        if (salat == null) return;
        binding.fajr.setText(salat.getFajr());
        binding.subah.setText(salat.getSubah());
        binding.asr.setText(salat.getAsr());
        binding.dhuhr.setText(salat.getDhuhr());
        binding.maghrib.setText(salat.getMaghreb());
        binding.isha.setText(salat.getImsak());
        binding.date.setText(salat.getDate());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!(NetworkUtil.isNetworkOnline(this))){
            binding.back.setVisibility(View.VISIBLE);
            binding.connection.setVisibility(View.VISIBLE);
            binding.restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLoaderManager().restartLoader(0, null, SalawetActivity.this);
                }
            });
        }else {
            getLoaderManager().restartLoader(0, null, this);
        }
    }

    @Override
    public Loader<SalawetDetail> onCreateLoader(int id, Bundle args) {
            binding.back.setVisibility(View.VISIBLE);
            binding.pr.setVisibility(View.VISIBLE);
            return new  SalawetDetail.Async(SalawetActivity.this, cor, preferences, year, month, day);

    }

    @Override
    public void onLoadFinished(Loader<SalawetDetail> loader, SalawetDetail data) {
        binding.pr.setVisibility(View.GONE);
            binding.back.setVisibility(View.GONE);
            UpdatUI(data);


    }

    @Override
    public void onLoaderReset(Loader<SalawetDetail> loader) {

    }




}


