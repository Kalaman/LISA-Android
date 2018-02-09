package com.kala.lisa;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import org.json.JSONObject;

import java.net.URL;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kalaman on 16.12.2017.
 */

public class LoginActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    private QRCodeReaderView qrCodeReaderView;
    public static String QRCodePayload = "";
    //private MQTTService mqttService;
    private final int PERMISSION_REQUEST_CODE = 1455;
    public static PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferenceManager = new PreferenceManager(this);

        checkRegistration();

        //Check if permissions are granted => if not request
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }

        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(1000);

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //checkRegistration();
        qrCodeReaderView.startCamera();
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setQRDecodingEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if (!QRCodePayload.equals(text)) {
            Utilities.playBeepSound(this);
            QRCodePayload = text;

            try {
                new AsyncLogin(this).execute(text).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    qrCodeReaderView.startCamera();
                    qrCodeReaderView.setOnQRCodeReadListener(this);
                    qrCodeReaderView.setQRDecodingEnabled(true);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean checkRegistration(){
        String userID = preferenceManager.readSharedString(Constants.KEY_USERID);
        Constants.SERVER_IP = preferenceManager.readSharedString(Constants.KEY_SERVERIP);
        Constants.USER_ID = userID;
        if (userID == null) {
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }


    private class AsyncLogin extends AsyncTask<String,Void,Boolean>
    {
        ProgressDialog progressDialog;
        Context context;

        public AsyncLogin(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Bitte warten ...");
            progressDialog.setMessage("Einkaufswagen wird freigeschaltet");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... session) {
            JSONObject jsonObject = new JSONObject();
            try {
                URL url = new URL("http://" + Constants.SERVER_IP +"/session/unlock/" + session[0] + "/" + Constants.USER_ID);
                Response response;

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                response = client.newCall(request).execute();

                JSONObject jsonResponse =new JSONObject(response.body().string());
                String strResponse = jsonResponse.getString("Info");

                if (strResponse.equalsIgnoreCase("success")) {
                    Constants.SESSION_KEY = session[0];
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            progressDialog.dismiss();
            if (!success) {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Freischalten fehlgeschlagen");
                alertDialog.setMessage("Fehler beim verbinden mit dem Server (" + Constants.SERVER_IP + ")");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                QRCodePayload = "";
                            }
                        });
                alertDialog.show();
            }
        }
    }
}
