package com.example.digilocker_1.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.digilocker_1.DataClasses.GetAccessTokenResponse;
import com.example.digilocker_1.DataClasses.GetUserDetailsResponse;
import com.example.digilocker_1.HelperClasses.ServiceGenerator;
import com.example.digilocker_1.R;
import com.example.digilocker_1.RetrofitRequests.InitRequest;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefreshAccessToken implements Callback<GetAccessTokenResponse> {
    private Context context;
    private SharedPreferences sp;

    public RefreshAccessToken(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(context.getString(R.string.sharedPreference),Context.MODE_PRIVATE);
    }

    public void start() throws IOException {
        InitRequest request = new ServiceGenerator(context.getString(R.string.getUserDetails)
                                                    , GsonConverterFactory.create()
                                                    , true
                                                    , context.getString(R.string.appId)
                                                    , context.getString(R.string.secretKey)).createService(InitRequest.class);

        Call<GetAccessTokenResponse> call = request.refreshAccessToken(sp.getString("refresh_token",""), "refresh_token");

        call.execute();
    }

    @Override
    public void onResponse(Call<GetAccessTokenResponse> call, Response<GetAccessTokenResponse> response) {
        if (response.isSuccessful()) {
            GetAccessTokenResponse detailsResponse = response.body();
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("access_token",detailsResponse.getAccess_token());
            editor.putString("refresh_token",detailsResponse.getRefresh_token());
            editor.putInt("expires_in",detailsResponse.getExpires_in());
            editor.putLong("last_update", Calendar.getInstance().getTimeInMillis()/1000);
            editor.putString("digilockerid",detailsResponse.getUser().getDigilockerid());
//            We can ignore updating these details if it makes the app slow.
            editor.putString("name",detailsResponse.getUser().getName());
            editor.putString("dob",detailsResponse.getUser().getDob());
            editor.putString("gender",detailsResponse.getUser().getGender());
            editor.putString("eaadhaar",detailsResponse.getUser().getEaadhaar());
            editor.apply();
        }
    }

    @Override
    public void onFailure(Call<GetAccessTokenResponse> call, Throwable t) {

    }
}
