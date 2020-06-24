package com.example.digilocker_1.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.Toast;

import com.example.digilocker_1.DataClasses.GetAccessTokenResponse;
import com.example.digilocker_1.DataClasses.UserLoginResponse;
import com.example.digilocker_1.HelperClasses.ServiceGenerator;
import com.example.digilocker_1.MainActivity;
import com.example.digilocker_1.R;
import com.example.digilocker_1.RetrofitRequests.InitRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoginRequest implements Callback<UserLoginResponse> {
    private Context context;
    private String code_challenge = "code_challenge";
    public UserLoginRequest(Context con) {
        this.context = con;
    }

    public void start() throws IOException {
//        The response is not actually JSON. It is a query response. But I am writing code for JSON response as IDK what query response is.

        InitRequest request = new ServiceGenerator(Resources.getSystem().getString(R.string.authUrl),
                GsonConverterFactory.create())
                .createService(InitRequest.class);
        Call<UserLoginResponse> call = request.loginUser(context.getString(R.string.responseTypeCode)
                                        , Resources.getSystem().getString(R.string.appId)
                                        , context.getString(R.string.redirectUri)
                                        , ""
                                        , code_challenge
                                        , "S256");

        call.execute();
    }
    @Override
    public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
//        Technically nothing is returned except a query body (I think in the url maybe). IDK the format in which it is returned.
        if (response.isSuccessful()) {
            UserLoginResponse loginResponse = response.body();
            Toast.makeText(context,loginResponse.toString(), Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.sharedPreference),Context.MODE_PRIVATE).edit();
            editor.putString("auth_code",loginResponse.getCode());
            editor.putString("code_challenge",code_challenge);
            editor.apply();

//            Need to call the getAccessTokenApi as well. We don't need to show anything to the user for this call.
            GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest(context);
            try {
                getAccessTokenRequest.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onFailure(Call<UserLoginResponse> call, Throwable t) {
        t.printStackTrace();
    }
}
