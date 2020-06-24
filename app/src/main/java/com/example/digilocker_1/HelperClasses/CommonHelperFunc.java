package com.example.digilocker_1.HelperClasses;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CommonHelperFunc {

    public static String verifyHmac(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key.getBytes(),"HmacSHA256"));

        return Base64.encodeToString(mac.doFinal(data.getBytes()),Base64.DEFAULT);
    }

//    Bytes to string.
    private String bin2hex(byte[] digest) {
        StringBuilder hex = new StringBuilder(digest.length * 2);
        for (byte b : digest)
            hex.append(String.format("%02x", b & 0xFF));
        return hex.toString();
    }
}