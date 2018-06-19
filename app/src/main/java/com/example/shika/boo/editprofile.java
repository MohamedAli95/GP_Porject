package com.example.shika.boo;

import android.app.AlertDialog;
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
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Place;

public class editprofile extends AppCompatActivity {
    private ImageButton pick_image;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    CircleImageView profile_pic;
    EditText username;
    EditText useremail;
    EditText userphone;
    EditText userage;
    EditText userpassword;
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private String encoded_string;
    MaterialBetterSpinner betterSpinner;
    String[] Gender = {"male", "female"};
    SharedPreferences sharedpreferences;
    RequestQueue requestQueue;
    StringRequest request;
    private String editeprofileURL = "http://gp.sendiancrm.com/offerall/editprofile.php";
    AlertDialog alertDialog;
    Button submit;
    String name;
    String email;
    String phone;
    String age;
    String gender;
    String password;
    int userid;
    TextView viewname;
    TextView viewemail;
    TextView viewphone;
    TextView viewage;
    TextView viewgender;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Context c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        username = (EditText) findViewById(R.id.changename);
        userage = (EditText) findViewById(R.id.changeage);
        userphone = (EditText) findViewById(R.id.changephone);
        useremail = (EditText) findViewById(R.id.changeemail);
        useremail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        userpassword = (EditText) findViewById(R.id.changepassword);
        alertDialog = new AlertDialog.Builder(this).create();
        submit = (Button) findViewById(R.id.submitedit);
        pick_image = (ImageButton) findViewById(R.id.changeprofilepic);
        viewname=(TextView)findViewById(R.id.nameview);
        viewage=(TextView)findViewById(R.id.ageview);
        viewemail=(TextView)findViewById(R.id.emailview);
        viewgender=(TextView)findViewById(R.id.genderview);
        viewphone=(TextView)findViewById(R.id.phoneview);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        c=this;
        encoded_string="";



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Gender);
        betterSpinner = (MaterialBetterSpinner) findViewById(R.id.sp1);
        betterSpinner.setAdapter(arrayAdapter);
        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        userid = sharedpreferences.getInt("Id", 0);

        viewphone.setText(sharedpreferences.getString("phone","phone"));
        viewname.setText(sharedpreferences.getString("Name","username"));
        viewemail.setText(sharedpreferences.getString("Email","useremail"));
        viewgender.setText(sharedpreferences.getString("gender","usergender"));
        viewage.setText(String.valueOf(sharedpreferences.getInt("Age",0)));
        collapsingToolbarLayout.setTitle(sharedpreferences.getString("Name", "UserName"));


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editporfileback editprofilenew = new editporfileback(c);
                 editprofilenew.execute();

                /*submitediteprofile(name,email,phone,age, gender,encoded_string,sharedpreferences.getInt("Id",0),password);*/
            }
        });


        pick_image = (ImageButton) findViewById(R.id.PickImage);
        profile_pic = (CircleImageView) findViewById(R.id.changeprofile_image);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedpreferences.getBoolean("logged in", false)) {
            String profilepicurl = sharedpreferences.getString("profilepicture", null);
            Picasso.get().load(profilepicurl).into(profile_pic);
        }
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

                Bitmap bit= MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmap =  Bitmap.createScaledBitmap(bit, 380, 420, true);;
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_pic.setImageBitmap(bitmap2);
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
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            bitmap.recycle();

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }
    }

    /* public void submitediteprofile(final String name, final String email,final String phone,final String age,final String gender,final String profilepic,final int userid, final String userpassword){
         request = new StringRequest(Request.Method.POST, editeprofileURL, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 try {
                     JSONObject jsonObject = new JSONObject(response);

                     //if (jsonObject.names().get(0).equals("success")){
                     //Toast.makeText(getApplicationContext(), ""+jsonObject.get("success"),
                     //Toast.LENGTH_LONG).show();else

                     if (jsonObject.names().get(0).equals("error"))
                     {
                         Toast.makeText(getApplicationContext(), ""+jsonObject.get("error"),
                                 Toast.LENGTH_LONG).show();
                     }
                     else{
                         alertDialog.setMessage("Update Successful");
                         alertDialog.show();

                     }



                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(getApplicationContext(), "Something went wrong",Toast.LENGTH_LONG).show();
                 alertDialog.setMessage("error found when connecting to internet " +"\n"+"You must enable you internet connection");
                 alertDialog.show();
                 error.printStackTrace();

             }
         }) {
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 HashMap<String, String> hashMap = new HashMap<>();
                 hashMap.put("email", email);
                 hashMap.put("password", userpassword);
                 hashMap.put("user_id", String.valueOf(userid));
                 hashMap.put("user_name", name);
                 hashMap.put("user_age", age);
                 hashMap.put("user_phone", phone);
                 hashMap.put("user_Gender", gender);
                 hashMap.put("encoded_string", profilepic);

                 return hashMap;
             }

         };
         requestQueue.add(request);

     }*/
    private class editporfileback extends AsyncTask<String, Void, String> {
        Context context;
        editporfileback(Context ctx) {

            context = ctx;
        }
        String name = username.getText().toString();
        String email = useremail.getText().toString();
        String phone = userphone.getText().toString();
        String age = userage.getText().toString();
        String gender = betterSpinner.getText().toString();
        String password = userpassword.getText().toString();
        protected String doInBackground(String... params) {
            try {


                URL url = new URL(editeprofileURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("user_age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8") + "&"
                        + URLEncoder.encode("user_phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&"
                        + URLEncoder.encode("user_Gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") + "&"
                        + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(userid), "UTF-8") + "&"
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

                JSONParser parser = new JSONParser();
                if (result.equals("error")) {
                    alertDialog.setMessage("error");

                    alertDialog.setMessage("Edit Successful");
                    Intent too = new Intent(context, MapsActivity.class);
                    startActivity(too);

                } else {

                    sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    if (!name.equals(""))
                        editor.putString("Name", name);
                    if (!age.equals(""))
                        editor.putInt("Age", Integer.valueOf(age));
                    if (!email.equals(""))
                        editor.putString("Email", email);
                    if (!password.equals(""))
                        editor.putString("Password", password);
                    if (!phone.equals(""))
                        editor.putString("phone", phone);
                    if (!gender.equals(""))
                        editor.putString("gender", gender);
                    if (result != null || !result.equals("error"))
                        editor.putString("profilepicture", result);
                    editor.commit();
                    alertDialog.setMessage("Edit Successful");
                    Intent too = new Intent(context, MapsActivity.class);
                    startActivity(too);


                }
            }
            catch (Exception e){
                Toast.makeText(getApplication(),e.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    }
}
