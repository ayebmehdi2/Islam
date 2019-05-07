package com.mehdi.rosary;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class ReadAzkarJson {

    private String category;
    private String count;
    private String description;
    private String reference;
    private String zekr;



    private ReadAzkarJson( String category, String count, String description, String reference, String zekr){

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


    static ArrayList<ReadAzkarJson> readJson(Context context, int ty){

        String data = loadJSONFromAsset(context, ty);

        if (data == null) return null;

        String category = "";
        String count = "";
        String description = "";
        String reference = "";
        String zekr = "";

        ArrayList<ReadAzkarJson> d = new ArrayList<>();

        JSONArray array = null;
        try {

            array = new JSONArray(data);

            for (int i =0; i < array.length(); i++){

                JSONObject object = array.getJSONObject(i);

                category = object.optString("category");
                count = object.optString("count");
                description = object.optString("description");
                reference = object.optString("reference");
                zekr = object.optString("zekr");

                d.add(new ReadAzkarJson(category, count, description, reference, zekr));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return d;
    }

    private static String loadJSONFromAsset(Context context, int ty) {
        String json;
        String path;
        switch (ty){
            case 1:
                path = "morning.json";
                break;
            case 2:
                path = "afternoon.json";
                break;
            case 3:
                path = "all_day.json";
                break;
            default:
                path = "morning.json";
        }
        try {
            InputStream is = context.getAssets().open(path);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


}
