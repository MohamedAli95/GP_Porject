package com.example.shika.boo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import android.support.v7.app.AlertDialog;
import com.android.volley.Request;

import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;
import android.app.ProgressDialog;
import android.os.Bundle;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

public class Merchant_Add_Reward extends AppCompatActivity {
private int PICK_IMAGE_REQUEST = 1;
  double  latitude,longitude  ;
    // Creating EditText.
    EditText FirstName, LastName, Email ,from, to ;
 MaterialBetterSpinner betterSpinner;
 private Bitmap bitmap;
    // Creating button;
    Button InsertButton;
    int strSavedMem1;
    ImageView imageView;

    RequestQueue requestQueue;
Button buttonChoose;
    // Create string variable to hold the EditText Value.
    String FirstNameHolder, LastNameHolder, EmailHolder , RewardHolder , lat , lon , frdate,todate ;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
public Map<String, String> par;

    DatePickerDialog picker;

    // Storing server url into String variable.
    String HttpUrl = "http://gp.sendiancrm.com/offerall/Add_reward.php";
String  ses;

    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.ENGLISH);

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__add__reward);

        // Assigning ID's to EditText.
        FirstName = (EditText) findViewById(R.id.retitle);
        LastName = (EditText) findViewById(R.id.point);
        buttonChoose = (Button) findViewById(R.id.upimage);
        imageView  = (ImageView) findViewById(R.id.imageView);

        from=(EditText) findViewById(R.id.etxt_fromdate);
        from.setInputType(InputType.TYPE_NULL);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Merchant_Add_Reward.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                from.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        to=(EditText) findViewById(R.id.etxt_todate);
        to.setInputType(InputType.TYPE_NULL);
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int dayt = cldr.get(Calendar.DAY_OF_MONTH);
                int montht = cldr.get(Calendar.MONTH);
                int yeart = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Merchant_Add_Reward.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                to.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, yeart, montht, dayt);
                picker.show();
            }
        });



      //  buttonChoose.setOnClickListener(this);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
            public void onClick(View v) {
            showFileChooser();
                }
             });
         SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
       // strSavedMem1 = sharedPreferences.getString("Name", "");
         strSavedMem1 = sharedPreferences.getInt("Id",0);
                       ses = Integer.toString(strSavedMem1);


        // Assigning ID's to Button.
        InsertButton = (Button) findViewById(R.id.subbtn);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(Merchant_Add_Reward.this);

        progressDialog = new ProgressDialog(Merchant_Add_Reward.this);

        // Adding click listener to button.
        InsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Showing progress dialog at user registration time.
                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();

                // Calling method to get value from EditText.
                GetValueFromEditText();
/*
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
*/
                // Creating string request with post method.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {

                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Showing response message coming from server.
                                Toast.makeText(Merchant_Add_Reward.this, ServerResponse, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Showing error message if something goes wrong.
                                Toast.makeText(Merchant_Add_Reward.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError{
                      // String image = getStringImage(bitmap);
                        // Creating Map String Params.
                        par = new HashMap<String, String>();

                        // Adding All values to Params.

                        par.put("Title", FirstNameHolder);
                        par.put("Points_num", LastNameHolder);
                       par.put("From_date",frdate);
                       par.put("To_date",todate);
                    par.put("Branch_id", ses);

                        return par;
                    }

                };
                     uploadImage();
                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(Merchant_Add_Reward.this);

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);


            }

        });

    }

    // Creating method to get value from EditText.
    public void GetValueFromEditText(){

        FirstNameHolder = FirstName.getText().toString().trim();
        LastNameHolder = LastName.getText().toString().trim();
        frdate = from.getText().toString().trim();
        todate = to.getText().toString().trim();


    }




    /******************* image section **********************/


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

       public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


     private void uploadImage(){
        class UploadImage extends android.os.AsyncTask<Bitmap,Void,String>{

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Merchant_Add_Reward.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override           // de function el upload mohma gdn
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
          //     String vx = Integer.toString(strSavedMem1);
                HashMap<String,String> data = new HashMap<>();

                par.put("Reward_image", uploadImage);
               // data.put("",vx);
                String result = rh.sendPostRequest(HttpUrl,par);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
}
