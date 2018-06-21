package com.example.shika.boo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Place;

public class Branch_Edit_profile extends AppCompatActivity {
    private Button pick_image;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 0;
    private final int REQUEST_CODE_PLACEPICKER = 1;

    CircleImageView profile_pic;
    EditText braname;
    EditText braphone;
    EditText brapass;
    DatePickerDialog picker;
TextView mkan;
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private String encoded_string;
    MaterialBetterSpinner betterSpinner;
    String[] Gender = {"male", "female"};
    SharedPreferences sharedpreferences;
    RequestQueue requestQueue;
    StringRequest request;
    private String editeprofileURL = "http://gp.sendiancrm.com/offerall/Branch_Edit_Profile.php";
    AlertDialog alertDialog;
    Button submit;
    String name , lat ,lon;
    String email;
    String phone;
    String age;
    String gender;
    String password;
    int userid;
    ImageView imageView;
    double  latitude,longitude  ;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Context c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch__edit_profile);

        braname = (EditText) findViewById(R.id.retitle);
        braphone = (EditText) findViewById(R.id.point);
        brapass = (EditText) findViewById(R.id.ps);
        mkan = (TextView) findViewById(R.id.loc);

        submit = (Button) findViewById(R.id.insertbtn);
        pick_image = (Button) findViewById(R.id.upimage);
        imageView = (ImageView) findViewById(R.id.imageView);
        //    collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        c=this;
        encoded_string="";



        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        //   sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //    userid = sharedpreferences.getInt("idName", 0);
        Intent u = getIntent();
        userid = u.getIntExtra("braid",0);
        String title =     u.getStringExtra("braname");
        String Phone =     u.getStringExtra("braphone");
        String Pass =     u.getStringExtra("brapass");
        String img = u.getStringExtra("braimage");
        String address = u.getStringExtra("bralocation");
        latitude = u.getDoubleExtra("lt",0);
        lat = String.valueOf(latitude);
        longitude = u.getDoubleExtra("ln",0);
        lon = String.valueOf(longitude);


        braname.setText(title);
        mkan.setText(address);
        braphone.setText(Phone);
        brapass.setText(Pass);
        com.bumptech.glide.Glide.with(Branch_Edit_profile.this)
                .load(img)
                .into(imageView);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( validate()) {
                    editporfileback editprofilenew = new editporfileback(c);
                    editprofilenew.execute();
                }
                /*submitediteprofile(name,email,phone,age, gender,encoded_string,sharedpreferences.getInt("Id",0),password);*/
            }
        });


        Button bu = (Button) findViewById(R.id.pickup_btn);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });

        pick_image = (Button) findViewById(R.id.upimage);
        // profile_pic = (CircleImageView) findViewById(R.id.changeprofile_image);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
   /*     if (sharedpreferences.getBoolean("logged in", false)) {
            String profilepicurl = sharedpreferences.getString("profilepicture", null);
            Picasso.get().load(profilepicurl).into(profile_pic);
        }*/
        requestQueue = Volley.newRequestQueue(this);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:

                if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                        && data != null && data.getData() != null) {
                    filePath = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        imageView.setImageBitmap(bitmap2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    new Encode_image().execute();
                }
                break;
            case 1:
                if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK) {
                    displaySelectedPlaceFromPlacePicker(data);
                }
                break;
        }
    }

    private class Encode_image extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
           /* if(bitmap==null){
                bitmap=((BitmapDrawable)profilepic.getDrawable()).getBitmap();
            }*/

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            bitmap.recycle();

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }
    }


    private class editporfileback extends AsyncTask<String, Void, String> {
        Context context;
        ProgressDialog loading;

        editporfileback(Context ctx) {

            context = ctx;
        }
        String name = braname.getText().toString();
        String mobile = braphone.getText().toString();
        String pass = brapass.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Branch_Edit_profile.this, "Editing in progress...", null,true,true);
        }

        protected String doInBackground(String... params) {
            try {


                URL url = new URL(editeprofileURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =  URLEncoder.encode("branch_name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("branch_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(userid), "UTF-8")+ "&"+
                        URLEncoder.encode("branch_phone", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(mobile), "UTF-8")+ "&"+
                        URLEncoder.encode("branch_pass", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pass), "UTF-8")+ "&"
                        + URLEncoder.encode("encoded_string", "UTF-8") + "=" + URLEncoder.encode(encoded_string, "UTF-8")+ "&"
                        + URLEncoder.encode("lati", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lat), "UTF-8")+ "&"
                        + URLEncoder.encode("loni", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lon), "UTF-8");
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
            return null;
        }

        protected void onPostExecute(String result) {
            try {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Edit Success",Toast.LENGTH_LONG).show();
                Intent inoz = new Intent(Branch_Edit_profile.this,Merchant_Branch_Profile.class);
                inoz.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inoz);
                JSONParser parser = new JSONParser();
                if (result.equals("error")) {
                    alertDialog.setMessage("error");

                    alertDialog.setMessage("Edit Successful");
                    //    Intent too = new Intent(context, MapsActivity.class);
                    //      startActivity(too);

                } else {

            /*        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    if (!name.equals(""))
                        editor.putString("Name", name);

                    editor.commit();
                    alertDialog.setMessage("Edit Successful");
                    Intent too = new Intent(context, MapsActivity.class);
                    startActivity(too);
*/

                }
            }
            catch (Exception e){
                Toast.makeText(getApplication(),e.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    }



    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        // this would only work if you have your Google Places API working

        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        com.google.android.gms.location.places.Place placeSelected = PlacePicker.getPlace(data, this);

        // String name = placeSelected.getName().toString();
        String address = placeSelected.getAddress().toString();
        latitude = placeSelected.getLatLng().latitude;
        lat = String.valueOf(latitude);
        longitude = placeSelected.getLatLng().longitude;
        lon = String.valueOf(longitude);

        android.widget.TextView enterCurrentLocation = (android.widget.TextView) findViewById(R.id.loc);
        enterCurrentLocation.setText( address);
    }



    public boolean validate() {
        boolean valid = true;
      //  String phone = Email.getText().toString();
        if(braname.getText().toString().matches("")||braname.length()>32){
            braname.setError("Please Enter Valid Name");
            valid=false;
        }else if(!braname.getText().toString().matches("[a-zA-Z ]+"))
        {
            braname.requestFocus();
            braname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            valid=false;
        }
        if(brapass.getText().toString().matches("")||brapass.length()<8){
            brapass.setError("Enter password At Least 8 CharS ");
            valid=false;
        }

        if(braphone.getText().toString().matches("")){
            braphone.setError("phone is required");
            valid=false;
        }
  /*      if(!isValidPhone(phone)){
            braphone.setError("Phone Number must be 11 numbers");
            valid=false;
        }*/
        if(mkan.getText().toString().matches("")){
            mkan.setError("Selecting Location is required");
            valid=false;
        }
        return valid;
    }

   /* public static boolean isValidPhone(String phone)
    {
        String expression = "\\d{3}\\d{8}";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }
*/
}