package com.kala.lisa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Kalaman on 31.01.18.
 */

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextServerIp;
    Button buttonRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextServerIp = (EditText)findViewById(R.id.editTextServerIP);

        buttonRegister = (Button)findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPassword.getText().toString().length() > 0 &&
                        editTextUsername.getText().toString().length() > 0 &&
                            editTextServerIp.getText().toString().length() > 0)
                {
                    String username = editTextUsername.getText().toString();
                    String password = editTextPassword.getText().toString();
                    String serverip = editTextServerIp.getText().toString() + ":5000";
                    Constants.SERVER_IP = serverip;

                    try {
                        JSONObject jsonObject= new FetchJSONObject("http://" + Constants.SERVER_IP + "/user/register/" + username + "/" + password).execute().get();
                        String uid = jsonObject.getString("UID");
                        LoginActivity.preferenceManager.saveSharedString(Constants.KEY_USERID,uid);
                        LoginActivity.preferenceManager.saveSharedString(Constants.KEY_SERVERIP,serverip);
                        Constants.USER_ID = uid;
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this,"Überprüfen Sie Ihre eingaben !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
