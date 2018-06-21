package com.example.shika.boo;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;

import java.util.HashMap;
import java.util.Map;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Merchant_Add_Branch extends AppCompatActivity {

    private final int REQUEST_CODE_PLACEPICKER = 1;
    private int PICK_IMAGE_REQUEST = 0;
private Bitmap bitmap,bitmap2;
    double  latitude,longitude  ;
    ImageView imageView;
    // Creating EditText.
    EditText FirstName, LastName, Email ,Location;
    MaterialBetterSpinner betterSpinner;
    // Creating button;
    Button InsertButton;
    int strSavedMem1, RewardHolder2;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
String image;
    // Create string variable to hold the EditText Value.
    String FirstNameHolder, LastNameHolder, EmailHolder , RewardHolder , lat , lon ;
    String[] Reward = {"UnAvailable","Available"};
    // Creating Progress dialog.
    ProgressDialog progressDialog;
Button buttonChoose;
    // Storing server url into String variable.
    String HttpUrl = "http://gp.sendiancrm.com/offerall/Add_branch.php";
    String  ses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__add__branch);

        // Assigning ID's to EditText.
        FirstName = (EditText) findViewById(R.id.bname);
        LastName = (EditText) findViewById(R.id.bpass);
        Email = (EditText) findViewById(R.id.bphone);
Location = (EditText) findViewById(R.id.loc);
        buttonChoose = (Button) findViewById(R.id.upimage);
        imageView = (ImageView) findViewById(R.id.imageView);

        Location.setKeyListener(null);

        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        // strSavedMem1 = sharedPreferences.getString("Name", "");
        strSavedMem1 = sharedPreferences.getInt("PID",0);
        ses = Integer.toString(strSavedMem1);

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        Button bu = (Button) findViewById(R.id.pickup_btn);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });
      //  bu.setText(Integer.toString(strSavedMem1));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Reward);
        betterSpinner = (MaterialBetterSpinner) findViewById(R.id.sp1);
        betterSpinner.setAdapter(arrayAdapter);

        // Assigning ID's to Button.
        InsertButton = (Button) findViewById(R.id.addbtn);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(Merchant_Add_Branch.this);

        progressDialog = new ProgressDialog(Merchant_Add_Branch.this);

        // Adding click listener to button.
        InsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Showing progress dialog at user registration time.
              //  progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
               // progressDialog.show();

                if(validate())
                {
                    addNewBranch();
                }

                // Calling method to get value from EditText.


            }
        });

    }

    public void addNewBranch(){
        GetValueFromEditText();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
                        Toast.makeText(Merchant_Add_Branch.this, ServerResponse, Toast.LENGTH_LONG).show();
                        Intent inoz = new Intent(Merchant_Add_Branch.this,SecondHome.class);
                        inoz.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(inoz);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Merchant_Add_Branch.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.

                params.put("Branch_name", FirstNameHolder);
                params.put("Branch_Password", LastNameHolder);
                params.put("Branch_phone", EmailHolder);
                params.put("RewardSystemAvailabilty", Integer.toString(RewardHolder2));
                params.put("latitude", lat);
                params.put("longitude", lon);
                params.put("Place_id", ses);
                params.put("Branch_image", image);


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Merchant_Add_Branch.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    // Creating method to get value from EditText.
    public void GetValueFromEditText(){

        FirstNameHolder = FirstName.getText().toString().trim();
        LastNameHolder = LastName.getText().toString().trim();
        EmailHolder = Email.getText().toString().trim();
        RewardHolder =  betterSpinner.getText().toString();
        if(RewardHolder.equals("Available")){
            RewardHolder2=1;
        }else if(RewardHolder.equals("UnAvailable")){
            RewardHolder2=0;
        }
       image = getStringImage(bitmap);

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
        Place placeSelected = PlacePicker.getPlace(data, this);

        // String name = placeSelected.getName().toString();
        String address = placeSelected.getAddress().toString();
        latitude = placeSelected.getLatLng().latitude;
        lat = String.valueOf(latitude);
        longitude = placeSelected.getLatLng().longitude;
        lon = String.valueOf(longitude);

       // android.widget.TextView enterCurrentLocation = (android.widget.TextView) findViewById(R.id.loctex);
       Location.setText( address);
    }





    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Reward Picture"), PICK_IMAGE_REQUEST);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:

                if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                        && data != null && data.getData() != null) {
                    Uri filePath = data.getData();
                    try {
                        //Getting the Bitmap from Gallery
                        Bitmap bit = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        bitmap2 =  Bitmap.createScaledBitmap(bit, 380, 420, true);
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        //Setting the Bitmap to ImageView
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK) {
                    displaySelectedPlaceFromPlacePicker(data);
                }
                break;
        }
    }

    public boolean validate() {
        boolean valid = true;
        String phone = Email.getText().toString();
        if (FirstName.getText().toString().matches("") || FirstName.length() > 32) {
            FirstName.requestFocus();
            FirstName.setError("Please Enter Valid Name");
            valid = false;
        } else if (!FirstName.getText().toString().matches("[a-zA-Z ]+")) {
            FirstName.requestFocus();
            FirstName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            valid = false;
        }
        if (LastName.getText().toString().matches("") || LastName.length() < 8) {
            LastName.requestFocus();
            LastName.setError("Enter password At Least 8 CharS ");
            valid = false;
        }

        if (Email.getText().toString().matches("")) {
            Email.requestFocus();
            Email.setError("phone is required");
            valid = false;
        }
        if (!isValidPhone(phone)) {
            Email.requestFocus();
            Email.setError("Phone Number must be 11 numbers");
            valid = false;
        }
        if (Location.getText().toString().matches("")) {
            Location.requestFocus();
            Location.setError("Selecting Location is required");
            valid = false;
        }
        if (betterSpinner.getText().toString().matches("")){
            final AlertDialog alertDialog = new AlertDialog.Builder(
                    Merchant_Add_Branch.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Rewards Availability");

            // Setting Dialog Message
            alertDialog.setMessage("Kindly specify if this branch has a (reward system) or not !");

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
    } if(imageView.getDrawable() == null){
            final AlertDialog alertDialog = new AlertDialog.Builder(
                    Merchant_Add_Branch.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Branch Image");

            // Setting Dialog Message
            alertDialog.setMessage("your customers wish to provide an Image for your branch");

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

    public static boolean isValidPhone(String phone)
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
}