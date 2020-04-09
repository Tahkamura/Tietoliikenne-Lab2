package com.example.lab1t3;

import android.os.AsyncTask;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncClass extends AsyncTask<String,Integer, String> {
    String htmlText;

    public interface Face{
        void done(String data);
    }

    public void setCallBackInterface(Face callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    Face callBackInterface;

    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            htmlText = Utilities.fromStream(in);
        }catch (Exception e){

        }
        return htmlText;
    }

    @Override
    protected void onPostExecute(String aBoolean) {
        if(callBackInterface != null){
            callBackInterface.done(htmlText);
        }
    }
}
