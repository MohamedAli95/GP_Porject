package com.example.shika.boo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Place;

public class Merchant_Edit_profile extends AppCompatActivity {
    private Button pick_image;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;
    CircleImageView profile_pic;
    EditText placename;
    EditText placeemail;
    EditText placepass;
    DatePickerDialog picker;

    private Bitmap bitmap;
    private Bitmap bitmap2;
    private String encoded_string;
    MaterialBetterSpinner betterSpinner;
    String[] Gender = {"male", "female"};
    SharedPreferences sharedpreferences;
    RequestQueue requestQueue;
    StringRequest request;
    private String editeprofileURL = "http://gp.sendiancrm.com/offerall/Merchant_Edit_Profile.php";
    AlertDialog alertDialog;
    Button submit;
    String name;
    String email;
    String phone;
    String age;
    String gender;
    String password;
    int userid;
    ImageView imageView;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Context c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__edit_profile);

        placename = (EditText) findViewById(R.id.retitle);
        placeemail = (EditText) findViewById(R.id.point);
        placepass = (EditText) findViewById(R.id.ps);
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
        userid = u.getIntExtra("Placeid",0);
        String title =     u.getStringExtra("Placename");
        String Email =     u.getStringExtra("Placeemail");
        String Pass =     u.getStringExtra("Placepass");
        String img = u.getStringExtra("Placeimage");
        placename.setText(title);
        placeemail.setText(Email);
        placepass.setText(Pass);
        com.bumptech.glide.Glide.with(Merchant_Edit_profile.this)
                .load(img)
                .into(imageView);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    editporfileback editprofilenew = new editporfileback(c);
                    editprofilenew.execute();
                }
                /*submitediteprofile(name,email,phone,age, gender,encoded_string,sharedpreferences.getInt("Id",0),password);*/
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
        String name = placename.getText().toString();
        String mail = placeemail.getText().toString();
        String pass = placepass.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Merchant_Edit_profile.this, "Editing in progress...", null,true,true);
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
                String post_data =  URLEncoder.encode("place_name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("place_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(userid), "UTF-8")+ "&"+
                        URLEncoder.encode("place_email", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(mail), "UTF-8")+ "&"+
                        URLEncoder.encode("place_pass", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pass), "UTF-8")+ "&"
                        + URLEncoder.encode("encoded_string", "UTF-8") + "=" + URLEncoder.encode(encoded_string, "UTF-8");
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
                Intent inoz = new Intent(Merchant_Edit_profile.this,Merchant_Profile.class);
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



    public boolean validate() {
        boolean valid = true;
      //  String phone = Email.getText().toString();
        if(placename.getText().toString().matches("")||placename.length()>32){
            placename.setError("Please Enter Valid Name");
            valid=false;
        }else if(!placename.getText().toString().matches("[a-zA-Z ]+"))
        {
            placename.requestFocus();
            placename.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            valid=false;
        }
        if(placepass.getText().toString().matches("")||placepass.length()<8){
           placepass.setError("Enter password At Least 8 CharS ");
            valid=false;
        }


        if(placeemail.getText().equals("")||!android.util.Patterns.EMAIL_ADDRESS.matcher(placeemail.getText()).matches()){
            placeemail.setError("Please Enter Valid Email Adress");
            valid=false;
        }

        if(imageView.getDrawable() == null){
            final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(
                    Merchant_Edit_profile.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Place Image");

            // Setting Dialog Message
            alertDialog.setMessage("your customers wish to provide an Image for your place");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.error);

            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });

            // Showing Alert Message
            alertDialog.show();
            valid = false;
        }

        return valid;
    }


}