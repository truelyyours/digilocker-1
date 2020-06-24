package com.example.digilocker_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.digilocker_1.Controllers.UserLoginRequest;
import com.example.digilocker_1.DataClasses.UserLoginResponse;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    final Button login_btn = findViewById(R.id.login_btn);
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences(getString(R.string.sharedPreference), Context.MODE_PRIVATE);

//        Let the user login. The Digilocker provides a web interface for logging in. That's the only way to let the user login.
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aadhar = findViewById(R.id.aadharNum).toString();
                String passwd = findViewById(R.id.password).toString();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("aadhar",aadhar);
//                We need to encrypt this password.... Or maybe don't save it at all. The user need to login only once  anyways.
                editor.putString("passwd",passwd);
                editor.apply();

                UserLoginRequest request = new UserLoginRequest(getApplicationContext());
                try {
                    request.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Starting the nexxt activity  is in the onResponse method of useLoginRequest.
            }
        });
    }
}
