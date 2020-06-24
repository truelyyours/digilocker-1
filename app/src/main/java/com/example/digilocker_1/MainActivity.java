package com.example.digilocker_1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digilocker_1.HelperClasses.CommonHelperFunc;
import com.example.digilocker_1.HelperClasses.GetPathFromUri;
import com.example.digilocker_1.XMLClasses.DocDetails;
import com.example.digilocker_1.XMLClasses.GetDocRequestXML;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {
    final public static int SELECT_FILE = 1;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Check for file read permission.
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//            Should we show the explanation;
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this,"Need this to read the files", Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10);
        }

        Button fileBtn = findViewById(R.id.file);
        text = findViewById(R.id.details);
        fileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPdfContent();
            }
        });


//        try {
//            doRequest();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Toast.makeText(getApplicationContext()
                , grantResults[0] == PackageManager.PERMISSION_GRANTED?"Permission grated :)":"Permission denied!! :("
                , Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void displayPdfContent() {
        Intent selFile = new Intent(Intent.ACTION_GET_CONTENT);
        selFile.setType("application/pdf");

        Intent finalIntent = Intent.createChooser(selFile,"Select PDF file");
        startActivityForResult(finalIntent,SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String pdfPath;
        if (requestCode == SELECT_FILE) {
            if (resultCode == -1) {
                pdfPath = GetPathFromUri.getPath(getApplicationContext(),data.getData());
                text.setText(pdfPath);

                File file = new File(pdfPath);
//                TessBaseAPI baseAPI = new TessBaseAPI();
//                Toast.makeText(getApplicationContext(),baseAPI.getInitLanguagesAsString(),Toast.LENGTH_SHORT).show();
//                baseAPI.setImage(file);
//                text.setText(baseAPI.getUTF8Text());

                StringBuilder parsedText = new StringBuilder();
                try {
                    PdfReader reader = new PdfReader(pdfPath);
                    int n = reader.getNumberOfPages();
                    parsedText.append(n).append("\n");
                    for (int i = 0; i < n; i++) {
//                        parsedText.append(PdfTextExtractor.getTextFromPage(reader,i+1).trim().contains("Extracurriculars"));
                        parsedText.append(PdfTextExtractor.getTextFromPage(reader, i + 1).trim()).append('\n');
                    }
                    reader.close();
                    text.setText(parsedText.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//        User PdfRenderer to display pdf
//        Use default image showing library to show image.

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

//    private void doRequest() throws NoSuchAlgorithmException {
//        //  Response from the API will be in JSON
//
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
//
//        String uri = "";
//        String txn = "";
//        String ver = "1.0";
//        String orgId = getString(R.string.orgId);
//        String appId = getString(R.string.appId);
//        String secret_key = getString(R.string.secretKey);
//        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).format(new Date());
//        String text = secret_key + timestamp;
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        md.update( text.getBytes( StandardCharsets.UTF_8 ) );
//        byte[] digest = md.digest();
//        String hash = String.format( "%064x", new BigInteger( 1, digest ) );
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(getString(R.string.authUrl)
//                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .build();
//
//        Serializer serializer = new Persister(new Format(xml));
//        DocDetails docDetail = new DocDetails(uri);
//        GetDocRequestXML req = new GetDocRequestXML(ver,timestamp,txn,orgId,appId,hash,docDetail);
//
//    }
}
