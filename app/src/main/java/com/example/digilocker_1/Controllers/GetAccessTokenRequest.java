package com.example.digilocker_1.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.digilocker_1.DataClasses.GetAccessTokenResponse;
import com.example.digilocker_1.HelperClasses.ServiceGenerator;
import com.example.digilocker_1.MainActivity;
import com.example.digilocker_1.R;
import com.example.digilocker_1.RetrofitRequests.InitRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAccessTokenRequest implements Callback<GetAccessTokenResponse> {
    private Context context;
    private SharedPreferences sp;

    public GetAccessTokenRequest(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(context.getString(R.string.sharedPreference),Context.MODE_PRIVATE);
    }

    public void start() throws IOException {
//        Response is a Json.
        InitRequest request = new ServiceGenerator(context.getString(R.string.getAccessToken),
                GsonConverterFactory.create())
                .createService(InitRequest.class);

//        redirect Uri should be in strings.xml     --      code_verifier will have to be generated in UserLoginRequest and saved in shared preference.
        Call<GetAccessTokenResponse> call = request.getAccessToken(sp.getString("auth_code",""),
                                            "authorization_code",
                                            context.getString(R.string.appId),
                                            context.getString(R.string.secretKey),
                                            context.getString(R.string.redirectUri),
                                            sp.getString("code_challenge",""));

        call.execute();
    }


    @Override
    public void onResponse(Call<GetAccessTokenResponse> call, Response<GetAccessTokenResponse> response) {
//        On successful response. Store userdetails in Shared Preference and OpenMainActivity.
        if (response.isSuccessful()) {
            GetAccessTokenResponse tokenResponse = response.body();
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("digilockerid",tokenResponse.getUser().getDigilockerid());
            editor.putString("name",tokenResponse.getUser().getName());
            editor.putString("dob",tokenResponse.getUser().getDob());
            editor.putString("gender",tokenResponse.getUser().getGender());
            editor.putString("eaadhaar",tokenResponse.getUser().getEaadhaar());
            editor.putString("access_token",tokenResponse.getAccess_token());
            editor.putString("refresh_token",tokenResponse.getRefresh_token());
            editor.putInt("expires_in",tokenResponse.getExpires_in());
            editor.putLong("last_update", Calendar.getInstance().getTimeInMillis()/1000);
            editor.apply();

            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }

    @Override
    public void onFailure(Call<GetAccessTokenResponse> call, Throwable t) {

    }
}
