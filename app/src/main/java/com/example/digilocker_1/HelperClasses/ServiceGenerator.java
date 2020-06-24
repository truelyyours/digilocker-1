package com.example.digilocker_1.HelperClasses;

import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ServiceGenerator {
    private String baseUrl;
    private Converter.Factory converterFactory;
    private boolean doBasicAuth;
    private String username;
    private String password;

    public ServiceGenerator(String url, Converter.Factory factory) {
        this.baseUrl = url;
        this.converterFactory = factory;
    }

    public ServiceGenerator(String baseUrl, Converter.Factory converterFactory, boolean doBasicAuth, String username, String pass) {
        this.baseUrl = baseUrl;
        this.converterFactory = converterFactory;
        this.doBasicAuth = doBasicAuth;
        this.username = username;
        this.password = pass;
    }

    private Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory);

    public <S> S createService(Class<S> serviceClass) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request original = chain.request();

                        if (doBasicAuth) {
                            Request newRequest = original.newBuilder().header("Authorization", Credentials.basic(username,password)).build();
                            return chain.proceed(newRequest);
                        }
                        return chain.proceed(original);
                    }
                }).build();

        Retrofit retrofit = builder.client(client).build();
//      Use OkHttp in case you need logging interceptor and all.
//    private OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        return retrofit.create(serviceClass);
    }
}
