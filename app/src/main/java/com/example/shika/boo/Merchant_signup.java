package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;





import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Merchant_signup extends AppCompatActivity {

    private ImageView imageUpload,imageUploadedGane;
    private String encoded_ImageString ;
    private Bitmap bitmap ;

   // private float ratingPlace = (float) 7.5;
   // private int approvePlace = 1 ;
    private EditText ET_email ,ET_password, ET_BrandName ;
    private RequestQueue requestQueue ;
    private StringRequest request ;
    private static String registerPlaceURL = "http://gp.sendiancrm.com/offerall/registerPlace.php";

    private int spinner_position =1  ;
    private MaterialBetterSpinner betterSpinner;

    String[] catogry = {"cloth and fashion", "sports and fitness", "supermarkets", "restaurants"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_signup);

        betterSpinner = (MaterialBetterSpinner) findViewById(R.id.spinnerCat);
        ET_email = (EditText)findViewById(R.id.Pemail);
        ET_password =(EditText)findViewById(R.id.Ppassword);
        ET_BrandName =(EditText)findViewById(R.id.PbrandName);
        imageUpload = (ImageView) findViewById(R.id.PickImage) ;
        imageUploadedGane = (ImageView) findViewById(R.id.showUploadedImage) ;

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,catogry);
        betterSpinner.setAdapter(arrayAdapter);

      /*  betterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_position = (int) parent.getSelectedItemPosition() +1;
                Toast.makeText(getApplicationContext(),""+spinner_position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_uploadImage = new Intent();
                intent_uploadImage.setType("image/*");
                intent_uploadImage.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent_uploadImage,100);

            }
        });



      //  ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, location);
        //MaterialBetterSpinner betterSpinner2 = (MaterialBetterSpinner) findViewById(R.id.sp2);
        //betterSpinner2.setAdapter(arrayAdapter2);

      //  siup = (Button) findViewById(R.id.button_signup);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode== 100 && resultCode == RESULT_OK && data != null )
        {
            Uri uri_path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri_path);
                imageUploadedGane.setImageBitmap(bitmap);
                imageUploadedGane.setVisibility(View.VISIBLE);
                encoded_imageString ();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void encoded_imageString ()
    {
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytes_image = stream.toByteArray();
        encoded_ImageString = Base64.encodeToString(bytes_image,Base64.DEFAULT);
    }




    public void registerPlace(View view) {

        request = new StringRequest(Request.Method.POST,registerPlaceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("success"))
                    {
                        Toast.makeText(getApplicationContext(), ""+jsonObject.get("success"),
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),sign_in_merchant.class);
                        startActivity(intent);
                    }else if (jsonObject.names().get(0).equals("error"))
                    {
                        Toast.makeText(getApplicationContext(), ""+jsonObject.get("error"),
                                Toast.LENGTH_SHORT).show();
                    }else if (jsonObject.names().get(0).equals("faild"))
                    {
                        Toast.makeText(getApplicationContext(), ""+jsonObject.get("faild"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap =new HashMap<>();
                String x1 =ET_email.getText().toString() ;
                String x2 =ET_password.getText().toString() ;
                String x3= ET_BrandName.getText().toString();
                String x4 = ""+spinner_position ;
                String x5 = encoded_ImageString ;
                hashMap.put("email",ET_email.getText().toString());
                hashMap.put("password",ET_password.getText().toString());
                hashMap.put("placeName",ET_BrandName.getText().toString());
                hashMap.put("category_id", ""+spinner_position);
                hashMap.put("encoded_ImageString",encoded_ImageString);

                return  hashMap;
            }
        };

        requestQueue.add(request);
    }



    //public void to_Home(View v) {
    //    Intent next = new Intent(this, MainActivity.class);
    //    startActivity(next);
    //}
}