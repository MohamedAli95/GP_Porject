package com.example.shika.boo;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import models.User;

public class SignIn extends AppCompatActivity {
    Button Signin;
    Button Signup;
    EditText email;
    EditText password;
    User user;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    SharedPreferences sharedpreferences;
    AlertDialog alertDialog;
    ImageView profilepic;
    Button map;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedpreferences.getBoolean("logged in",false)) {
            Intent too = new Intent(this, MapsActivity.class);
            startActivity(too);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = (EditText)findViewById(R.id.useremail);
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        password = (EditText)findViewById(R.id.userpassword);
        Signup = (Button) findViewById(R.id.button_signup);
        Signin = (Button) findViewById(R.id.button_signin);

        alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Login Status");
    }

    public void reg(View v){
        Intent too = new Intent(this,SignUp.class);
        startActivity(too);
    }
    public void login(View v){
        if(validate()) {
            String useremail = email.getText().toString();
            String userpassword = password.getText().toString();
            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, useremail, userpassword);
        }

    }

    public boolean validate() {
        boolean valid = true;
        if(email.getText().equals("")||!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            email.setError("Please Enter Valid Email Adress");
            valid=false;
        }
        if(password.getText().toString().matches("")){
            password.setError("please enter password");
            valid=false;
        }
        return valid;
    }


    public void toforgotpassword(View view) {
        Intent too = new Intent(this,ForgotPassword.class);
        startActivity(too);
    }
}

