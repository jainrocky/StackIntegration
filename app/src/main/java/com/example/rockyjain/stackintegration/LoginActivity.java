package com.example.rockyjain.stackintegration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_CODE;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_DISPLAYNAME;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_PASSWORD;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_USERNAME;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassword;
    private Button mButton;
    private TextView mNewUser;
    private StackViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserName = findViewById(R.id.tv_username);
        mPassword = findViewById(R.id.tv_password);
        mButton = findViewById(R.id.btn_login);
        mNewUser = findViewById(R.id.tv_newuser);
        mViewModel = new StackViewModel(getApplication());
        //mViewModel = viewModel;
        mNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REQUEST_CODE);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUserName.getText().toString().trim().toLowerCase();
                String password = mUserName.getText().toString();
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){

                    Users users = mViewModel.getUser();
                    if(users!=null && users.getUserName().equals(username) && !users.isLoggedIn()){
                        users.setLoggedIn(true);
                        mViewModel.createUser(users);
                        startActivity(new Intent(LoginActivity.this, UserInterestActivity.class));
                        finish();
                    }else
                        Toast.makeText(LoginActivity.this, "username or password was not matched", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(LoginActivity.this, "username and password required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            mViewModel.createUser(new Users(data.getStringExtra(REQUEST_DISPLAYNAME), data.getStringExtra(REQUEST_USERNAME), data.getStringExtra(REQUEST_PASSWORD)));
        }
    }
}
