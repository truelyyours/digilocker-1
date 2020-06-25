package com.example.digilocker_1.HelperClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.Base64;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Response;

public class CommonHelperFunc {

    public static String getHmacinBase64(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException {
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

    public ArrayList<Bitmap> pdfToBitmap(File pdfFile) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        try {
            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(pdfFile,ParcelFileDescriptor.MODE_READ_ONLY));

            Bitmap bitmap;
            final int pageCount = renderer.getPageCount();
            for( int i = 0; i< pageCount;i++) {
                PdfRenderer.Page page = renderer.openPage(i);

                int width = Resources.getSystem().getDisplayMetrics().densityDpi / 72 * page.getWidth();
                int height = Resources.getSystem().getDisplayMetrics().densityDpi / 72 * page.getHeight();
//                Can use ARGB_4444 if we want to use less memory
                bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

                page.render(bitmap,null,null,PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                bitmaps.add(bitmap);

                page.close();
            }

            renderer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmaps;
    }
}