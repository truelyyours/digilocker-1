package com.example.digilocker_1.RetrofitRequests;

import com.example.digilocker_1.DataClasses.GetAccessTokenResponse;
import com.example.digilocker_1.DataClasses.GetIssuedDocsResponse;
import com.example.digilocker_1.DataClasses.GetSelfUploadedDocsResponse;
import com.example.digilocker_1.DataClasses.GetUserDetailsResponse;
import com.example.digilocker_1.DataClasses.UserLoginResponse;
import com.example.digilocker_1.XMLClasses.GetDocRequestXML;

import androidx.annotation.Nullable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InitRequest {

    @GET
    Call<UserLoginResponse> loginUser(@Query("response_type") String response_type,
                                      @Query("client_id") String client_id,
                                      @Query("redirect_uri") String redirect_uri,
                                      @Query("state") String state,
                                      @Query("code_challenge") String code_challenge,
                                      @Query("code_challenge_method") String code_challenge_method);

    @FormUrlEncoded
    @POST
    Call<GetAccessTokenResponse> getAccessToken(@Field("code") String code,
                                                @Field("grant_type") String grant_type,
                                                @Field("client_id") String client_id,
                                                @Field("client_secret") String client_secret,
                                                @Field("redirect_uri") String redirect_uri,
                                                @Field("code_verifier") String code_verifier);

//    Authentication done by HTTP Basic auth. Used okhttp3 library for this.
    @FormUrlEncoded
    @POST
    Call<GetAccessTokenResponse> refreshAccessToken(@Field("refresh_token") String refresh_token,
                                                    @Field("grant_type") String grant_type);

    @GET
    Call<GetUserDetailsResponse> getUserDetails(@Header("Authorization") String token);

    @GET
    Call<GetSelfUploadedDocsResponse> getSelfUploadedDocs(@Header("Authorization") String token,
                                                          @Nullable @Query("id") String id);

    @GET
    Call<GetIssuedDocsResponse> getIssuedDocs(@Header("Authorization") String token);

    @GET
    Call<Response> getFileFromUri(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST
    Call<Response> revokeToken(@Field("token") String token,
                               @Nullable @Field("token_type_hint") String token_type);

    @Headers("Content-Type: application/xml")
    @POST
    Call<GetDocRequestXML> getDocData(@Body RequestBody body);
}
