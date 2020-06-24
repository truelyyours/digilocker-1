package com.example.digilocker_1.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.digilocker_1.DataClasses.GetIssuedDocsResponse;
import com.example.digilocker_1.HelperClasses.AccessToken;
import com.example.digilocker_1.HelperClasses.ServiceGenerator;
import com.example.digilocker_1.R;
import com.example.digilocker_1.RetrofitRequests.InitRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetIssuedDocsRequest implements Callback<GetIssuedDocsResponse> {
    private Context context;
    private SharedPreferences sp;

    public GetIssuedDocsRequest(Context context) {
        this.context = context;
        this.sp = context.getSharedPreferences(context.getString(R.string.sharedPreference),Context.MODE_PRIVATE);
    }

    public void start() throws IOException {
        InitRequest request = new ServiceGenerator(context.getString(R.string.getIssuedDocs),
                                                    GsonConverterFactory.create())
                                                    .createService(InitRequest.class);

        String accessToken = new AccessToken(context).getToken();
        Call<GetIssuedDocsResponse> call = request.getIssuedDocs("Bearer " + accessToken);

        call.execute();
    }

    @Override
    public void onResponse(Call<GetIssuedDocsResponse> call, Response<GetIssuedDocsResponse> response) {
//      TODO: Should display these along with SelfUploadedDocs. RN no implementation is done.
        if (response.isSuccessful()) {
            GetIssuedDocsResponse issuedDocsResponse = response.body();

        }
    }

    @Override
    public void onFailure(Call<GetIssuedDocsResponse> call, Throwable t) {

    }
}
