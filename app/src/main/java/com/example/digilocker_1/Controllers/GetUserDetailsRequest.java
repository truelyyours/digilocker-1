package com.example.digilocker_1.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.digilocker_1.DataClasses.GetUserDetailsResponse;
import com.example.digilocker_1.HelperClasses.AccessToken;
import com.example.digilocker_1.HelperClasses.ServiceGenerator;
import com.example.digilocker_1.R;
import com.example.digilocker_1.RetrofitRequests.InitRequest;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetUserDetailsRequest implements Callback<GetUserDetailsResponse> {
    private Context context;
    private SharedPreferences sp;

    public GetUserDetailsRequest(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(context.getString(R.string.sharedPreference),Context.MODE_PRIVATE);
    }

    public void start() throws IOException {
        InitRequest request = new ServiceGenerator(context.getString(R.string.getUserDetails),
                GsonConverterFactory.create()).createService(InitRequest.class);

        String accessToken = new AccessToken(context).getToken();
        Call<GetUserDetailsResponse> call = request.getUserDetails("Bearer " + accessToken);

        call.execute();
    }


    @Override
    public void onResponse(Call<GetUserDetailsResponse> call, Response<GetUserDetailsResponse> response) {
        if (response.isSuccessful()) {
            GetUserDetailsResponse detailsResponse = response.body();
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("digilockerid",detailsResponse.getDigilockerid());
            editor.putString("name", detailsResponse.getName());
            editor.putString("dob",detailsResponse.getDob());
            editor.putString("gender",detailsResponse.getGender());
            editor.putString("eaadhaar",detailsResponse.getEaadhaar());
            editor.apply();

        }
    }

    @Override
    public void onFailure(Call<GetUserDetailsResponse> call, Throwable t) {
        t.printStackTrace();
    }
}
