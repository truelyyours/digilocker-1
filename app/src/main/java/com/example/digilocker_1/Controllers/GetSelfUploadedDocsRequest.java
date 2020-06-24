package com.example.digilocker_1.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.digilocker_1.DataClasses.GetSelfUploadedDocsResponse;
import com.example.digilocker_1.HelperClasses.AccessToken;
import com.example.digilocker_1.HelperClasses.ServiceGenerator;
import com.example.digilocker_1.R;
import com.example.digilocker_1.RetrofitRequests.InitRequest;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetSelfUploadedDocsRequest implements Callback<GetSelfUploadedDocsResponse> {
    private Context context;
    private SharedPreferences sp;

    public GetSelfUploadedDocsRequest(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(context.getString(R.string.sharedPreference),Context.MODE_PRIVATE);
    }

    public void start(@Nullable String id) throws IOException {
        InitRequest request = new ServiceGenerator(context.getString(R.string.getSelfUploadedDocs),
                                                    GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                                                    .createService(InitRequest.class);

        String accessToken = new AccessToken(context).getToken();
        Call<GetSelfUploadedDocsResponse> call = request.getSelfUploadedDocs("Bearer " + accessToken, id);

        call.execute();
    }

    @Override
    public void onResponse(Call<GetSelfUploadedDocsResponse> call, Response<GetSelfUploadedDocsResponse> response) {
//        #TODO: Display the directory structure in the activity. RN it just displays the response as is.
        if (response.isSuccessful()) {
            GetSelfUploadedDocsResponse docsResponse = response.body();
//            Display the data in an activity
        }
    }

    @Override
    public void onFailure(Call<GetSelfUploadedDocsResponse> call, Throwable t) {

    }
}
