package com.example.digilocker_1.HelperClasses;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class StringConverter extends Converter.Factory {
//    #TODO: Need to implement the below method so that the incoming response can be taken as a String.
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        return super.responseBodyConverter(type, annotations, retrofit);
    }
}
