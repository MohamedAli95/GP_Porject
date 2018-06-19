package com.example.shika.boo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Locale;

import models.User;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    User user;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    SharedPreferences sharedpreferences;
    String type1="";

    BackgroundWorker(Context ctx) {

        context = ctx;
    }



    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://gp.sendiancrm.com/offerall/login.php";
        String registration_url = "http://gp.sendiancrm.com/offerall/register.php";
        if (type.equals("login")) {
            try {
                type1=type;
                String email = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("register")){
            try {
                type1=type;
                String email = params[4];
                String password = params[3];
                String name = params[1];
                String age = params[2];
                String gender = params[6];
                String phone = params[5];
                String userlastlogin = params[7];
                String profilepic = params[8];

                URL url = new URL(registration_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")+ "&"
                        + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")+ "&"
                        + URLEncoder.encode("user_age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8")+ "&"
                        + URLEncoder.encode("UserLastLogin", "UTF-8") + "=" + URLEncoder.encode(userlastlogin, "UTF-8")+ "&"
                        + URLEncoder.encode("user_phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+ "&"
                        + URLEncoder.encode("user_Gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8")+ "&"
                        + URLEncoder.encode("encoded_string", "UTF-8") + "=" + URLEncoder.encode(profilepic, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();

    }

    @Override
    protected void onPostExecute(String result) {
        try {



        JSONParser parser = new JSONParser();
        if (type1.equals("login")) {
            try {
                if (result.equals("login not success")) {
                    alertDialog.setMessage("Wrong UserName Or Password");
                } else {

                    Object obj = parser.parse(result);
                    JSONArray array = (JSONArray) obj;
                    JSONObject User_json = (JSONObject) array.get(0);


                    user = new User();
                    user.setName((String) User_json.get("UserName"));
                    user.setAge(Integer.parseInt((String) User_json.get("Age")));
                    user.setId(Integer.parseInt((String) User_json.get("UserId")));
                    user.setEmail((String) User_json.get("UserEmail"));
                    user.setPassword((String) User_json.get("UserPassword"));
                    user.setLastlogin(formatter.parse((String) User_json.get("UserLastLogin")));
                    user.setPhone((String) User_json.get("UserPhone"));
                    user.setGender((String) User_json.get("User_Gender"));
                    user.setProfile_Picture((String) User_json.get("Userpicture"));
                    user.setSetting((String) User_json.get("UserSetting"));
                    UserSesionStart(user);
                }

                if (sharedpreferences != null) {
                    alertDialog.setMessage("Welcome" + "\t" + sharedpreferences.getString("Name", "error"));
                    alertDialog.show();
                    AppController.getInstance().ServiceStart();
                    Intent too = new Intent(context, MapsActivity.class);
                    too.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(too);

                } else {
                    alertDialog.setMessage("Wrong UserName Or Password");
                    alertDialog.show();
                }


            } catch (ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();

                e.printStackTrace();
            }


        } else if (type1.equals("register")) {
            if (result.equals("insert succ")) {
                alertDialog.setMessage("Signup Successful");
                Intent too = new Intent(context, SignIn.class);
                context.startActivity(too);
            } else {
                alertDialog.setMessage(result);
            }

            alertDialog.show();
        }
    }
    catch (Exception e){
        Toast.makeText(context,e.getMessage(), Toast.LENGTH_LONG).show();

    }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    public  void UserSesionStart(User user){
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Name",user.getName());
        editor.putInt("Age",user.getAge());
        editor.putInt("Id",user.getId());
        editor.putString("Email",user.getEmail());
        editor.putString("Password",user.getPassword());
        editor.putString("phone",user.getPhone());
        editor.putString("gender",user.getGender());
        editor.putString("LastLogin",formatter.format(user.getLastlogin()));
        editor.putString("profilepicture",user.getProfile_Picture());
        editor.putBoolean("logged in",true);
        editor.putString("Setting",user.getSetting());
        editor.commit();

    }
}