package com.mehdi.rosary.AzkarSong;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.mehdi.rosary.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SongsDetail {

    private String name;
    private int id;
    private String audio;

    private SongsDetail(String name, String audio, int id){
        this.name = name;
        this.id = id;
        this.audio = audio;
    }

    public int getId() {
        return id;
    }

    public String getAudio() {
        return audio;
    }

    public String getName() {
        return name;
    }

    private static ArrayList<SongsDetail> getSongsDetail(String json){
        if (json == null) return null;

        ArrayList<SongsDetail> arrayList = new ArrayList<>();
        JSONObject object = null;
        JSONArray array = null;
        int ID = 0;
        String TITLE = null;
        String AUDIO = null;
        try {
            object = new JSONObject(json);
            array = object.getJSONArray("العربية");
            for (int i = 0; i < array.length(); i++){
                JSONObject ob = array.getJSONObject(i);
                ID = ob.optInt("ID");
                TITLE = ob.optString("TITLE");
                AUDIO = ob.getString("AUDIO_URL");

                Log.e("SOOONGS : ", ID + "  | " + TITLE + "  | " + AUDIO);
                arrayList.add(new SongsDetail(TITLE, AUDIO, ID));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    };

    static class AsynSong extends AsyncTaskLoader<ArrayList<SongsDetail>> {

        AsynSong(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public ArrayList<SongsDetail> loadInBackground() {
            URL url = null;
            try {
                url = new URL("https://www.hisnmuslim.com/api/ar/husn_ar.json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (url == null)return null;
            Log.e("SOOONGS : ", url.toString());

            String json = null;
            try {
                json = NetworkUtil.makeHttpReqeust(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (json == null)return null;
            Log.e("SOOONGS : ", json.substring(0, 100).toUpperCase());
            return getSongsDetail(json);
        }
    }


}
