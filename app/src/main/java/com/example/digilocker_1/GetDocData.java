package com.example.digilocker_1;

import android.content.res.Resources;
import android.util.Log;

import com.example.digilocker_1.RetrofitRequests.InitRequest;
import com.example.digilocker_1.XMLClasses.DocDetails;
import com.example.digilocker_1.XMLClasses.GetDocRequestXML;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class GetDocData implements Callback<GetDocRequestXML> {

    public void run() throws NoSuchAlgorithmException {

        String uri = "";
        String txn = "";
        String ver = "1.0";
        String orgId = Resources.getSystem().getString(R.string.orgId);
        String appId = Resources.getSystem().getString(R.string.appId);
        String secret_key = Resources.getSystem().getString(R.string.secretKey);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).format(new Date());
        String text = secret_key + timestamp;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update( text.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        String hash = String.format( "%064x", new BigInteger( 1, digest ) );

        DocDetails docDetail = new DocDetails(uri);
        GetDocRequestXML req = new GetDocRequestXML(ver,timestamp,txn,orgId,appId,hash,docDetail);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Resources.getSystem().getString(R.string.requesterAPIUrl))
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml"),"");
        InitRequest initRequest = retrofit.create(InitRequest.class);

        Call<GetDocRequestXML> call = initRequest.getDocData(requestBody);
        call.enqueue(this);



    }

    @Override
    public void onResponse(Call<GetDocRequestXML> call, Response<GetDocRequestXML> response) {
        if (response.isSuccessful()) {
            Log.d("GetDocResponse",response.body().toString());
        }
    }

    @Override
    public void onFailure(Call<GetDocRequestXML> call, Throwable t) {
        t.printStackTrace();
    }
}
