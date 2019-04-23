package com.example.rockyjain.stackintegration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_CODE;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_DISPLAYNAME;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_PASSWORD;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_USERNAME;

public class RegisterActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mDisplayName;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mRegister = findViewById(R.id.btn_register);
        mUserName = findViewById(R.id.et_username);
        mDisplayName = findViewById(R.id.et_displayname);
        mPassword = findViewById(R.id.et_password);
        mConfirmPassword = findViewById(R.id.et_confirmpassword);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUserName.getText().toString().trim().toLowerCase();
                String dispalyname = mDisplayName.getText().toString().trim();
                String password = mPassword.getText().toString();
                String confirmpassword = mConfirmPassword.getText().toString();
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(dispalyname) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmpassword)){
                    if(password.equals(confirmpassword)){
                        Intent intent = new Intent();
                        intent.putExtra(REQUEST_DISPLAYNAME, dispalyname);
                        intent.putExtra(REQUEST_USERNAME, username);
                        intent.putExtra(REQUEST_PASSWORD, password);
                        setResult(REQUEST_CODE, intent);
                        finish();
                    }else
                        Toast.makeText(RegisterActivity.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(RegisterActivity.this, "No any Optional Attributes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
