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
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
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

public class Edit_offer extends AppCompatActivity {
    private Button pick_image;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;
    CircleImageView profile_pic;
    EditText username;
    EditText useremail;
    EditText from , to;
    EditText points;
    EditText userpassword, price;
    DatePickerDialog picker;
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.ENGLISH);

    private Bitmap bitmap;
    private Bitmap bitmap2;
    private String encoded_string;
    MaterialBetterSpinner betterSpinner;
    String[] Gender = {"male", "female"};
    SharedPreferences sharedpreferences;
    RequestQueue requestQueue;
    StringRequest request;
    private String editeprofileURL = "http://gp.sendiancrm.com/offerall/Edit_offer.php";
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
        setContentView(R.layout.activity_edit_offer);

        username = (EditText) findViewById(R.id.name);
        points = (EditText) findViewById(R.id.poins);
        price = (EditText) findViewById(R.id.price);
        submit = (Button) findViewById(R.id.addbtn);
        pick_image = (Button) findViewById(R.id.upimage);
        imageView = (ImageView) findViewById(R.id.imageView);
        //    collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        c=this;
        encoded_string="";


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
                picker = new DatePickerDialog(Edit_offer.this,
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
        String getfromdate = from.getText().toString().trim();
        //    String getfrom[] = getfromdate.split("/");
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int dayt = cldr.get(Calendar.DAY_OF_MONTH);
                int montht = cldr.get(Calendar.MONTH);
                int yeart = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Edit_offer.this,
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




        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        //   sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //    userid = sharedpreferences.getInt("idName", 0);
        Intent u = getIntent();
        userid = u.getIntExtra("offerid",0);
        String title =     u.getStringExtra("offername");
        int p = u.getIntExtra("offerpoints",0);
        String start =     u.getStringExtra("offerfrom");
        String end =     u.getStringExtra("offerto");
        String img = u.getStringExtra("offerimage");
        Double money = u.getDoubleExtra("Price",0);

        username.setText(title);
        price.setText(Double.toString(money));
        from.setText(start);
        to.setText(end);
        points.setText(Integer.toString(p));
        com.bumptech.glide.Glide.with(Edit_offer.this)
                .load(img)
                .into(imageView);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(validate()) {
                        editporfileback editprofilenew = new editporfileback(c);
                        editprofilenew.execute();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
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
        startActivityForResult(Intent.createChooser(intent, "Select offer Picture"), PICK_IMAGE_REQUEST);
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
        ProgressDialog loading;

        editporfileback(Context ctx) {

            context = ctx;
        }
        String name = username.getText().toString();
        String pric = price.getText().toString();
        String poin = points.getText().toString();
        String sdate = from.getText().toString();
        String edate = to.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Edit_offer.this, "Editing in progress...", null,true,true);
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
                String post_data =  URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(userid), "UTF-8")+ "&"+
                        URLEncoder.encode("start_date", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(sdate), "UTF-8")+ "&"+
                        URLEncoder.encode("end_date", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(edate), "UTF-8")+ "&"
                        + URLEncoder.encode("encoded_string", "UTF-8") + "=" + URLEncoder.encode(encoded_string, "UTF-8")+ "&"
                        + URLEncoder.encode("points", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(poin), "UTF-8")+ "&"
                        + URLEncoder.encode("flos", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pric), "UTF-8");
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
            loading.dismiss();
            Toast.makeText(getApplicationContext(),"Edit Success",Toast.LENGTH_LONG).show();
            Intent inoz = new Intent(Edit_offer.this,Merchant_Branch_ManageOffer.class);
            inoz.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(inoz);
            try {

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


    public boolean validate() throws ParseException {
        boolean valid = true;
        String money = price.getText().toString();
        if(username.getText().toString().matches("")||username.length()>32){
            username.setError("Please Enter Valid Name");
            valid=false;
        }else if(!username.getText().toString().matches("[a-zA-Z ]+"))
        {
            username.requestFocus();
            username.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            valid=false;
        }

        if(formatter.parse(to.getText().toString()).before(formatter.parse(from.getText().toString()))){
            // Toast.makeText(Merchant_add_offer.this, "Nooo " , Toast.LENGTH_LONG).show();
            final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(
                    Edit_offer.this).create();

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
            final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(
                    Edit_offer.this).create();

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