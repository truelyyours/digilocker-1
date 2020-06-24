package com.example.digilocker_1.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.digilocker_1.Controllers.RefreshAccessToken;
import com.example.digilocker_1.R;

import java.io.IOException;
import java.util.Calendar;

public class AccessToken {

    private Context context;
    private SharedPreferences sp;

    public AccessToken(Context context) {
        this.context = context;
        this.sp = context.getSharedPreferences(context.getString(R.string.sharedPreference),Context.MODE_PRIVATE);
    }

    public String getToken() throws IOException {
        String accessToken = sp.getString("access_token","");
        int expiresIn = sp.getInt("expires_in",0);

        if (Calendar.getInstance().getTimeInMillis()/1000 - expiresIn >= sp.getLong("last_update",0)) {
            RefreshAccessToken refreshAccessToken = new RefreshAccessToken(context);
            refreshAccessToken.start();
            accessToken = sp.getString("access_token","");
        }

        return accessToken;
    }
}
