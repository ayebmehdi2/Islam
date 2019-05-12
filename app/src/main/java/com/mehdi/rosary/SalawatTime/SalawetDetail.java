package com.mehdi.rosary.SalawatTime;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.mehdi.rosary.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SalawetDetail {

    private String fajr;
    private String subah;
    private String dhuhr;
    private String asr;
    private String maghreb;
    private String isha;
    private String imsak;

    private String date;

    private Double latitude;
    private Double longitude;

    public SalawetDetail(String fajr, String subah, String dhuhr, String asr, String maghreb,
                          String isha, String imsak, String date, Double latitude, Double longitude){
        this.fajr = fajr;
        this.subah = date;
        this.asr = asr;
        this.date = date;
        this.dhuhr = dhuhr;
        this.maghreb = maghreb;
        this.imsak = imsak;
        this.isha = isha;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getFajr() {
        return fajr;
    }

    public String getDhuhr() {
        return dhuhr;
    }

    public String getSubah() {
        return subah;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAsr() {
        return asr;
    }

    public String getDate() {
        return date;
    }

    public String getImsak() {
        return imsak;
    }


    public String getIsha() {
        return isha;
    }

    public String getMaghreb() {
        return maghreb;
    }


    private static URL builURL(String  la, String  lon, String month, String year){
        Uri uri = Uri.parse("http://api.aladhan.com/v1/calendar").buildUpon().appendQueryParameter("latitude", la)
                .appendQueryParameter("longitude", lon)
                .appendQueryParameter("month", month)
                .appendQueryParameter("year", year).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e("SalawetActivity", "URI : " + uri.toString());

        return url;
    }

    private static SalawetDetail getSalatDetail(String json, int day){

        String fajr = null;
        String subah = null;
        String dhuhr = null;
        String asr = null;
        String maghreb = null;
        String isha = null;
        String imsak = null;

        String date = null;

        double latitude = 0;;
        double longitude =  0;

        JSONObject object = null;
        JSONArray array = null;
        JSONObject object1 = null;
        JSONObject timings = null;
        JSONObject dateObject = null;
        JSONObject meta = null;

        try {

            object = new JSONObject(json);
            array = object.getJSONArray("data");

            object1 = array.getJSONObject(day);
            if (object1 == null) return null;
            timings = object1.getJSONObject("timings");
            if (timings != null){
                fajr = timings.getString("Fajr");
                subah  = timings.getString("Sunrise");
                dhuhr = timings.getString("Dhuhr");
                asr = timings.getString("Asr");
                maghreb = timings.getString("Maghrib");
                isha = timings.getString("Isha");
                imsak = timings.getString("Imsak");
            }

            dateObject = object1.getJSONObject("date");
            if (dateObject != null){
                date = dateObject.optString("readable");
            }

            meta = object1.getJSONObject("meta");
            if (meta != null){
                longitude = meta.optDouble("longitude");
                latitude = meta.optDouble("latitude");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("SalawetActivity", "YEAR : " + fajr);
        return new SalawetDetail(fajr, subah, dhuhr, asr, maghreb, isha, imsak, date, latitude, longitude);

    }


    static class Async extends AsyncTaskLoader<SalawetDetail> {


        String[] cor;
        SharedPreferences preferences;
        String year;
        String month;
        String day;

        Async(Context context, String[] cor, SharedPreferences preferences, String year, String month, String day) {
            super(context);
            this.cor = cor;
            this.day = day;
            this.preferences = preferences;
            this.year = year;
            this.month = month;
        }
        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public SalawetDetail loadInBackground() {
            String s = cor[preferences.getInt("pos", 0)];
            Log.e("SalawetActivity", "CORDONNE_PLACE : " + year);
            String[] arr = s.split(",");
            try {
                return getSalatDetail(NetworkUtil.makeHttpReqeust(builURL(arr[1], arr[2], month, year)), Integer.valueOf(day));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


    }


}



