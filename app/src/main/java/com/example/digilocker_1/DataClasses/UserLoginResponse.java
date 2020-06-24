package com.example.digilocker_1.DataClasses;

import androidx.annotation.NonNull;

public class UserLoginResponse {
//    #####    We are using the response_type = code in API call.
    private String code;        // The auth_code
    private String state;       // application specific data passed in original request.

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //    NOTE:: access_token is access_code in the API response. It'll be referred as access_token. It's called access_code here only.
//    private String access_code;
//    private int expires_in;
//    private String token_type;
//    private String scope;

//    public String getAccess_code() {
//        return access_code;
//    }
//
//    public int getExpires_in() {
//        return expires_in;
//    }
//
//    public String getToken_type() {
//        return token_type;
//    }
//
//    public String getScope() {
//        return scope;
//    }
//
//    public void setAccess_code(String access_code) {
//        this.access_code = access_code;
//    }
//
//    public void setExpires_in(int expires_in) {
//        this.expires_in = expires_in;
//    }
//
//    public void setToken_type(String token_type) {
//        this.token_type = token_type;
//    }
//
//    public void setScope(String scope) {
//        this.scope = scope;
//    }


    @NonNull
    @Override
    public String toString() {
        return this.code + "," + this.state;
    }
}
