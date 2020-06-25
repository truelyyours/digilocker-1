package com.example.digilocker_1.Controllers;

import android.content.Context;
import android.util.Log;

import com.example.digilocker_1.DataClasses.FileClass;
import com.example.digilocker_1.HelperClasses.AccessToken;
import com.example.digilocker_1.HelperClasses.CommonHelperFunc;
import com.example.digilocker_1.HelperClasses.ServiceGenerator;
import com.example.digilocker_1.HelperClasses.StringConverter;
import com.example.digilocker_1.R;
import com.example.digilocker_1.RetrofitRequests.InitRequest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFileFromUri implements Callback<Response> {
    private Context context;

    public GetFileFromUri(Context context) {
        this.context = context;
    }

    public void start() throws IOException {
        InitRequest request = new ServiceGenerator(context.getString(R.string.getFileFromUri),
                new StringConverter()).createService(InitRequest.class);

        String access_token = new AccessToken(context).getToken();
        Call<Response> call = request.getFileFromUri("Bearer " + access_token);

        call.execute();
    }

    @Override
    public void onResponse(Call<Response> call, Response<Response> response) {
//        #TODO: I am creating an instance of FileClass DataClass. Need to figure out a way to display the image/pdf as per the mime type of the returned file.
        if (response.isSuccessful()) {
            Response response1 = response.body();

            Headers headers = response.headers();
            FileClass file = new FileClass();
            file.setMime_type(headers.get("Content-Type"));
            file.setSize(headers.get("Content-Length"));
            file.setHmac(headers.get("hmac"));
            try {
                if (file.getHmac().equals(CommonHelperFunc.getHmacinBase64(file.getFile_content(),context.getString(R.string.secretKey)))) {
                    //            Not sure if it should be response1.toString or response1.message()
                    file.setFile_content(response1.toString());
                }
                else {
                    Log.d("HMAC-Mismatch","The Hmac of file mismatched.");
                }
            } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {

    }
}
