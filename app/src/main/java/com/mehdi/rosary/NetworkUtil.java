package com.mehdi.rosary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class NetworkUtil {

    public static String makeHttpReqeust(URL url) throws IOException {

        String output = "";

        if (url == null){
            return output;
        }

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setDoInput(true);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                output = readStream(inputStream);
            } else {
            }
        } catch (IOException e){

        }
        finally {
            if (urlConnection != null){ urlConnection.disconnect(); }

            if (inputStream != null){
                inputStream.close();
            }
        }
        return output;


    }
    public static String readStream(InputStream in) throws IOException {

        StringBuilder out = new StringBuilder();

        InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();
        while (line != null){
            out.append(line);
            line = bufferedReader.readLine();
        }

        return out.toString();

    }


}
