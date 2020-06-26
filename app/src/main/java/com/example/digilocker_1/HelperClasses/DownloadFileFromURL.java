package com.example.digilocker_1.HelperClasses;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromURL extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//    Show Progress bat before execution....

    }

    @Override
    protected String doInBackground(String... strings) {
        int count;
        try {
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();

//            Download the file.
            InputStream inputStream = new BufferedInputStream(url.openStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
