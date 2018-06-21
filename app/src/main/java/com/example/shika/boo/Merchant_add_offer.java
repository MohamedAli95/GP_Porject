package com.example.shika.boo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Merchant_add_offer extends AppCompatActivity   {
    private Button buttonChoose , buttonInsert;
    private ImageView imageView;
    private EditText offerName, offerPoints,from,to,price;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL ="http://gp.sendiancrm.com/offerall/Add_offer.php";
    ProgressDialog progressDialog;
    int strSaved;
    String image;
    Bitmap bitmap2;
    int z;
    // public Map<String, String> par;
    String startDate, endDate;
    DatePickerDialog picker;
    String ses;
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.ENGLISH);
    // private String KEY_IMAGE = “image”;
    //private String KEY_NAME = “name”;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_add_offer);

        offerName = (EditText) findViewById(R.id.name);
        offerPoints = (EditText) findViewById(R.id.poins);
        price = (EditText) findViewById(R.id.price);
        price.setFilters(new InputFilter[]{new InputFilterMinMax("0", "10000")});
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonChoose = (Button) findViewById(R.id.upimage);
        buttonInsert = (Button) findViewById(R.id.addbtn);
        // buttonChoose.setOnClickListener(this);
        //  buttonInsert.setOnClickListener(this);

        from = (EditText) findViewById(R.id.etxt_fromdate);
        from.setInputType(InputType.TYPE_NULL);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Merchant_add_offer.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                from.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(cldr.getTimeInMillis());

                picker.show();
            }
        });

        to = (EditText) findViewById(R.id.etxt_todate);
        to.setInputType(InputType.TYPE_NULL);
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int dayt = cldr.get(Calendar.DAY_OF_MONTH);
                int montht = cldr.get(Calendar.MONTH);
                int yeart = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Merchant_add_offer.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                to.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, yeart, montht, dayt);
                picker.getDatePicker().setMinDate(cldr.getTimeInMillis());

                picker.show();
            }
        });



        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        // strSavedMem1 = sharedPreferences.getString("Name", "");
        strSaved = sharedPreferences.getInt("BId", 0);


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(validate()) {
                        uploadImage();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Inserting your offer","Please wait",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(Merchant_add_offer.this, s , Toast.LENGTH_LONG).show();
                        Intent inoz = new Intent(Merchant_add_offer.this,Merchant_Branch_ManageOffer.class);
                        inoz.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(inoz);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        //Showing toast
                        //    Toast.makeText(RewardTest.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                //Converting Bitmap to String
                 image = getStringImage(bitmap);
                //Getting Image Name
                String name = offerName.getText().toString().trim();
                String points = offerPoints.getText().toString().trim();
                String pric = price.getText().toString().trim();
                 startDate = from.getText().toString().trim();
                 endDate = to.getText().toString().trim();
                ses = Integer.toString(strSaved);

                //Creating parameters
                Map<String,String> params = new HashMap<String, String>();
                //Adding parameters
                params.put("Title", name);
                params.put("points", points);
                params.put("StartDate",startDate);
                params.put("EndDate",endDate);
                params.put("Branch_id", ses);
                params.put("Offer_image", image);
                params.put("Price", pric);

                //returning parameters
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Offer Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
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
    }






    public boolean validate() throws ParseException {
        boolean valid = true;
        String money = price.getText().toString();
        if(offerName.getText().toString().matches("")||offerName.length()>32){
            offerName.setError("Please Enter Valid Name");
            valid=false;
        }else if(!offerName.getText().toString().matches("[a-zA-Z ]+"))
        {
            offerName.requestFocus();
            offerName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            valid=false;
        }

        if(formatter.parse(to.getText().toString()).before(formatter.parse(from.getText().toString()))){
           // Toast.makeText(Merchant_add_offer.this, "Nooo " , Toast.LENGTH_LONG).show();
            final AlertDialog alertDialog = new AlertDialog.Builder(
                    Merchant_add_offer.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Date Validation");

            // Setting Dialog Message
            alertDialog.setMessage("start date must be before end date");

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
            valid=false;
        }

        if(imageView.getDrawable() == null){
            final AlertDialog alertDialog = new AlertDialog.Builder(
                    Merchant_add_offer.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Offer Image");

            // Setting Dialog Message
            alertDialog.setMessage("your customers wish to provide an Image for this offer");

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

        if (price.getText().toString().matches("") || price.length()>4) {
            price.requestFocus();
           price.setError("Enter a valid price");
            valid = false;
        }

      /*  if(image.matches("")){
            Toast.makeText(Merchant_add_offer.this, "no image selected !!" , Toast.LENGTH_LONG).show();

            valid=false;
        }*/
       /* if(formatter.parse(from.getText().toString()).after(formatter.parse(to.getText().toString()))){
          //  Toast.makeText(Merchant_add_offer.this, "Dates is unvalid " , Toast.LENGTH_LONG).show();
            AlertDialog alertDialog = new AlertDialog.Builder(
                    Merchant_add_offer.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Alert Dialog");

            // Setting Dialog Message
            alertDialog.setMessage("Welcome to AndroidHive.info");

            // Setting Icon to Dialog
           // alertDialog.setIcon(R.drawable.tick);

            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            // Showing Alert Message
            alertDialog.show();
            valid=false;
        }*/

        return valid;
    }


    public class InputFilterMinMax implements InputFilter {
        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }


}