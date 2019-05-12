package com.mehdi.rosary.Azkar;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.mehdi.rosary.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class AzkarDetail {

    private String category;
    private String count;
    private String description;
    private String reference;
    private String zekr;


    private AzkarDetail(String category, String count, String description, String reference, String zekr){

        this.category = category;
        this.count = count;
        this.description = description;
        this.reference = reference;
        this.zekr = zekr;

    }

    public String getCategory() {
        return category;
    }

    public String getCount() {
        return count;
    }

    String getDescription() {
        return description;
    }

    String getReference() {
        return reference;
    }

    String getZekr() {
        return zekr;
    }

    private static ArrayList<AzkarDetail> getAzkarDetail(String sabah){

        if (sabah == null) return null;

        String category = "";
        String count = "";
        String description = "";
        String reference = "";
        String zekr = "";
        ArrayList<AzkarDetail> d = new ArrayList<>();

        JSONArray array = null;

        try {

            array = new JSONArray(sabah);

            for (int i =0; i < array.length(); i++){

                JSONObject object = array.getJSONObject(i);
                    category = object.optString("category");
                    count = object.optString("count");
                    description = object.optString("description");
                    reference = object.optString("reference");
                    zekr = object.optString("zekr");


                    d.add(new AzkarDetail(category, count, description, reference, zekr));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return d;
    }

    static class AsyncAzkar extends AsyncTaskLoader<ArrayList<AzkarDetail>> {

        int ty;
        AsyncAzkar(Context context, int ty) {
            super(context);
            this.ty = ty;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public ArrayList<AzkarDetail> loadInBackground() {
            URL url1 = null;

            try {
                switch (ty){
                    case 1:
                        url1 = new URL("https://api.myjson.com/bins/u7fim");
                        break;
                    case 2:
                        url1 = new URL("https://api.myjson.com/bins/12hctq");
                        break;
                    case 3:
                        url1 = new URL("https://api.myjson.com/bins/veaq6");
                        break;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (url1 == null)return null;

            String json = null;
            try {
                json = NetworkUtil.makeHttpReqeust(url1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (json == null)return null;
            return getAzkarDetail(json);
        }
    }

}
