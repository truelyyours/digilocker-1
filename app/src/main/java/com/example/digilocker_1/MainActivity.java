package com.example.digilocker_1;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digilocker_1.HelperClasses.CommonHelperFunc;
import com.example.digilocker_1.HelperClasses.DownloadFileFromURL;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {
    final public static int SELECT_FILE = 1;
//    final private static String DATA_PATH = Environment.get;
    TextView text;
    private String DATA_PATH;
    private ProgressBar progressBar;
    private long downloadId;
//    On download complete Receiver!!
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
//                Fetching the download id received with the broadcast
                    long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);

//                If the received id is same as our download id...
                    if (id == downloadId) {
                        Toast.makeText(MainActivity.this, "Download od eng.traineddata Complete", Toast.LENGTH_SHORT).show();
                    }
                }
            };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Read permission granted", Toast.LENGTH_SHORT).show();
                downloadTrainingFile();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Check for file read & internet permission.
        if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||
            getApplicationContext().checkSelfPermission(Manifest.permission.INTERNET) !=  PackageManager.PERMISSION_GRANTED) {
//            Should we show the explanation;
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this,"Need this to read and write the files", Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10);
//            requestPermissions(new String[]{Manifest.permission.INTERNET},12);
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},11);
        } else {
            downloadTrainingFile();
        }

//        Register the broadcast receiver!!
        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        DATA_PATH = getApplicationContext().getExternalFilesDir(null).getPath();

        Button fileBtn = findViewById(R.id.file);
        text = findViewById(R.id.details);
        fileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                This the the function which let's us select a pdf file and on another Override method, the tess-two library is called.
                displayPdfContent();
            }
        });


//        try {
//            doRequest();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

    }

//    This function downloads the training data file into a app specific file location.
    private void downloadTrainingFile() {
        File file = new File(getApplicationContext().getExternalFilesDir(null) + "/tessdata/" + getString(R.string.eng_traineddata_filename));
//        for (String f: file.list())
        Log.d("existanceeee!!",String.valueOf(file.exists()) + "---" + file.getPath());
        if (!file.isFile()) {
            Toast.makeText(MainActivity.this, "Please wait while the training data is downloaded.", Toast.LENGTH_LONG).show();
//        Download the eng.traineddata file in user's mobile.
            new DownloadFileFromURL().execute(getString(R.string.eng_trained_data_url));

            DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(getString(R.string.eng_trained_data_url));

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(getString(R.string.eng_traineddata_filename));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setDescription("Downloading training data for OCR scanning.");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setVisibleInDownloadsUi(false);
            request.setDestinationInExternalFilesDir(MainActivity.this, "tessdata",getString(R.string.eng_traineddata_filename));

            downloadId = downloadmanager.enqueue(request);
        } else {
            Toast.makeText(MainActivity.this, "Training Data file already exist. Great!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayPdfContent() {
        Intent selFile = new Intent(Intent.ACTION_GET_CONTENT);
        selFile.setType("application/pdf");

        Intent finalIntent = Intent.createChooser(selFile,"Select PDF file");
        startActivityForResult(finalIntent,SELECT_FILE);
    }

//    This function reads the pdf file using tesserract's tess-two fork.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String pdfPath;
        if (requestCode == SELECT_FILE) {
            if (resultCode == -1) {
                pdfPath = GetPathFromUri.getPath(getApplicationContext(), data.getData());
                text.setText(pdfPath);

                File file = new File(pdfPath);
                if (!file.exists()) {
                    Toast.makeText(getApplicationContext(), "Unable to access file at pdfPath", Toast.LENGTH_SHORT).show();
                }
                TessBaseAPI baseAPI = new TessBaseAPI();

//                #TODO: init the baseAPI. Here you pass the path to "tessdata" directory. tessdata directory contains all the training files for all the languages
//                Here I have used only "eng" (english). You may initialize it with many other different languages like hindi, guj, kannada, etc etc.
                baseAPI.init(DATA_PATH ,"eng" );
                ArrayList<String> pageText = new ArrayList<>();
//                Here the PDF is converted into a BITMAP image. Irrespective of whether it is a scanned PDF or an actual text pdf, we can convert it into BITMAP images.
//                It returns a list of Bitmap. Each page is converted into a bitmap image.
                ArrayList<Bitmap> pagesInBitmap= CommonHelperFunc.pdfToBitmap(file);
//                Scanning each page for text.
                for (Bitmap bitmap: pagesInBitmap){
                      baseAPI.setImage(bitmap);
//                        baseAPI.getBoxText();
//                        baseAPI.getHOCRText();
//                    baseAPI.getUTF8Text() get's the text in the initialized language(s).
                      pageText.add(baseAPI.getUTF8Text());
                 }
//                #TODO: All the extracted information is in pageText variable. the information is page number wise. Iterate all the pages and get the data.
                text.setText(pageText.get(0));
//                Use the pageText list to extract information.

//                #TODO: This is the implementation of itextPDf tool. It can parse data from SIMPLE PDF documents.
//                We might not need to use this as the data of simple PDF docs is sent as raw text data and we can get information directly from that raw data.
//                StringBuilder parsedText = new StringBuilder();
//                try {
//                    PdfReader reader = new PdfReader(pdfPath);
//                    int n = reader.getNumberOfPages();
//                    parsedText.append(n).append("\n");
//                    for (int i = 0; i < n; i++) {
////                        parsedText.append(PdfTextExtractor.getTextFromPage(reader,i+1).trim().contains("Extracurriculars"));
//                        parsedText.append(PdfTextExtractor.getTextFromPage(reader, i + 1).trim()).append('\n');
//                    }
//                    reader.close();
//                    text.setText(parsedText.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//        User PdfRenderer to display pdf
//        Use default image showing library to show image.

            }
        }
    }
//    #TODO: SimpleXMLConverfactory is also imported in the gradle. It can be used to serve the getAadhaar details in XML and get certi detials in XML APIs.


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Unregister the receiver so it is active only as long as the activity is active.
        unregisterReceiver(onDownloadComplete);
    }
}
