package com.example.digilocker_1.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.digilocker_1.HelperClasses.ServiceGenerator;
import com.example.digilocker_1.HelperClasses.StringConverter;
import com.example.digilocker_1.R;
import com.example.digilocker_1.RetrofitRequests.InitRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevokeToken implements Callback<Response> {
    private Context context;

    public RevokeToken(Context context) {
        this.context = context;
    }

    public void start(String token, String token_type) throws IOException {
        InitRequest request = new ServiceGenerator(context.getString(R.string.revokeToken)
                                                    , new StringConverter()).createService(InitRequest.class);

        Call<Response> call = request.revokeToken(token, token_type);

        call.execute();
    }

    @Override
    public void onResponse(Call<Response> call, Response<Response> response) {
        if (response.isSuccessful()) {
            if (response.code() == 200) {
                Toast.makeText(context,"Token Revoked Successfully",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {
        t.printStackTrace();
    }
}
